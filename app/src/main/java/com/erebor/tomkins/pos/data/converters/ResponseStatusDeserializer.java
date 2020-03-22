package com.erebor.tomkins.pos.data.converters;

import com.erebor.tomkins.pos.data.remote.response.ResponseStatusConstant;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ResponseStatusDeserializer implements JsonDeserializer<ResponseStatusConstant> {

    @Override
    public ResponseStatusConstant deserialize(JsonElement json,
                                      Type typeOfT, JsonDeserializationContext ctx)
            throws JsonParseException {
        int typeInt = json.getAsInt();

        return getRecordStatus(typeInt);
    }

    private ResponseStatusConstant getRecordStatus(int num) {
        for (ResponseStatusConstant recordStatus : ResponseStatusConstant.values()) {
            if (recordStatus.getValue() == num)
                return recordStatus;
        }
        throw new InvalidSerializationException("ResponseStatusConstant");
    }

}
