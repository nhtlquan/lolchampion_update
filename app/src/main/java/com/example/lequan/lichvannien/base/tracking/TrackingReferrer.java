package com.example.lequan.lichvannien.base.tracking;

import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.facebook.internal.ServerProtocol;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import org.json.JSONObject;

public class TrackingReferrer {
    public static void checkSendReferrer(final Context context) {
        if (SharedPreferencesLibUtil.getValue(context, "isSendReferrer") == null || !SharedPreferencesLibUtil.getValue(context, "isSendReferrer").equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
            final String referrer = SharedPreferencesLibUtil.getValue(context, "referrer");
            if (!TextUtils.isEmpty(referrer)) {
                new Thread() {
                    public void run() {
                        try {
                            String deviceID = TrackingReferrer.getDeviceId(context);
                            String versionAPP = TrackingReferrer.getVersionAppName(context);
                            String versionOS = TrackingReferrer.getVersionOS();
                            boolean status = Boolean.parseBoolean(new JSONObject(new OkHttpClient().newCall(new Builder().url("http://sdk.hdvietpro.com/android/apps/check_referer.php").post(new FormBody.Builder().add("referrer", referrer).add("deviceID", deviceID).add("versionAPP", versionAPP).add("versionOS", versionOS).build()).build()).execute().body().string()).getString("status"));
                            if (status) {
                                SharedPreferencesLibUtil.setValue(context, "isSendReferrer", status + "");
                            }
                        } catch (Exception e) {
                        }
                    }
                }.start();
            }
        }
    }

    private static String getDeviceId(Context context) {
        try {
            return Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            return "unknown";
        }
    }

    private static String getVersionAppName(Context context) {
        String versionName = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            return "";
        }
    }

    private static String getVersionOS() {
        return VERSION.RELEASE + " SDK: " + VERSION.SDK_INT;
    }
}
