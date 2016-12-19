package com.markandersonix.localpets.Models.Get;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.markandersonix.localpets.Models.Search.Option;
import com.markandersonix.localpets.Models.Search.Options;

import java.lang.reflect.Type;

/**
 * Created by Mark on 12/14/2016.
 */

public class OptionsDeserializer implements JsonDeserializer<com.markandersonix.localpets.Models.Search.Options> {
    @Override
    public com.markandersonix.localpets.Models.Search.Options deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
            JsonElement option = json.getAsJsonObject().get("option");
            if(option.isJsonArray()){
            return new com.markandersonix.localpets.Models.Search.Options((Option[]) context.deserialize(option.getAsJsonArray(), Option[].class));
            }else if(option.isJsonObject()){
            return new Options((Option) context.deserialize(option.getAsJsonObject(), Option.class));
            }else {
            throw new JsonParseException("Unsupported type of Option element");
            }
            }
}
