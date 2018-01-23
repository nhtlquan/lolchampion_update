package com.example.lequan.lichvannien.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.base.ads.Banner;
import com.example.lequan.lichvannien.base.custominterface.BannerListener;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.alert.BroadcastReceiverAlarm;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.custom.DatePicker;
import com.example.lequan.lichvannien.custom.DatePicker.onActionFromDatePicker;
import com.example.lequan.lichvannien.customInterface.onActionFromFragmentHome;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.fragment.FragmentChangeDay;
import com.example.lequan.lichvannien.fragment.FragmentDayNew;
import com.example.lequan.lichvannien.fragment.FragmentMonth;
import com.example.lequan.lichvannien.fragment.FragmentMore;
import com.example.lequan.lichvannien.fragment.FragmentStar;
import com.example.lequan.lichvannien.util.IabHelper;
import com.example.lequan.lichvannien.util.IabHelper.IabAsyncInProgressException;
import com.example.lequan.lichvannien.util.IabHelper.OnIabSetupFinishedListener;
import com.example.lequan.lichvannien.util.IabHelper.QueryInventoryFinishedListener;
import com.example.lequan.lichvannien.util.IabResult;
import com.example.lequan.lichvannien.util.Inventory;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.Calendar;

public class HomeActivity extends BaseActivity implements OnClickListener {
    public static int density = 0;
    public static int width = 0;
    private MainApplication application;
    private Banner banner;
    int currentChildView = 0;
    public DatePicker datePicker;
    private FrameLayout flChangeDay;
    private FrameLayout flDay;
    private FrameLayout flMonth;
    private FrameLayout flMore;
    private FrameLayout flStar;
    FragmentChangeDay fragmentChangeDay = null;
    public FragmentDayNew fragmentDayNew = null;
    FragmentManager fragmentManager;
    FragmentMonth fragmentMonth = null;
    FragmentMore fragmentMore = null;
    FragmentStar fragmentStar = null;
    private ImageView imgChangeDay;
    private ImageView imgDay;
    private ImageView imgMonth;
    private ImageView imgMore;
    private ImageView imgStar;
    private ImageView ivBG;
    private ImageView ivBG1;
    private ImageView ivClose;
    QueryInventoryFinishedListener mGotInventoryListener = new C11662();
    private IabHelper mHelper;
    public int positionBG = 3;
    private RelativeLayout rlChangeDay;
    private RelativeLayout rlDay;
    private RelativeLayout rlMonth;
    private RelativeLayout rlMore;
    private RelativeLayout rlStar;
    public int status = 0;
    private TextView tvChangeDay;
    private TextView tvDay;
    private TextView tvMonth;
    private TextView tvMore;
    private TextView tvStar;
    private TextView tvTitle;
    private ViewFlipper vfHome;

    class C11651 implements OnIabSetupFinishedListener {
        C11651() {
        }

        public void onIabSetupFinished(IabResult result) {
            if (!result.isSuccess()) {
                HomeActivity.this.initAll();
            } else if (HomeActivity.this.mHelper == null) {
                HomeActivity.this.initAll();
            } else {
                try {
                    HomeActivity.this.mHelper.queryInventoryAsync(HomeActivity.this.mGotInventoryListener);
                } catch (IabAsyncInProgressException e) {
                    HomeActivity.this.initAll();
                }
            }
        }
    }

    class C11662 implements QueryInventoryFinishedListener {
        C11662() {
        }

        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(BaseActivity.TAG, "Query inventory finished.");
            if (HomeActivity.this.mHelper == null) {
                HomeActivity.this.initAll();
            } else if (result.isFailure()) {
                Log.e(BaseActivity.TAG, "Failed to query inventory: " + result);
                HomeActivity.this.initAll();
            } else {
                Log.d(BaseActivity.TAG, "Query inventory was successful.");
                if (inventory.getPurchase(MainApplication.skuId) != null) {
                    HomeActivity.this.baseApplication.isPurchase = true;
                    Log.i(BaseActivity.TAG, "isPurchase = true");
                } else {
                    Log.e(BaseActivity.TAG, "premiumPurchase = null");
                }
                HomeActivity.this.initAll();
            }
        }
    }

    class C11673 implements BannerListener {
        C11673() {
        }

        public void onAdLoaded() {
            HomeActivity.this.banner.setVisibility(0);
            HomeActivity.this.ivClose.setVisibility(0);
        }
    }

    class C11684 implements OnClickListener {
        C11684() {
        }

        public void onClick(View v) {
            HomeActivity.this.ivClose.setVisibility(8);
            HomeActivity.this.banner.setVisibility(8);
            HomeActivity.this.banner.clear();
        }
    }

    class C11695 implements onActionFromDatePicker {
        C11695() {
        }

        public void onSelect(String date) {
            try {
                if (HomeActivity.this.status == 0) {
                    if (HomeActivity.this.fragmentDayNew != null) {
                        HomeActivity.this.fragmentDayNew.showDayInfoFirst(date);
                        HomeActivity.this.fragmentDayNew.flagY = -100;
                    }
                } else if (HomeActivity.this.status == 1) {
                    if (HomeActivity.this.fragmentMonth != null) {
                        if (!date.equals(Utils.getCurrentDay())) {
                            HomeActivity.this.fragmentMonth.rlToDay.setVisibility(0);
                        }
                        HomeActivity.this.fragmentMonth.onSelectedDayPickerNew(date);
                    }
                } else if (HomeActivity.this.status == 2 && HomeActivity.this.fragmentChangeDay != null) {
                    HomeActivity.this.fragmentChangeDay.onSelectedDayPickerNew(date);
                }
            } catch (Exception e) {
            }
        }
    }

    class C11716 implements onActionFromFragmentHome {

        class C11701 implements Runnable {
            C11701() {
            }

            public void run() {
                PendingIntent.getBroadcast(HomeActivity.this, BaseConstant.ID_ALARM_LOCAL_NOTIFICATION, new Intent(HomeActivity.this, BroadcastReceiverAlarm.class), 134217728).cancel();
                Log.d(BaseActivity.TAG, "start alarm");
                Intent intent = new Intent(HomeActivity.this, BroadcastReceiverAlarm.class);
                intent.putExtra("isHome", true);
                ((AlarmManager) HomeActivity.this.getSystemService("alarm")).set(0, Calendar.getInstance().getTimeInMillis() + 1000, PendingIntent.getBroadcast(HomeActivity.this, BaseConstant.ID_ALARM_LOCAL_NOTIFICATION, intent, 134217728));
            }
        }

        C11716() {
        }

        public void onViewCreated() {
            HomeActivity.this.flMonth.setVisibility(4);
            HomeActivity.this.flChangeDay.setVisibility(4);
            HomeActivity.this.flStar.setVisibility(4);
            HomeActivity.this.flMore.setVisibility(4);
            HomeActivity.this.fragmentDayNew.initData();
            HomeActivity.this.findViewById(R.id.ivSplash).setVisibility(8);
            new Handler().postDelayed(new C11701(), 1000);
            HomeActivity.this.showThumbnail();
        }
    }

    class impleRunnable implements Runnable {
        private int index = 0;

        impleRunnable(int index) {
            this.index = index;
        }

        public void run() {
            try {
                HomeActivity.this.changeTabbar2(this.index);
            } catch (Exception e) {
                HomeActivity.this.handler.postDelayed(this, 100);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = (MainApplication) getApplication();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.application.widthPixels = metrics.widthPixels;
        this.application.heightPixels = metrics.heightPixels;
        setContentView((int) R.layout.activity_home);
        this.application.downloadIMG();
        this.application.resetTime();
        this.application.loadDataConfig();
        initAll();
//        initInappBilling();
    }

    private void initAll() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Define.listQuotation = databaseAccess.getAllQuotation();
        Define.listSpecial = databaseAccess.getAllSpecialDay();
        Log.d("Database: ", " Data: " + Define.listDay.size());
        databaseAccess.close();
        Define.SELECTED_DAY = Utils.getCurrentDay();
        InitUI();
    }

    private void initInappBilling() {
        this.mHelper = new IabHelper(this, MainApplication.base64EncodedPublicKey);
        this.mHelper.enableDebugLogging(true);
        this.mHelper.startSetup(new C11651());
    }

    public void InitUI() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        density = (int) displaymetrics.density;
        this.banner = (Banner) findViewById(R.id.banner);
        this.banner.setBannerListener(new C11673());
        this.ivClose = (ImageView) findViewById(R.id.ivClose);
        this.ivClose.setOnClickListener(new C11684());
        this.flDay = (FrameLayout) findViewById(R.id.container_body);
        this.flMonth = (FrameLayout) findViewById(R.id.container_month);
        this.flChangeDay = (FrameLayout) findViewById(R.id.container_change_day);
        this.flStar = (FrameLayout) findViewById(R.id.container_star);
        this.flMore = (FrameLayout) findViewById(R.id.container_more);
        this.rlDay = (RelativeLayout) findViewById(R.id.rl_top_chart);
        this.rlMonth = (RelativeLayout) findViewById(R.id.rl_search);
        this.rlChangeDay = (RelativeLayout) findViewById(R.id.rl_my_song);
        this.rlStar = (RelativeLayout) findViewById(R.id.rl_playlist);
        this.rlMore = (RelativeLayout) findViewById(R.id.rl_setting);
        this.imgDay = (ImageView) findViewById(R.id.img_top_chart);
        this.imgMonth = (ImageView) findViewById(R.id.img_search);
        this.imgChangeDay = (ImageView) findViewById(R.id.img_my_song);
        this.imgStar = (ImageView) findViewById(R.id.img_playlist);
        this.imgMore = (ImageView) findViewById(R.id.img_setting);
        this.tvDay = (TextView) findViewById(R.id.tv_top_chart);
        this.tvMonth = (TextView) findViewById(R.id.tv_search);
        this.tvChangeDay = (TextView) findViewById(R.id.tv_my_song);
        this.tvStar = (TextView) findViewById(R.id.tv_playlist);
        this.tvMore = (TextView) findViewById(R.id.tv_setting);
        this.tvDay.setTypeface(this.typeRegularNew);
        this.tvMonth.setTypeface(this.typeRegularNew);
        this.tvChangeDay.setTypeface(this.typeRegularNew);
        this.tvStar.setTypeface(this.typeRegularNew);
        this.tvMore.setTypeface(this.typeRegularNew);
        this.rlDay.setOnClickListener(this);
        this.rlMonth.setOnClickListener(this);
        this.rlChangeDay.setOnClickListener(this);
        this.rlStar.setOnClickListener(this);
        this.rlMore.setOnClickListener(this);
        this.vfHome = (ViewFlipper) findViewById(R.id.vfHome);
        this.ivBG = (ImageView) findViewById(R.id.iv_bg);
        this.ivBG1 = (ImageView) findViewById(R.id.iv_bg1);
        changeBGFragmentdayHome();
        this.datePicker = (DatePicker) findViewById(R.id.datePicker);
        this.datePicker.setOnActionFromDatePicker(new C11695());
        Define.isReset = true;
        this.imgDay.setImageResource(R.drawable.home_footer_lich_ngay);
        this.imgMonth.setImageResource(R.drawable.home_footer_lich_thang);
        this.imgChangeDay.setImageResource(R.drawable.home_footer_doi_ngay);
        this.imgStar.setImageResource(R.drawable.home_footer_tu_vi);
        this.imgMore.setImageResource(R.drawable.home_footer_mo_rong);
        this.tvDay.setTextColor(getResources().getColor(R.color.colorBlue));
        this.tvMonth.setTextColor(getResources().getColor(R.color.colorBlue));
        this.tvChangeDay.setTextColor(getResources().getColor(R.color.colorBlue));
        this.tvStar.setTextColor(getResources().getColor(R.color.colorBlue));
        this.tvMore.setTextColor(getResources().getColor(R.color.colorBlue));
        this.imgDay.setImageResource(R.drawable.home_footer_lich_ngay_select);
        this.tvDay.setTextColor(-1);
        try {
            if (this.fragmentManager == null) {
                this.fragmentManager = getSupportFragmentManager();
            }
            FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
            this.fragmentDayNew = FragmentDayNew.newInstance();
            fragmentTransaction.replace(R.id.container_body, this.fragmentDayNew);
            this.fragmentMonth = FragmentMonth.newInstance();
            fragmentTransaction.replace(R.id.container_month, this.fragmentMonth);
            this.fragmentChangeDay = FragmentChangeDay.newInstance();
            fragmentTransaction.replace(R.id.container_change_day, this.fragmentChangeDay);
            this.fragmentStar = FragmentStar.newInstance();
            fragmentTransaction.replace(R.id.container_star, this.fragmentStar);
            this.fragmentMore = FragmentMore.newInstance();
            fragmentTransaction.replace(R.id.container_more, this.fragmentMore);
            this.fragmentMore.setOnActionFromFragmentHome(new C11716());
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.e(BaseActivity.TAG, "error initUI HomeActivity: " + e.getMessage());
            finish();
        }
    }

    protected void getResponse(String jsonObject, String api) {
        super.getResponse(jsonObject, api);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_top_chart:
                changeTabbar(0);
                return;
            case R.id.rl_search:
                changeTabbar(1);
                return;
            case R.id.rl_my_song:
                changeTabbar(2);
                return;
            case R.id.rl_playlist:
                changeTabbar(3);
                return;
            case R.id.rl_setting:
                changeTabbar(4);
                return;
            default:
                return;
        }
    }

    public void onClose(Object object) {
        super.onClose(object);
        if (object != null && (object instanceof Integer)) {
            this.handler.post(new impleRunnable(((Integer) object).intValue()));
        }
    }

    private void changeTabbar(int index) {
        if (!showPopup(Integer.valueOf(index), false, true)) {
            changeTabbar2(index);
        }
    }

    public void showThumbnail() {
        if (!this.baseApplication.isPurchase) {
            int tempCount = this.baseApplication.pref.getInt(BaseConstant.KEY_PREF_COUNT_SHOW_THUMBNAIL, -1) + 1;
            this.baseApplication.editor.putInt(BaseConstant.KEY_PREF_COUNT_SHOW_THUMBNAIL, tempCount);
            this.baseApplication.editor.commit();
            int start_show_thumbnail = this.baseApplication.getBaseConfig().getThumbnail_app_lich().getStart_show_thumbnail();
            long offset_show_thumbnail = (long) this.baseApplication.getBaseConfig().getThumbnail_app_lich().getOffset_show_thumbnail();
            Log.v(BaseActivity.TAG, "tempCount: " + tempCount + " start_show: " + start_show_thumbnail + " offset_show: " + offset_show_thumbnail);
            if (tempCount < start_show_thumbnail) {
                return;
            }
            if (tempCount <= start_show_thumbnail || ((long) (tempCount - start_show_thumbnail)) % offset_show_thumbnail == 0) {
                Log.d(BaseActivity.TAG, "SHOW THUMBNAIL");
                this.banner.reloadAds();
            }
        }
    }

    public void hidenThumbnail() {
        this.banner.setVisibility(8);
        this.ivClose.setVisibility(8);
    }

    public void changeTabbar2(int index) {
        Define.isReset = true;
        this.imgDay.setImageResource(R.drawable.home_footer_lich_ngay);
        this.imgMonth.setImageResource(R.drawable.home_footer_lich_thang);
        this.imgChangeDay.setImageResource(R.drawable.home_footer_doi_ngay);
        this.imgStar.setImageResource(R.drawable.home_footer_tu_vi);
        this.imgMore.setImageResource(R.drawable.home_footer_mo_rong);
        this.tvDay.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        this.tvMonth.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        this.tvChangeDay.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        this.tvStar.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        this.tvMore.setTextColor(ContextCompat.getColor(this, R.color.colorBlue));
        if (this.fragmentManager == null) {
            this.fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction fragmentTransaction = this.fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                this.imgDay.setImageResource(R.drawable.home_footer_lich_ngay_select);
                this.tvDay.setTextColor(-1);
                this.flDay.setVisibility(0);
                this.flMonth.setVisibility(8);
                this.flChangeDay.setVisibility(8);
                this.flStar.setVisibility(8);
                this.flMore.setVisibility(8);
                changeBGFragmentdayHome();
                break;
            case 1:
                this.imgMonth.setImageResource(R.drawable.home_footer_lich_thang_select);
                this.tvMonth.setTextColor(-1);
                this.flDay.setVisibility(8);
                this.flMonth.setVisibility(0);
                this.flChangeDay.setVisibility(8);
                this.flStar.setVisibility(8);
                this.flMore.setVisibility(8);
                changeBGStatic();
                break;
            case 2:
                this.imgChangeDay.setImageResource(R.drawable.home_footer_doi_ngay_select);
                this.tvChangeDay.setTextColor(-1);
                this.flDay.setVisibility(8);
                this.flMonth.setVisibility(8);
                this.flChangeDay.setVisibility(0);
                this.flStar.setVisibility(8);
                this.flMore.setVisibility(8);
                changeBGStatic();
                break;
            case 3:
                this.imgStar.setImageResource(R.drawable.home_footer_tu_vi_select);
                this.tvStar.setTextColor(-1);
                this.flDay.setVisibility(8);
                this.flMonth.setVisibility(8);
                this.flChangeDay.setVisibility(8);
                this.flStar.setVisibility(0);
                this.flMore.setVisibility(8);
                changeBGStatic();
                break;
            case 4:
                this.imgMore.setImageResource(R.drawable.home_footer_mo_rong_select);
                this.tvMore.setTextColor(-1);
                this.flDay.setVisibility(8);
                this.flMonth.setVisibility(8);
                this.flChangeDay.setVisibility(8);
                this.flStar.setVisibility(8);
                this.flMore.setVisibility(0);
                changeBGStatic();
                break;
        }
        fragmentTransaction.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESTCODE) {
            Log.v(BaseActivity.TAG, "onActivityResult home");
            if (resultCode == -10) {
                changeBGStatic();
            }
            showPopup(null, false);
        } else if (requestCode == 1221 && this.baseApplication.isPurchase) {
            Log.d(BaseActivity.TAG, "result from upgrade premium activity");
            if (this.baseApplication.isPurchase) {
                try {
                    this.fragmentDayNew.reloadAds();
                } catch (Exception e) {
                }
                try {
                    this.fragmentMonth.reloadAds();
                } catch (Exception e2) {
                }
                try {
                    this.fragmentChangeDay.reloadAds();
                } catch (Exception e3) {
                }
                try {
                    this.fragmentStar.reloadAds();
                } catch (Exception e4) {
                }
                try {
                    this.fragmentMore.reloadAds();
                } catch (Exception e5) {
                }
            }
        }
    }

    public void changeBG(int type) {
        if (this.positionBG >= this.application.listBGNew.size()) {
            this.positionBG = 0;
        } else {
            this.positionBG++;
        }
        changeBGFragmentdayNew(type);
    }

    public void changeBGFragmentdayNew(int type) {
        int i = 1;
        if (this.ivBG != null && this.vfHome != null && this.ivBG1 != null) {
            if (this.application.listBGNew.size() > this.positionBG) {
                String filePath = (String) this.application.listBGNew.get(this.positionBG);
                if (new File(filePath).exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    if (this.currentChildView == 0) {
                        this.ivBG1.setImageBitmap(bitmap);
                    } else {
                        this.ivBG.setImageBitmap(bitmap);
                    }
                } else {
                    defaultBG(true);
                }
            } else {
                defaultBG(true);
            }
            switch (type) {
                case 1:
                    this.vfHome.setInAnimation(this, R.anim.in_from_right);
                    this.vfHome.setOutAnimation(this, R.anim.out_to_left);
                    break;
                case 2:
                    this.vfHome.setInAnimation(this, R.anim.in_from_left);
                    this.vfHome.setOutAnimation(this, R.anim.out_to_right);
                    break;
                case 3:
                    this.vfHome.setInAnimation(this, R.anim.in_from_bottom);
                    this.vfHome.setOutAnimation(this, R.anim.out_to_top);
                    break;
                case 4:
                    this.vfHome.setInAnimation(this, R.anim.in_from_top);
                    this.vfHome.setOutAnimation(this, R.anim.out_to_bottom);
                    break;
            }
            this.vfHome.showNext();
            if (this.currentChildView != 0) {
                i = 0;
            }
            this.currentChildView = i;
        }
    }

    public void changeBGFragmentdayHome() {
        if (this.ivBG != null && this.ivBG1 != null) {
            if (this.application.listBGNew.size() > this.positionBG) {
                String filePath = (String) this.application.listBGNew.get(this.positionBG);
                if (new File(filePath).exists()) {
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    if (this.currentChildView == 0) {
                        this.ivBG.setImageBitmap(bitmap);
                        return;
                    } else {
                        this.ivBG1.setImageBitmap(bitmap);
                        return;
                    }
                }
            }
            defaultBG(true);
        }
    }

    private void defaultBG(boolean isFragmentDay) {
        if (this.currentChildView == 0) {
            if (isFragmentDay) {
                this.ivBG.setImageResource(R.drawable.bg_default);
            } else {
                this.ivBG.setImageResource(R.drawable.bgr_screen_app);
            }
        } else if (isFragmentDay) {
            this.ivBG1.setImageResource(R.drawable.bg_default);
        } else {
            this.ivBG1.setImageResource(R.drawable.bgr_screen_app);
        }
    }

    public void changeBGStatic() {
        String filePath = "http://sdk.hdvietpro.com//android//apps//lichvn//images//bg_noi_dung_8.png";
        if (!new File(filePath).exists()) {
            defaultBG(false);
        } else if (this.currentChildView == 0) {
            Glide.with(this.baseActivity).load("http://sdk.hdvietpro.com//android//apps//lichvn//images//bg_noi_dung_8.png").into(this.ivBG);
        } else {
            Glide.with(this.baseActivity).load(filePath).into(this.ivBG1);
        }
    }

    public void onBackPressed() {
        try {
            if (!(this.fragmentDayNew == null || this.fragmentDayNew.popupWindow == null || !this.fragmentDayNew.popupWindow.isShowing())) {
                this.fragmentDayNew.popupWindow.dismiss();
                return;
            }
        } catch (Exception e) {
        }
        super.onBackPressed();
    }
}
