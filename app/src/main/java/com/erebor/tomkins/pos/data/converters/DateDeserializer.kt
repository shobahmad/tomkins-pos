package com.erebor.tomkins.pos.data.converters

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException

import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateDeserializer : JsonDeserializer<Date> {

    @Throws(JsonParseException::class)
    override fun deserialize(element: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {
        val date = element.asString

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        try {
            return format.parse(date)
        } catch (e: ParseException) {
            return null
        }

    }
}
