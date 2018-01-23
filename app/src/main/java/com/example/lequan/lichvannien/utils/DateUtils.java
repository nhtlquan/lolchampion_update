package com.example.lequan.lichvannien.utils;

import com.example.lequan.lichvannien.model.Alert;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final long[] ARRAY_BEFORE = new long[]{3600, 1800, 900, 300, 0};
    public static final String DATE_FORMAT1 = "yyyyMMdd_HHmmss";
    public static final String DATE_FORMAT2 = "yyyy/MM/dd HH:mm";
    public static final String DATE_FORMAT3 = "dd-MM-yyyy";
    public static final String DATE_FORMAT4 = "dd/MM";
    public static final int NO_REPEAT = 0;
    public static final int REPEAT_A_DAY = 1;
    public static final int REPEAT_A_MONTH = 3;
    public static final int REPEAT_A_WEEK = 2;
    public static final int REPEAT_A_YEAR = 4;
    public static final String TIME_FORMAT1 = "HH:mm";

    public static String convertDateToString(Date date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date convertStringToDate(String date, String format) {
        try {
            return new SimpleDateFormat(format, Locale.getDefault()).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static boolean checkBeforeCurrent(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis() > 0) {
            return true;
        }
        return false;
    }

    public static void increaTime(Calendar nextTime, Alert alert) {
        switch (alert.getFrequency()) {
            case 1:
                nextTime.add(5, alert.getRepeatFrequent());
                return;
            case 2:
                nextTime.add(3, alert.getRepeatFrequent());
                return;
            case 3:
                nextTime.add(2, alert.getRepeatFrequent());
                return;
            case 4:
                nextTime.add(1, alert.getRepeatFrequent());
                return;
            default:
                return;
        }
    }
}
