package com.erebor.tomkins.pos.helper;

import java.util.Calendar;
import java.util.Date;

public interface DateConverterHelper {

    String toDatetring(Date date);

    String toDateTimeString(Date date);

    String toDateTimeDisplayString(Date date);

    String getDifference(Long time);

    Calendar atEndOfDay(Calendar calendar);
}
