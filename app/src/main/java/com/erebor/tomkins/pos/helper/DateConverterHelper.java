package com.erebor.tomkins.pos.helper;

import java.util.Calendar;
import java.util.Date;

public interface DateConverterHelper {

    String toDatetring(Date date);

    String toDateNoBon(Date date);

    String toDateTimeString(Date date);

    String toTimeString(Date date);

    String toDateTimeStringParameter(Date date);

    String toDateTimeDisplayString(Date date);

    String toDateTimeDisplayString(Long time);

    String getDifference(Long time);

    Calendar atEndOfDay(Calendar calendar);
}
