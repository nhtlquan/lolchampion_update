package com.example.lequan.lichvannien.base.ads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.example.lequan.lichvannien.base.BaseApplication;
import com.example.lequan.lichvannien.base.custominterface.PopupListener;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.base.utils.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Popup {
    private AdRequest admobRequest;
    private BaseApplication baseApplication;
    private Context context;
    private ArrayList<PopupListener> listPopupListener = new ArrayList();
    private InterstitialAd mInterstitialAdmob;
    private com.facebook.ads.InterstitialAd mInterstitialFacebook;
    private Object tempObject = null;

    class C11191 extends AdListener {
        C11191() {
        }

        public void onAdOpened() {
            super.onAdOpened();
        }

        public void onAdClosed() {
            super.onAdClosed();
            Popup.this.mInterstitialAdmob.loadAd(Popup.this.admobRequest);
            Iterator it = Popup.this.listPopupListener.iterator();
            while (it.hasNext()) {
                PopupListener popupListener = (PopupListener) it.next();
                if (popupListener != null) {
                    popupListener.onClose(Popup.this.tempObject);
                }
            }
        }

        public void onAdLoaded() {
            super.onAdLoaded();
            Log.m1446d("loadded popup admob.");
        }

        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            Log.m1447e("error load popup admob: " + errorCode);
        }

        public void onAdLeftApplication() {
            Popup.this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_CLICKED_POPUP, System.currentTimeMillis());
            Popup.this.baseApplication.editor.apply();
            super.onAdLeftApplication();
        }
    }

    class C11202 implements InterstitialAdListener {
        C11202() {
        }

        public void onInterstitialDisplayed(Ad ad) {
        }

        public void onInterstitialDismissed(Ad ad) {
            Popup.this.mInterstitialFacebook.loadAd();
            Iterator it = Popup.this.listPopupListener.iterator();
            while (it.hasNext()) {
                PopupListener popupListener = (PopupListener) it.next();
                if (popupListener != null) {
                    popupListener.onClose(Popup.this.tempObject);
                }
            }
        }

        public void onError(Ad ad, AdError adError) {
            Log.m1447e("error load popup facebook: " + adError.getErrorMessage());
        }

        public void onAdLoaded(Ad ad) {
            Log.m1446d("loaded popup facebook");
        }

        public void onAdClicked(Ad ad) {
            Popup.this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_CLICKED_POPUP, System.currentTimeMillis());
            Popup.this.baseApplication.editor.apply();
        }

        @Override
        public void onLoggingImpression(Ad ad) {

        }
    }

    public Object getTempObject() {
        return this.tempObject;
    }

    public Popup(Context context) {
        this.context = context;
        this.baseApplication = (BaseApplication) context.getApplicationContext();
        loadAds();
    }

    private void loadAds() {
        if (this.baseApplication.getBaseConfig().getAds_network_new().getPopup().equals("admob")) {
            this.mInterstitialAdmob = new InterstitialAd(this.context);
            this.mInterstitialAdmob.setAdUnitId(this.baseApplication.getBaseConfig().getKey().getAdmob().getPopup());
            this.mInterstitialAdmob.setAdListener(new C11191());
            this.admobRequest = new Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build();
            this.mInterstitialAdmob.loadAd(this.admobRequest);
            return;
        }
        this.mInterstitialFacebook = new com.facebook.ads.InterstitialAd(this.context, this.baseApplication.getBaseConfig().getKey().getFacebook().getPopup());
        this.mInterstitialFacebook.setAdListener(new C11202());
        this.mInterstitialFacebook.loadAd();
    }

    public boolean showPopup(Activity activity, Object object, boolean withOutCondition) {
        if (this.baseApplication.isPurchase) {
            return false;
        }
        this.tempObject = object;
        if (!withOutCondition) {
            long temp_time_now = System.currentTimeMillis();
            if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_TIME_START_APP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getTime_start_show_popup() * 1000))) {
                Log.m1446d("Chua du thoi gian start");
                return false;
            } else if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getOffset_time_show_popup() * 1000))) {
                Log.m1446d("Chua du thoi gian show before");
                return false;
            } else if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_CONTROLADS_TIME_CLICKED_POPUP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getTime_hidden_to_click_popup() * 1000))) {
                Log.m1446d("Chua du thoi gian click before");
                return false;
            }
        }
        if (new Random().nextInt(100) < this.baseApplication.getBaseConfig().getThumnail_config().getRandom_show_popup_hdv() && this.baseApplication.getBaseConfig().getMore_apps().size() > 0) {
            try {
                Log.m1446d("show popup custom");
                activity.startActivityForResult(new Intent(activity, PopupCustom.class), BaseConstant.REQUEST_SHOW_POPUP_CUSTOM);
                this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, System.currentTimeMillis());
                this.baseApplication.editor.apply();
                return true;
            } catch (Exception e) {
                Log.m1447e("error start popup custom: " + e.getMessage());
                return false;
            }
        } else if (this.baseApplication.getBaseConfig().getAds_network_new().getPopup().equals("admob")) {
            Log.m1446d("show popup admob: " + this.baseApplication.getBaseConfig().getKey().getAdmob().getPopup());
            if (this.mInterstitialAdmob.isLoaded()) {
                this.mInterstitialAdmob.show();
                this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, System.currentTimeMillis());
                this.baseApplication.editor.apply();
                return true;
            }
            this.mInterstitialAdmob.loadAd(this.admobRequest);
            return false;
        } else {
            Log.m1446d("show popup facebook: " + this.baseApplication.getBaseConfig().getKey().getFacebook().getPopup());
            if (this.mInterstitialFacebook.isAdLoaded()) {
                this.mInterstitialFacebook.show();
                this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, System.currentTimeMillis());
                this.baseApplication.editor.apply();
                return true;
            }
            this.mInterstitialFacebook.loadAd();
            return false;
        }
    }

    public boolean showPopup(Activity activity, Object object, boolean withOutCondition, boolean isCustom) {
        if (this.baseApplication.getBaseConfig().getThumnail_config().getIs_show_popup_chuyen_tab() == 0) {
            Log.m1446d("is show popup custom = 0");
            return false;
        } else if (this.baseApplication.isPurchase) {
            return false;
        } else {
            this.tempObject = object;
            if (!withOutCondition) {
                long temp_time_now = System.currentTimeMillis();
                if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_TIME_START_APP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getTime_start_show_popup() * 1000))) {
                    Log.m1446d("Chua du thoi gian start");
                    return false;
                } else if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getOffset_time_show_popup() * 1000))) {
                    Log.m1446d("Chua du thoi gian show before");
                    return false;
                } else if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_CONTROLADS_TIME_CLICKED_POPUP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getTime_hidden_to_click_popup() * 1000))) {
                    Log.m1446d("Chua du thoi gian click before");
                    return false;
                }
            }
            try {
                if (this.baseApplication.getBaseConfig().getMore_apps().size() == 0) {
                    Log.m1446d("size more apps = 0");
                    return false;
                }
                Log.m1446d("show popup custom");
                activity.startActivityForResult(new Intent(activity, PopupCustom.class), BaseConstant.REQUEST_SHOW_POPUP_CUSTOM);
                this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, System.currentTimeMillis());
                this.baseApplication.editor.apply();
                return true;
            } catch (Exception e) {
                Log.m1447e("error start popup custom: " + e.getMessage());
                return false;
            }
        }
    }

    public boolean showPopup(Object object, boolean withOutCondition, boolean noPopupCustom) {
        if (this.baseApplication.isPurchase) {
            return false;
        }
        this.tempObject = object;
        if (!withOutCondition) {
            long temp_time_now = System.currentTimeMillis();
            if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_TIME_START_APP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getTime_start_show_popup() * 1000))) {
                Log.m1446d("Chua du thoi gian start");
                return false;
            } else if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getOffset_time_show_popup() * 1000))) {
                Log.m1446d("Chua du thoi gian show before");
                return false;
            } else if (temp_time_now - this.baseApplication.pref.getLong(BaseConstant.KEY_CONTROLADS_TIME_CLICKED_POPUP, 0) < ((long) (this.baseApplication.getBaseConfig().getConfig_ads().getTime_hidden_to_click_popup() * 1000))) {
                Log.m1446d("Chua du thoi gian click before");
                return false;
            }
        }
        if (this.baseApplication.getBaseConfig().getAds_network_new().getPopup().equals("admob")) {
            Log.m1446d("show popup admob: " + this.baseApplication.getBaseConfig().getKey().getAdmob().getPopup());
            if (this.mInterstitialAdmob.isLoaded()) {
                this.mInterstitialAdmob.show();
                this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, System.currentTimeMillis());
                this.baseApplication.editor.apply();
                return true;
            }
            this.mInterstitialAdmob.loadAd(this.admobRequest);
            return false;
        }
        Log.m1446d("show popup facebook: " + this.baseApplication.getBaseConfig().getKey().getFacebook().getPopup());
        if (this.mInterstitialFacebook.isAdLoaded()) {
            this.mInterstitialFacebook.show();
            this.baseApplication.editor.putLong(BaseConstant.KEY_CONTROLADS_TIME_SHOWED_POPUP, System.currentTimeMillis());
            this.baseApplication.editor.apply();
            return true;
        }
        this.mInterstitialFacebook.loadAd();
        return false;
    }

    public void addPopupListtener(PopupListener popupListener) {
        this.listPopupListener.add(popupListener);
    }

    public void removePopupListener(PopupListener popupListener) {
        if (this.listPopupListener.contains(popupListener)) {
            this.listPopupListener.remove(popupListener);
        }
    }

    public void removeAllPopupListener() {
        this.listPopupListener.clear();
    }
}
