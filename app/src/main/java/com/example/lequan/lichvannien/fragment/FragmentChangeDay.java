package com.example.lequan.lichvannien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.example.lequan.lichvannien.base.ads.Banner;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.BaseFragment;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.activity.UpgradePremiumActivity;
import com.example.lequan.lichvannien.adapter.BestDayAdapter;
import com.example.lequan.lichvannien.adapter.BestDayAdapter.ClickNgayTotListener;
import com.example.lequan.lichvannien.customInterface.onActionFromFragmentHome;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.utils.NSDialog;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.ConvertCalendar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Lunar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Solar;
import com.example.lequan.lichvannien.R;

import java.util.ArrayList;
import java.util.Calendar;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

public class FragmentChangeDay extends BaseFragment implements OnClickListener {
    private Button btnDetail;
    Button btnSearch;
    TextView btnTime;
    TextView btnWork;
    private String date = "";
    FrameLayout frame;
    private RelativeLayout layoutBestDay;
    private RelativeLayout layoutChangeDay;
    private String[] listWork = new String[]{"Cầu tài", "Khai trương", "Xuất hành", "Giao dịch", "Ký hợp đồng", "Cầu phúc", "Tế tự", "Động thổ", "Tố tụng", "Giải oan", "Hôn thú", "Giá thú", "Lộc", "Di chuyển", "An táng", "Mai táng", "Xây dựng", "Sửa nhà", "Khởi tạo"};
    private ArrayList<String> listbestDay = new ArrayList();
    WheelView lunarDay;
    WheelView lunarMonth;
    WheelView lunarYear;
    private onActionFromFragmentHome onActionFromFragmentHome;
    private RecyclerView rcBestDay;
    OnWheelScrollListener scrolledListener = new C12685();
    RelativeLayout rootBestDay;
    private WheelView solarDay;
    private WheelView solarMonth;
    private WheelView solarYear;
    ScrollView svRootChangeDay;
    long timeDelayClick = 0;
    private TextView tvHeader;
    View view;
    private boolean wheelScrolled = false;
    private String work = "";

    class C12641 implements OnClickListener {
        C12641() {
        }

        public void onClick(View v) {
            FragmentChangeDay.this.showChooseSingle("Chọn công việc: ", FragmentChangeDay.this.listWork);
        }
    }

    class C12652 implements OnClickListener {
        C12652() {
        }

        public void onClick(View v) {
            ((HomeActivity) FragmentChangeDay.this.getActivity()).status = 2;
            ((HomeActivity) FragmentChangeDay.this.getActivity()).datePicker.numberPickerDay.setVisibility(8);
            ((HomeActivity) FragmentChangeDay.this.getActivity()).datePicker.setVisibility(0);
        }
    }

    class C12663 implements OnClickListener {
        C12663() {
        }

        public void onClick(View v) {
            FragmentChangeDay.this.getActivity().startActivityForResult(new Intent(FragmentChangeDay.this.getActivity(), UpgradePremiumActivity.class), 1221);
        }
    }

    class C12674 implements ClickNgayTotListener {
        C12674() {
        }

        public void clickNgayTot(String day) {
            int day1 = Integer.parseInt(day.trim());
            int month1 = FragmentChangeDay.this.solarMonth.getCurrentItem() + 1;
            ((BaseActivity) FragmentChangeDay.this.getActivity()).showDateDetail((day1 < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + day1 + "/" : day1 + "/") + (month1 < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + month1 + "/" : month1 + "/") + (FragmentChangeDay.this.solarYear.getCurrentItem() + 1888));
        }
    }

    class C12685 implements OnWheelScrollListener {
        C12685() {
        }

        public void onScrollingStarted(WheelView wheel) {
            FragmentChangeDay.this.wheelScrolled = true;
        }

        public void onScrollingFinished(WheelView wheel) {
            FragmentChangeDay.this.wheelScrolled = false;
            if (!FragmentChangeDay.this.isSameDay()) {
                try {
                    Solar mSolar;
                    Lunar mLunar;
                    if (wheel == FragmentChangeDay.this.solarDay || wheel == FragmentChangeDay.this.solarMonth || wheel == FragmentChangeDay.this.solarYear) {
                        mSolar = new Solar();
                        mSolar.solarDay = FragmentChangeDay.this.solarDay.getCurrentItem() + 1;
                        mSolar.solarMonth = FragmentChangeDay.this.solarMonth.getCurrentItem() + 1;
                        mSolar.solarYear = FragmentChangeDay.this.solarYear.getCurrentItem() + 1888;
                        mLunar = ConvertCalendar.SolarToLunar(mSolar);
                        FragmentChangeDay.this.updateLunarDays(mLunar.lunarDay, mLunar.lunarMonth, mLunar.lunarYear, mSolar);
                        Log.v(BaseActivity.TAG, "mLunar: " + mLunar.toString() + " mSolar: " + mSolar.toString());
                    } else if (wheel == FragmentChangeDay.this.lunarDay || wheel == FragmentChangeDay.this.lunarMonth || wheel == FragmentChangeDay.this.lunarYear) {
                        mLunar = new Lunar();
                        mLunar.lunarDay = FragmentChangeDay.this.lunarDay.getCurrentItem() + 1;
                        mLunar.lunarMonth = FragmentChangeDay.this.lunarMonth.getCurrentItem() + 1;
                        mLunar.lunarYear = FragmentChangeDay.this.lunarYear.getCurrentItem() + 1888;
                        mSolar = ConvertCalendar.LunarToSolar(mLunar);
                        FragmentChangeDay.this.updateSolarDays(mSolar.solarDay, mSolar.solarMonth, mSolar.solarYear, mSolar);
                        Log.v(BaseActivity.TAG, "mLunar: " + mLunar.toString() + " mSolar: " + mSolar.toString());
                    }
                } catch (Exception e) {
                    Log.e(BaseActivity.TAG, "error ConvertCalendar lib: " + e.getMessage());
                }
            }
        }
    }

    public static FragmentChangeDay newInstance() {
        return new FragmentChangeDay();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.frame != null) {
            this.frame.removeAllViews();
            this.frame = null;
        }
        this.frame = new FrameLayout(getActivity());
        if (this.view == null) {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            this.view = inflater.inflate(R.layout.fragment_change_day, container, false);
            Init();
        }
        this.frame.addView(this.view);
        return this.frame;
    }

    public void Init() {
        ((TextView) this.view.findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.tv_changeday)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.tv_best_day)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.tvDuongLichTitle)).setTypeface(this.typeBoldNew);
        ((TextView) this.view.findViewById(R.id.tvAmLichTitle)).setTypeface(this.typeBoldNew);
        this.layoutChangeDay = (RelativeLayout) this.view.findViewById(R.id.tab_changeday);
        this.layoutBestDay = (RelativeLayout) this.view.findViewById(R.id.tab_best_day);
        this.svRootChangeDay = (ScrollView) this.view.findViewById(R.id.root_changeday);
        this.rootBestDay = (RelativeLayout) this.view.findViewById(R.id.root_bestday);
        this.btnDetail = (Button) this.view.findViewById(R.id.btn_detail);
        this.btnWork = (TextView) this.view.findViewById(R.id.btn_work);
        this.btnWork.setTypeface(this.typeRegularNew);
        this.btnTime = (TextView) this.view.findViewById(R.id.btn_time);
        this.btnTime.setTypeface(this.typeRegularNew);
        this.btnSearch = (Button) this.view.findViewById(R.id.btn_search);
        this.rcBestDay = (RecyclerView) this.view.findViewById(R.id.rc_best_day);
        this.tvHeader = (TextView) this.view.findViewById(R.id.tv_header);
        this.tvHeader.setTypeface(this.typeRegularNew);
        this.solarDay = (WheelView) this.view.findViewById(R.id.solar_day);
        this.solarMonth = (WheelView) this.view.findViewById(R.id.solar_month);
        this.solarYear = (WheelView) this.view.findViewById(R.id.solar_year);
        this.lunarDay = (WheelView) this.view.findViewById(R.id.lunar_day);
        this.lunarMonth = (WheelView) this.view.findViewById(R.id.lunar_month);
        this.lunarYear = (WheelView) this.view.findViewById(R.id.lunar_year);
        this.solarMonth.setViewAdapter(new NumericWheelAdapter(getActivity(), 1, 12));
        this.solarYear.setViewAdapter(new NumericWheelAdapter(getActivity(), 1888, 2100));
        Solar mSolar = new Solar();
        mSolar.solarDay = Integer.parseInt(Utils.getTime(0, Utils.getCurrentDay()));
        mSolar.solarMonth = Integer.parseInt(Utils.getTime(1, Utils.getCurrentDay()));
        mSolar.solarYear = Integer.parseInt(Utils.getTime(2, Utils.getCurrentDay()));
        updateSolarDays(mSolar.solarDay, mSolar.solarMonth, mSolar.solarYear, mSolar);
        Lunar mLunar = ConvertCalendar.SolarToLunar(mSolar);
        this.lunarDay.setViewAdapter(new NumericWheelAdapter(getActivity(), 1, 30));
        this.lunarMonth.setViewAdapter(new NumericWheelAdapter(getActivity(), 1, 12));
        this.lunarYear.setViewAdapter(new NumericWheelAdapter(getActivity(), 1888, 2100));
        updateLunarDays(mLunar.lunarDay, mLunar.lunarMonth, mLunar.lunarYear, mSolar);
        this.solarDay.addScrollingListener(this.scrolledListener);
        this.solarMonth.addScrollingListener(this.scrolledListener);
        this.solarYear.addScrollingListener(this.scrolledListener);
        this.lunarDay.addScrollingListener(this.scrolledListener);
        this.lunarMonth.addScrollingListener(this.scrolledListener);
        this.lunarYear.addScrollingListener(this.scrolledListener);
        this.layoutChangeDay.setOnClickListener(this);
        this.layoutBestDay.setOnClickListener(this);
        this.btnDetail.setOnClickListener(this);
        this.btnSearch.setOnClickListener(this);
        this.date = Utils.getMonthYear(Utils.getCurrentDay());
        ((RelativeLayout) this.view.findViewById(R.id.rl_work)).setOnClickListener(new C12641());
        ((RelativeLayout) this.view.findViewById(R.id.rl_Time)).setOnClickListener(new C12652());
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        } else {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setOnClickListener(new C12663());
        }
    }

    public void reloadAds() {
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        }
        ((Banner) this.view.findViewById(R.id.banner)).reloadAds();
    }

    void updateSolarDays(int day, int month, int year, Solar mSolar) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, mSolar.solarYear);
        calendar.set(2, mSolar.solarMonth - 1);
        this.solarDay.setViewAdapter(new NumericWheelAdapter(getActivity(), 1, calendar.getActualMaximum(5)));
        this.solarDay.setCurrentItem(day - 1);
        this.solarMonth.setCurrentItem(month - 1);
        this.solarYear.setCurrentItem(year - 1888);
    }

    void updateLunarDays(int day, int month, int year, Solar mSolar) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, mSolar.solarYear);
        calendar.set(2, mSolar.solarMonth - 1);
        this.solarDay.setViewAdapter(new NumericWheelAdapter(getActivity(), 1, calendar.getActualMaximum(5)));
        this.lunarDay.setViewAdapter(new NumericWheelAdapter(getActivity(), 1, 30));
        this.lunarDay.setCurrentItem(day - 1);
        this.lunarMonth.setCurrentItem(month - 1);
        this.lunarYear.setCurrentItem(year - 1888);
    }

    private boolean isSameDay() {
        try {
            Solar mSolar = new Solar();
            mSolar.solarDay = this.solarDay.getCurrentItem();
            mSolar.solarMonth = this.solarMonth.getCurrentItem();
            mSolar.solarYear = this.solarYear.getCurrentItem() + 1887;
            Lunar mLunar = new Lunar();
            mLunar.lunarDay = this.lunarDay.getCurrentItem();
            mLunar.lunarMonth = this.lunarMonth.getCurrentItem();
            mLunar.lunarYear = this.lunarYear.getCurrentItem() + 1887;
            Lunar temp = ConvertCalendar.SolarToLunar(mSolar);
            if (mLunar.lunarDay == temp.lunarDay && mLunar.lunarMonth == temp.lunarMonth && mLunar.lunarYear == temp.lunarYear) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Log.e(BaseActivity.TAG, "error isSameDay:" + e.getMessage());
            return true;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_changeday:
                this.svRootChangeDay.setVisibility(0);
                this.rootBestDay.setVisibility(8);
                this.layoutChangeDay.setBackgroundResource(R.drawable.bg_doingay_select_tab_selected_left);
                this.layoutBestDay.setBackgroundResource(R.drawable.bg_doingay_select_tab_noselected);
                return;
            case R.id.tab_best_day:
                this.svRootChangeDay.setVisibility(8);
                this.rootBestDay.setVisibility(0);
                this.layoutBestDay.setBackgroundResource(R.drawable.bg_doingay_select_tab_selected_right);
                this.layoutChangeDay.setBackgroundResource(R.drawable.bg_doingay_select_tab_noselected);
                return;
            case R.id.btn_search:
                if (this.work.length() == 0) {
                    NSDialog.showDialogBasic((BaseActivity) getActivity(), "Vui lòng chọn việc cần làm");
                    return;
                }
                ArrayList<String> listBestDay = new ArrayList();
                DatabaseAccess mDatabaseAccess = DatabaseAccess.getInstance(getActivity());
                mDatabaseAccess.open();
                listBestDay.addAll(mDatabaseAccess.getSpecialDay(this.date, this.work));
                this.tvHeader.setVisibility(0);
                if (listBestDay.size() > 0) {
                    this.tvHeader.setText("Ngày đẹp " + this.work.toUpperCase() + " trong tháng " + this.date.toUpperCase());
                    this.rcBestDay.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                    BestDayAdapter mBestDayAdapter = new BestDayAdapter(getActivity(), listBestDay);
                    mBestDayAdapter.setClickNgayTotListener(new C12674());
                    this.rcBestDay.setAdapter(mBestDayAdapter);
                    this.rcBestDay.setVisibility(0);
                    return;
                }
                this.rcBestDay.setVisibility(8);
                this.tvHeader.setText("Không tìm được ngày đẹp.");
                return;
            case R.id.btn_detail:
                if (Calendar.getInstance().getTimeInMillis() - this.timeDelayClick >= 1000) {
                    String strMonth;
                    this.timeDelayClick = Calendar.getInstance().getTimeInMillis();
                    int day = this.solarDay.getCurrentItem() + 1;
                    int month = this.solarMonth.getCurrentItem() + 1;
                    int year = this.solarYear.getCurrentItem() + 1888;
                    String strDay = day < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + day + "/" : day + "/";
                    if (month < 10) {
                        strMonth = AppEventsConstants.EVENT_PARAM_VALUE_NO + month + "/";
                    } else {
                        strMonth = month + "/";
                    }
                    ((BaseActivity) getActivity()).showDateDetail(strDay + strMonth + year);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onSelectedDayPicker(String day) {
        this.date = Utils.getMonthYear(day);
        this.btnTime.setText(this.date);
    }

    public void onSelectedDayPickerNew(String day) {
        this.date = Utils.getMonthYear(day);
        this.btnTime.setText(this.date);
    }

    protected void onChoise(String value, int index) {
        this.work = value;
        this.btnWork.setText(value);
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
