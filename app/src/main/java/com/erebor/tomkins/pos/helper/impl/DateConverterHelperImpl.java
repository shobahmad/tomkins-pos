package com.erebor.tomkins.pos.helper.impl;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.helper.DateConverterHelper;
import com.erebor.tomkins.pos.helper.ResourceHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConverterHelperImpl implements DateConverterHelper {
    private ResourceHelper resourceHelper;

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATETIME_DISPLAY_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public DateConverterHelperImpl(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    @Override
    public String toDateTimeString(Date date) {
        return toStringFormat(date, DATETIME_FORMAT);
    }

    @Override
    public String toDateTimeDisplayString(Date date) {
        return toStringFormat(date, DATETIME_DISPLAY_FORMAT);
    }

    private String toStringFormat(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        if (date != null)
            return simpleDateFormat.format(date);
        else
            return null;
    }

    @Override
    public String getDifference(Long time) {
        if (time == 0) {
            return "-";
        }
        long curTime = System.currentTimeMillis();
        if (time > curTime) {
            return resourceHelper.getResourceString(R.string.time_elapsed_just_now);
        }
        long difference = System.currentTimeMillis() - time;

        long a_second = 1000;
        long five_second = 5 * 1000;
        long a_minutes = 60 * a_second;
        long a_hour = 60 * a_minutes;
        long three_hour = 3 * a_hour;
        long a_day = 24 * a_hour;

        if (difference <= five_second) {
            return resourceHelper.getResourceString(R.string.time_elapsed_just_now);
        }
        if (difference <= a_minutes) {
            return (difference / a_second) + " " + resourceHelper.getResourceString(R.string.time_elapsed_seconds_ago);
        }
        if (difference <= a_hour) {
            return (difference / a_minutes) + " " + resourceHelper.getResourceString(R.string.time_elapsed_minutes_ago);
        }
        if (difference < a_hour * 2) {
            return resourceHelper.getResourceString(R.string.time_elapsed_a_hour);
        }
        if (difference <= three_hour) {
            return (difference / a_hour) + " " + resourceHelper.getResourceString(R.string.time_elapsed_hours_ago);
        }
        if (difference <= a_day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            return simpleDateFormat.format(calendar.getTime());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-YYYY | HH:mm:ss", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public Calendar atEndOfDay(Calendar calendar) {
        Calendar endDate = (Calendar) calendar.clone();
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);

        return endDate;
    }

}
