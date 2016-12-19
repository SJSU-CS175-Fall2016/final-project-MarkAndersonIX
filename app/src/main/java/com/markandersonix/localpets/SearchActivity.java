package com.markandersonix.localpets;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//Petfinder class from Breeds package. API does not return consistent data.
import com.markandersonix.localpets.Models.Breeds.Breed;
import com.markandersonix.localpets.Models.Breeds.BreedData;
import com.markandersonix.localpets.Models.Breeds.Petfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.type_spinner) Spinner typeSpinner;
    @BindView(R.id.breed_spinner) Spinner breedSpinner;
    @BindView(R.id.sex_spinner) Spinner sexSpinner;
    @BindView(R.id.size_spinner) Spinner sizeSpinner;
    @BindView(R.id.age_spinner) Spinner ageSpinner;
    @BindView(R.id.zip_edittext) EditText zipEditText;
    @BindView(R.id.search_button) Button searchButton;
    @BindView(R.id.zip_checkbox) CheckBox zipCheckBox;
    @BindString(R.string.url_base) String url_base;
    HashMap<String,String> options;
    ArrayList<String> breeds;
    ArrayAdapter<String> breedsAdapter;
    String zipcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        options = new HashMap<>();
        breeds = new ArrayList<>();
        breeds.add("onCreate()");
        //Array adapter for dynamic breeds spinner
        breedsAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_dropdown_item,breeds);
        breedsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(breedsAdapter);

        //get the user's zipcode using geolocation
        Geocoder gc = new Geocoder(this, Locale.getDefault());
        Location location = MainActivity.getLocation();
        try {
            List<Address> addresses = gc.getFromLocation(location.getLatitude(), location.getLongitude(),1);
            zipcode = addresses.get(0).getPostalCode();
        }catch(Exception ex){
            Log.e("geocoder exception",ex.getMessage());
        }
        if(zipcode != null){
            Toast.makeText(this, zipcode, Toast.LENGTH_LONG).show();
            zipEditText.setText(zipcode);
        }
        //add listener to zipcheckbox
        zipCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zipCheckBox.isChecked()){
                    zipEditText.setEnabled(false);
                    zipEditText.setText(zipcode);
                }else{
                    zipEditText.setEnabled(true);
                    zipEditText.setText("");
                }
            }
        });
        //add itemselectedlistener to typespinner
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getSelectedItem().toString().toLowerCase() != "any") {
                    getBreeds(adapterView.getSelectedItem().toString().toLowerCase());
                    breedsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Process search data
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("typeSpinner:",typeSpinner.getSelectedItem().toString());
            if (zipEditText.getText().toString().length() == 5) {
                if (!typeSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("animal", typeSpinner.getSelectedItem().toString().toLowerCase());
                }
                if (!breedSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("breed", breedSpinner.getSelectedItem().toString().toLowerCase());
                }
                if (!sexSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("sex", sexSpinner.getSelectedItem().toString().toLowerCase());
                }
                if (!sizeSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("size", sizeSpinner.getSelectedItem().toString().toLowerCase());
                }
                if (!ageSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("age", ageSpinner.getSelectedItem().toString().toLowerCase());
                }
                options.put("location", zipEditText.getText().toString());
                Intent resultIntent = new Intent();
                resultIntent.putExtra("options", options); //return map of options to main
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Please enter a valid zipcode.",Toast.LENGTH_LONG).show();
            }
            }
        });

    }//generate breed list for selected animal
    protected void getBreeds(String animal){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url_base)
                    .addConverterFactory(GsonConverterFactory.create()) //add modified factory to handle PetFinder API..
                    .build();
            PetFinderService service = retrofit.create(PetFinderService.class);
            Log.e("getBreeds",animal);
            breedsAdapter.clear();
            breedsAdapter.add("Any");
            Call<BreedData> data = service.getBreeds(animal);
            try {
                data.enqueue(new Callback<BreedData>() {
                    BreedData breedData = null;
                    @Override
                    public void onResponse(Call<BreedData> call, Response<BreedData> response) {
                        breedData = response.body();
                        Log.e("breedData",breedData.toString());
                        try {
                            List<Breed> breedResults = breedData.getPetfinder().getBreeds().getBreed();
                            for (Breed breed : breedResults) {
                                breedsAdapter.add(breed.get$t());
                            }
                        }catch(Exception e){
                            Log.e("onResponse:",e.getMessage());
                            //breeds.clear();
                        }
                    }

                    @Override
                    public void onFailure(Call<BreedData> call, Throwable t) {
                        Log.e("data:",t.getMessage());
                        breeds.clear();
                    }
                });
                Log.e("breeds",breeds.toString());
            }catch(Exception ex){
                Log.e("getBreeds",ex.getMessage());
            }
    }

}
