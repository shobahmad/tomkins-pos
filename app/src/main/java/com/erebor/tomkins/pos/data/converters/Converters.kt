package com.erebor.tomkins.pos.data.converters

import androidx.room.TypeConverter

import com.erebor.tomkins.pos.data.model.EncryptedAesField
import com.erebor.tomkins.pos.data.model.EncryptedSha1Field

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Converters {
    private val DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss"

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun getEncryptedAes(value: String): EncryptedAesField {
        return EncryptedAesField(value)
    }

    @TypeConverter
    fun fromEncryptedAes(encryptedField: EncryptedAesField): String? {
        return encryptedField.decryptedValue
    }

    @TypeConverter
    fun getEncryptedSha1(value: String): EncryptedSha1Field {
        val encryptedSha1Field = EncryptedSha1Field()
        encryptedSha1Field.value = value
        return encryptedSha1Field
    }

    @TypeConverter
    fun fromEncryptedSha1(encryptedField: EncryptedSha1Field): String? {
        return encryptedField.value
    }

    fun fromString(date: String): Date? {
        val format = SimpleDateFormat(DATETIME_PATTERN)

        try {
            return format.parse(date)
        } catch (e: ParseException) {
            return null
        }

    }

    fun calendarToString(calendar: Calendar?): String? {
        return if (calendar == null)
            null
        else
            SimpleDateFormat(DATETIME_PATTERN, Locale.getDefault()).format(calendar.time)
    }

    fun dateToString(date: Date): String {
        val dateFormat = SimpleDateFormat(DATETIME_PATTERN, Locale.getDefault())
        return dateFormat.format(date)
    }

    fun intToMoneyFormat(value: Int): String {
        val result = String.format(Locale.US, "%,d", value).replace(',', '.')
        return "Rp. $result"
    }
}
