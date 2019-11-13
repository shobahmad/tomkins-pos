package com.erebor.tomkins.pos.data.converters;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import com.erebor.tomkins.pos.data.remote.EncryptedAesField;
import com.erebor.tomkins.pos.data.remote.EncryptedSha1Field;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Converters {
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static EncryptedAesField getEncryptedAes(@NonNull String value) {
        return new EncryptedAesField(value);
    }

    @TypeConverter
    public static String fromEncryptedAes(@NonNull EncryptedAesField encryptedField) {
        return encryptedField.getDecryptedValue();
    }

    @TypeConverter
    public static EncryptedSha1Field getEncryptedSha1(@NonNull String value) {
        EncryptedSha1Field encryptedSha1Field = new EncryptedSha1Field();
        encryptedSha1Field.setValue(value);
        return encryptedSha1Field;
    }

    @TypeConverter
    public static String fromEncryptedSha1(@NonNull EncryptedSha1Field encryptedField) {
        return encryptedField.getValue();
    }

    public static Date fromString(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATETIME_PATTERN);

        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String calendarToString(Calendar calendar) {
        return calendar == null ? null :
                new SimpleDateFormat(DATETIME_PATTERN, Locale.getDefault()).format(calendar.getTime());
    }

    public static String dateToString(@NonNull Date date) {
        DateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN, Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String intToMoneyFormat(int value) {
        String result = String.format(Locale.US, "%,d", value).replace(',', '.');
        return "Rp. " + result;
    }
}
