package com.markandersonix.localpets.Models.Get;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.markandersonix.localpets.Models.Get.Option;

import java.lang.reflect.Type;

/**
 * Created by Mark on 12/14/2016.
 */

public class OptionsDeserializer implements JsonDeserializer<Options> {
    @Override
    public Options deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
            JsonElement option = json.getAsJsonObject().get("option");
            if(option.isJsonArray()){
            return new Options((Option[]) context.deserialize(option.getAsJsonArray(), Option[].class));
            }else if(option.isJsonObject()){
            return new Options((Option) context.deserialize(option.getAsJsonObject(), Option.class));
            }else {
            throw new JsonParseException("Unsupported type of Option element");
            }
            }
}
