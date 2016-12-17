package com.markandersonix.localpets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.markandersonix.localpets.Models.Favorites.FavoritesContract;
import com.markandersonix.localpets.Models.Favorites.FavoritesDbHelper;
import com.markandersonix.localpets.Models.Search.Pet;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PetDetailActivity extends AppCompatActivity {
    Pet pet;
    Bundle bundle;
    @BindView(R.id.detail_image) ImageView detailImage;
    @BindView(R.id.detail_name) TextView detailName;
    @BindView(R.id.detail_type) TextView detailType;
    @BindView(R.id.detail_breed) TextView detailBreed;
    @BindView(R.id.detail_sex) TextView detailSex;
    @BindView(R.id.detail_age) TextView detailAge;
    @BindView(R.id.detail_location) TextView detailLocation;
    @BindView(R.id.detail_phone) TextView detailPhone;
    @BindView(R.id.detail_email) TextView detailEmail;
    @BindView(R.id.detail_description) TextView detailDescription;
    @BindView(R.id.detail_email_button) TextView detailEmailButton;
    @BindView(R.id.detail_phone_button) TextView detailPhoneButton;
    @BindView(R.id.detail_directions_button) TextView detailDirectionsButton;

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
        //initialize the information fields using bundle data
        if(bundle != null) {
            pet = (Pet) bundle.getSerializable("pet");
            String large = pet.getMedia().getPhotos().getPhoto().get(2).get$t();
            Picasso.with(this).load(large).centerInside()
                    .resize(size.x,size.y)
                    .into(detailImage);
            detailName.setText("Name: " + pet.getName().get$t());
            detailType.setText("Type: " + pet.getAnimal().get$t());
            detailBreed.setText("Breed: " + pet.getBreeds().toString());
            detailSex.setText(pet.getSex().get$t() == "M"?"Sex: Male":"Sex: Female");
            detailAge.setText("Age: " + pet.getAge().get$t());
            String address = pet.getContact().getAddress1().get$t() != null?
                    pet.getContact().getAddress1().get$t()+", ": " ";
            detailLocation.setText("Address: " + address +
                pet.getContact().getCity().get$t() + " " +
                pet.getContact().getState().get$t());
            String phone = pet.getContact().getPhone().get$t() != null?pet.getContact().getPhone().get$t():"Unlisted";
            detailPhone.setText("Phone: " + phone);
            detailEmail.setText("Email: " + pet.getContact().getEmail().get$t());
            detailDescription.setText("\nAbout: " + pet.getDescription().get$t());
        }
        //add click listener to image to toggle between zoom levels.
        detailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pet.getMedia().getPhotos().getPhoto().get(2).get$t() != null) {
                    String large = pet.getMedia().getPhotos().getPhoto().get(2).get$t();
                    if(cropped) {
                        Picasso.with(getApplicationContext()).load(large)
                                .resize(size.x, size.y)
                                .centerInside()
                                .into(detailImage);
                        cropped = false;
                    }else{
                        Picasso.with(getApplicationContext()).load(large)
                                .resize(detailImage.getWidth(),detailImage.getHeight())
                                .centerCrop()
                                .into(detailImage);
                        cropped = true;
                    }
            }
        }});
        //if email is valid, add click listener
        if(pet.getContact().getEmail().get$t() != null) {
            detailEmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pet.getContact().getEmail().get$t() != null) {
                        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
                        mailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
                        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{pet.getContact().getEmail().get$t()});
                        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "LocalPets App: I'm Interested in adopting a pet!");
                        mailIntent.putExtra(Intent.EXTRA_TEXT,
                                "LocalPets, matching owners with their new pets!\n" +
                                        "Name: " + pet.getName().get$t() + "\n" +
                                        "Type: " + pet.getAnimal().get$t() + "\n");
                        if (mailIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(mailIntent);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Email unavailable.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            detailEmailButton.setBackgroundResource(R.mipmap.ic_email_d);
        }
        //if phone is valid, add click listener
        if(pet.getContact().getPhone().get$t() != null) {
            detailPhoneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pet.getContact().getPhone().get$t() != null) {
                        Uri phoneUri = Uri.parse("tel:" + pet.getContact().getPhone().get$t().replaceAll("[^\\d.]", ""));
                        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, phoneUri);
                        startActivity(phoneIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Phone unavailable.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            detailPhoneButton.setBackgroundResource(R.mipmap.ic_phone_d);
        }
        //If address is valid, add click listener
        if(pet.getContact().getAddress1().get$t() != null && !pet.getContact().getAddress1().get$t().contains("P.O.")) {
            detailDirectionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pet.getContact().getAddress1().get$t() != null) {
                        Uri mapUri = Uri.parse("geo:0,0?q=" + pet.getContact().getAddress1().get$t() + ", " +
                                pet.getContact().getCity().get$t().replace(" ", "+") + ", " + pet.getContact().getState().get$t());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Directions unavailable.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            detailDirectionsButton.setBackgroundResource(R.mipmap.ic_directions_d);
        }

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
            values.put(FavoritesContract.FavoriteEntry.COLUMN_NAME_URL, pet.getId().get$t());
            Toast.makeText(this, "Listing saved to favorites.", Toast.LENGTH_LONG).show();
            //insert, returns primary key
            long newRowId = db.insert(FavoritesContract.FavoriteEntry.TABLE_NAME, null, values);
        }
        return super.onOptionsItemSelected(item);
    }
}
