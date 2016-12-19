package com.markandersonix.localpets;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.markandersonix.localpets.Models.Favorites.FavoritesContract;
import com.markandersonix.localpets.Models.Favorites.FavoritesDbHelper;
import com.markandersonix.localpets.Models.Get.Breeds;
import com.markandersonix.localpets.Models.Get.BreedsDeserializer;
import com.markandersonix.localpets.Models.Get.GetData;
import com.markandersonix.localpets.Models.Get.Options;
import com.markandersonix.localpets.Models.Get.OptionsDeserializer;
import com.markandersonix.localpets.Models.Get.Pet;

//No need for deserializers in favorites so far.
//import com.markandersonix.localpets.Models.Get.BreedsDeserializer;
//import com.markandersonix.localpets.Models.Get.OptionsDeserializer;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesActivity extends AppCompatActivity {
    @BindString(R.string.url_base)
    String url_base;
    @BindView(R.id.favorites_recycler_view)
    RecyclerView favoritesRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> ids;
    ArrayList<Pet> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        //String[] projection = {FavoritesContract.FavoriteEntry.COLUMN_NAME_URL};
        ids = new ArrayList<>();
        pets = new ArrayList<>();
        FavoritesDbHelper dbHelper = new FavoritesDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(
                FavoritesContract.FavoriteEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        if (c != null && c.getCount() > 0) {
            do {
                ids.add(c.getString(1));
                getPet(c.getString(1));
            } while (c.moveToNext());
            Toast.makeText(this, ids.toString(),Toast.LENGTH_LONG).show();
            //Toast.makeText(this, pets.size(),Toast.LENGTH_LONG).show();

        } else {
            Log.e("db cursor", "Cursor is null!");
        }

        favoritesRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoritesRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavoriteAdapter(this, pets);
        favoritesRecyclerView.setAdapter(mAdapter);
        //Toast.makeText(this, ids.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_favorites, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_clear_favorites) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.clear_favorites_message);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    FavoritesDbHelper dbHelper = new FavoritesDbHelper(getApplicationContext());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.execSQL(FavoritesDbHelper.getSqlDeleteEntries());
                    db.execSQL(FavoritesDbHelper.getSqlCreateEntries());
                    ids.clear();
                    mAdapter.notifyDataSetChanged();

                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    return;
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getPet(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_base)
                .addConverterFactory(customConverterWithDeserializers()) //add modified factory to handle PetFinder API..
                .build();
        PetFinderService service = retrofit.create(PetFinderService.class);
        Call<GetData> data = service.getPet(id);
        Log.e("getPet id", id);
        try {
            data.enqueue(new Callback<GetData>() {
                Pet pet = null;
                @Override
                public void onResponse(Call<GetData> call, Response<GetData> response) {
                    if(response.isSuccessful() && response.body() != null) {
                        pet = response.body().getPetfinder().getPet();
                        pets.add(response.body().getPetfinder().getPet());
                        Log.e("Pet",pet.getName().get$t());
                        //Log.e("pet", response.body().toString());
                        mAdapter.notifyDataSetChanged();
                    }else{
                        Log.e("FA onReponse", response.message());
                    }
                }
                @Override
                public void onFailure(Call<GetData> call, Throwable t) {
                    Log.e("FA getPet() Callback", t.getMessage());
                }
            });
        } catch (Exception ex) {
            Log.e("FA getPet()", ex.getMessage());
        }
    }
    //creates a ConverterFactory which handles the abiguous case for PetFinder API breed response.
    public static GsonConverterFactory customConverterWithDeserializers(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Breeds.class, new BreedsDeserializer());
        gsonBuilder.registerTypeAdapter(Options.class, new OptionsDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }
}
