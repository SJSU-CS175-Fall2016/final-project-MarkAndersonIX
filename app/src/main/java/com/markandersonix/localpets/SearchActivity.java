package com.markandersonix.localpets;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

//Petfinder class from Breeds package. API does not return consistent data.
import com.markandersonix.localpets.Models.Breeds.Breed;
import com.markandersonix.localpets.Models.Breeds.BreedData;
import com.markandersonix.localpets.Models.Breeds.Petfinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @BindString(R.string.url_base) String url_base;
    HashMap<String,String> options;
    ArrayList<String> breeds;
    ArrayAdapter<String> breedsAdapter;

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
                (this,R.layout.support_simple_spinner_dropdown_item,breeds);
        breedsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(breedsAdapter);
        //breedsAdapter.add("New");
        //breedsAdapter.notifyDataSetChanged();
        //Type item click listener to prompt update of breeds list
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
                if(!typeSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("animal", typeSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!breedSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("breed", breedSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!sexSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("sex", sexSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!sizeSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("size", sizeSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!ageSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("age", ageSpinner.getSelectedItem().toString().toLowerCase());
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("options",options); //return map of options to main
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
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
                        breedsAdapter.add("getBreeds()"); //initialize with Any, then add animal breed results.

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
