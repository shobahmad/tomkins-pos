package com.erebor.tomkins.pos.data.field;

import androidx.annotation.NonNull;

import java.util.Date;

public class DateDelivery extends  Date {

    public DateDelivery(long date) {
        super(date);
    }

    public DateDelivery(@NonNull Date date) {
        setTime(date.getTime());
    }
}
