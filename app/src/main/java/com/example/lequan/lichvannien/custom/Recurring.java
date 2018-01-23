package com.example.lequan.lichvannien.custom;

import java.util.Calendar;

public class Recurring {
    private static final int DAY_TIME = 86400000;
    public static final int END_FOREVER = 0;
    public static final int END_FOR_EVENT = 2;
    public static final int END_UNTIL_DATE = 1;
    public static final int MONTH_SAME_DAY = 0;
    public static final int MONTH_SAME_WEEKDAY = 1;
    public static final int REPEAT_DAILY = 1;
    public static final int REPEAT_MONTHLY = 3;
    public static final int REPEAT_NONE = 0;
    public static final int REPEAT_WEEKLY = 2;
    public static final int REPEAT_YEARLY = 4;
    private static final int[] WEEKDAY_MASK = new int[]{1, 2, 4, 8, 16, 32, 64};
    private int mEndMode;
    private long mEndSetting;
    private int mPeriod = 1;
    private int mRepeatMode;
    private int mRepeatSetting;
    private long mStartTime;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Recurring.class.getSimpleName()).append("[mode=");
        switch (this.mRepeatMode) {
            case 0:
                sb.append("none");
                break;
            case 1:
                sb.append("daily").append("; period=").append(this.mPeriod);
                break;
            case 2:
                sb.append("weekly").append("; period=").append(this.mPeriod).append("; setting=");
                for (int i = 1; i <= 7; i++) {
                    if (isEnabledWeekday(i)) {
                        sb.append(i);
                    }
                }
                break;
            case 3:
                sb.append("monthly").append("; period=").append(this.mPeriod).append("; setting=").append(getMonthRepeatType() == 0 ? "same_day" : "same_weekday");
                break;
            case 4:
                sb.append("yearly").append("; period=").append(this.mPeriod);
                break;
        }
        if (this.mRepeatMode != 0) {
            switch (this.mEndMode) {
                case 0:
                    sb.append("; end=forever");
                    break;
                case 1:
                    sb.append("; end=until ");
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(this.mEndSetting);
                    sb.append(cal.get(5)).append('/').append(cal.get(2) + 1).append('/').append(cal.get(1));
                    break;
                case 2:
                    sb.append("; end=for ").append(this.mEndSetting).append(" events");
                    break;
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public void setStartTime(long time) {
        this.mStartTime = time;
    }

    public long getStartTime() {
        return this.mStartTime;
    }

    public void setRepeatMode(int mode) {
        this.mRepeatMode = mode;
    }

    public int getRepeatMode() {
        return this.mRepeatMode;
    }

    public void setPeriod(int period) {
        this.mPeriod = period;
    }

    public int getPeriod() {
        return this.mPeriod;
    }

    public int getRepeatSetting() {
        return this.mRepeatSetting;
    }

    public void setRepeatSetting(int setting) {
        this.mRepeatSetting = setting;
    }

    public void clearWeekdaySetting() {
        if (this.mRepeatMode == 2) {
            this.mRepeatSetting = 0;
        }
    }

    public void setEnabledWeekday(int dayOfWeek, boolean enable) {
        if (this.mRepeatMode == 2) {
            if (enable) {
                this.mRepeatSetting |= WEEKDAY_MASK[dayOfWeek - 1];
            } else {
                this.mRepeatSetting &= WEEKDAY_MASK[dayOfWeek - 1] ^ -1;
            }
        }
    }

    public boolean isEnabledWeekday(int weekday) {
        if (this.mRepeatMode == 2 && (this.mRepeatSetting & WEEKDAY_MASK[weekday - 1]) != 0) {
            return true;
        }
        return false;
    }

    public void setMonthRepeatType(int type) {
        if (this.mRepeatMode == 3) {
            this.mRepeatSetting = type;
        }
    }

    public int getMonthRepeatType() {
        return this.mRepeatSetting;
    }

    public void setEndMode(int mode) {
        this.mEndMode = mode;
    }

    public int getEndMode() {
        return this.mEndMode;
    }

    public long getEndSetting() {
        return this.mEndSetting;
    }

    public void setEndSetting(long setting) {
        this.mEndSetting = setting;
    }

    public void setEndDate(long date) {
        if (this.mEndMode == 1) {
            this.mEndSetting = date;
        }
    }

    public long getEndDate() {
        if (this.mEndMode != 1) {
            return 0;
        }
        return this.mEndSetting;
    }

    public void setEventNumber(int number) {
        if (this.mEndMode == 2) {
            this.mEndSetting = (long) number;
        }
    }

    public int getEventNumber() {
        if (this.mEndMode != 2) {
            return 0;
        }
        return (int) this.mEndSetting;
    }

    public long getNextEventTime() {
        return getNextEventTime(System.currentTimeMillis());
    }

    public long getNextEventTime(long now) {
        if (this.mStartTime >= now) {
            return this.mStartTime;
        }
        Calendar cal = Calendar.getInstance();
        switch (this.mRepeatMode) {
            case 1:
                return getNextDailyEventTime(cal, this.mStartTime, now);
            case 2:
                return getNextWeeklyEventTime(cal, this.mStartTime, now);
            case 3:
                return getNextMonthlyEventTime(cal, this.mStartTime, now);
            case 4:
                return getNextYearlyEventTime(cal, this.mStartTime, now);
            default:
                return 0;
        }
    }

    private long getNextDailyEventTime(Calendar cal, long start, long now) {
        long period = (long) (this.mPeriod * DAY_TIME);
        long time = start + (((now - start) / period) * period);
        while (time < now) {
            time += period;
        }
        return time;
    }

    private static long gotoFirstDayOfWeek(Calendar cal) {
        int dayOfWeek = cal.get(7);
        int firstDayOfWeek = cal.getFirstDayOfWeek();
        cal.add(5, -(dayOfWeek >= firstDayOfWeek ? dayOfWeek - firstDayOfWeek : (dayOfWeek + 7) - firstDayOfWeek));
        return cal.getTimeInMillis();
    }

    private long getNextWeeklyEventTime(Calendar cal, long start, long now) {
        if (this.mRepeatSetting == 0) {
            return 0;
        }
        long time;
        if (this.mRepeatSetting == 127 && this.mPeriod == 1) {
            time = start + (((now - start) / 86400000) * 86400000);
            while (time < now) {
                time += 86400000;
            }
            return time;
        }
        long period = (long) ((this.mPeriod * 7) * DAY_TIME);
        cal.setTimeInMillis(now);
        long nowFirstDayTime = gotoFirstDayOfWeek(cal);
        cal.setTimeInMillis(start);
        long startFirstDayTime = gotoFirstDayOfWeek(cal);
        time = startFirstDayTime + (((nowFirstDayTime - startFirstDayTime) / period) * period);
        while (true) {
            cal.setTimeInMillis(time);
            int dayOfWeek = cal.get(7);
            for (int i = 0; i < 7; i++) {
                int nextDayOfWeek = dayOfWeek + i;
                if (nextDayOfWeek > 7) {
                    nextDayOfWeek = 1;
                }
                if (isEnabledWeekday(nextDayOfWeek)) {
                    long nextTime = time + ((long) (DAY_TIME * i));
                    if (nextTime >= now) {
                        return nextTime;
                    }
                }
            }
            time += period;
        }
    }

    public static int getWeekDayOrderNum(Calendar cal) {
        return cal.get(5) + 7 > cal.getActualMaximum(5) ? -1 : (cal.get(5) - 1) / 7;
    }

    private static int getDay(Calendar cal, int dayOfWeek, int orderNum) {
        int day = cal.getActualMaximum(5);
        cal.set(5, day);
        int lastWeekday = cal.get(7);
        day -= lastWeekday >= dayOfWeek ? lastWeekday - dayOfWeek : (lastWeekday + 7) - dayOfWeek;
        if (orderNum < 0) {
            return day;
        }
        cal.set(5, day);
        int lastOrderNum = (cal.get(5) - 1) / 7;
        return orderNum < lastOrderNum ? day - ((lastOrderNum - orderNum) * 7) : day;
    }

    private long getNextMonthlyEventTime(Calendar cal, long start, long now) {
        int nowMonthYear;
        int startMonthYear;
        int monthYear;
        if (this.mRepeatSetting == 0) {
            cal.setTimeInMillis(now);
            nowMonthYear = cal.get(2) + (cal.get(1) * 12);
            cal.setTimeInMillis(start);
            startMonthYear = cal.get(2) + (cal.get(1) * 12);
            int startDay = cal.get(5);
            monthYear = startMonthYear + (((nowMonthYear - startMonthYear) / this.mPeriod) * this.mPeriod);
            while (true) {
                cal.set(5, 1);
                cal.set(1, monthYear / 12);
                cal.set(2, monthYear % 12);
                cal.set(5, Math.min(startDay, cal.getActualMaximum(5)));
                if (cal.getTimeInMillis() >= now) {
                    return cal.getTimeInMillis();
                }
                monthYear += this.mPeriod;
            }
        } else {
            cal.setTimeInMillis(now);
            nowMonthYear = cal.get(2) + (cal.get(1) * 12);
            cal.setTimeInMillis(start);
            startMonthYear = cal.get(2) + (cal.get(1) * 12);
            int dayOfWeek = cal.get(7);
            int orderNum = getWeekDayOrderNum(cal);
            monthYear = startMonthYear + (((nowMonthYear - startMonthYear) / this.mPeriod) * this.mPeriod);
            while (true) {
                cal.set(5, 1);
                cal.set(1, monthYear / 12);
                cal.set(2, monthYear % 12);
                cal.set(5, getDay(cal, dayOfWeek, orderNum));
                if (cal.getTimeInMillis() >= now) {
                    return cal.getTimeInMillis();
                }
                monthYear += this.mPeriod;
            }
        }
    }

    private long getNextYearlyEventTime(Calendar cal, long start, long now) {
        cal.setTimeInMillis(now);
        int nowYear = cal.get(1);
        cal.setTimeInMillis(start);
        int startYear = cal.get(1);
        int year = startYear + (((nowYear - startYear) / this.mPeriod) * this.mPeriod);
        while (true) {
            cal.setTimeInMillis(start);
            cal.set(1, year);
            if (cal.getTimeInMillis() >= now) {
                return cal.getTimeInMillis();
            }
            year += this.mPeriod;
        }
    }
}
