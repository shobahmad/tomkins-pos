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
    private static final String DATETIME_PARAM_FORMAT = "yyyyMMddHHmm";
    private static final String DATE_FORMAT = "dd MMMM yyyy";
    private static final String DATETIME_DISPLAY_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private static final String DATETIME_INFO_FORMAT = "dd-MMM-yyyy | HH:mm:ss";

    public DateConverterHelperImpl(ResourceHelper resourceHelper) {
        this.resourceHelper = resourceHelper;
    }

    @Override
    public String toDatetring(Date date) {
        return toStringFormat(date, DATE_FORMAT);
    }

    @Override
    public String toDateNoBon(Date date) {
        return toStringFormat(date, "yyyymmdd");
    }

    @Override
    public String toDateTimeString(Date date) {
        return toStringFormat(date, DATETIME_FORMAT);
    }

    @Override
    public String toDateTimeStringParameter(Date date) {
        return toStringFormat(date, DATETIME_PARAM_FORMAT);
    }

    @Override
    public String toDateTimeDisplayString(Date date) {
        return toStringFormat(date, DATETIME_INFO_FORMAT);
    }

    @Override
    public String toDateTimeDisplayString(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return toStringFormat(calendar.getTime(), DATETIME_INFO_FORMAT);
    }

    private String toStringFormat(Date date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
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

        Calendar calendar = Calendar.getInstance();

        int nowDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTimeInMillis(time);
        int timeDayOfMOnth = calendar.get(Calendar.DAY_OF_MONTH);

        boolean sameDay = nowDayOfMonth == timeDayOfMOnth;

        if (difference <= five_second && sameDay) {
            return resourceHelper.getResourceString(R.string.time_elapsed_just_now);
        }
        if (difference <= a_minutes && sameDay) {
            return (difference / a_second) + " " + resourceHelper.getResourceString(R.string.time_elapsed_seconds_ago);
        }
        if (difference <= a_hour && sameDay) {
            return (difference / a_minutes) + " " + resourceHelper.getResourceString(R.string.time_elapsed_minutes_ago);
        }
        if (difference < a_hour * 2 && sameDay) {
            return resourceHelper.getResourceString(R.string.time_elapsed_a_hour);
        }
        if (difference <= three_hour && sameDay)  {
            return (difference / a_hour) + " " + resourceHelper.getResourceString(R.string.time_elapsed_hours_ago);
        }
        if (difference <= a_day && sameDay) {
            calendar.setTimeInMillis(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            return simpleDateFormat.format(calendar.getTime());
        }
        if (difference <= a_day) {
            calendar.setTimeInMillis(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            return resourceHelper.getResourceString(R.string.yesterday) + " " + simpleDateFormat.format(calendar.getTime());
        }

        calendar.setTimeInMillis(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy | HH:mm:ss", Locale.getDefault());
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
