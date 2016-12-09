package com.markandersonix.localpets;

import com.markandersonix.localpets.Models.Search.Pet;
import com.markandersonix.localpets.Models.Search.SearchData;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Mark on 10/8/2016.
 */

public interface PetFinderService {
//    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
//    Call<SearchData> getListings(
//            @Query("animal") String type,
//            @Query("breed") String breed,
//            @Query("sex") String sex,
//            @Query("size") String size,
//            @Query("age") String age,
//            @Query("location") String location    );

    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<SearchData> getListings(@QueryMap(encoded = true) Map<String,String> options);

    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<SearchData> getListings(@Query("location") String location);

    @GET("pet.get?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<Pet> getPet(@Query("id") String id);

}
