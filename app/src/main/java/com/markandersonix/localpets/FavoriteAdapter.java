package com.markandersonix.localpets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.markandersonix.localpets.Models.Search.Breeds;
import com.markandersonix.localpets.Models.Search.BreedsDeserializer;
import com.markandersonix.localpets.Models.Search.Pet;
import com.squareup.picasso.Picasso;

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

/**
 * Created by Mark on 10/9/2016.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    private ArrayList<String> data;
    private Context context;
    Pet pet;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView ageText;
        public TextView breedText;
        public TextView locationText;
        public ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            nameText = (TextView) view.findViewById(R.id.tile_name_txt);
            ageText = (TextView) view.findViewById(R.id.tile_age_txt);
            breedText = (TextView) view.findViewById(R.id.tile_breed_txt);
            locationText = (TextView) view.findViewById(R.id.tile_location_txt);
            imageView = (ImageView) view.findViewById(R.id.tile_img);
        }
    }
    public FavoriteAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.tile_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        pet = getPet(data.get(position));
        holder.nameText.setText("Name: " + pet.getName().get$t());
        holder.ageText.setText("Age: " + pet.getAge().get$t());
        holder.breedText.setText("Breed: " + pet.getBreeds().toString());
        holder.locationText.setText("Location: " + pet.getContact().getCity().get$t());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(pet != null) {
                    Intent intent = new Intent(view.getContext(), PetDetailActivity.class);
                    intent.putExtra("pet", pet);
                    view.getContext().startActivity(intent);
                }
            }
        });
        Picasso.with(context).load(pet.getMedia().getPhotos().getPhoto().get(2).get$t())
                .resize(200,200)//holder.imageView.getWidth(), holder.imageView.getHeight())
                .centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public String getUrl(int position) {
        if(position >= 0 && position < data.size()) {
            return data.get(position);
        }else{
            return null;
        }
    }
    protected Pet getPet(String id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.url_base))
                .addConverterFactory(customConverterWithBreedDeserializer()) //add modified factory to handle PetFinder API..
                .build();
        PetFinderService service = retrofit.create(PetFinderService.class);
        Call<Pet> petData = service.getPet(id);
        try {
            petData.enqueue(new Callback<Pet>() {
                @Override
                public void onResponse(Call<Pet> call, Response<Pet> response) {
                    pet = response.body();
                }
                @Override
                public void onFailure(Call<Pet> call, Throwable t) {
                    Log.e("data:",t.getMessage());
                }
            });
        }catch(Exception ex){
            Log.e("Exception:","getPets() Exception");
            return null;
        }
        return pet;
    }
    //creates a ConverterFactory which handles the abiguous case for PetFinder API breed response.
    private GsonConverterFactory customConverterWithBreedDeserializer(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Breeds.class, new BreedsDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }
}
