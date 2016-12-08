package com.markandersonix.localpets;

import com.markandersonix.localpets.Models.Search.Pet;
import com.markandersonix.localpets.Models.Search.SearchData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Mark on 10/8/2016.
 */

public interface PetFinderService {
    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json&animal=cat")
    Call<SearchData> getListings(@Query("location") String location);
//    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
//    Call<SearchData> getListings(@Query("location") String location);

    @GET("pet.get?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<Pet> getPet(@Query("id") String location);

    @GET("search/pets/?")
    Call<SearchData> searchPhotos(@Query("client_id") String client_id, @Query("query") String query, @Query("page") int page);

    //@GET("search/")

}
