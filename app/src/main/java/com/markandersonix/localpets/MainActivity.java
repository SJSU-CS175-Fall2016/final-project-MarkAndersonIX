package com.markandersonix.localpets;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.markandersonix.localpets.Models.Favorites.FavoritesDbHelper;
import com.markandersonix.localpets.Models.Search.Breed;
import com.markandersonix.localpets.Models.Search.Breeds;
import com.markandersonix.localpets.Models.Search.BreedsDeserializer;
import com.markandersonix.localpets.Models.Search.Options;
import com.markandersonix.localpets.Models.Search.OptionsDeserializer;
import com.markandersonix.localpets.Models.Search.Pet;
import com.markandersonix.localpets.Models.Search.SearchData;

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

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.pet_recycler_view) RecyclerView petRecyclerView;
    @BindString(R.string.application_id) String application_id;
    @BindString(R.string.url_base) String url_base;
    @BindView(R.id.main_status) TextView mainStatus;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Pet> pets;
    HashMap<String,String> nullmap;
    final int SEARCH_REQUEST = 1;
    int randPageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        pets = new ArrayList<>();
        nullmap = new HashMap<>(); //pass to getListings for zero option request
        nullmap.put("location","95117");
        if(savedInstanceState != null && savedInstanceState.containsKey("pets")){
            pets.addAll( (ArrayList) savedInstanceState.getSerializable("pets"));
            attachRecyclerAdapter();
        }else {
            getPets(nullmap);
        }
        petRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        petRecyclerView.setLayoutManager(mLayoutManager);

        //load favorites database
        SQLiteDatabase db = new FavoritesDbHelper(this).getWritableDatabase();
        //db.execSQL(FavoritesDbHelper.getSqlDeleteEntries());
        db.execSQL(FavoritesDbHelper.getSqlCreateEntries());
    }
    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        if(Intent.ACTION_SEARCH.equals(intent.getAction()) &&
                intent.hasExtra("query")) {
            getPets(nullmap); //intent.getStringExtra("query"), 1);
        }else {
            getPets(nullmap);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SEARCH_REQUEST){
            if(resultCode == Activity.RESULT_OK){
                HashMap<String,String> options = (HashMap<String,String>)data.getExtras().getSerializable("options");
                Log.e("onActivityResult:",options.values().toString());
                getPets(options);
            }
        }
    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Menu handling
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.main_favorites){
            //Toast.makeText(this,"FavoritesActivity",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, FavoritesActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.main_search){
            //Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
            //onSearchRequested();
            Intent intent = new Intent(this, SearchActivity.class);
            startActivityForResult(intent, SEARCH_REQUEST);
        }
        if(item.getItemId() == R.id.main_random){
            randPageNumber++;
            //Toast.makeText(this, "Page: "+randPageNumber, Toast.LENGTH_SHORT).show();
            if(!getPets(nullmap)){
                randPageNumber = 1;
                getPets(nullmap);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("pets", pets);
    }

    protected boolean getPets(HashMap<String,String> options){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_base)
                .addConverterFactory(customConverterWithDeserializers()) //add modified factory to handle PetFinder API..
                .build();
        PetFinderService service = retrofit.create(PetFinderService.class);
        //location is required, default to San Jose zipcode.
        if(!options.containsKey("location")){
            options.put("location","95117");
        }
        Log.e("options:",options.values().toString());
        //asynchronous call
        Call<SearchData> data = service.getListings(options);
        boolean success = true;
        try {
            data.enqueue(new Callback<SearchData>() {
                SearchData searchData = null;
                @Override
                public void onResponse(Call<SearchData> call, Response<SearchData> response) {
                    pets.clear();
                    searchData = response.body();
                    Log.e("searchdata",searchData.toString());
                    try {
                        List<Pet> petResults = searchData.getPetfinder().getPets().getPet();
                        for (Pet petResult : petResults) {
                            pets.add(petResult);
                            Log.e("Pet: ", petResult.getName().get$t());
                        }
                        for (Pet p : pets) {
                            Log.e("Pet: ", p.getName().get$t());
                        }
                        mAdapter = new PetAdapter(getApplicationContext(), pets);
                        petRecyclerView.setAdapter(mAdapter);
                        mainStatus.setVisibility(View.GONE);
                        mainStatus.setText("");
                    }catch(Exception e){
                        Log.e("onResponse:",e.getMessage());
                        clearData();
                    }
                }

                @Override
                public void onFailure(Call<SearchData> call, Throwable t) {
                    Log.e("data:",t.getMessage());
                    clearData();
                }
                //clear data if an error occurred
                public void clearData(){
                    pets.clear();
                    mainStatus.setVisibility(View.VISIBLE);
                    mainStatus.setText("No Results.");
                    mAdapter = new PetAdapter(getApplicationContext(), pets);
                    petRecyclerView.setAdapter(mAdapter);
                }
            });
        }catch(Exception ex){
            Log.e("Exception:","getPets() Exception");
            success = false;
        }
        return success;
    }
    //creates a ConverterFactory which handles the abiguous case for PetFinder API breed response.
    public static GsonConverterFactory customConverterWithDeserializers(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Breeds.class, new BreedsDeserializer());
        gsonBuilder.registerTypeAdapter(Options.class, new OptionsDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }
    protected void attachRecyclerAdapter(){
        petRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        petRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new PetAdapter(getApplicationContext(), pets);
        petRecyclerView.setAdapter(mAdapter);
    }

}
