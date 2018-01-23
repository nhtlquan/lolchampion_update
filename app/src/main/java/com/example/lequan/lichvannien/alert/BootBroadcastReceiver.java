package com.example.lequan.lichvannien.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.Alert;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import java.util.ArrayList;

public class BootBroadcastReceiver extends BroadcastReceiver {
    protected DatabaseAccess mDatabaseAccess;

    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")) {
            this.mDatabaseAccess = DatabaseAccess.getInstance(context);
            this.mDatabaseAccess.open();
            AlarmHandler handler = new AlarmHandler(context);
            ArrayList<EventInfo> listEventInfos = this.mDatabaseAccess.getEvent();
            for (int i = 0; i < listEventInfos.size(); i++) {
                Alert alert = (Alert) new Gson().fromJson(((EventInfo) listEventInfos.get(i)).getAlert(), Alert.class);
                if (alert.isOn()) {
                    handler.startAlert(alert);
                }
            }
        }
    }
}
