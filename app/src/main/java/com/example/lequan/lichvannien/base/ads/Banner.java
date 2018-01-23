package com.example.lequan.lichvannien.base.ads;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lequan.lichvannien.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.example.lequan.lichvannien.base.BaseApplication;
import com.example.lequan.lichvannien.base.custominterface.BannerListener;
import com.example.lequan.lichvannien.base.dao.BaseConfig.more_apps;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.base.utils.Log;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.Random;

public class Banner extends RelativeLayout {
    private BannerListener bannerListener = null;
    private BaseApplication baseApplication;
    private boolean isLoadedBanner = false;
    private boolean isLoadedCustom = false;
    private boolean isThumbnail = false;
    private ImageView ivAdsVietmobi = null;
    private AdView mAdViewAbmob;
    private com.facebook.ads.AdView mAdViewFacebook;
    private LinearLayout mLnrAdview;
    private boolean onlyLocalAds = false;

    class C11121 extends SimpleTarget<Drawable> {
        C11121() {
        }

        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            if (Banner.this.ivAdsVietmobi != null) {
                Banner.this.ivAdsVietmobi.setImageDrawable(resource);
            }
            if (Banner.this.mLnrAdview != null) {
                Banner.this.mLnrAdview.removeAllViews();
                Banner.this.mLnrAdview.addView(Banner.this.ivAdsVietmobi);
                Banner.this.isLoadedCustom = true;
                if (Banner.this.bannerListener != null) {
                    Banner.this.bannerListener.onAdLoaded();
                }
            }
        }
    }

    class C11132 extends SimpleTarget<Drawable> {
        C11132() {
        }

        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            if (Banner.this.ivAdsVietmobi != null) {
                Banner.this.ivAdsVietmobi.setImageDrawable(resource);
            }
            if (Banner.this.mLnrAdview != null) {
                Banner.this.mLnrAdview.removeAllViews();
                Banner.this.mLnrAdview.addView(Banner.this.ivAdsVietmobi);
                Banner.this.isLoadedCustom = true;
                if (Banner.this.bannerListener != null) {
                    Banner.this.bannerListener.onAdLoaded();
                }
            }
        }
    }

    class C11143 implements Callback {
        C11143() {
        }

        public void onSuccess() {
            Banner.this.mLnrAdview.removeAllViews();
            Banner.this.mLnrAdview.addView(Banner.this.ivAdsVietmobi);
            Banner.this.isLoadedCustom = true;
        }

        public void onError() {
        }
    }

    class C11154 implements Callback {
        C11154() {
        }

        public void onSuccess() {
            Banner.this.mLnrAdview.removeAllViews();
            Banner.this.mLnrAdview.addView(Banner.this.ivAdsVietmobi);
            Banner.this.isLoadedCustom = true;
        }

        public void onError() {
        }
    }

    class C11165 extends AdListener {
        C11165() {
        }

        public void onAdClosed() {
            super.onAdClosed();
        }

        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
            switch (errorCode) {
                case 0:
                    Log.m1447e("admob banner fail ERROR_CODE_INTERNAL_ERROR");
                    return;
                case 1:
                    Log.m1447e("admob banner fail ERROR_CODE_INVALID_REQUEST");
                    return;
                case 2:
                    Log.m1447e("admob banner fail ERROR_CODE_NETWORK_ERROR");
                    return;
                case 3:
                    Log.m1447e("admob banner fail ERROR_CODE_NO_FILL");
                    return;
                default:
                    return;
            }
        }

        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        public void onAdLoaded() {
            super.onAdLoaded();
            Log.m1446d("onAdLoaded banner");
            Banner.this.mLnrAdview.removeAllViews();
            Banner.this.mLnrAdview.addView(Banner.this.mAdViewAbmob);
            Banner.this.isLoadedBanner = true;
        }

        public void onAdOpened() {
        }
    }

    class C11176 implements com.facebook.ads.AdListener {
        C11176() {
        }

        public void onError(Ad ad, AdError error) {
            Log.m1447e("facebook banner fail: " + error.getErrorMessage());
        }

        public void onAdLoaded(Ad ad) {
            Banner.this.mLnrAdview.removeAllViews();
            Banner.this.mLnrAdview.addView(Banner.this.mAdViewFacebook);
            Banner.this.isLoadedBanner = true;
        }

        public void onAdClicked(Ad ad) {
        }

        @Override
        public void onLoggingImpression(Ad ad) {

        }
    }

    private class impleOnClick implements OnClickListener {
        String url_store = "";

        impleOnClick(String url_store) {
            this.url_store = url_store;
        }

        public void onClick(View v) {
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(this.url_store));
            Banner.this.getContext().startActivity(i);
        }
    }

    public boolean isLoaded() {
        return this.isLoadedCustom || this.isLoadedBanner;
    }

    public void setBannerListener(BannerListener bannerListener) {
        this.bannerListener = bannerListener;
    }

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setAttributes(attrs);
        initView();
    }

    public Banner(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
        initView();
    }

    public Banner(Context context, boolean isThumbnail) {
        super(context);
        initView();
    }

    private void setAttributes(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ActionBar);
            if (a.hasValue(R.styleable.AdsAttrs_adSize)) {
                this.isThumbnail = a.getBoolean(R.styleable.AdsAttrs_adSize, false);
            }
            if (a.hasValue(R.styleable.AdsAttrs_adSize)) {
                this.onlyLocalAds = a.getBoolean(R.styleable.AdsAttrs_adSize, false);
            }
            a.recycle();
        }
    }

    private void initView() {
        View mView = inflate(getContext(), R.layout.ads_banner, null);
        addView(mView);
        this.mLnrAdview = (LinearLayout) mView.findViewById(R.id.ads_banner_ll);
        this.mLnrAdview.removeAllViews();
        this.baseApplication = (BaseApplication) getContext().getApplicationContext();
        if (!this.baseApplication.isPurchase && !this.onlyLocalAds) {
            loadAds();
        }
    }

    public void loadAdsOnlyLocal() {
        try {
            if (this.baseApplication.getBaseConfig().getThumnail_config().getIs_show_thumbnail_lich_ngay() == 0) {
                Log.m1446d("is show banner custom = 0");
            } else if (this.baseApplication.getBaseConfig().getMore_apps().size() != 0) {
                if (this.ivAdsVietmobi == null) {
                    this.ivAdsVietmobi = new ImageView(getContext());
                    if (this.isThumbnail) {
                        this.ivAdsVietmobi.setLayoutParams(new LayoutParams(-1, -2));
                    } else {
                        this.ivAdsVietmobi.setLayoutParams(new LayoutParams(-1, BaseUtils.genpx(getContext(), 50)));
                    }
                    this.ivAdsVietmobi.setAdjustViewBounds(true);
                }
                more_apps more_apps = (more_apps) this.baseApplication.getBaseConfig().getMore_apps().get(new Random().nextInt(this.baseApplication.getBaseConfig().getMore_apps().size()));
                if (this.isThumbnail) {
                    if (!more_apps.getThumbai().equals("")) {
                        Glide.with(getContext()).load(more_apps.getThumbai()).apply(new RequestOptions().fitCenter().override(this.baseApplication.widthPixels - BaseUtils.genpx(getContext(), 60))).into(new C11121());
                    }
                } else if (!more_apps.getBanner().equals("")) {
                    Glide.with(getContext()).load(more_apps.getBanner()).into(new C11132());
                }
                this.mLnrAdview.setOnClickListener(new impleOnClick(more_apps.getUrl_store()));
            }
        } catch (Exception e) {
            Log.m1447e("error show adsview HDV: " + e.getMessage());
        }
    }

    public void loadAds() {
        try {
            if (new Random().nextInt(100) < this.baseApplication.getBaseConfig().getThumnail_config().getRandom_show_thumbai_hdv() && this.baseApplication.getBaseConfig().getMore_apps().size() > 0) {
                this.ivAdsVietmobi = new ImageView(getContext());
                if (this.isThumbnail) {
                    this.ivAdsVietmobi.setLayoutParams(new LayoutParams(-1, -2));
                } else {
                    this.ivAdsVietmobi.setLayoutParams(new LayoutParams(-1, BaseUtils.genpx(getContext(), 50)));
                }
                this.ivAdsVietmobi.setScaleType(ScaleType.CENTER_CROP);
                this.ivAdsVietmobi.setAdjustViewBounds(true);
                more_apps more_apps = (more_apps) this.baseApplication.getBaseConfig().getMore_apps().get(new Random().nextInt(this.baseApplication.getBaseConfig().getMore_apps().size()));
                if (this.isThumbnail) {
                    if (!more_apps.getThumbai().equals("")) {
                        Picasso.with(getContext()).load(more_apps.getThumbai()).into(this.ivAdsVietmobi, new C11143());
                    }
                } else if (!more_apps.getBanner().equals("")) {
                    Picasso.with(getContext()).load(more_apps.getBanner()).into(this.ivAdsVietmobi, new C11154());
                }
                this.mLnrAdview.setOnClickListener(new impleOnClick(more_apps.getUrl_store()));
            } else if (this.baseApplication.getBaseConfig().getAds_network_new().getBanner().equals("admob")) {
                loadAdmob();
            } else {
                loadFacebook();
            }
        } catch (Exception e) {
            Log.m1447e("error show adsview HDV: " + e.getMessage());
        }
    }

    public void clear() {
        this.mLnrAdview.removeAllViews();
    }

    public void reloadAds() {
        if (this.baseApplication.isPurchase) {
            this.mLnrAdview.removeAllViews();
            this.isLoadedBanner = false;
            this.isLoadedCustom = false;
        } else if (this.onlyLocalAds) {
            loadAdsOnlyLocal();
        } else {
            loadAds();
        }
    }

    private void loadAdmob() {
        Log.m1449v("load admob " + (this.isThumbnail ? "thumbnail" : "banner") + " " + this.baseApplication.getBaseConfig().getKey().getAdmob().getBanner());
        if (this.mAdViewAbmob == null || !this.isLoadedBanner) {
            AdRequest mAdRequest = new Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build();
            this.mAdViewAbmob = new AdView(getContext());
            if (this.isThumbnail) {
                this.mAdViewAbmob.setAdSize(AdSize.MEDIUM_RECTANGLE);
            } else {
                this.mAdViewAbmob.setAdSize(AdSize.SMART_BANNER);
            }
            this.mAdViewAbmob.setLayoutParams(new LayoutParams(-1, -2));
            this.mAdViewAbmob.setAdUnitId(this.baseApplication.getBaseConfig().getKey().getAdmob().getBanner());
            this.mAdViewAbmob.setAdListener(new C11165());
            this.mAdViewAbmob.loadAd(mAdRequest);
            return;
        }
        this.mLnrAdview.removeAllViews();
        this.mLnrAdview.addView(this.mAdViewAbmob);
    }

    private void loadFacebook() {
        Log.m1449v("load facebook banner: " + this.baseApplication.getBaseConfig().getKey().getFacebook().getBanner());
        if (this.mAdViewFacebook == null || !this.isLoadedBanner) {
            this.mAdViewFacebook = new com.facebook.ads.AdView(getContext(), this.baseApplication.getBaseConfig().getKey().getFacebook().getBanner(), this.isThumbnail ? com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250 : com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            this.mAdViewFacebook.setAdListener(new C11176());
            this.mAdViewFacebook.loadAd();
            return;
        }
        this.mLnrAdview.removeAllViews();
        this.mLnrAdview.addView(this.mAdViewFacebook);
    }

    public void destroy() {
        if (this.mAdViewFacebook != null) {
            this.mAdViewFacebook.destroy();
        }
        if (this.mAdViewAbmob != null) {
            this.mAdViewAbmob.destroy();
        }
    }

    public void pause() {
        if (this.mAdViewAbmob != null) {
            this.mAdViewAbmob.pause();
        }
    }

    public void resume() {
        if (this.mAdViewAbmob != null) {
            this.mAdViewAbmob.resume();
        }
    }
}
