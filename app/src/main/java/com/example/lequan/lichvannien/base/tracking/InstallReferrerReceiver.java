package com.example.lequan.lichvannien.base.tracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class InstallReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            String referrer = intent.getStringExtra("referrer");
            if (!TextUtils.isEmpty(referrer)) {
                SharedPreferencesLibUtil.setValue(context, "referrer", referrer);
                TrackingReferrer.checkSendReferrer(context);
            }
        } catch (Exception e) {
        }
    }
}
