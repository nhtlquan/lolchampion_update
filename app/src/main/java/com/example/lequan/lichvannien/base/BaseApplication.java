package com.example.lequan.lichvannien.base;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.lequan.lichvannien.R;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.base.ads.OtherAppLauncher;
import com.example.lequan.lichvannien.base.ads.Popup;
import com.example.lequan.lichvannien.base.dao.BaseConfig;
import com.example.lequan.lichvannien.base.dao.BaseConfig.shortcut_dynamic;
import com.example.lequan.lichvannien.base.dao.BaseTypeface;
import com.example.lequan.lichvannien.base.tracking.TrackingReferrer;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.base.utils.Log;
import java.io.File;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.json.JSONObject;

public class BaseApplication extends Application {
    private BaseConfig baseConfig = new BaseConfig();
    private BaseTypeface baseTypeface;
    public Editor editor;
    public Gson gson;
    public int heightPixels = 0;
    public boolean isInitUpdate = false;
    public boolean isPurchase = false;
    private OkHttpClient okHttpClient;
    private Popup popup;
    public SharedPreferences pref;
    public int widthPixels = 0;

    private class LoadData extends AsyncTask<Void, Void, Void> {
        private LoadData() {
        }

        protected Void doInBackground(Void... params) {
            Response response;
            String resultIpInfo = "null";
            try {
                response = BaseApplication.this.getOkHttpClient().newCall(new Builder().url("https://ipinfo.io/json").build()).execute();
                if (response.isSuccessful()) {
                    resultIpInfo = response.body().string();
                    BaseUtils.setCountry(BaseApplication.this.getApplicationContext(), new JSONObject(resultIpInfo).getString("country"));
                }
            } catch (Exception e) {
                Log.m1447e("error progress ipInfo: " + e.getMessage());
            }
            String version_manifest = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            try {
                version_manifest = BaseApplication.this.getApplicationContext().getPackageManager().getPackageInfo(BaseApplication.this.getApplicationContext().getPackageName(), 0).versionCode + "";
            } catch (NameNotFoundException e2) {
            }
            String url = "http://sdk.hdvietpro.com/android/apps/control-new.php?code=" + BaseApplication.this.getResources().getString(R.string.code_app) + "&date_install=" + BaseUtils.getDateInstall(BaseApplication.this.getApplicationContext()) + "&version=" + version_manifest + "&deviceID=" + BaseUtils.getDeviceID(BaseApplication.this.getApplicationContext()) + "&country=" + BaseUtils.getCountry(BaseApplication.this.getApplicationContext()) + "&referrer=" + BaseApplication.this.getApplicationContext().getSharedPreferences("global_lib", 0).getString("referrer", null) + "&ipInfo=" + resultIpInfo;
            Log.m1449v("url base: " + url);
            try {
                response = BaseApplication.this.getOkHttpClient().newCall(new Builder().url(url).build()).execute();
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    BaseApplication.this.baseConfig = (BaseConfig) BaseApplication.this.gson.fromJson(result, BaseConfig.class);
                    if (BaseApplication.this.baseConfig != null) {
                        BaseApplication.this.baseConfig.initOtherApps(BaseApplication.this.getApplicationContext());
                        BaseUtils.writeTxtFile(new File(BaseApplication.this.getApplicationContext().getFilesDir().getPath() + "/txt/base.txt"), result);
                        return null;
                    }
                }
            } catch (Exception e3) {
                Log.m1447e("error request base: " + e3.getMessage());
            }
            BaseApplication.this.initBaseConfigFromFile();
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.m1448i("appId admob: " + BaseApplication.this.baseConfig.getKey().getAdmob().getAppid());
//            MobileAds.initialize(BaseApplication.this.getApplicationContext(), BaseApplication.this.baseConfig.getKey().getAdmob().getAppid());
//            BaseApplication.this.popup = new Popup(BaseApplication.this.getApplicationContext());
//            BaseApplication.this.addShortcut();
        }
    }

    class extBaseTarget extends BaseTarget {
        private shortcut_dynamic shortcut_dynamic;

        extBaseTarget() {
        }

        public void setShortcut_dynamic(shortcut_dynamic shortcut_dynamic) {
            this.shortcut_dynamic = shortcut_dynamic;
        }

        public void onResourceReady(Object resource, Transition transition) {
            if (resource instanceof BitmapDrawable) {
                try {
                    Intent shortcutIntent = new Intent(BaseApplication.this.getApplicationContext(), OtherAppLauncher.class);
                    shortcutIntent.setAction("android.intent.action.MAIN");
                    shortcutIntent.putExtra("link", this.shortcut_dynamic.getLink());
                    shortcutIntent.putExtra("package_name", this.shortcut_dynamic.getPackage_name());
                    Intent addIntent = new Intent();
                    addIntent.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
                    addIntent.putExtra("android.intent.extra.shortcut.NAME", this.shortcut_dynamic.getName_shotcut());
                    addIntent.putExtra("android.intent.extra.shortcut.ICON", ((BitmapDrawable) resource).getBitmap());
                    addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
                    addIntent.putExtra("duplicate", false);
                    BaseApplication.this.getApplicationContext().sendBroadcast(addIntent);
                    BaseApplication.this.editor.putBoolean("pref_" + this.shortcut_dynamic.getId(), true);
                    BaseApplication.this.editor.commit();
                } catch (Exception e) {
                    Log.m1447e("error add shortcut: " + e.getMessage());
                }
            }
        }

        public void getSize(SizeReadyCallback cb) {
            cb.onSizeReady((int) BaseApplication.this.getResources().getDimension(17104896), (int) BaseApplication.this.getResources().getDimension(17104896));
        }

        public void removeCallback(SizeReadyCallback cb) {
        }
    }

    public void onCreate() {
        super.onCreate();
        new File(getFilesDir().getPath() + "/txt/").mkdirs();
        this.pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor = this.pref.edit();
        this.gson = new Gson();
        FirebaseAnalytics.getInstance(this);
        TrackingReferrer.checkSendReferrer(this);
        BaseUtils.setDateInstall(getApplicationContext());
        resetTime();
        initBaseConfigFromFile();
    }

    public void loadDataConfig() {
        new LoadData().execute(new Void[0]);
    }

    public void resetTime() {
        this.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, 0);
        this.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_CLICKED_POPUP, 0);
        this.editor.putLong(BaseConstant.KEY_TIME_START_APP, System.currentTimeMillis());
        this.editor.putInt(BaseConstant.KEY_PREF_COUNT_SHOW_THUMBNAIL, -1);
        this.editor.apply();
    }

    public Popup getPopup() {
        return this.popup;
    }

    public BaseConfig getBaseConfig() {
        return this.baseConfig;
    }

    private void addShortcut() {
//        Iterator it = getBaseConfig().getShortcut_dynamic().iterator();
//        while (it.hasNext()) {
//            shortcut_dynamic shortcut_dynamic = (shortcut_dynamic) it.next();
//            if (!this.pref.getBoolean("pref_" + shortcut_dynamic.getId(), false) || shortcut_dynamic.getLoop() != 0) {
//                Target extBaseTarget = new extBaseTarget();
//                extBaseTarget.setShortcut_dynamic(shortcut_dynamic);
//                Glide.with(getApplicationContext()).load(shortcut_dynamic.getIcon()).into(extBaseTarget);
//            }
//        }
    }

    private void initBaseConfigFromFile() {
        try {
            File fileBase = new File(getApplicationContext().getFilesDir().getPath() + "/txt/base.txt");
            if (fileBase.exists()) {
                Log.m1446d("base file in sdcard");
                this.baseConfig = (BaseConfig) this.gson.fromJson(BaseUtils.readTxtFile(fileBase), BaseConfig.class);
            } else {
                Log.m1446d("base file in assets");
                this.baseConfig = (BaseConfig) this.gson.fromJson(BaseUtils.readFileFromAsset(getApplicationContext(), "base.txt"), BaseConfig.class);
            }
            this.baseConfig.initOtherApps(getApplicationContext());
            Log.m1449v("content baseConfig: " + this.gson.toJson(this.baseConfig));
        } catch (Exception e) {
            Log.m1447e("error init data base file: " + e.getMessage());
        }
    }

    public BaseTypeface getBaseTypeface() {
        if (this.baseTypeface == null) {
            this.baseTypeface = new BaseTypeface(this);
        }
        return this.baseTypeface;
    }

    public OkHttpClient getOkHttpClient() {
        if (this.okHttpClient == null) {
            this.okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build();
        }
        return this.okHttpClient;
    }
}
