package com.example.lequan.lichvannien.alert;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat.Builder;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.base.utils.Log;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BroadcastReceiverAlarm extends BroadcastReceiver {
    String d1 = "Hôm nay là mùng 1 tháng xxxxxx âm lịch";
    String d14 = "Ngày mai là 15 tháng xxxxxx âm lịch";
    String d15 = "Hôm nay là 15 tháng xxxxxx âm lịch";
    String d31 = "Ngày mai là mùng 1 tháng xxxxxx âm lịch";
    String title1 = "Mùng 1";
    String title15 = "Ngày rằm";

    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            Log.m1446d("onReceive " + intent.getExtras().getBoolean("isHome"));
            DatabaseAccess mDatabaseAccess = DatabaseAccess.getInstance(context);
            mDatabaseAccess.open();
            DayInfo mDayInfoNow = mDatabaseAccess.getDay(Utils.getCurrentDay());
            DayInfo mDayInfoNext = mDatabaseAccess.getDay(Utils.getDayChange(Utils.getCurrentDay(), 0, 1));
            String dateNow = mDayInfoNow.getAmLich().split("/")[0];
            String dateNext = mDayInfoNext.getAmLich().split("/")[0];
            if (!intent.getExtras().getBoolean("isHome")) {
                if (dateNow.equals("15")) {
                    showNotification(context, this.title15, this.d15.replace("xxxxxx", mDayInfoNow.getAmLich().split("/")[1]));
                } else if (dateNext.equals("15")) {
                    showNotification(context, this.title15, this.d14.replace("xxxxxx", mDayInfoNow.getAmLich().split("/")[1]));
                } else if (dateNow.equals("1")) {
                    showNotification(context, this.title1, this.d1.replace("xxxxxx", mDayInfoNow.getAmLich().split("/")[1]));
                } else if (dateNext.equals("1")) {
                    showNotification(context, this.title1, this.d31.replace("xxxxxx", mDayInfoNext.getAmLich().split("/")[1]));
                }
            }
            Log.m1449v("hom nay là ngày " + dateNow + " tháng " + mDayInfoNow.getAmLich().split("/")[1]);
            long delayTime = 3000;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Define.TIME_FORMAT);
            if (Integer.parseInt(dateNow) <= 13 && Integer.parseInt(dateNow) >= 1) {
                try {
                    int delayDate = 14 - Integer.parseInt(dateNow);
                    cal.setTime(simpleDateFormat.parse(Utils.getDayChange(Utils.getCurrentDay(), 0, delayDate)));
                    delayTime = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    Log.m1449v("còn " + delayDate + " ngày nữa là đến 14 âm. thời gian: " + delayTime);
                } catch (Exception e) {
                    Log.m1447e("error BroadcastReceiverAlarm 14: " + e.getMessage());
                }
            } else if (Integer.parseInt(dateNow) == 14) {
                try {
                    cal.setTime(simpleDateFormat.parse(Utils.getDayChange(Utils.getCurrentDay(), 0, 1)));
                    delayTime = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    Log.m1449v("còn 1 ngày nữa là đến 15 âm. thời gian: " + delayTime);
                } catch (Exception e2) {
                    Log.m1447e("error BroadcastReceiverAlarm 15: " + e2.getMessage());
                }
            } else if (Integer.parseInt(dateNow) >= 15 && Integer.parseInt(dateNext) != 1) {
                int add = 0;
                while (true) {
                    if (mDatabaseAccess.getDay(Utils.getDayChange(Utils.getCurrentDay(), 0, add)) != null) {
                        if (mDatabaseAccess.getDay(Utils.getDayChange(Utils.getCurrentDay(), 0, add)).getAmLich().split("/")[0].equals("1")) {
                            break;
                        }
                        add++;
                    } else {
                        add++;
                        if (add > 100) {
                            break;
                        }
                    }
                }
                add--;
                try {
                    cal.setTime(simpleDateFormat.parse(Utils.getDayChange(Utils.getCurrentDay(), 0, add)));
                    delayTime = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    Log.m1449v("còn " + add + " ngày nữa là đến cuối tháng âm. thời gian: " + delayTime);
                } catch (Exception e22) {
                    Log.m1447e("error BroadcastReceiverAlarm cuối tháng: " + e22.getMessage());
                }
            } else if (Integer.parseInt(dateNext) == 1) {
                try {
                    cal.setTime(simpleDateFormat.parse(Utils.getDayChange(Utils.getCurrentDay(), 0, 1)));
                    delayTime = cal.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                    Log.m1449v("còn 1 ngày nữa là đến mùng 1 âm. thời gian: " + delayTime);
                } catch (Exception e222) {
                    Log.m1447e("error BroadcastReceiverAlarm 1: " + e222.getMessage());
                }
            }
            Log.m1449v("delayTime done: " + delayTime);
            delayTime += 28800000;
            PendingIntent.getBroadcast(context, BaseConstant.ID_ALARM_LOCAL_NOTIFICATION, new Intent(context, BroadcastReceiverAlarm.class), 134217728).cancel();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
            intent.putExtra("isHome", false);
            PendingIntent sender = PendingIntent.getBroadcast(context, BaseConstant.ID_ALARM_LOCAL_NOTIFICATION, intent, 134217728);
            if (VERSION.SDK_INT >= 19) {
                alarmManager.setExact(0, Calendar.getInstance().getTimeInMillis() + delayTime, sender);
            } else {
                alarmManager.set(0, Calendar.getInstance().getTimeInMillis() + delayTime, sender);
            }
        }
    }

    private void showNotification(Context context, String title, String content) {
        Builder builder = new Builder(context).setSmallIcon(R.drawable.icon).setContentTitle(title).setContentText(content);
        builder.setDefaults(2);
        ((PowerManager) context.getSystemService("power")).newWakeLock(805306394, "Alarm Notification 2214").acquire(100);
        builder.setSound(RingtoneManager.getDefaultUri(2));
        builder.setUsesChronometer(true);
        builder.setAutoCancel(false);
        builder.setPriority(2);
        builder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class), 0));
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(BaseConstant.ID_ALARM_LOCAL_NOTIFICATION_SHOW);
        mNotificationManager.notify(BaseConstant.ID_ALARM_LOCAL_NOTIFICATION_SHOW, builder.build());
    }
}
