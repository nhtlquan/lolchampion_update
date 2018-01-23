package com.example.lequan.lichvannien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.lequan.lichvannien.base.ads.Banner;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.BaseFragment;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.activity.PersonalActivity;
import com.example.lequan.lichvannien.activity.UpgradePremiumActivity;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.customInterface.onActionFromFragmentHome;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.CalendarView;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.CalendarView.OnDateClickListener;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.CalendarView.OnDateLongClickListener;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.CalendarView.OnMonthChangedListener;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.example.lequan.lichvannien.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentMonth extends BaseFragment implements OnClickListener {
    private static String TAG = FragmentMonth.class.getSimpleName();
    private CalendarView calendarView;
    private SimpleDateFormat df;
    FrameLayout frame;
    private ArrayList<EventInfo> lsEvent = new ArrayList();
    private DatabaseAccess mDatabaseAccess;
    private onActionFromFragmentHome onActionFromFragmentHome;
    public RelativeLayout rlToDay;
    private String selectDay = "";
    TextView tvMonthHeader;
    View view;

    class C12823 implements OnClickListener {
        C12823() {
        }

        public void onClick(View v) {
            ((HomeActivity) FragmentMonth.this.getActivity()).status = 1;
            ((HomeActivity) FragmentMonth.this.getActivity()).datePicker.setVisibility(0);
        }
    }

    class C12834 implements OnDateLongClickListener {
        C12834() {
        }

        public void onDateLongClick(@NonNull Date selectedDate) {
            SimpleDateFormat df = new SimpleDateFormat(Define.TIME_FORMAT, Locale.getDefault());
        }
    }

    class C12845 implements OnDateClickListener {
        C12845() {
        }

        public void onDateClick(@NonNull Date selectedDate) {
            FragmentMonth.this.selectDay = FragmentMonth.this.df.format(selectedDate);
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTime(selectedDate);
            FragmentMonth.this.calendarView.refreshCalendar(mCalendar);
            FragmentMonth.this.calendarView.setDateAsSelected(selectedDate);
            SimpleDateFormat df = new SimpleDateFormat(Define.TIME_FORMAT, Locale.getDefault());
            String monthYear = df.format(selectedDate);
            if (monthYear.equalsIgnoreCase(Utils.getCurrentDay())) {
                FragmentMonth.this.rlToDay.setVisibility(8);
            } else {
                FragmentMonth.this.rlToDay.setVisibility(0);
            }
            FragmentMonth.this.tvMonthHeader.setText(Utils.getMonthYearNew(df.format(selectedDate)));
            ((BaseActivity) FragmentMonth.this.getActivity()).showDateDetail(monthYear);
        }
    }

    class C12856 implements OnMonthChangedListener {
        C12856() {
        }

        public void onMonthChanged(@NonNull Date monthDate) {
            String monthYear = new SimpleDateFormat("MM/yyyy", Locale.getDefault()).format(monthDate);
            String monthYearNew = "Tháng " + monthYear.split("/")[0] + " - " + monthYear.split("/")[1];
            if (monthYear.equalsIgnoreCase(Utils.getMonthYear(Utils.getCurrentDay()))) {
                FragmentMonth.this.rlToDay.setVisibility(8);
            } else {
                FragmentMonth.this.rlToDay.setVisibility(0);
            }
            FragmentMonth.this.tvMonthHeader.setText(monthYearNew);
        }
    }

    class C12867 implements OnClickListener {
        C12867() {
        }

        public void onClick(View v) {
            FragmentMonth.this.getActivity().startActivityForResult(new Intent(FragmentMonth.this.getActivity(), UpgradePremiumActivity.class), 1221);
        }
    }

    public static FragmentMonth newInstance() {
        return new FragmentMonth();
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
        if (this.view == null || this.mDatabaseAccess == null) {
            this.view = inflater.inflate(R.layout.fragment_month, container, false);
            Init();
        }
        this.frame.addView(this.view);
        return this.frame;
    }

    public void Init() {
        this.mDatabaseAccess = DatabaseAccess.getInstance(getActivity());
        this.mDatabaseAccess.open();
        this.lsEvent = this.mDatabaseAccess.getEvent();
        this.lsEvent.addAll(Define.listSpecial);
        this.calendarView = (CalendarView) this.view.findViewById(R.id.calendar_view);
        this.tvMonthHeader = (TextView) this.view.findViewById(R.id.header_button_center_tv);
        this.tvMonthHeader.setTypeface(this.typeRegularNew);
        final ImageView ivToDay = (ImageView) this.view.findViewById(R.id.btn_today_iv);
        this.rlToDay = (RelativeLayout) this.view.findViewById(R.id.btn_today);
        this.rlToDay.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivToDay.setImageResource(R.drawable.header_hom_nay);
                    FragmentMonth.this.rlToDay.setVisibility(8);
                    Calendar mCalendar = Calendar.getInstance();
                    FragmentMonth.this.calendarView.setmCurrentMonthIndex(0);
                    FragmentMonth.this.calendarView.refreshCalendar(mCalendar);
                    FragmentMonth.this.calendarView.setDateAsSelected(mCalendar.getTime());
                    FragmentMonth.this.tvMonthHeader.setText(Utils.getMonthYearNew(Utils.getCurrentDay()));
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivToDay.setImageResource(R.drawable.header_hom_nay_select);
                    return true;
                }
            }
        });
        this.rlToDay.setVisibility(8);
        final ImageView ivCaNhan = (ImageView) this.view.findViewById(R.id.header_button_right_iv);
        ((RelativeLayout) this.view.findViewById(R.id.header_button_right)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivCaNhan.setImageResource(R.drawable.header_ca_nhan);
                    ((BaseActivity) FragmentMonth.this.getActivity()).changeActivity(true, PersonalActivity.class);
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivCaNhan.setImageResource(R.drawable.header_ca_nhan_select);
                    return true;
                }
            }
        });
        ((RelativeLayout) this.view.findViewById(R.id.header_button_center)).setOnClickListener(new C12823());
        this.tvMonthHeader.setText(Utils.getMonthYearNew(Utils.getCurrentDay()));
        this.calendarView.setFirstDayOfWeek(2);
        this.calendarView.setIsOverflowDateVisible(true);
        this.calendarView.setCurrentDay(new Date(System.currentTimeMillis()));
        this.calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()));
        this.calendarView.setListEvent(this.lsEvent);
        this.calendarView.refreshCalendar(Calendar.getInstance(Locale.getDefault()));
        this.df = new SimpleDateFormat(Define.TIME_FORMAT, Locale.getDefault());
        this.selectDay = this.df.format(Calendar.getInstance().getTime());
        this.calendarView.setOnDateLongClickListener(new C12834());
        this.calendarView.setOnDateClickListener(new C12845());
        this.calendarView.setOnMonthChangedListener(new C12856());
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        } else {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setOnClickListener(new C12867());
        }
    }

    public void reloadAds() {
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        }
        ((Banner) this.view.findViewById(R.id.banner)).reloadAds();
    }

    public void onClick(View v) {
        v.getId();
    }

    public void onSelectedDayPickerNew(String day) {
        this.tvMonthHeader.setText(Utils.getMonthYearNew(day));
        try {
            Date mDate = this.df.parse(day);
            this.selectDay = this.df.format(mDate);
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTime(mDate);
            this.calendarView.refreshCalendar(mCalendar);
            this.calendarView.setDateAsSelected(mDate);
            Calendar tempCalendar = Calendar.getInstance(Locale.getDefault());
            this.calendarView.setmCurrentMonthIndex((((Integer.parseInt(day.split("/")[2]) - tempCalendar.get(1)) * 12) + (Integer.parseInt(day.split("/")[1]) - tempCalendar.get(2))) - 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void onSelectedDayPicker(String day) {
        this.tvMonthHeader.setText(Utils.getMonthYearNew(day));
        try {
            Date mDate = this.df.parse(day);
            this.selectDay = this.df.format(mDate);
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTime(mDate);
            this.calendarView.refreshCalendar(mCalendar);
            this.calendarView.setDateAsSelected(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    protected void getResponse(String jsonObject, String api) {
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
