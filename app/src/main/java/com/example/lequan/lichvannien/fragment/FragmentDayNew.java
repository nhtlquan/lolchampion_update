package com.example.lequan.lichvannien.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.base.utils.Log;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.BaseFragment;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.activity.PersonalActivity;
import com.example.lequan.lichvannien.activity.UpgradePremiumActivity;
import com.example.lequan.lichvannien.activity.WeatherActivity;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.custom.TextViewOutline;
import com.example.lequan.lichvannien.customInterface.onActionFromFragmentHome;
import com.example.lequan.lichvannien.dao.DAOWeather;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.model.Quotation;
import com.example.lequan.lichvannien.utils.DateUtils;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import okhttp3.Request;
import okhttp3.Request.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public class FragmentDayNew extends BaseFragment implements OnClickListener {
    static final int MIN_DISTANCE = 50;
    private static String TAG = FragmentDayNew.class.getSimpleName();
    String API_WEATHER_NATIVE = "http://ios.hdvietpro.com/Mini_Project/AppLich/public/api_thoi_tiet.php?codeId=xxxxxx&os=android";
    private HomeActivity activity;
    MainApplication application;
    ImageView btnToday;
    ImageView btnToday1;
    int currentChildView = 0;
    public int flagX = 0;
    public int flagY = 0;
    FrameLayout frame;
    private ImageView imgZodiac;
    ImageView imgZodiacHour;
    boolean isCurrentDate = true;
    ImageView ivBgDanhNgon;
    ImageView ivCaNhan;
    ImageView ivThoiTiet;
    private LinearLayout layoutDate;
    private DatabaseAccess mDatabaseAccess;
    private DayInfo mDayInfo;
    OnTouchListener mOnTouchListener = new OnTouchListener() {
        public boolean onTouch(View v, MotionEvent motionEvent) {
            int i = 0;
            switch (motionEvent.getAction()) {
                case 0:
                    FragmentDayNew.this.x1 = motionEvent.getX();
                    FragmentDayNew.this.y1 = motionEvent.getY();
                    break;
                case 1:
                    FragmentDayNew.this.x2 = motionEvent.getX();
                    FragmentDayNew.this.y2 = motionEvent.getY();
                    float deltaX = FragmentDayNew.this.x2 - FragmentDayNew.this.x1;
                    float deltaY = FragmentDayNew.this.y2 - FragmentDayNew.this.y1;
                    FragmentDayNew fragmentDayNew;
                    if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) >= 50.0f) {
                        if (FragmentDayNew.this.x2 <= FragmentDayNew.this.x1) {
                            Log.m1446d("next day");
                            FragmentDayNew.this.showDayInfo2(Utils.getDayChange(Define.SELECTED_DAY, 0, 1));
                            try {
                                ((HomeActivity) FragmentDayNew.this.getActivity()).changeBG(1);
                            } catch (Exception e) {
                            }
                            FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_right);
                            FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_left);
                            FragmentDayNew.this.vfDay.showNext();
                            fragmentDayNew = FragmentDayNew.this;
                            if (FragmentDayNew.this.currentChildView == 0) {
                                i = 1;
                            }
                            fragmentDayNew.currentChildView = i;
                            fragmentDayNew = FragmentDayNew.this;
                            fragmentDayNew.flagX++;
                            FragmentDayNew.this.activity.showThumbnail();
                            break;
                        }
                        int i2;
                        Log.m1446d("prev day");
                        FragmentDayNew.this.showDayInfo2(Utils.getDayChange(Define.SELECTED_DAY, 0, -1));
                        try {
                            ((HomeActivity) FragmentDayNew.this.getActivity()).changeBG(2);
                        } catch (Exception e2) {
                        }
                        FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_left);
                        FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_right);
                        FragmentDayNew.this.vfDay.showNext();
                        FragmentDayNew fragmentDayNew2 = FragmentDayNew.this;
                        if (FragmentDayNew.this.currentChildView == 0) {
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                        fragmentDayNew2.currentChildView = i2;
                        fragmentDayNew = FragmentDayNew.this;
                        fragmentDayNew.flagX--;
                        FragmentDayNew.this.activity.showThumbnail();
                        break;
                    } else if (Math.abs(deltaX) <= Math.abs(deltaY) && Math.abs(deltaY) >= 50.0f) {
                        if (FragmentDayNew.this.y2 <= FragmentDayNew.this.y1) {
                            Log.m1446d("next month");
                            FragmentDayNew.this.showDayInfo2(Utils.getDayChange(Define.SELECTED_DAY, 1, 1));
                            try {
                                ((HomeActivity) FragmentDayNew.this.getActivity()).changeBG(3);
                            } catch (Exception e3) {
                            }
                            FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_bottom);
                            FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_top);
                            FragmentDayNew.this.vfDay.showNext();
                            fragmentDayNew = FragmentDayNew.this;
                            if (FragmentDayNew.this.currentChildView == 0) {
                                i = 1;
                            }
                            fragmentDayNew.currentChildView = i;
                            fragmentDayNew = FragmentDayNew.this;
                            fragmentDayNew.flagY++;
                            FragmentDayNew.this.activity.showThumbnail();
                            break;
                        }
                        Log.m1446d("prev month");
                        FragmentDayNew.this.showDayInfo2(Utils.getDayChange(Define.SELECTED_DAY, 1, -1));
                        try {
                            ((HomeActivity) FragmentDayNew.this.getActivity()).changeBG(4);
                        } catch (Exception e4) {
                        }
                        FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_top);
                        FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_bottom);
                        FragmentDayNew.this.vfDay.showNext();
                        fragmentDayNew = FragmentDayNew.this;
                        if (FragmentDayNew.this.currentChildView == 0) {
                            i = 1;
                        }
                        fragmentDayNew.currentChildView = i;
                        fragmentDayNew = FragmentDayNew.this;
                        fragmentDayNew.flagY--;
                        FragmentDayNew.this.activity.showThumbnail();
                        break;
                    } else {
                        ((BaseActivity) FragmentDayNew.this.getContext()).showDateDetail(Define.SELECTED_DAY);
                        break;
                    }
            }
            return true;
        }
    };
    CountDownTimer newtimer = new CountDownTimer(1000000000, 60000) {
        public void onTick(long millisUntilFinished) {
            Calendar c = Calendar.getInstance();
            FragmentDayNew.this.tvHour.setText((c.get(11) < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + c.get(11) : c.get(11) + "") + ":" + (c.get(12) < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + c.get(12) : c.get(12) + ""));
            if (FragmentDayNew.this.checkZodiacHour(FragmentDayNew.this.mDayInfo, 11) == 1) {
                FragmentDayNew.this.imgZodiacHour.setImageResource(R.drawable.hoang_dao);
                FragmentDayNew.this.imgZodiacHour.setVisibility(0);
            } else if (FragmentDayNew.this.checkZodiacHour(FragmentDayNew.this.mDayInfo, 11) == -1) {
                FragmentDayNew.this.imgZodiacHour.setImageResource(R.drawable.hac_dao);
                FragmentDayNew.this.imgZodiacHour.setVisibility(0);
            } else {
                FragmentDayNew.this.imgZodiacHour.setVisibility(4);
            }
        }

        public void onFinish() {
        }
    };
    private onActionFromFragmentHome onActionFromFragmentHome;
    public PopupWindow popupWindow = null;
    RelativeLayout rlDanhNgon;
    int tempPositionBG = -1;
    TextView tvAuthor;
    TextView tvAuthor1;
    TextView tvCalendar;
    TextView tvCalendar1;
    private TextView tvHour;
    private TextView tvLunarDay;
    TextView tvLunarDayDetail;
    TextView tvLunarHour;
    TextView tvLunarMonth;
    TextView tvLunarMonthDetail;
    TextView tvLunarYearDetail;
    TextView tvMonthHeader;
    TextView tvQuotation;
    TextView tvQuotation1;
    TextView tvWeekDay;
    TextView tvWeekDay1;
    ViewFlipper vfDay;
    View view;
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    class C12711 implements OnClickListener {
        C12711() {
        }

        public void onClick(View v) {
            FragmentDayNew.this.initPopupShare(FragmentDayNew.this.tvQuotation.getText().toString(), FragmentDayNew.this.tvAuthor.getText().toString());
        }
    }

    class C12722 implements OnClickListener {
        C12722() {
        }

        public void onClick(View v) {
            FragmentDayNew.this.initPopupShare(FragmentDayNew.this.tvQuotation.getText().toString(), FragmentDayNew.this.tvAuthor.getText().toString());
        }
    }

    class C12733 implements OnTouchListener {
        C12733() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 1) {
                FragmentDayNew.this.btnToday.setImageResource(R.drawable.header_hom_nay);
                if (FragmentDayNew.this.flagX == 0 && FragmentDayNew.this.flagY == 0) {
                    return true;
                }
                int i;
                FragmentDayNew.this.showDayInfo2(Utils.getCurrentDay());
                if (FragmentDayNew.this.flagX < 0 || FragmentDayNew.this.flagY < 0) {
                    FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_right);
                    FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_left);
                } else {
                    FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_left);
                    FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_right);
                }
                FragmentDayNew.this.vfDay.showNext();
                FragmentDayNew fragmentDayNew = FragmentDayNew.this;
                if (FragmentDayNew.this.currentChildView == 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                fragmentDayNew.currentChildView = i;
                FragmentDayNew.this.flagX = 0;
                FragmentDayNew.this.flagY = 0;
                return true;
            } else if (event.getAction() != 0) {
                return false;
            } else {
                FragmentDayNew.this.btnToday.setImageResource(R.drawable.header_hom_nay_select);
                return true;
            }
        }
    }

    class C12744 implements OnClickListener {
        C12744() {
        }

        public void onClick(View v) {
            FragmentDayNew.this.initPopupShare(FragmentDayNew.this.tvQuotation1.getText().toString(), FragmentDayNew.this.tvAuthor1.getText().toString());
        }
    }

    class C12755 implements OnClickListener {
        C12755() {
        }

        public void onClick(View v) {
            FragmentDayNew.this.initPopupShare(FragmentDayNew.this.tvQuotation1.getText().toString(), FragmentDayNew.this.tvAuthor1.getText().toString());
        }
    }

    class C12766 implements OnTouchListener {
        C12766() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 1) {
                FragmentDayNew.this.btnToday1.setImageResource(R.drawable.header_hom_nay);
                if (FragmentDayNew.this.flagX == 0 && FragmentDayNew.this.flagY == 0) {
                    return true;
                }
                int i;
                FragmentDayNew.this.showDayInfo2(Utils.getCurrentDay());
                if (FragmentDayNew.this.flagX < 0 || FragmentDayNew.this.flagY < 0) {
                    FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_right);
                    FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_left);
                } else {
                    FragmentDayNew.this.vfDay.setInAnimation(FragmentDayNew.this.getActivity(), R.anim.in_from_left);
                    FragmentDayNew.this.vfDay.setOutAnimation(FragmentDayNew.this.getActivity(), R.anim.out_to_right);
                }
                FragmentDayNew.this.vfDay.showNext();
                FragmentDayNew fragmentDayNew = FragmentDayNew.this;
                if (FragmentDayNew.this.currentChildView == 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                fragmentDayNew.currentChildView = i;
                FragmentDayNew.this.flagX = 0;
                FragmentDayNew.this.flagY = 0;
                return true;
            } else if (event.getAction() != 0) {
                return false;
            } else {
                FragmentDayNew.this.btnToday1.setImageResource(R.drawable.header_hom_nay_select);
                return true;
            }
        }
    }

    class C12777 implements OnTouchListener {
        C12777() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 1) {
                FragmentDayNew.this.ivThoiTiet.setImageResource(R.drawable.header_thoi_tiet);
                ((HomeActivity) FragmentDayNew.this.getActivity()).startActivityForResult(new Intent(FragmentDayNew.this.getActivity(), WeatherActivity.class), HomeActivity.REQUESTCODE);
                return true;
            } else if (event.getAction() != 0) {
                return false;
            } else {
                FragmentDayNew.this.ivThoiTiet.setImageResource(R.drawable.header_thoi_tiet_select);
                return true;
            }
        }
    }

    class C12788 implements OnTouchListener {
        C12788() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 1) {
                FragmentDayNew.this.ivCaNhan.setImageResource(R.drawable.header_ca_nhan);
                ((BaseActivity) FragmentDayNew.this.getContext()).changeActivity(true, PersonalActivity.class);
                return true;
            } else if (event.getAction() != 0) {
                return false;
            } else {
                FragmentDayNew.this.ivCaNhan.setImageResource(R.drawable.header_ca_nhan_select);
                return true;
            }
        }
    }

    class C12799 implements OnClickListener {
        C12799() {
        }

        public void onClick(View v) {
            ((HomeActivity) FragmentDayNew.this.getActivity()).status = 0;
            ((HomeActivity) FragmentDayNew.this.getActivity()).datePicker.setVisibility(0);
        }
    }

    class LoadWeather extends AsyncTask<Void, Void, Boolean> {
        LoadWeather() {
        }

        protected Boolean doInBackground(Void... voids) {
            try {
                SharedPreferences sharedPreferences = FragmentDayNew.this.application.pref;
                MainApplication mainApplication = FragmentDayNew.this.application;
                String urlWeatherNative = FragmentDayNew.this.API_WEATHER_NATIVE.replace("xxxxxx", sharedPreferences.getString(MainApplication.KEY_PREF_CITY_CODE, "2347727"));
                Request request = new Builder().url(urlWeatherNative).build();
                Log.m1448i("url weather native: " + urlWeatherNative);
                JSONObject jsonObject = new JSONObject(FragmentDayNew.this.application.getOkHttpClient().newCall(request).execute().body().string());
                try {
                    FragmentDayNew.this.application.url_thumbnail_weather = jsonObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("thumbnail");
                    FragmentDayNew.this.application.url_video_weather = jsonObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("videoUrl");
                } catch (Exception e) {
                }
                JSONArray jsonArray = jsonObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getJSONArray("weathers");
                FragmentDayNew.this.application.listWeather.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    FragmentDayNew.this.application.listWeather.add(new Gson().fromJson(jsonArray.getString(i), DAOWeather.class));
                }
                return Boolean.valueOf(true);
            } catch (Exception e2) {
                Log.m1447e("error get weather: " + e2.getMessage());
                return Boolean.valueOf(false);
            }
        }
    }

    public static FragmentDayNew newInstance() {
        return new FragmentDayNew();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = (MainApplication) getActivity().getApplication();
        this.activity = (HomeActivity) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.frame != null) {
            this.frame.removeAllViews();
            this.frame = null;
        }
        this.frame = new FrameLayout(getActivity());
        if (this.view == null) {
            this.view = inflater.inflate(R.layout.fragment_day2_test1, container, false);
            Init();
        }
        this.frame.addView(this.view);
        return this.frame;
    }

    public void Init() {
        this.mDatabaseAccess = DatabaseAccess.getInstance(getActivity());
        this.mDatabaseAccess.open();
        this.tvLunarDay = (TextViewOutline) this.view.findViewById(R.id.tv_lunar_day);
        this.tvLunarDay.setTypeface(this.typeRegularNew);
        this.tvLunarMonth = (TextView) this.view.findViewById(R.id.tv_lunar_month);
        this.tvLunarMonth.setTypeface(this.typeRegularNew);
        this.tvLunarDayDetail = (TextView) this.view.findViewById(R.id.lunar_day_detail);
        this.tvLunarDayDetail.setTypeface(this.typeRegularNew);
        this.tvLunarMonthDetail = (TextView) this.view.findViewById(R.id.lunar_month_detail);
        this.tvLunarMonthDetail.setTypeface(this.typeRegularNew);
        this.tvLunarYearDetail = (TextView) this.view.findViewById(R.id.lunar_year_detail);
        this.tvLunarYearDetail.setTypeface(this.typeRegularNew);
        this.tvHour = (TextView) this.view.findViewById(R.id.tvHour);
        this.tvHour.setTypeface(this.typeRegularNew);
        this.tvLunarHour = (TextView) this.view.findViewById(R.id.lunar_hour);
        this.tvLunarHour.setTypeface(this.typeRegularNew);
        this.tvMonthHeader = (TextView) this.view.findViewById(R.id.header_button_center_tv);
        this.tvMonthHeader.setTypeface(this.typeRegularNew);
        this.imgZodiac = (ImageView) this.view.findViewById(R.id.zodiac_day);
        this.imgZodiacHour = (ImageView) this.view.findViewById(R.id.zodiac_hour);
        this.layoutDate = (LinearLayout) this.view.findViewById(R.id.layout_date);
        this.vfDay = (ViewFlipper) this.view.findViewById(R.id.vfDay);
        this.tvCalendar = (TextViewOutline) this.view.findViewById(R.id.tv_calendar_day);
        this.tvCalendar.setTypeface(this.typeBoldNew);
        this.tvWeekDay = (TextView) this.view.findViewById(R.id.tv_week_day);
        this.tvWeekDay.setTypeface(this.typeRegularNew);
        this.tvQuotation = (TextView) this.view.findViewById(R.id.tv_quotations);
        this.tvQuotation.setTypeface(this.typeRegularNew);
        this.tvAuthor = (TextView) this.view.findViewById(R.id.tv_author);
        this.tvAuthor.setTypeface(this.typeRegularNew);
        this.tvQuotation.setOnClickListener(new C12711());
        this.tvAuthor.setOnClickListener(new C12722());
        this.btnToday = (ImageView) this.view.findViewById(R.id.btn_today);
        this.btnToday.setOnTouchListener(new C12733());
        this.tvCalendar1 = (TextViewOutline) this.view.findViewById(R.id.tv_calendar_day1);
        this.tvCalendar1.setTypeface(this.typeBoldNew);
        this.tvWeekDay1 = (TextView) this.view.findViewById(R.id.tv_week_day1);
        this.tvWeekDay1.setTypeface(this.typeRegularNew);
        this.tvQuotation1 = (TextView) this.view.findViewById(R.id.tv_quotations1);
        this.tvQuotation1.setTypeface(this.typeRegularNew);
        this.tvAuthor1 = (TextView) this.view.findViewById(R.id.tv_author1);
        this.tvAuthor1.setTypeface(this.typeRegularNew);
        this.tvQuotation1.setOnClickListener(new C12744());
        this.tvAuthor1.setOnClickListener(new C12755());
        this.btnToday1 = (ImageView) this.view.findViewById(R.id.btn_today1);
        this.btnToday1.setOnTouchListener(new C12766());
        this.ivThoiTiet = (ImageView) this.view.findViewById(R.id.header_button_left_iv);
        ((RelativeLayout) this.view.findViewById(R.id.header_button_left)).setOnTouchListener(new C12777());
        this.ivCaNhan = (ImageView) this.view.findViewById(R.id.header_button_right_iv);
        ((RelativeLayout) this.view.findViewById(R.id.header_button_right)).setOnTouchListener(new C12788());
        ((RelativeLayout) this.view.findViewById(R.id.header_button_center)).setOnClickListener(new C12799());
        this.layoutDate.setOnClickListener(this);
        this.vfDay.setOnTouchListener(this.mOnTouchListener);
        this.mDayInfo = this.mDatabaseAccess.getDay(Define.SELECTED_DAY);
        showDayInfoFirst(Define.SELECTED_DAY);
        this.newtimer.start();
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        } else {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    FragmentDayNew.this.getActivity().startActivityForResult(new Intent(FragmentDayNew.this.getActivity(), UpgradePremiumActivity.class), 1221);
                }
            });
        }
    }

    public void reloadAds() {
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        }
    }

    public void initData() {
        new LoadWeather().execute(new Void[0]);
    }

    public void showDayInfo2(String date) {
        this.mDayInfo = this.mDatabaseAccess.getDay(date);
        if (this.mDayInfo != null) {
            Define.SELECTED_DAY = date;
            if (date.equalsIgnoreCase(Utils.getCurrentDay())) {
                if (this.currentChildView == 0) {
                    this.btnToday1.setVisibility(4);
                } else {
                    this.btnToday.setVisibility(4);
                }
                this.isCurrentDate = true;
            } else {
                if (this.currentChildView == 0) {
                    this.btnToday1.setVisibility(0);
                } else {
                    this.btnToday.setVisibility(0);
                }
                this.isCurrentDate = false;
            }
            int dayCalendar = Integer.parseInt(Utils.getTime(0, this.mDayInfo.getDuongLich()));
            if (this.currentChildView == 0) {
                this.tvCalendar1.setText(dayCalendar >= 10 ? dayCalendar + "" : (dayCalendar + "").replace(AppEventsConstants.EVENT_PARAM_VALUE_NO, ""));
                this.tvWeekDay1.setText(this.mDayInfo.getThu().toUpperCase());
            } else {
                this.tvCalendar.setText(dayCalendar >= 10 ? dayCalendar + "" : (dayCalendar + "").replace(AppEventsConstants.EVENT_PARAM_VALUE_NO, ""));
                this.tvWeekDay.setText(this.mDayInfo.getThu().toUpperCase());
            }
            String tempLunarDay = Utils.getTime(0, this.mDayInfo.getAmLich());
            if (tempLunarDay.equals("15") || tempLunarDay.equals("1")) {
                this.tvLunarDay.setTextColor(SupportMenu.CATEGORY_MASK);
            } else {
                this.tvLunarDay.setTextColor(getResources().getColor(R.color.colorBlue));
            }
            this.tvLunarDay.setText(tempLunarDay);
            this.tvLunarMonth.setText("Tháng " + Utils.getTime(1, this.mDayInfo.getAmLich()));
            this.tvLunarDayDetail.setText("Ng. " + this.mDayInfo.getNgay());
            this.tvLunarMonthDetail.setText("Th. " + this.mDayInfo.getThang());
            this.tvLunarYearDetail.setText("Năm " + this.mDayInfo.getNam());
            if (Define.listQuotation.size() > 0) {
                int index = new Random().nextInt(Define.listQuotation.size());
                if (this.currentChildView == 0) {
                    if (this.application.pref.getString("contentDanhNgon", "").equals("")) {
                        this.tvQuotation1.setText(((Quotation) Define.listQuotation.get(index)).getQuotation());
                    } else {
                        this.tvQuotation1.setText(this.application.pref.getString("contentDanhNgon", ""));
                        this.application.editor.putString("contentDanhNgon", "");
                        this.application.editor.commit();
                    }
                    if (this.tvQuotation1.getLineCount() <= 0 || this.tvQuotation1.getLineCount() >= 3) {
                        this.tvAuthor1.setText("");
                    } else {
                        this.tvAuthor1.setText(((Quotation) Define.listQuotation.get(index)).getAuthor());
                    }
                } else {
                    if (this.application.pref.getString("contentDanhNgon", "").equals("")) {
                        this.tvQuotation.setText(((Quotation) Define.listQuotation.get(index)).getQuotation());
                    } else {
                        this.tvQuotation.setText(this.application.pref.getString("contentDanhNgon", ""));
                        this.application.editor.putString("contentDanhNgon", "");
                        this.application.editor.commit();
                    }
                    if (this.tvQuotation.getLineCount() <= 0 || this.tvQuotation.getLineCount() >= 3) {
                        this.tvAuthor.setText("");
                    } else {
                        this.tvAuthor.setText(((Quotation) Define.listQuotation.get(index)).getAuthor());
                    }
                }
            }
            this.tvMonthHeader.setText(Utils.getMonthYearNew(date));
            if (this.mDayInfo.getHoangDao() == 0) {
                this.imgZodiac.setVisibility(4);
            } else if (this.mDayInfo.getHoangDao() == 1) {
                this.imgZodiac.setImageResource(R.drawable.hoang_dao);
                this.imgZodiac.setVisibility(0);
            } else if (this.mDayInfo.getHoangDao() == -1) {
                this.imgZodiac.setImageResource(R.drawable.hac_dao);
                this.imgZodiac.setVisibility(0);
            }
            if (this.currentChildView == 0) {
                if (this.mDayInfo.getThu().equalsIgnoreCase("Chủ Nhật")) {
                    this.tvCalendar1.setTextColor(SupportMenu.CATEGORY_MASK);
                    this.tvWeekDay1.setTextColor(SupportMenu.CATEGORY_MASK);
                } else {
                    this.tvCalendar1.setTextColor(getResources().getColor(R.color.colorBlue));
                    this.tvWeekDay1.setTextColor(getResources().getColor(R.color.colorBlue));
                }
            } else if (this.mDayInfo.getThu().equalsIgnoreCase("Chủ Nhật")) {
                this.tvCalendar.setTextColor(SupportMenu.CATEGORY_MASK);
                this.tvWeekDay.setTextColor(SupportMenu.CATEGORY_MASK);
            } else {
                this.tvCalendar.setTextColor(getResources().getColor(R.color.colorBlue));
                this.tvWeekDay.setTextColor(getResources().getColor(R.color.colorBlue));
            }
            Calendar c = Calendar.getInstance();
            this.tvHour.setText(c.get(11) + ":" + (c.get(12) < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + c.get(12) : c.get(12) + ""));
            if (checkZodiacHour(this.mDayInfo, 11) == 1) {
                this.imgZodiacHour.setImageResource(R.drawable.hoang_dao);
                this.imgZodiacHour.setVisibility(0);
            } else if (checkZodiacHour(this.mDayInfo, 11) == -1) {
                this.imgZodiacHour.setImageResource(R.drawable.hac_dao);
                this.imgZodiacHour.setVisibility(0);
            } else {
                this.imgZodiacHour.setVisibility(4);
            }
            this.tvLunarHour.setText(getTimeCanChi(this.mDayInfo.getNgay().split(" ")[0], new SimpleDateFormat(DateUtils.TIME_FORMAT1).format(Calendar.getInstance().getTime())));
        }
    }

    public void showDayInfoFirst(String date) {
        this.mDayInfo = this.mDatabaseAccess.getDay(date);
        if (this.mDayInfo != null) {
            Define.SELECTED_DAY = date;
            if (date.equalsIgnoreCase(Utils.getCurrentDay())) {
                if (this.currentChildView == 0) {
                    this.btnToday.setVisibility(4);
                } else {
                    this.btnToday1.setVisibility(4);
                }
                this.isCurrentDate = true;
            } else {
                if (this.currentChildView == 0) {
                    this.btnToday.setVisibility(0);
                } else {
                    this.btnToday1.setVisibility(0);
                }
                this.isCurrentDate = false;
            }
            int dayCalendar = Integer.parseInt(Utils.getTime(0, this.mDayInfo.getDuongLich()));
            if (this.currentChildView == 0) {
                this.tvCalendar.setText(dayCalendar >= 10 ? dayCalendar + "" : (dayCalendar + "").replace(AppEventsConstants.EVENT_PARAM_VALUE_NO, ""));
                this.tvWeekDay.setText(this.mDayInfo.getThu().toUpperCase());
            } else {
                this.tvCalendar1.setText(dayCalendar >= 10 ? dayCalendar + "" : (dayCalendar + "").replace(AppEventsConstants.EVENT_PARAM_VALUE_NO, ""));
                this.tvWeekDay1.setText(this.mDayInfo.getThu().toUpperCase());
            }
            String tempLunarDay = Utils.getTime(0, this.mDayInfo.getAmLich());
            if (tempLunarDay.equals("15") || tempLunarDay.equals("1")) {
                this.tvLunarDay.setTextColor(SupportMenu.CATEGORY_MASK);
            } else {
                this.tvLunarDay.setTextColor(getResources().getColor(R.color.colorBlue));
            }
            this.tvLunarDay.setText(tempLunarDay);
            this.tvLunarMonth.setText("Tháng " + Utils.getTime(1, this.mDayInfo.getAmLich()));
            this.tvLunarDayDetail.setText("Ng. " + this.mDayInfo.getNgay());
            this.tvLunarMonthDetail.setText("Th. " + this.mDayInfo.getThang());
            this.tvLunarYearDetail.setText("Năm " + this.mDayInfo.getNam());
            if (Define.listQuotation.size() > 0) {
                int index = new Random().nextInt(Define.listQuotation.size());
                if (this.currentChildView == 0) {
                    if (this.application.pref.getString("contentDanhNgon", "").equals("")) {
                        this.tvQuotation.setText(((Quotation) Define.listQuotation.get(index)).getQuotation());
                    } else {
                        this.tvQuotation.setText(this.application.pref.getString("contentDanhNgon", ""));
                        this.application.editor.putString("contentDanhNgon", "");
                        this.application.editor.commit();
                    }
                    if (this.tvQuotation.getLineCount() <= 0 || this.tvQuotation.getLineCount() >= 3) {
                        this.tvAuthor.setText("");
                    } else {
                        this.tvAuthor.setText(((Quotation) Define.listQuotation.get(index)).getAuthor());
                    }
                } else {
                    if (this.application.pref.getString("contentDanhNgon", "").equals("")) {
                        this.tvQuotation1.setText(((Quotation) Define.listQuotation.get(index)).getQuotation());
                    } else {
                        this.tvQuotation1.setText(this.application.pref.getString("contentDanhNgon", ""));
                        this.application.editor.putString("contentDanhNgon", "");
                        this.application.editor.commit();
                    }
                    if (this.tvQuotation1.getLineCount() <= 0 || this.tvQuotation1.getLineCount() >= 3) {
                        this.tvAuthor1.setText("");
                    } else {
                        this.tvAuthor1.setText(((Quotation) Define.listQuotation.get(index)).getAuthor());
                    }
                }
            }
            this.tvMonthHeader.setText(Utils.getMonthYearNew(date));
            if (this.mDayInfo.getHoangDao() == 0) {
                this.imgZodiac.setVisibility(4);
            } else if (this.mDayInfo.getHoangDao() == 1) {
                this.imgZodiac.setImageResource(R.drawable.hoang_dao);
                this.imgZodiac.setVisibility(0);
            } else if (this.mDayInfo.getHoangDao() == -1) {
                this.imgZodiac.setImageResource(R.drawable.hac_dao);
                this.imgZodiac.setVisibility(0);
            }
            if (this.mDayInfo.getThu().equalsIgnoreCase("Chủ Nhật")) {
                if (this.currentChildView == 0) {
                    this.tvCalendar.setTextColor(SupportMenu.CATEGORY_MASK);
                    this.tvWeekDay.setTextColor(SupportMenu.CATEGORY_MASK);
                } else {
                    this.tvCalendar1.setTextColor(SupportMenu.CATEGORY_MASK);
                    this.tvWeekDay1.setTextColor(SupportMenu.CATEGORY_MASK);
                }
            } else if (this.currentChildView == 0) {
                this.tvCalendar.setTextColor(getResources().getColor(R.color.colorBlue));
                this.tvWeekDay.setTextColor(getResources().getColor(R.color.colorBlue));
            } else {
                this.tvCalendar1.setTextColor(getResources().getColor(R.color.colorBlue));
                this.tvWeekDay1.setTextColor(getResources().getColor(R.color.colorBlue));
            }
            Calendar c = Calendar.getInstance();
            this.tvHour.setText(c.get(11) + ":" + (c.get(12) < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + c.get(12) : c.get(12) + ""));
            if (checkZodiacHour(this.mDayInfo, 11) == 1) {
                this.imgZodiacHour.setImageResource(R.drawable.hoang_dao);
                this.imgZodiacHour.setVisibility(0);
            } else if (checkZodiacHour(this.mDayInfo, 11) == -1) {
                this.imgZodiacHour.setImageResource(R.drawable.hac_dao);
                this.imgZodiacHour.setVisibility(0);
            } else {
                this.imgZodiacHour.setVisibility(4);
            }
            this.tvLunarHour.setText(getTimeCanChi(this.mDayInfo.getNgay().split(" ")[0], new SimpleDateFormat(DateUtils.TIME_FORMAT1).format(Calendar.getInstance().getTime())));
        }
    }

    public void onClick(View v) {
        v.getId();
    }

    public void onDestroy() {
        super.onDestroy();
        this.newtimer.cancel();
    }

    protected void onSelectedDayPicker(String day) {
    }

    private int checkZodiacHour(DayInfo mDayInfo, int hour) {
        if (mDayInfo == null) {
            return 0;
        }
        if ((hour == 23 || hour == 0) && mDayInfo.getGioHoangDao().contains("Tý")) {
            return 1;
        }
        if ((hour == 23 || hour == 0) && mDayInfo.getGioHacDao().contains("Tý")) {
            return -1;
        }
        if ((hour == 1 || hour == 2) && mDayInfo.getGioHoangDao().contains("Sửu")) {
            return 1;
        }
        if ((hour == 1 || hour == 2) && mDayInfo.getGioHacDao().contains("Sửu")) {
            return -1;
        }
        if ((hour == 3 || hour == 4) && mDayInfo.getGioHoangDao().contains("Dần")) {
            return 1;
        }
        if ((hour == 3 || hour == 4) && mDayInfo.getGioHacDao().contains("Dần")) {
            return -1;
        }
        if ((hour == 5 || hour == 6) && mDayInfo.getGioHoangDao().contains("Mão")) {
            return 1;
        }
        if ((hour == 5 || hour == 6) && mDayInfo.getGioHacDao().contains("Mão")) {
            return -1;
        }
        if ((hour == 7 || hour == 8) && mDayInfo.getGioHoangDao().contains("Thìn")) {
            return 1;
        }
        if ((hour == 7 || hour == 8) && mDayInfo.getGioHacDao().contains("Thìn")) {
            return -1;
        }
        if ((hour == 9 || hour == 10) && mDayInfo.getGioHoangDao().contains("Tỵ")) {
            return 1;
        }
        if ((hour == 9 || hour == 10) && mDayInfo.getGioHacDao().contains("Tỵ")) {
            return -1;
        }
        if ((hour == 11 || hour == 12) && mDayInfo.getGioHoangDao().contains("Ngọ")) {
            return 1;
        }
        if ((hour == 11 || hour == 12) && mDayInfo.getGioHacDao().contains("Ngọ")) {
            return -1;
        }
        if ((hour == 13 || hour == 14) && mDayInfo.getGioHoangDao().contains("Mùi")) {
            return 1;
        }
        if ((hour == 13 || hour == 14) && mDayInfo.getGioHacDao().contains("Mùi")) {
            return -1;
        }
        if ((hour == 15 || hour == 16) && mDayInfo.getGioHoangDao().contains("Thân")) {
            return 1;
        }
        if ((hour == 15 || hour == 16) && mDayInfo.getGioHacDao().contains("Thân")) {
            return -1;
        }
        if ((hour == 17 || hour == 18) && mDayInfo.getGioHoangDao().contains("Dậu")) {
            return 1;
        }
        if ((hour == 17 || hour == 18) && mDayInfo.getGioHacDao().contains("Dậu")) {
            return -1;
        }
        if ((hour == 19 || hour == 20) && mDayInfo.getGioHoangDao().contains("Tuất")) {
            return 1;
        }
        if ((hour == 19 || hour == 20) && mDayInfo.getGioHacDao().contains("Tuất")) {
            return -1;
        }
        if ((hour == 21 || hour == 22) && mDayInfo.getGioHoangDao().contains("Hợi")) {
            return 1;
        }
        if ((hour == 21 || hour == 22) && mDayInfo.getGioHacDao().contains("Hợi")) {
            return -1;
        }
        return 0;
    }

    public void onAnimationEnd(Animation animation) {
    }

    private String getTimeCanChi(String canofDate, String input) {
        int canofHourFirst;
        int chi;
        if (canofDate.equals("Giáp") || canofDate.equals("Kỷ")) {
            canofHourFirst = 0;
        } else if (canofDate.equals("Canh") || canofDate.equals("Ất")) {
            canofHourFirst = 2;
        } else if (canofDate.equals("Bính") || canofDate.equals("Tân")) {
            canofHourFirst = 4;
        } else if (canofDate.equals("Đinh") || canofDate.equals("Nhâm")) {
            canofHourFirst = 6;
        } else {
            canofHourFirst = 8;
        }
        String chi_chu = "";
        int hour = Integer.parseInt(input.split(":")[0]);
        if (hour >= 1 && hour < 3) {
            chi = 2;
            chi_chu = "Sửu";
        } else if (hour >= 3 && hour < 5) {
            chi = 3;
            chi_chu = "Dần";
        } else if (hour >= 5 && hour < 7) {
            chi = 4;
            chi_chu = "Mão";
        } else if (hour >= 7 && hour < 9) {
            chi = 5;
            chi_chu = "Thìn";
        } else if (hour >= 9 && hour < 11) {
            chi = 6;
            chi_chu = "Tỵ";
        } else if (hour >= 11 && hour < 13) {
            chi = 7;
            chi_chu = "Ngọ";
        } else if (hour >= 13 && hour < 15) {
            chi = 8;
            chi_chu = "Mùi";
        } else if (hour >= 15 && hour < 17) {
            chi = 9;
            chi_chu = "Thân";
        } else if (hour >= 17 && hour < 19) {
            chi = 10;
            chi_chu = "Dậu";
        } else if (hour >= 19 && hour < 21) {
            chi = 11;
            chi_chu = "Tuất";
        } else if (hour < 21 || hour >= 23) {
            chi = 1;
            chi_chu = "Tý";
        } else {
            chi = 12;
            chi_chu = "Hợi";
        }
        switch (((canofHourFirst + chi) - 1) % 10) {
            case 0:
                return "Giáp " + chi_chu;
            case 1:
                return "Ất " + chi_chu;
            case 2:
                return "Bính " + chi_chu;
            case 3:
                return "Đinh " + chi_chu;
            case 4:
                return "Mậu " + chi_chu;
            case 5:
                return "Kỷ " + chi_chu;
            case 6:
                return "Canh " + chi_chu;
            case 7:
                return "Tân " + chi_chu;
            case 8:
                return "Nhâm " + chi_chu;
            case 9:
                return "Quý " + chi_chu;
            default:
                return "";
        }
    }

    void initPopupShare(String content, String author) {
        try {
            View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_share, null);
            this.popupWindow = new PopupWindow(popupView, -1, -2);
            this.rlDanhNgon = (RelativeLayout) popupView.findViewById(R.id.rlDanhNgon);
            TextView tvContent = (TextView) popupView.findViewById(R.id.tvContent);
            tvContent.setTypeface(this.typeRegularNew);
            TextView tvAuthor = (TextView) popupView.findViewById(R.id.tvAuthor);
            tvAuthor.setTypeface(this.typeRegularNew);
            tvContent.setText(content.trim());
            if (author.equals("")) {
                author = "Vô Danh";
            }
            tvAuthor.setText(author);
            this.ivBgDanhNgon = (ImageView) popupView.findViewById(R.id.ivBGDanhNgon);
            if (this.application.listBGNew.size() > ((HomeActivity) getActivity()).positionBG) {
                String filePath = (String) this.application.listBGNew.get(((HomeActivity) getActivity()).positionBG);
                if (new File(filePath).exists()) {
                    this.ivBgDanhNgon.setImageBitmap(BitmapFactory.decodeFile(filePath));
                }
            }
            ((RelativeLayout) popupView.findViewById(R.id.rlShare)).setOnClickListener(new OnClickListener() {

                class C12691 implements Runnable {
                    C12691() {
                    }

                    public void run() {
                        FragmentDayNew.this.takeScreenshot(2);
                    }
                }

                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(FragmentDayNew.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        Toast.makeText(FragmentDayNew.this.getActivity(), "Hãy cho phép ứng dụng ghi dữ liệu vào thẻ nhớ.", 0).show();
                        ActivityCompat.requestPermissions(FragmentDayNew.this.getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 222);
                        return;
                    }
                    Toast.makeText(FragmentDayNew.this.getActivity(), "Đang lưu ảnh. Xin vui lòng đợi...", 0).show();
                    new Handler().postDelayed(new C12691(), 300);
                }
            });
            ((RelativeLayout) popupView.findViewById(R.id.rlSave)).setOnClickListener(new OnClickListener() {

                class C12701 implements Runnable {
                    C12701() {
                    }

                    public void run() {
                        FragmentDayNew.this.takeScreenshot(0);
                    }
                }

                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(FragmentDayNew.this.getActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        Toast.makeText(FragmentDayNew.this.getActivity(), "Hãy cho phép ứng dụng ghi dữ liệu vào thẻ nhớ.", 0).show();
                        ActivityCompat.requestPermissions(FragmentDayNew.this.getActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 222);
                        return;
                    }
                    Toast.makeText(FragmentDayNew.this.getActivity(), "Đang lưu ảnh. Xin vui lòng đợi...", 0).show();
                    new Handler().postDelayed(new C12701(), 300);
                }
            });
            ((RelativeLayout) popupView.findViewById(R.id.rlHinhNen)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (FragmentDayNew.this.tempPositionBG == -1) {
                        FragmentDayNew.this.tempPositionBG = ((HomeActivity) FragmentDayNew.this.getActivity()).positionBG;
                    }
                    if (FragmentDayNew.this.tempPositionBG >= FragmentDayNew.this.application.listBGNew.size()) {
                        FragmentDayNew.this.tempPositionBG = 0;
                    } else {
                        FragmentDayNew fragmentDayNew = FragmentDayNew.this;
                        fragmentDayNew.tempPositionBG++;
                    }
                    try {
                        if (FragmentDayNew.this.application.listBGNew.size() > FragmentDayNew.this.tempPositionBG) {
                            String filePath = (String) FragmentDayNew.this.application.listBGNew.get(FragmentDayNew.this.tempPositionBG);
                            if (new File(filePath).exists()) {
                                FragmentDayNew.this.ivBgDanhNgon.setImageBitmap(BitmapFactory.decodeFile(filePath));
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            });
            ((RelativeLayout) popupView.findViewById(R.id.rlCancel)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    FragmentDayNew.this.popupWindow.dismiss();
                }
            });
            ((RelativeLayout) popupView.findViewById(R.id.rlOK)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                }
            });
            this.popupWindow.setBackgroundDrawable(new BitmapDrawable());
            this.popupWindow.setOutsideTouchable(true);
            this.popupWindow.setOnDismissListener(new OnDismissListener() {
                public void onDismiss() {
                    Log.m1446d("dismiss popup share");
                }
            });
            if (this.popupWindow != null && !this.popupWindow.isShowing()) {
                this.popupWindow.showAtLocation(this.btnToday, 17, 0, 0);
            }
        } catch (Exception e) {
            Log.m1447e("XXXXXXXXXX " + e.getMessage());
        }
    }

    private void takeScreenshot(int type) {
        Date now = new Date();
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            new File(Environment.getExternalStorageDirectory().toString() + "/chia_se_danh_ngon/").mkdirs();
            String mPath = Environment.getExternalStorageDirectory().toString() + "/chia_se_danh_ngon/" + now + ".png";
            this.rlDanhNgon.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(this.rlDanhNgon.getDrawingCache());
            this.rlDanhNgon.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            switch (type) {
                case 0:
                    openScreenshot(imageFile);
                    return;
                case 1:
                    Toast.makeText(getActivity(), "Ảnh chụp màn hình đã được lưu vào thẻ nhớ.", 0).show();
                    return;
                case 2:
                    shareImage(imageFile);
                    return;
                default:
                    return;
            }
        } catch (Throwable e) {
            Log.m1447e("error take screenShot: " + e.getMessage());
        }
    }

    private void shareImage(File file) {
        Intent shareIntent = new Intent();
        shareIntent.setAction("android.intent.action.SEND");
        shareIntent.putExtra("android.intent.extra.SUBJECT", "Danh ngôn cuộc sống");
        shareIntent.putExtra("android.intent.extra.TEXT", "Nice :)");
        shareIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(file));
        shareIntent.setType("image/png");
        shareIntent.addFlags(1);
        startActivity(Intent.createChooser(shareIntent, "Chọn lựa ứng dụng chia sẻ"));
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
        startActivity(intent);
        Toast.makeText(getActivity(), "Ảnh chụp màn hình đã được lưu vào thẻ nhớ.", 0).show();
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.onActionFromFragmentHome != null) {
            this.onActionFromFragmentHome.onViewCreated();
        }
    }

    public void setOnActionFromFragmentHome(onActionFromFragmentHome onActionFromFragmentHome) {
        this.onActionFromFragmentHome = onActionFromFragmentHome;
    }
}
