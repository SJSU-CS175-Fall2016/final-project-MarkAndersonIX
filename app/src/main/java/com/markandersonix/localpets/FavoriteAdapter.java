package com.markandersonix.localpets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.markandersonix.localpets.Models.Favorites.Favorite;
import com.markandersonix.localpets.Models.Get.Pet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mark on 10/9/2016.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    private ArrayList<Pet> data;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView ageText;
        public TextView breedText;
        public TextView locationText;
        public ImageView imageView;
        public ViewHolder(View view){
            super(view);
            nameText = (TextView) view.findViewById(R.id.tile_name_txt);
            ageText = (TextView) view.findViewById(R.id.tile_age_txt);
            breedText = (TextView) view.findViewById(R.id.tile_breed_txt);
            locationText = (TextView) view.findViewById(R.id.tile_location_txt);
            imageView = (ImageView) view.findViewById(R.id.tile_img);
        }
    }
    public FavoriteAdapter(Context context, ArrayList<Pet> data){
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
        holder.nameText.setText("Name: " + data.get(position).getName().get$t());
        holder.ageText.setText("Age: " + data.get(position).getAge().get$t());
        holder.breedText.setText("Breed: " + data.get(position).getBreeds().toString());
        holder.locationText.setText("Location: " + data.get(position).getContact().getCity().get$t());
        final Pet pet = data.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pet != null) {
                    Intent intent = new Intent(view.getContext(), PetDetailActivity.class);
                    intent.putExtra("pet", pet); //<<<<Does this work?
                    view.getContext().startActivity(intent);
                }
            }
        });
        Picasso.with(context).load(data.get(position).getMedia().getPhotos().getPhoto().get(2).get$t())
                .resize(200, 200)//holder.imageView.getWidth(), holder.imageView.getHeight())
                .centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public Pet getPhoto(int position) {
        if(position >= 0 && position < data.size()) {
            return data.get(position);
        }else{
            return null;
        }
    }
}
