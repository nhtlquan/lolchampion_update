package com.example.lequan.lichvannien.alert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.lequan.lichvannien.model.Alert;
import com.example.lequan.lichvannien.utils.DateUtils;
import java.util.Calendar;

public class AlarmHandler {
    private final Context mContext;

    public AlarmHandler(Context context) {
        this.mContext = context;
    }

    public void startAlert(Alert alert) {
        Intent intent = new Intent(this.mContext, AlertBroadcastReceiver.class);
        intent.putExtra("alertid", alert.getId() + "");
        stopAlert(alert);
        Calendar nextTime = Calendar.getInstance();
        nextTime.setTimeInMillis(alert.getStartTimeMillis());
        DateUtils.increaTime(nextTime, alert);
        increaRepeatFrequent(nextTime, alert);
        int i = 0;
        while (i < 5) {
            if (!alert.isEnableTimeDistance(i) || Calendar.getInstance().getTimeInMillis() >= nextTime.getTimeInMillis() - (DateUtils.ARRAY_BEFORE[i] * 1000)) {
                i++;
            } else {
                Log.d("hungkm", "enalbe : " + i);
                nextTime.add(13, (int) (-1 * DateUtils.ARRAY_BEFORE[i]));
                ((AlarmManager) this.mContext.getSystemService("alarm")).set(0, nextTime.getTimeInMillis(), PendingIntent.getBroadcast(this.mContext, alert.getId(), intent, 134217728));
                return;
            }
        }
    }

    public void increaRepeatFrequent(Calendar nextTime, Alert alert) {
        if (alert.getFrequency() > 0 && Calendar.getInstance().getTimeInMillis() > nextTime.getTimeInMillis()) {
            alert.setRepeatFrequent(1);
            DateUtils.increaTime(nextTime, alert);
            Log.d("hungkm", "after increa : " + nextTime.toString());
            if (Calendar.getInstance().getTimeInMillis() > nextTime.getTimeInMillis()) {
                increaRepeatFrequent(nextTime, alert);
            }
        }
    }

    public void stopAlert(Alert alert) {
        PendingIntent.getBroadcast(this.mContext, alert.getId(), new Intent(this.mContext, AlertBroadcastReceiver.class), 134217728).cancel();
    }
}
