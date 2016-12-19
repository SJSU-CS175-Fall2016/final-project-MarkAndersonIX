package com.markandersonix.localpets.Models.Get;

/**
 * Created by Mark on 12/7/2016.
 */

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.markandersonix.localpets.Models.Get.Breed;

import java.lang.reflect.Type;

public class BreedsDeserializer implements JsonDeserializer<Breeds>{
    @Override
    public Breeds deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
        JsonElement breed = json.getAsJsonObject().get("breed");
        if(breed.isJsonArray()){
            return new Breeds((Breed[]) context.deserialize(breed.getAsJsonArray(), Breed[].class));
        }else if(breed.isJsonObject()){
            return new Breeds((Breed) context.deserialize(breed.getAsJsonObject(), Breed.class));
        }else {
            throw new JsonParseException("Unsupported type of Breed element");
        }
    }
}
