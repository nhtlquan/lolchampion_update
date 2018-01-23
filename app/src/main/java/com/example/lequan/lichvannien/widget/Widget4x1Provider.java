package com.example.lequan.lichvannien.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.ServiceUpdateWidget;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;

public class Widget4x1Provider extends AppWidgetProvider {
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(BaseActivity.TAG, "onUpdate");
        for (int abc : appWidgetIds) {
            Log.i(BaseActivity.TAG, "id: " + abc);
        }
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, Widget4x1Provider.class));
        DatabaseAccess mDatabaseAccess = DatabaseAccess.getInstance(context);
        mDatabaseAccess.open();
        DayInfo mDayInfo = mDatabaseAccess.getDay(Utils.getCurrentDay());
        if (mDayInfo != null) {
            String[] currentDay = Utils.getCurrentDay().split("/");
            String[] currentDayAm = mDayInfo.getAmLich().split("/");
            for (int widgetId : allWidgetIds) {
                Log.i(BaseActivity.TAG, "update Idwidget: " + widgetId);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_4x1);
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_duong_New, currentDay[0]);
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonthAndYear_duong_New, "THÁNG " + Integer.parseInt(currentDay[1]) + "-" + currentDay[2]);
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvThu_duong_New, mDayInfo.getThu().toUpperCase());
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvYear_am_New, "NĂM " + mDayInfo.getNam().toUpperCase());
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_am_New, currentDayAm[0] + "/" + currentDayAm[1]);
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDayText_am_New, "Ngày: " + mDayInfo.getNgay());
                remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonth_am_New, "Tháng: " + mDayInfo.getThang());
                remoteViews.setOnClickPendingIntent(R.id.rlWidget, PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class), 0));
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }
        }
        PendingIntent pending = PendingIntent.getService(context, 0, new Intent(context, ServiceUpdateWidget.class), 0);
        AlarmManager alarm = (AlarmManager) context.getSystemService("alarm");
        alarm.cancel(pending);
        alarm.setRepeating(3, SystemClock.elapsedRealtime(), 60000, pending);
    }

    public void onEnabled(Context context) {
        Log.d(BaseActivity.TAG, "enabled widget");
        super.onEnabled(context);
    }

    public void onDisabled(Context context) {
        Log.d(BaseActivity.TAG, "ondisabled widget");
        ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getService(context, 0, new Intent(context, ServiceUpdateWidget.class), 0));
        super.onDisabled(context);
    }

    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(BaseActivity.TAG, "ondeleted widget");
        for (int abc : appWidgetIds) {
            Log.i(BaseActivity.TAG, "id widget delete: " + abc);
        }
        super.onDeleted(context, appWidgetIds);
    }
}
