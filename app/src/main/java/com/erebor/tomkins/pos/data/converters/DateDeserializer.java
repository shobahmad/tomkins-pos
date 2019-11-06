package com.erebor.tomkins.pos.data.converters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
