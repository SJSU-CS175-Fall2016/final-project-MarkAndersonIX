package com.markandersonix.localpets;

import com.markandersonix.localpets.Models.Breeds.BreedData;
import com.markandersonix.localpets.Models.Get.GetData;
import com.markandersonix.localpets.Models.Search.SearchData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Mark on 10/8/2016.
 */

public interface PetFinderService {

    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<SearchData> getListings(@QueryMap(encoded = true) Map<String,String> options);

    @GET("pet.find?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<SearchData> getListings(@Query("location") String location);

    @GET("pet.get?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<GetData> getPet(@Query("id") String id);

    @GET("breed.list?key=8ed5a6a2883bd0e47fe636efe4b14821&format=json")
    Call<BreedData> getBreeds(@Query("animal") String animal);


}
