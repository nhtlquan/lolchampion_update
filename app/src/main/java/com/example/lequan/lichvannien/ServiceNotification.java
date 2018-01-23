package com.example.lequan.lichvannien;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;

public class ServiceNotification extends Service {
    public static boolean IS_SERVICE_RUNNING = false;
    public static String STARTFOREGROUND_ACTION = "ACTION_START_FORCEGROUND_SERVICE";
    public static String STOPFOREGROUND_ACTION = "ACTION_STOP_FORCEGROUND_SERVICE";
    private Handler handler = new Handler();
    private Builder mBuilder;
    private DatabaseAccess mDatabaseAccess;
    private NotificationManager mNotificationManager;
    private RemoteViews remoteViews;
    private Runnable updateNotification = new C11391();

    class C11391 implements Runnable {
        C11391() {
        }

        public void run() {
            try {
                if (ServiceNotification.this.mDatabaseAccess != null) {
                    Log.d(BaseActivity.TAG, "update notification");
                    DayInfo mDayInfo = ServiceNotification.this.mDatabaseAccess.getDay(Utils.getCurrentDay());
                    String[] currentDay = Utils.getCurrentDay().split("/");
                    String[] currentDayAm = mDayInfo.getAmLich().split("/");
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_duong_New, currentDay[0]);
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonthAndYear_duong_New, "THÁNG " + Integer.parseInt(currentDay[1]) + "-" + currentDay[2]);
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvThu_duong_New, mDayInfo.getThu().toUpperCase());
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvYear_am_New, "NĂM " + mDayInfo.getNam().toUpperCase());
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_am_New, currentDayAm[0] + "/" + currentDayAm[1] + " âm lịch");
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDayText_am_New, "Ngày: " + mDayInfo.getNgay());
                    ServiceNotification.this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonth_am_New, "Tháng: " + mDayInfo.getThang());
                    ServiceNotification.this.mNotificationManager.notify(BaseConstant.ID_NOTIFICATION_SERVICE, ServiceNotification.this.mBuilder.build());
                    ServiceNotification.this.handler.postDelayed(this, 60000);
                }
            } catch (Exception e) {
                Log.e(BaseActivity.TAG, "error update notification: " + e.getMessage());
            }
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        Log.d(BaseActivity.TAG, "onBind ServiceNotification");
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mDatabaseAccess = DatabaseAccess.getInstance(this);
        this.mDatabaseAccess.open();
        Log.d(BaseActivity.TAG, "onStart Command ServiceNotification");
        if (intent == null) {
            Log.d(BaseActivity.TAG, "intent null");
            return super.onStartCommand(intent, flags, startId);
        } else if (intent.getAction() == null) {
            Log.d(BaseActivity.TAG, "action null");
            return super.onStartCommand(intent, flags, startId);
        } else {
            if (intent.getAction().equals(STARTFOREGROUND_ACTION)) {
                showNotification();
            } else if (intent.getAction().equals(STOPFOREGROUND_ACTION)) {
                IS_SERVICE_RUNNING = false;
                stopForeground(true);
                stopSelf();
            }
            return super.onStartCommand(intent, flags, startId);
        }
    }

    public void onDestroy() {
        Log.d(BaseActivity.TAG, "onDestroy ServiceNotification");
        IS_SERVICE_RUNNING = false;
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        Log.d(BaseActivity.TAG, "onUnbind ServiceNotification");
        return super.onUnbind(intent);
    }

    private void showNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, HomeActivity.class), 0);
        this.mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        this.remoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        DayInfo mDayInfo = this.mDatabaseAccess.getDay(Utils.getCurrentDay());
        if (mDayInfo != null) {
            String[] currentDay = Utils.getCurrentDay().split("/");
            String[] currentDayAm = mDayInfo.getAmLich().split("/");
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_duong_New, currentDay[0]);
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonthAndYear_duong_New, "THÁNG " + Integer.parseInt(currentDay[1]) + "-" + currentDay[2]);
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvThu_duong_New, mDayInfo.getThu().toUpperCase());
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvYear_am_New, "NĂM " + mDayInfo.getNam().toUpperCase());
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDay_am_New, currentDayAm[0] + "/" + currentDayAm[1] + " âm lịch");
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvDayText_am_New, "Ngày: " + mDayInfo.getNgay());
            this.remoteViews.setTextViewText(R.id.widget_layout_4x1_tvMonth_am_New, "Tháng: " + mDayInfo.getThang());
        }
        this.mBuilder = new Builder(this);
        startForeground(BaseConstant.ID_NOTIFICATION_SERVICE, this.mBuilder.setContent(this.remoteViews).setContentIntent(pendingIntent).setSmallIcon(R.drawable.icon).setPriority(2).build());
        this.handler.postDelayed(this.updateNotification, 3000);
    }
}
