package com.example.lequan.lichvannien;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.widget.Widget4x1Provider;
import com.example.lequan.lichvannien.R;

public class ServiceUpdateWidget extends Service {
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(BaseActivity.TAG, "onStart Command");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, Widget4x1Provider.class));
        for (int i : allWidgetIds) {
            Log.i(BaseActivity.TAG, "id widget: " + i);
        }
        if (allWidgetIds.length == 0) {
            return super.onStartCommand(intent, flags, startId);
        }
        DatabaseAccess mDatabaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        mDatabaseAccess.open();
        DayInfo mDayInfo = mDatabaseAccess.getDay(Utils.getCurrentDay());
        if (mDayInfo == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        String[] currentDay = Utils.getCurrentDay().split("/");
        String[] currentDayAm = mDayInfo.getAmLich().split("/");
        for (int updateAppWidget : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout_4x1);
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_duong_New, currentDay[0]);
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonthAndYear_duong_New, "THÁNG " + Integer.parseInt(currentDay[1]) + "-" + currentDay[2]);
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvThu_duong_New, mDayInfo.getThu().toUpperCase());
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvYear_am_New, "NĂM " + mDayInfo.getNam().toUpperCase());
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_am_New, currentDayAm[0] + "/" + currentDayAm[1]);
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDayText_am_New, "Ngày: " + mDayInfo.getNgay());
            remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonth_am_New, "Tháng: " + mDayInfo.getThang());
            remoteViews.setOnClickPendingIntent(R.id.rlWidget, PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), HomeActivity.class), 0));
            appWidgetManager.updateAppWidget(updateAppWidget, remoteViews);
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
