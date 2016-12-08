package com.markandersonix.localpets.Models.Search;

/**
 * Created by Mark on 12/7/2016.
 */
import android.util.Log;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
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
