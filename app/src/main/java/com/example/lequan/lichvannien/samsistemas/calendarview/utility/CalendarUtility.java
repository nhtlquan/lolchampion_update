package com.example.lequan.lichvannien.samsistemas.calendarview.utility;

import android.content.Context;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtility {
    public static boolean isSameMonth(Calendar c1, Calendar c2) {
        return c1 != null && c2 != null && c1.get(0) == c2.get(0) && c1.get(1) == c2.get(1) && c1.get(2) == c2.get(2);
    }

    public static boolean isToday(Calendar calendar) {
        return isSameDay(calendar, Calendar.getInstance());
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        } else if (cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6)) {
            return true;
        } else {
            return false;
        }
    }

    public static Calendar getTodayCalendar(Context context, int firstDayOfWeek) {
        Calendar currentCalendar = Calendar.getInstance(context.getResources().getConfiguration().locale);
        currentCalendar.setFirstDayOfWeek(firstDayOfWeek);
        return currentCalendar;
    }

    public static int getMonthOffset(Calendar currentCalendar, int firstDayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(firstDayOfWeek);
        calendar.setTime(currentCalendar.getTime());
        calendar.set(5, 1);
        int firstDayWeekPosition = calendar.getFirstDayOfWeek();
        int dayPosition = calendar.get(7);
        if (firstDayWeekPosition == 1) {
            return dayPosition - 1;
        }
        if (dayPosition == 1) {
            return 6;
        }
        return dayPosition - 2;
    }

    public static int getWeekIndex(int weekIndex, Calendar calendar) {
        if (calendar.getFirstDayOfWeek() == 1) {
            return weekIndex;
        }
        if (weekIndex == 1) {
            return 7;
        }
        return weekIndex - 1;
    }

    public static String getCurrentMonth(int monthIndex) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        DateFormatSymbols dateFormat = new DateFormatSymbols(Locale.getDefault());
        calendar.add(2, monthIndex);
        return (calendar.get(2) + 1) + "";
    }
}
