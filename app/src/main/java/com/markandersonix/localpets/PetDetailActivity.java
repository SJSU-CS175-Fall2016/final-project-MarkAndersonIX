package com.markandersonix.localpets;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.markandersonix.localpets.Models.Favorites.FavoritesContract;
import com.markandersonix.localpets.Models.Favorites.FavoritesDbHelper;
import com.markandersonix.localpets.Models.Search.Pet;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {
    Pet pet;
    Bundle bundle;
    @BindView(R.id.detail_image) ImageView detailImage;
    boolean cropped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        ButterKnife.bind(this);
        bundle = getIntent().getExtras();
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        cropped = false;

        if(bundle != null) {
            pet = (Pet) bundle.getSerializable("pet");
            String large = pet.getMedia().getPhotos().getPhoto().get(2).get$t();
            Picasso.with(this).load(large).centerInside()
                    .resize(size.x,size.y)
                    .into(detailImage);
        }

        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pet != null) {
                    String large = pet.getMedia().getPhotos().getPhoto().get(2).get$t();
                    if(cropped) {
                        Picasso.with(getApplicationContext()).load(large)
                                .resize(size.x, size.y)
                                .centerInside()
                                .into(detailImage);
                        cropped = false;
                    }else{
//                        Toast.makeText(getApplicationContext(), Integer.toString(detailImage.getWidth())+" "
//                                +Integer.toString(detailImage.getHeight()),
//                                Toast.LENGTH_LONG).show();
                        Picasso.with(getApplicationContext()).load(large)
                                .resize(detailImage.getWidth(),detailImage.getHeight())
                                .centerCrop()
                                .into(detailImage);
                        cropped = true;
                    }
            }
        }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_image_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.detail_favorites){
            //get writable db
            FavoritesDbHelper dbHelper = new FavoritesDbHelper(getApplicationContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //create ContentValues to add row to db
            ContentValues values = new ContentValues();
            values.put(FavoritesContract.FavoriteEntry.COLUMN_NAME_URL, pet.getMedia().getPhotos().getPhoto().get(1).get$t());
            Toast.makeText(this, "Url saved to favorites.", Toast.LENGTH_LONG).show();
            //insert, returns primary key
            long newRowId = db.insert(FavoritesContract.FavoriteEntry.TABLE_NAME, null, values);
        }
        return super.onOptionsItemSelected(item);
    }
}
