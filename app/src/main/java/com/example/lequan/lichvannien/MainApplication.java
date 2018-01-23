package com.example.lequan.lichvannien;

import android.content.Context;
import android.os.AsyncTask;
import android.support.multidex.MultiDex;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.example.lequan.lichvannien.base.BaseApplication;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.dao.DAOWeather;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.onesignal.OneSignal.NotificationOpenedHandler;
import com.onesignal.OneSignal.OSInFocusDisplayOption;
import com.rey.material.app.ThemeManager;
import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.util.ArrayList;
import okhttp3.Request.Builder;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainApplication extends BaseApplication {
    public static String KEY_PREF_CITY_CODE = "key_pref_city_code";
    public static String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhUiC+0sUDpmYka88/j/VLKZ0fbDMRNAxoVwpa/BpG353sp8eDLhxGFkHz9wLbdDpQX/54gjQw00toO1vKyZO+Vx1G5sRk3Xonyfx7WLo+MUKgdzSbijbgnPAS6LSAUaUcG6masRJcXXlV7HU4wOkHycL6xSXRT5LABATRm3eSZEPRmMbNgmXv8QXfIXDxqMrY1+tRmOxaCnvmICXeHx05gDMWb7mMCg6hT1K87nKks3+TZgwgN7eHTQS8ADPEu+/br1yDdkEwlXXfK9koivoU1uuZYxOcq/moertcHRbd9kyfxGBtbyeLeKUApTRzxG+Prgmo2NQ4wFzNuR8hamvQQIDAQAB";
    public static String skuId = "upgrade_premium";
    public ArrayList<String> listBGNew = new ArrayList();
    public ArrayList<String> listBGNoiDung = new ArrayList();
    public ArrayList<DAOWeather> listWeather = new ArrayList();
    public ArrayList<String> srcLaBan = new ArrayList();
    public String url_thumbnail_weather = "";
    public String url_video_weather = "";

    private class ExampleNotificationOpenedHandler implements NotificationOpenedHandler {
        private ExampleNotificationOpenedHandler() {
        }

        public void notificationOpened(OSNotificationOpenResult result) {
            try {
                JSONObject data = result.toJSONObject();
                Log.v(BaseActivity.TAG, "result push: " + data.toString());
                MainApplication.this.editor.putString("contentDanhNgon", data.getJSONObject(NOTIFICATION_SERVICE).getJSONObject("payload").getString("body").replace("Danh Ngôn :", ""));
                MainApplication.this.editor.commit();
            } catch (Exception e) {
                Log.e(BaseActivity.TAG, "error parse notification: " + e.getMessage());
            }
        }
    }

    class ayncDownloadImage extends AsyncTask<Void, Void, Void> {
        ayncDownloadImage() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            new File(MainApplication.this.getFilesDir().getPath() + "/img/").mkdirs();
        }

        protected Void doInBackground(Void... params) {
            try {
                int i;
                Response response;
                BufferedSink sink;
                Response responseApi = MainApplication.this.getOkHttpClient().newCall(new Builder().url("http://sdk.hdvietpro.com/android/apps/lichvn/get_img.php").build()).execute();
                JSONObject jsonObject = new JSONObject(BaseUtils.readFileFromAsset(MainApplication.this.getApplicationContext(), "img.txt"));
                if (responseApi.isSuccessful()) {
                    try {
                        jsonObject = new JSONObject(responseApi.body().string());
                    } catch (JSONException e) {
                    }
                }
                JSONArray jsonArray = jsonObject.getJSONArray("bg");
                MainApplication.this.listBGNew.clear();
                for (i = 0; i < jsonArray.length(); i++) {
                    MainApplication.this.listBGNew.add(MainApplication.this.getFilesDir().getPath() + "/img/bg_" + i + ".png");
                    if (!new File(MainApplication.this.getFilesDir().getPath() + "/img/bg_" + i + ".png").exists()) {
                        response = MainApplication.this.getOkHttpClient().newCall(new Builder().url(jsonArray.getString(i)).build()).execute();
                        response.body().contentLength();
                        sink = Okio.buffer(Okio.sink(new File(MainApplication.this.getFilesDir().getPath() + "/img/bg_" + i + ".png")));
                        sink.writeAll(response.body().source());
                        sink.close();
                    }
                }
                jsonArray = jsonObject.getJSONArray("bg_noi_dung");
                MainApplication.this.listBGNoiDung.clear();
                for (i = 0; i < jsonArray.length(); i++) {
                    MainApplication.this.listBGNoiDung.add(jsonArray.getString(i));
                    if (!new File(MainApplication.this.getFilesDir().getPath() + "/img/bg_noi_dung_" + i + ".png").exists()) {
                        response = MainApplication.this.getOkHttpClient().newCall(new Builder().url(jsonArray.getString(i)).build()).execute();
                        response.body().contentLength();
                        sink = Okio.buffer(Okio.sink(new File(MainApplication.this.getFilesDir().getPath() + "/img/bg_noi_dung_" + i + ".png")));
                        sink.writeAll(response.body().source());
                        sink.close();
                    }
                }
                jsonArray = jsonObject.getJSONArray("laban");
                MainApplication.this.srcLaBan.clear();
                for (i = 0; i < jsonArray.length(); i++) {
                    MainApplication.this.srcLaBan.add(jsonArray.getString(i));
                    if (!new File(MainApplication.this.getFilesDir().getPath() + "/img/laban_" + i + ".png").exists()) {
                        response = MainApplication.this.getOkHttpClient().newCall(new Builder().url(jsonArray.getString(i)).build()).execute();
                        response.body().contentLength();
                        sink = Okio.buffer(Okio.sink(new File(MainApplication.this.getFilesDir().getPath() + "/img/laban_" + i + ".png")));
                        sink.writeAll(response.body().source());
                        sink.close();
                    }
                }
            } catch (Exception e2) {
                com.example.lequan.lichvannien.base.utils.Log.m1447e("error download img: " + e2.getMessage());
            }
            return null;
        }
    }

    public void onCreate() {
        super.onCreate();
        Log.w(BaseActivity.TAG, "onCreate MainApplication");
//        OneSignal.startInit(this).inFocusDisplaying(OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(true).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler()).init();
//        Fabric.with(this, new Crashlytics());
//        FacebookSdk.sdkInitialize(getApplicationContext());
        ThemeManager.init(this, 2, 0, null);
    }

    public void downloadIMG() {
        new ayncDownloadImage().execute(new Void[0]);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
