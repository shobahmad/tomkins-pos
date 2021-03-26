package com.erebor.tomkins.pos.data.converters;

import com.erebor.tomkins.pos.data.field.DateDelivery;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateDeliverySerializer implements JsonDeserializer<DateDelivery>, JsonSerializer<DateDelivery> {

    @Override
    public DateDelivery deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = element.getAsString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            return new DateDelivery(format.parse(date));
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public JsonElement serialize(DateDelivery src, Type typeOfSrc, JsonSerializationContext context) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return new JsonPrimitive(format.format(src));
    }
}
