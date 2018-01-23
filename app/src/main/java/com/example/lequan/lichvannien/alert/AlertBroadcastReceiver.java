package com.example.lequan.lichvannien.alert;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.activity.EventDetailActivity;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.Alert;
import com.example.lequan.lichvannien.utils.DateUtils;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.example.lequan.lichvannien.R;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlertBroadcastReceiver extends BroadcastReceiver {
    String alertID;
    protected DatabaseAccess mDatabaseAccess;

    public void onReceive(Context context, Intent intent) {
        this.mDatabaseAccess = DatabaseAccess.getInstance(context);
        this.mDatabaseAccess.open();
        this.alertID = intent.getStringExtra("alertid");
        Log.d("hungkm", "alertid : " + this.alertID);
        EventInfo eventInfo = this.mDatabaseAccess.getEventbyId(this.alertID);
        Log.d("hungkm", "eventInfo br: " + eventInfo.toString());
        Alert alert = (Alert) new Gson().fromJson(eventInfo.getAlert(), Alert.class);
        Calendar nextTime = Calendar.getInstance();
        nextTime.setTimeInMillis(alert.getStartTimeMillis());
        DateUtils.increaTime(nextTime, alert);
        Log.d("hungkm", "getRepeatFrequent : " + alert.getRepeatFrequent());
        int i = 0;
        while (i < 5) {
            AlarmManager alarmManager;
            PendingIntent sender;
            if (!alert.isEnableTimeDistance(i) || Calendar.getInstance().getTimeInMillis() >= nextTime.getTimeInMillis() - (DateUtils.ARRAY_BEFORE[i] * 1000)) {
                i++;
            } else {
                nextTime.add(13, (int) (-1 * DateUtils.ARRAY_BEFORE[i]));
                Log.d("hungkm", "nextTime2 : " + nextTime);
                alarmManager = (AlarmManager) context.getSystemService("alarm");
                sender = PendingIntent.getBroadcast(context, alert.getId(), intent, 134217728);
                if (VERSION.SDK_INT >= 19) {
                    alarmManager.setExact(0, nextTime.getTimeInMillis(), sender);
                } else {
                    alarmManager.set(0, nextTime.getTimeInMillis(), sender);
                }
                fireNotification(context, alert, nextTime, false);
                if (alert.getFrequency() > 0) {
                    if (Calendar.getInstance().getTimeInMillis() > nextTime.getTimeInMillis()) {
                        Intent cancelIntent = new Intent(context, AlertBroadcastReceiver.class);
                        PendingIntent.getBroadcast(context, alert.getId(), cancelIntent, 134217728).cancel();
                    }
                } else if (Calendar.getInstance().getTimeInMillis() > nextTime.getTimeInMillis()) {
                    alert.setRepeatFrequent(alert.getRepeatFrequent() + 1);
                    eventInfo.setAlert(alert.toString());
                    this.mDatabaseAccess.updateEvent(eventInfo);
                    i = 0;
                    while (i < 6) {
                        if (alert.isEnableTimeDistance(i)) {
                            i++;
                        } else {
                            Log.d("hungkm", "enalbe2 : " + i + "-" + alert.getRepeatFrequent());
                            nextTime.setTimeInMillis(alert.getStartTimeMillis());
                            DateUtils.increaTime(nextTime, alert);
                            nextTime.add(13, (int) (-1 * DateUtils.ARRAY_BEFORE[i]));
                            alarmManager = (AlarmManager) context.getSystemService("alarm");
                            sender = PendingIntent.getBroadcast(context, alert.getId(), intent, 134217728);
                            if (VERSION.SDK_INT < 19) {
                                alarmManager.setExact(0, nextTime.getTimeInMillis(), sender);
                            } else {
                                alarmManager.set(0, nextTime.getTimeInMillis(), sender);
                            }
                            fireNotification(context, alert, nextTime, false);
                        }
                    }
                    fireNotification(context, alert, nextTime, false);
                }
            }
        }
        fireNotification(context, alert, nextTime, false);
        if (alert.getFrequency() > 0) {
            if (Calendar.getInstance().getTimeInMillis() > nextTime.getTimeInMillis()) {
                alert.setRepeatFrequent(alert.getRepeatFrequent() + 1);
                eventInfo.setAlert(alert.toString());
                this.mDatabaseAccess.updateEvent(eventInfo);
                i = 0;
                while (i < 6) {
                    if (alert.isEnableTimeDistance(i)) {
                        i++;
                    } else {
                        Log.d("hungkm", "enalbe2 : " + i + "-" + alert.getRepeatFrequent());
                        nextTime.setTimeInMillis(alert.getStartTimeMillis());
                        DateUtils.increaTime(nextTime, alert);
                        nextTime.add(13, (int) (-1 * DateUtils.ARRAY_BEFORE[i]));
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
                        PendingIntent sender = PendingIntent.getBroadcast(context, alert.getId(), intent, 134217728);
                        if (VERSION.SDK_INT < 19) {
                            alarmManager.set(0, nextTime.getTimeInMillis(), sender);
                        } else {
                            alarmManager.setExact(0, nextTime.getTimeInMillis(), sender);
                        }
                        fireNotification(context, alert, nextTime, false);
                    }
                }
                fireNotification(context, alert, nextTime, false);
            }
        } else if (Calendar.getInstance().getTimeInMillis() > nextTime.getTimeInMillis()) {
            Intent cancelIntent2 = new Intent(context, AlertBroadcastReceiver.class);
            PendingIntent.getBroadcast(context, alert.getId(), cancelIntent2, 134217728).cancel();
        }
    }

    private void fireNotification(Context context, Alert alert, Calendar nextAlertTime, boolean initial) {
        Builder builder = new Builder(context).setSmallIcon(R.drawable.icon).setContentTitle(alert.getLabel());
        String report = "Không lặp";
        switch (alert.getFrequency()) {
            case 0:
                report = "Không lặp";
                break;
            case 1:
                report = "Hàng ngày";
                break;
            case 2:
                report = "Hàng tuần";
                break;
            case 3:
                report = "Hàng tháng";
                break;
            case 4:
                report = "Hàng năm";
                break;
        }
        builder.setStyle(new BigTextStyle().bigText("Lặp lại: " + report + "\nKế tiếp: " + DateFormat.getDateTimeInstance().format(new Date(nextAlertTime.getTimeInMillis()))));
        if (!initial) {
            if (alert.isVibrate()) {
                builder.setDefaults(2);
            }
            if (alert.isWake()) {
                ((PowerManager) context.getSystemService("power")).newWakeLock(805306394, "RepeatingReminder" + String.valueOf(alert.getId())).acquire(100);
            }
            if (!alert.isMute()) {
                builder.setSound(alert.getToneURI());
            }
            if (alert.getFrequency() != 0) {
                builder.setUsesChronometer(true);
            }
        }
        builder.setAutoCancel(false);
        builder.setPriority(0);
        Intent homeIntent = new Intent(context, EventDetailActivity.class);
        homeIntent.putExtra("alertid", alert.getId() + "");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(EventDetailActivity.class);
        stackBuilder.addNextIntent(homeIntent);
        builder.setContentIntent(stackBuilder.getPendingIntent(0, 134217728));
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(alert.getId());
        mNotificationManager.notify(alert.getId(), builder.build());
    }
}
