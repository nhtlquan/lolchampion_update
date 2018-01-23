package com.example.lequan.lichvannien.samsistemas.calendarview.widget;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.R;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.samsistemas.calendarview.decor.DayDecorator;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.CalendarUtility;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.ConvertCalendar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Lunar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Solar;

import io.fabric.sdk.android.services.common.AbstractSpiCall;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CalendarView extends LinearLayout {
    int CLOSE_ENOUGH;
    int DEFAULT_GUTTER_SIZE;
    int INVALID_POINTER;
    int MIN_DISTANCE_FOR_FLING;
    int MIN_FLING_VELOCITY;
    int SCROLL_STATE_DRAGGING;
    int SCROLL_STATE_IDLE;
    int SCROLL_STATE_SETTLING;
    boolean USE_CACHE;
    private ArrayList<EventInfo> listDay;
    private ArrayList<EventInfo> listEvent;
    private int mActivePointerId;
    private ImageView mBackButton;
    private Calendar mCalendar;
    private int mCalendarTitleBackgroundColor;
    private int mCalendarTitleTextColor;
    private int mCloseEnough;
    private Context mContext;
    private int mCurrentDayOfMonth;
    private int mCurrentMonthIndex;
    private int mDayOfWeekTextColor;
    private List<DayDecorator> mDecoratorsList;
    private int mDefaultGutterSize;
    private int mDisabledDayBackgroundColor;
    private int mDisabledDayTextColor;
    private final Runnable mEndScrollRunnable;
    private int mFirstDayOfWeek;
    private int mFlingDistance;
    private GestureDetectorCompat mGestureDetector;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsBeingDragged;
    private boolean mIsCommonDay;
    private boolean mIsOverflowDateVisible;
    private boolean mIsUnableToDrag;
    private float mLastMotionX;
    private float mLastMotionY;
    private Date mLastSelectedDay;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private ImageView mNextButton;
    private OnDateClickListener mOnDateClickListener;
    private OnDateLongClickListener mOnDateLongClickListener;
    private OnMonthChangedListener mOnMonthChangedListener;
    private OnMonthTitleClickListener mOnMonthTitleClickListener;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private int mSelectedDayTextColor;
    private int[] mTotalDayOfWeekend;
    private int mTouchSlop;
    private Typeface mTypeface;
    private VelocityTracker mVelocityTracker;
    private View mView;
    private int mWeekLayoutBackgroundColor;
    private int mWeekend;
    private int mWeekendColor;
    private OnClickListener onDayOfMonthClickListener;
    private OnLongClickListener onDayOfMonthLongClickListener;

    public interface OnDateLongClickListener {
        void onDateLongClick(@NonNull Date date);
    }

    public interface OnDateClickListener {
        void onDateClick(@NonNull Date date);
    }

    public interface OnMonthChangedListener {
        void onMonthChanged(@NonNull Date date);
    }

    class C14431 implements Runnable {
        C14431() {
        }

        public void run() {
            CalendarView.this.setScrollState(CalendarView.this.SCROLL_STATE_IDLE);
        }
    }

    class C14442 implements OnClickListener {
        C14442() {
        }

        public void onClick(View v) {
            CalendarView.this.mCurrentMonthIndex = CalendarView.this.mCurrentMonthIndex - 1;
            CalendarView.this.mCalendar = Calendar.getInstance(Locale.getDefault());
            CalendarView.this.mCalendar.add(2, CalendarView.this.mCurrentMonthIndex);
            CalendarView.this.refreshCalendar(CalendarView.this.mCalendar);
            if (CalendarView.this.mOnMonthChangedListener != null) {
                CalendarView.this.mOnMonthChangedListener.onMonthChanged(CalendarView.this.mCalendar.getTime());
            }
        }
    }

    class C14453 implements OnClickListener {
        C14453() {
        }

        public void onClick(View v) {
            CalendarView.this.mCurrentMonthIndex = CalendarView.this.mCurrentMonthIndex + 1;
            CalendarView.this.mCalendar = Calendar.getInstance(Locale.getDefault());
            CalendarView.this.mCalendar.add(2, CalendarView.this.mCurrentMonthIndex);
            CalendarView.this.refreshCalendar(CalendarView.this.mCalendar);
            if (CalendarView.this.mOnMonthChangedListener != null) {
                CalendarView.this.mOnMonthChangedListener.onMonthChanged(CalendarView.this.mCalendar.getTime());
            }
        }
    }

    class C14464 implements OnClickListener {
        C14464() {
        }

        public void onClick(View v) {
            if (CalendarView.this.mOnMonthTitleClickListener != null) {
                CalendarView.this.mOnMonthTitleClickListener.onMonthTitleClick(CalendarView.this.mCalendar.getTime());
                CalendarView.this.createDialogWithoutDateField(CalendarView.this.mContext);
            }
        }
    }

    class C14486 implements OnLongClickListener {
        C14486() {
        }

        public boolean onLongClick(View view) {
            String tagId = (String) ((ViewGroup) view).getTag();
            TextView dayOfMonthText = (TextView) view.findViewWithTag(CalendarView.this.mContext.getString(R.string.day_of_month_text) + tagId.substring(CalendarView.this.mContext.getString(R.string.day_of_month_container).length(), tagId.length()));
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(CalendarView.this.mFirstDayOfWeek);
            calendar.setTime(CalendarView.this.mCalendar.getTime());
            calendar.set(5, Integer.valueOf(dayOfMonthText.getText().toString()).intValue());
            CalendarView.this.setDateAsSelected(calendar.getTime());
            CalendarView.this.setCurrentDay(CalendarView.this.mCalendar.getTime());
            if (CalendarView.this.mOnDateLongClickListener != null) {
                CalendarView.this.mOnDateLongClickListener.onDateLongClick(calendar.getTime());
            }
            return false;
        }
    }

    class C14497 implements OnClickListener {
        C14497() {
        }

        public void onClick(View view) {
            String tagId = (String) ((ViewGroup) view).getTag();
            TextView dayOfMonthText = (TextView) view.findViewWithTag(CalendarView.this.mContext.getString(R.string.day_of_month_text) + tagId.substring(CalendarView.this.mContext.getString(R.string.day_of_month_container).length(), tagId.length()));
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(CalendarView.this.mFirstDayOfWeek);
            calendar.setTime(CalendarView.this.mCalendar.getTime());
            calendar.set(5, Integer.valueOf(dayOfMonthText.getText().toString()).intValue());
            CalendarView.this.setDateAsSelected(calendar.getTime());
            CalendarView.this.setCurrentDay(CalendarView.this.mCalendar.getTime());
            if (CalendarView.this.mOnDateClickListener != null) {
                CalendarView.this.mOnDateClickListener.onDateClick(calendar.getTime());
            }
        }
    }

    public class CalendarGestureDetector extends SimpleOnGestureListener {
        public boolean onDown(MotionEvent e) {
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(e2.getY() - e1.getY()) && Math.abs(diffX) > ((float) CalendarView.this.mTouchSlop) && Math.abs(velocityX) > ((float) CalendarView.this.mMinimumVelocity) && Math.abs(velocityX) < ((float) CalendarView.this.mMaximumVelocity)) {
                    if (e2.getX() - e1.getX() > ((float) CalendarView.this.mFlingDistance)) {
                        CalendarView.this.mCurrentMonthIndex = CalendarView.this.mCurrentMonthIndex - 1;
                        CalendarView.this.mCalendar = Calendar.getInstance(Locale.getDefault());
                        CalendarView.this.mCalendar.add(2, CalendarView.this.mCurrentMonthIndex);
                        CalendarView.this.refreshCalendar(CalendarView.this.mCalendar);
                        if (CalendarView.this.mOnMonthChangedListener != null) {
                            CalendarView.this.mOnMonthChangedListener.onMonthChanged(CalendarView.this.mCalendar.getTime());
                        }
                    } else if (e1.getX() - e2.getX() > ((float) CalendarView.this.mFlingDistance)) {
                        CalendarView.this.mCurrentMonthIndex = CalendarView.this.mCurrentMonthIndex + 1;
                        CalendarView.this.mCalendar = Calendar.getInstance(Locale.getDefault());
                        CalendarView.this.mCalendar.add(2, CalendarView.this.mCurrentMonthIndex);
                        CalendarView.this.refreshCalendar(CalendarView.this.mCalendar);
                        if (CalendarView.this.mOnMonthChangedListener != null) {
                            CalendarView.this.mOnMonthChangedListener.onMonthChanged(CalendarView.this.mCalendar.getTime());
                        }
                    }
                }
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        }
    }

    public interface OnMonthTitleClickListener {
        void onMonthTitleClick(@NonNull Date date);
    }

    public void setmCurrentMonthIndex(int mCurrentMonthIndex) {
        this.mCurrentMonthIndex = mCurrentMonthIndex;
    }

    public CalendarView(Context context) {
        this(context, null);
        this.mGestureDetector = new GestureDetectorCompat(context, new CalendarGestureDetector());
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.SCROLL_STATE_IDLE = 0;
        this.SCROLL_STATE_DRAGGING = 1;
        this.SCROLL_STATE_SETTLING = 2;
        this.USE_CACHE = false;
        this.MIN_DISTANCE_FOR_FLING = 25;
        this.DEFAULT_GUTTER_SIZE = 16;
        this.MIN_FLING_VELOCITY = 400;
        this.INVALID_POINTER = -1;
        this.CLOSE_ENOUGH = 2;
        this.mActivePointerId = this.INVALID_POINTER;
        this.listDay = new ArrayList();
        this.listEvent = new ArrayList();
        this.mScrollState = this.SCROLL_STATE_IDLE;
        this.mEndScrollRunnable = new C14431();
        this.mDecoratorsList = null;
        this.mIsOverflowDateVisible = true;
        this.mFirstDayOfWeek = 1;
        this.mCurrentMonthIndex = 0;
        this.onDayOfMonthLongClickListener = new C14486();
        this.onDayOfMonthClickListener = new C14497();
        this.mContext = context;
        this.mGestureDetector = new GestureDetectorCompat(context, new CalendarGestureDetector());
        if (VERSION.SDK_INT < 3 || !isInEditMode()) {
            getAttributes(attrs);
            init();
        }
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray a = this.mContext.obtainStyledAttributes(attrs, R.styleable.CalendarView, 0, 0);
        int white = ContextCompat.getColor(this.mContext, 17170443);
        int black = Color.parseColor("#321D5E");
        int dayDisableBackground = ContextCompat.getColor(this.mContext, R.color.day_disabled_background_color);
        int dayDisableTextColor = ContextCompat.getColor(this.mContext, R.color.day_disabled_text_color);
        int daySelectedBackground = ContextCompat.getColor(this.mContext, R.color.selected_day_background);
        int dayCurrent = ContextCompat.getColor(this.mContext, R.color.current_day_of_month);
        try {
            this.mCalendarTitleBackgroundColor = a.getColor(R.styleable.CalendarView_titleLayoutBackgroundColor, white);
            this.mCalendarTitleTextColor = a.getColor(R.styleable.CalendarView_calendarTitleTextColor, black);
            this.mWeekLayoutBackgroundColor = a.getColor(R.styleable.CalendarView_weekLayoutBackgroundColor, white);
            this.mDayOfWeekTextColor = a.getColor(R.styleable.CalendarView_dayOfWeekTextColor, black);
            this.mDisabledDayBackgroundColor = a.getColor(R.styleable.CalendarView_disabledDayBackgroundColor, dayDisableBackground);
            this.mDisabledDayTextColor = a.getColor(R.styleable.CalendarView_disabledDayTextColor, dayDisableTextColor);
            this.mSelectedDayTextColor = a.getColor(R.styleable.CalendarView_selectedDayTextColor, white);
            this.mCurrentDayOfMonth = a.getColor(R.styleable.CalendarView_currentDayOfMonthColor, dayCurrent);
            this.mWeekendColor = a.getColor(R.styleable.CalendarView_weekendColor, SupportMenu.CATEGORY_MASK);
            this.mWeekend = a.getInteger(R.styleable.CalendarView_weekend, 0);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    private void init() {
        this.mScroller = new Scroller(this.mContext, null);
        ViewConfiguration configuration = ViewConfiguration.get(this.mContext);
        float density = this.mContext.getResources().getDisplayMetrics().density;
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        this.mMinimumVelocity = (int) (((float) this.MIN_FLING_VELOCITY) * density);
        this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        this.mFlingDistance = (int) (((float) this.MIN_DISTANCE_FOR_FLING) * density);
        this.mCloseEnough = (int) (((float) this.CLOSE_ENOUGH) * density);
        this.mDefaultGutterSize = (int) (((float) this.DEFAULT_GUTTER_SIZE) * density);
        this.mView = LayoutInflater.from(this.mContext).inflate(R.layout.material_calendar_with_title, this, true);
        this.mBackButton = (ImageView) this.mView.findViewById(R.id.left_button);
        this.mNextButton = (ImageView) this.mView.findViewById(R.id.right_button);
        this.mBackButton.setOnClickListener(new C14442());
        this.mNextButton.setOnClickListener(new C14453());
        setFirstDayOfWeek(1);
        refreshCalendar(Calendar.getInstance(getLocale()));
    }

    public void setListEvent(ArrayList<EventInfo> list) {
        this.listEvent = list;
    }

    private void initTitleLayout() {
        this.mView.findViewById(R.id.title_layout).setBackgroundColor(this.mCalendarTitleBackgroundColor);
        TextView dateTitle = (TextView) this.mView.findViewById(R.id.dateTitle);
        dateTitle.setText(CalendarUtility.getCurrentMonth(this.mCurrentMonthIndex).toUpperCase(Locale.getDefault()) + " " + getCurrentYear());
        dateTitle.setTextColor(this.mCalendarTitleTextColor);
        if (getTypeface() != null) {
            dateTitle.setTypeface(getTypeface(), 1);
        }
        dateTitle.setOnClickListener(new C14464());
    }

    private void initWeekLayout() {
        String[] weekDaysArray = new String[]{"Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "CN"};
        for (int i = 1; i < 8; i++) {
            TextView dayOfWeek = (TextView) this.mView.findViewWithTag(this.mContext.getString(R.string.day_of_week) + i);
            if (i == 7) {
                dayOfWeek.setTextColor(SupportMenu.CATEGORY_MASK);
            }
            dayOfWeek.setText(weekDaysArray[i - 1]);
            if (getTypeface() != null) {
                dayOfWeek.setTypeface(getTypeface());
            }
        }
    }

    private void createDialogWithoutDateField(Context context) {
        this.mCalendar = Calendar.getInstance(Locale.getDefault());
        final int iYear = this.mCalendar.get(1);
        final int iMonth = this.mCalendar.get(2);
        DatePickerDialog dpd = new DatePickerDialog(context, R.style.CalendarViewTitle, new OnDateSetListener() {
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                CalendarView.this.mCurrentMonthIndex = ((year - iYear) * 12) + (monthOfYear - iMonth);
                CalendarView.this.mCalendar.add(2, CalendarView.this.mCurrentMonthIndex);
                CalendarView.this.refreshCalendar(CalendarView.this.mCalendar);
                if (CalendarView.this.mOnMonthChangedListener != null) {
                    CalendarView.this.mOnMonthChangedListener.onMonthChanged(CalendarView.this.mCalendar.getTime());
                }
            }
        }, iYear, iMonth, this.mCalendar.get(5));
        if (VERSION.SDK_INT >= 21) {
            int daySpinnerId = Resources.getSystem().getIdentifier("day", ShareConstants.WEB_DIALOG_PARAM_ID, AbstractSpiCall.ANDROID_CLIENT_TYPE);
            if (daySpinnerId != 0) {
                View daySpinner = dpd.getDatePicker().findViewById(daySpinnerId);
                if (daySpinner != null) {
                    daySpinner.setVisibility(8);
                }
            }
            int monthSpinnerId = Resources.getSystem().getIdentifier("month", ShareConstants.WEB_DIALOG_PARAM_ID, AbstractSpiCall.ANDROID_CLIENT_TYPE);
            if (monthSpinnerId != 0) {
                View monthSpinner = dpd.getDatePicker().findViewById(monthSpinnerId);
                if (monthSpinner != null) {
                    monthSpinner.setVisibility(0);
                }
            }
            int yearSpinnerId = Resources.getSystem().getIdentifier("year", ShareConstants.WEB_DIALOG_PARAM_ID, AbstractSpiCall.ANDROID_CLIENT_TYPE);
            if (yearSpinnerId != 0) {
                View yearSpinner = dpd.getDatePicker().findViewById(yearSpinnerId);
                if (yearSpinner != null) {
                    yearSpinner.setVisibility(0);
                }
            }
        } else {
            for (Field field : dpd.getDatePicker().getClass().getDeclaredFields()) {
                if (field.getName().equals("mDayPicker") || field.getName().equals("mDaySpinner")) {
                    field.setAccessible(true);
                    Object dayPicker = null;
                    try {
                        dayPicker = field.get(dpd.getDatePicker());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((View) dayPicker).setVisibility(8);
                }
                if (field.getName().equals("mMonthPicker") || field.getName().equals("mMonthSpinner")) {
                    field.setAccessible(true);
                    Object monthPicker = null;
                    try {
                        monthPicker = field.get(dpd.getDatePicker());
                    } catch (IllegalAccessException e2) {
                        e2.printStackTrace();
                    }
                    ((View) monthPicker).setVisibility(0);
                }
                if (field.getName().equals("mYearPicker") || field.getName().equals("mYearSpinner")) {
                    field.setAccessible(true);
                    Object yearPicker = null;
                    try {
                        yearPicker = field.get(dpd.getDatePicker());
                    } catch (IllegalAccessException e22) {
                        e22.printStackTrace();
                    }
                    ((View) yearPicker).setVisibility(0);
                }
            }
        }
        dpd.show();
    }

    private void setDaysInCalendar() {
        this.listDay.clear();
        Calendar calendar = Calendar.getInstance(getLocale());
        calendar.setTime(this.mCalendar.getTime());
        calendar.set(5, 1);
        calendar.setFirstDayOfWeek(this.mFirstDayOfWeek);
        int dayOfMonthIndex = CalendarUtility.getWeekIndex(calendar.get(7), calendar);
        int actualMaximum = calendar.getActualMaximum(5);
        Calendar startCalendar = (Calendar) calendar.clone();
        startCalendar.add(5, -(dayOfMonthIndex - 1));
        int monthEndIndex = 42 - ((actualMaximum + dayOfMonthIndex) - 1);
        for (int i = 1; i < 43; i++) {
            ViewGroup dayOfMonthContainer = (ViewGroup) this.mView.findViewWithTag(this.mContext.getString(R.string.day_of_month_container) + i);
            com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView dayView = (com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView) this.mView.findViewWithTag(this.mContext.getString(R.string.day_of_month_text) + i);
            TextView tvLunar = (TextView) this.mView.findViewWithTag(this.mContext.getString(R.string.lunar_of_month_text) + i);
            ImageView imgSpecial = (ImageView) this.mView.findViewWithTag(this.mContext.getString(R.string.image_of_month_text) + i);
            if (dayView != null) {
                dayOfMonthContainer.setOnClickListener(null);
                dayView.bind(startCalendar.getTime(), getDecoratorsList());
                dayView.setVisibility(0);
                String solarDate = new SimpleDateFormat(Define.TIME_FORMAT, Locale.getDefault()).format(startCalendar.getTime());
                Solar mSolar = new Solar();
                String[] arrDate = solarDate.split("/");
                mSolar.solarYear = Integer.parseInt(arrDate[2]);
                mSolar.solarMonth = Integer.parseInt(arrDate[1]);
                mSolar.solarDay = Integer.parseInt(arrDate[0]);
                Lunar mLunar = ConvertCalendar.SolarToLunar(mSolar);
                if (tvLunar != null) {
                    if (mLunar.lunarDay == 1 || mLunar.lunarDay == 15) {
                        tvLunar.setText(mLunar.lunarDay + "/" + mLunar.lunarMonth);
                    } else {
                        tvLunar.setText(String.valueOf(mLunar.lunarDay));
                    }
                }
                boolean isSpecialDay = false;
                Iterator it = this.listEvent.iterator();
                while (it.hasNext()) {
                    String day;
                    String month;
                    String date;
                    String fullDate;
                    EventInfo mSpecialDay = (EventInfo) it.next();
                    if (mSpecialDay.getSolar() == 1) {
                        day = mSolar.solarDay < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + mSolar.solarDay : mSolar.solarDay + "";
                        if (mSolar.solarMonth < 10) {
                            month = AppEventsConstants.EVENT_PARAM_VALUE_NO + mSolar.solarMonth;
                        } else {
                            month = mSolar.solarMonth + "";
                        }
                        date = day + "/" + month;
                        fullDate = (mSolar.solarYear + "") + "-" + month + "-" + day + " 08:00";
                        if (mSpecialDay.getNote() != null) {
                            if (compareRangeDate(fullDate, mSpecialDay.getStart(), mSpecialDay.getEnd()) == 0 && imgSpecial != null) {
                                imgSpecial.setVisibility(0);
                                isSpecialDay = true;
                                if (!this.listDay.contains(mSpecialDay)) {
                                    this.listDay.add(mSpecialDay);
                                }
                            }
                        } else if (mSpecialDay.getStart().equalsIgnoreCase(date) && imgSpecial != null) {
                            imgSpecial.setVisibility(0);
                            isSpecialDay = true;
                            if (!this.listDay.contains(mSpecialDay)) {
                                this.listDay.add(mSpecialDay);
                            }
                        }
                    }
                    if (mSpecialDay.getSolar() == 0) {
                        day = mLunar.lunarDay < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + mLunar.lunarDay : mLunar.lunarDay + "";
                        if (mLunar.lunarMonth < 10) {
                            month = AppEventsConstants.EVENT_PARAM_VALUE_NO + mLunar.lunarMonth;
                        } else {
                            month = mLunar.lunarMonth + "";
                        }
                        fullDate = (mLunar.lunarYear + "") + "-" + month + "-" + day + " 08:00";
                        date = day + "/" + month;
                        if (mSpecialDay.getNote() != null) {
                            if (compareRangeDate(fullDate, mSpecialDay.getStart(), mSpecialDay.getEnd()) == 0 && imgSpecial != null) {
                                imgSpecial.setVisibility(0);
                                isSpecialDay = true;
                                if (!this.listDay.contains(mSpecialDay)) {
                                    this.listDay.add(mSpecialDay);
                                }
                            }
                        } else if (mSpecialDay.getStart().equalsIgnoreCase(date) && imgSpecial != null) {
                            imgSpecial.setVisibility(0);
                            isSpecialDay = true;
                            if (!this.listDay.contains(mSpecialDay)) {
                                this.listDay.add(mSpecialDay);
                            }
                        }
                    }
                }
                if (!isSpecialDay) {
                    imgSpecial.setVisibility(4);
                }
                if (getTypeface() != null) {
                    dayView.setTypeface(getTypeface());
                }
                if (CalendarUtility.isSameMonth(calendar, startCalendar)) {
                    dayOfMonthContainer.setOnClickListener(this.onDayOfMonthClickListener);
                    dayOfMonthContainer.setOnLongClickListener(this.onDayOfMonthLongClickListener);
                    dayOfMonthContainer.setBackgroundResource(R.drawable.bg_white_1);
                    this.mIsCommonDay = true;
                    if (totalDayOfWeekend().length != 0) {
                        for (int weekend : totalDayOfWeekend()) {
                            if (startCalendar.get(7) == weekend) {
                                dayView.setTextColor(this.mWeekendColor);
                                if (tvLunar != null) {
                                    tvLunar.setTextColor(this.mWeekendColor);
                                }
                                this.mIsCommonDay = false;
                            }
                        }
                    }
                    if (this.mIsCommonDay) {
                        dayView.setTextColor(this.mDayOfWeekTextColor);
                        tvLunar.setTextColor(this.mDayOfWeekTextColor);
                    }
                } else {
                    dayOfMonthContainer.setBackgroundResource(R.drawable.bg_white_1);
                    dayView.setTextColor(this.mDisabledDayTextColor);
                    tvLunar.setTextColor(this.mDisabledDayTextColor);
                }
                dayView.decorate();
                if (this.mCalendar.get(2) == startCalendar.get(2)) {
                    setCurrentDay(this.mCalendar.getTime());
                }
                startCalendar.add(5, 1);
                dayOfMonthIndex++;
            }
        }
    }

    private void clearDayOfTheMonthStyle(Date currentDate) {
        if (currentDate != null) {
            Calendar calendar = CalendarUtility.getTodayCalendar(this.mContext, this.mFirstDayOfWeek);
            calendar.setFirstDayOfWeek(this.mFirstDayOfWeek);
            calendar.setTime(currentDate);
            com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView dayView = findViewByCalendar(calendar);
            TextView tvLunar = findViewByCalendarTvLunar(calendar);
            this.mIsCommonDay = true;
            if (totalDayOfWeekend().length != 0) {
                for (int weekend : totalDayOfWeekend()) {
                    if (calendar.get(7) == weekend) {
                        dayView.setTextColor(this.mWeekendColor);
                        tvLunar.setTextColor(this.mWeekendColor);
                        this.mIsCommonDay = false;
                    }
                }
            }
            if (this.mIsCommonDay) {
                dayView.setTextColor(this.mDayOfWeekTextColor);
                tvLunar.setTextColor(this.mDayOfWeekTextColor);
            }
        }
    }

    public com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView findViewByDate(@NonNull Date dateToFind) {
        Calendar calendar = Calendar.getInstance(getLocale());
        calendar.setTime(dateToFind);
        return (com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView) getView(this.mContext.getString(R.string.day_of_month_text), calendar);
    }

    private com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView findViewByCalendar(@NonNull Calendar calendarToFind) {
        return (com.example.lequan.lichvannien.samsistemas.calendarview.widget.DayView) getView(this.mContext.getString(R.string.day_of_month_text), calendarToFind);
    }

    private TextView findViewByCalendarTvLunar(@NonNull Calendar calendarToFind) {
        return (TextView) getView(this.mContext.getString(R.string.lunar_of_month_text), calendarToFind);
    }

    private RelativeLayout findViewByCalendarNew(@NonNull Calendar calendarToFind) {
        return (RelativeLayout) getView(this.mContext.getString(R.string.day_of_month_container), calendarToFind);
    }

    private RelativeLayout findThuHeader(int index) {
        return (RelativeLayout) this.mView.findViewWithTag(this.mContext.getString(R.string.day_of_week_rl) + index);
    }

    private int getDayIndexByDate(Calendar calendar) {
        return calendar.get(5) + CalendarUtility.getMonthOffset(calendar, this.mFirstDayOfWeek);
    }

    private View getView(String key, Calendar currentCalendar) {
        return this.mView.findViewWithTag(key + getDayIndexByDate(currentCalendar));
    }

    public void refreshCalendar(Calendar calendar) {
        this.mCalendar = calendar;
        this.mCalendar.setFirstDayOfWeek(this.mFirstDayOfWeek);
        initTitleLayout();
        setTotalDayOfWeekend();
        initWeekLayout();
        setDaysInCalendar();
    }

    private void setTotalDayOfWeekend() {
        int[] weekendDay = new int[Integer.bitCount(this.mWeekend)];
        char[] days = Integer.toBinaryString(this.mWeekend).toCharArray();
        int day = 1;
        int index = 0;
        for (int i = days.length - 1; i >= 0; i--) {
            if (days[i] == '1') {
                weekendDay[index] = day;
                index++;
            }
            day++;
        }
        this.mTotalDayOfWeekend = weekendDay;
    }

    private int[] totalDayOfWeekend() {
        return this.mTotalDayOfWeekend;
    }

    public void setCurrentDay(@NonNull Date todayDate) {
        Calendar calendar = Calendar.getInstance(getLocale());
        calendar.setTime(todayDate);
        if (CalendarUtility.isToday(calendar)) {
            findViewByCalendarNew(calendar).setBackgroundResource(R.drawable.bg_white_3);
            setSelectThuHeader(calendar);
        }
    }

    public void setDateAsSelected(Date currentDate) {
        Log.v(BaseActivity.TAG, "setDateAsSelected: " + currentDate);
        Calendar currentCalendar = CalendarUtility.getTodayCalendar(this.mContext, this.mFirstDayOfWeek);
        currentCalendar.setFirstDayOfWeek(this.mFirstDayOfWeek);
        currentCalendar.setTime(currentDate);
        clearDayOfTheMonthStyle(this.mLastSelectedDay);
        setLastSelectedDay(currentDate);
        findViewByCalendarNew(currentCalendar).setBackgroundResource(R.drawable.bg_white_3);
        setSelectThuHeader(currentCalendar);
    }

    private void setSelectThuHeader(@NonNull Calendar calendar) {
        int index = getDayIndexByDate(calendar);
        RelativeLayout tv2 = findThuHeader(1);
        RelativeLayout tv3 = findThuHeader(2);
        RelativeLayout tv4 = findThuHeader(3);
        RelativeLayout tv5 = findThuHeader(4);
        RelativeLayout tv6 = findThuHeader(5);
        RelativeLayout tv7 = findThuHeader(6);
        RelativeLayout tvCn = findThuHeader(7);
        tv2.setBackgroundResource(0);
        tv3.setBackgroundResource(0);
        tv4.setBackgroundResource(0);
        tv5.setBackgroundResource(0);
        tv6.setBackgroundResource(0);
        tv7.setBackgroundResource(0);
        tvCn.setBackgroundResource(0);
        if (index == 1 || index == 8 || index == 15 || index == 22 || index == 29 || index == 36) {
            tv2.setBackgroundResource(R.drawable.bg_thu_header);
        } else if (index == 2 || index == 9 || index == 16 || index == 23 || index == 30 || index == 37) {
            tv3.setBackgroundResource(R.drawable.bg_thu_header);
        } else if (index == 3 || index == 10 || index == 17 || index == 24 || index == 31 || index == 38) {
            tv4.setBackgroundResource(R.drawable.bg_thu_header);
        } else if (index == 4 || index == 11 || index == 18 || index == 25 || index == 32 || index == 39) {
            tv5.setBackgroundResource(R.drawable.bg_thu_header);
        } else if (index == 5 || index == 12 || index == 19 || index == 26 || index == 33 || index == 40) {
            tv6.setBackgroundResource(R.drawable.bg_thu_header);
        } else if (index == 6 || index == 13 || index == 20 || index == 27 || index == 34 || index == 41) {
            tv7.setBackgroundResource(R.drawable.bg_thu_header);
        } else {
            tvCn.setBackgroundResource(R.drawable.bg_thu_header);
        }
    }

    private boolean isGutterDrag(float x, float dx) {
        return (x < ((float) this.mDefaultGutterSize) && dx > 0.0f) || (x > ((float) (getWidth() - this.mDefaultGutterSize)) && dx < 0.0f);
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled != enabled) {
            this.mScrollingCacheEnabled = enabled;
            if (this.USE_CACHE) {
                int size = getChildCount();
                for (int i = 0; i < size; i++) {
                    View child = getChildAt(i);
                    if (child.getVisibility() != 8) {
                        child.setDrawingCacheEnabled(enabled);
                    }
                }
            }
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int pointerIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mLastMotionX = MotionEventCompat.getX(ev, newPointerIndex);
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void setScrollState(int newState) {
        if (this.mScrollState != newState) {
            this.mScrollState = newState;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            for (int i = group.getChildCount() - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight() && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()) {
                    if (canScroll(child, true, dx, (x + scrollX) - child.getLeft(), (y + scrollY) - child.getTop())) {
                        return true;
                    }
                }
            }
        }
        return checkV && ViewCompat.canScrollHorizontally(v, -dx);
    }

    private void completeScroll(boolean postEvents) {
        boolean needPopulate;
        if (this.mScrollState == this.SCROLL_STATE_SETTLING) {
            needPopulate = true;
        } else {
            needPopulate = false;
        }
        if (needPopulate) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int oldX = getScrollX();
            int oldY = getScrollY();
            int x = this.mScroller.getCurrX();
            int y = this.mScroller.getCurrY();
            if (!(oldX == x && oldY == y)) {
                scrollTo(x, y);
            }
        }
        if (!needPopulate) {
            return;
        }
        if (postEvents) {
            ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
        } else {
            this.mEndScrollRunnable.run();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.mGestureDetector == null) {
            return super.dispatchTouchEvent(ev);
        }
        this.mGestureDetector.onTouchEvent(ev);
        super.dispatchTouchEvent(ev);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = this.INVALID_POINTER;
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x = ev.getX();
                this.mInitialMotionX = x;
                this.mLastMotionX = x;
                x = ev.getY();
                this.mInitialMotionY = x;
                this.mLastMotionY = x;
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mIsUnableToDrag = false;
                this.mScroller.computeScrollOffset();
                if (this.mScrollState == this.SCROLL_STATE_SETTLING && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(this.SCROLL_STATE_DRAGGING);
                    break;
                }
                completeScroll(false);
                this.mIsBeingDragged = false;
                break;
            case 2:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != this.INVALID_POINTER) {
                    int pointerIndex = MotionEventCompat.findPointerIndex(ev, activePointerId);
                    float x2 = MotionEventCompat.getX(ev, pointerIndex);
                    float dx = x2 - this.mLastMotionX;
                    float xDiff = Math.abs(dx);
                    float y = MotionEventCompat.getY(ev, pointerIndex);
                    float yDiff = Math.abs(y - this.mInitialMotionY);
                    if (dx == 0.0f || isGutterDrag(this.mLastMotionX, dx) || !canScroll(this, false, (int) dx, (int) x2, (int) y)) {
                        if (xDiff <= ((float) this.mTouchSlop) || 0.5f * xDiff <= yDiff) {
                            if (yDiff > ((float) this.mTouchSlop)) {
                                this.mIsUnableToDrag = true;
                                break;
                            }
                        }
                        this.mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        setScrollState(this.SCROLL_STATE_DRAGGING);
                        this.mLastMotionX = dx > 0.0f ? this.mInitialMotionX + ((float) this.mTouchSlop) : this.mInitialMotionX - ((float) this.mTouchSlop);
                        this.mLastMotionY = y;
                        setScrollingCacheEnabled(true);
                        break;
                    }
                    this.mLastMotionX = x2;
                    this.mLastMotionY = y;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                break;
            case 6:
                onSecondaryPointerUp(ev);
                break;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.mGestureDetector.onTouchEvent(event);
    }

    public void setOnMonthTitleClickListener(OnMonthTitleClickListener onMonthTitleClickListener) {
        this.mOnMonthTitleClickListener = onMonthTitleClickListener;
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.mOnDateClickListener = onDateClickListener;
    }

    public void setOnDateLongClickListener(OnDateLongClickListener onDateLongClickListener) {
        this.mOnDateLongClickListener = onDateLongClickListener;
    }

    public void setOnMonthChangedListener(OnMonthChangedListener onMonthChangedListener) {
        this.mOnMonthChangedListener = onMonthChangedListener;
    }

    private void setLastSelectedDay(Date lastSelectedDay) {
        this.mLastSelectedDay = lastSelectedDay;
    }

    public void setTypeface(Typeface typeface) {
        this.mTypeface = typeface;
    }

    public void setDecoratorsList(List<DayDecorator> decoratorsList) {
        this.mDecoratorsList = decoratorsList;
    }

    public void setIsOverflowDateVisible(boolean isOverflowDateVisible) {
        this.mIsOverflowDateVisible = isOverflowDateVisible;
    }

    public void setFirstDayOfWeek(int firstDayOfWeek) {
        this.mFirstDayOfWeek = firstDayOfWeek;
    }

    public void setDisabledDayBackgroundColor(int disabledDayBackgroundColor) {
        this.mDisabledDayBackgroundColor = disabledDayBackgroundColor;
    }

    public void setDisabledDayTextColor(int disabledDayTextColor) {
        this.mDisabledDayTextColor = disabledDayTextColor;
    }

    public void setWeekLayoutBackgroundColor(int weekLayoutBackgroundColor) {
        this.mWeekLayoutBackgroundColor = weekLayoutBackgroundColor;
    }

    public void setCalendarTitleBackgroundColor(int calendarTitleBackgroundColor) {
        this.mCalendarTitleBackgroundColor = calendarTitleBackgroundColor;
    }

    public void setSelectedDayTextColor(int selectedDayTextColor) {
        this.mSelectedDayTextColor = selectedDayTextColor;
    }

    public void setCalendarTitleTextColor(int calendarTitleTextColor) {
        this.mCalendarTitleTextColor = calendarTitleTextColor;
    }

    public void setDayOfWeekTextColor(int dayOfWeekTextColor) {
        this.mDayOfWeekTextColor = dayOfWeekTextColor;
    }

    public void setCurrentDayOfMonth(int currentDayOfMonth) {
        this.mCurrentDayOfMonth = currentDayOfMonth;
    }

    public void setWeekendColor(int weekendColor) {
        this.mWeekendColor = weekendColor;
    }

    public void setWeekend(int weekend) {
        this.mWeekend = weekend;
    }

    public void setBackButtonColor(@ColorRes int colorId) {
        this.mBackButton.setColorFilter(ContextCompat.getColor(this.mContext, colorId), Mode.SRC_ATOP);
    }

    public void setNextButtonColor(@ColorRes int colorId) {
        this.mNextButton.setColorFilter(ContextCompat.getColor(this.mContext, colorId), Mode.SRC_ATOP);
    }

    public void setBackButtonDrawable(@DrawableRes int drawableId) {
        this.mBackButton.setImageDrawable(ContextCompat.getDrawable(this.mContext, drawableId));
    }

    public void setNextButtonDrawable(@DrawableRes int drawableId) {
        this.mNextButton.setImageDrawable(ContextCompat.getDrawable(this.mContext, drawableId));
    }

    public Typeface getTypeface() {
        return this.mTypeface;
    }

    public List<DayDecorator> getDecoratorsList() {
        return this.mDecoratorsList;
    }

    public Locale getLocale() {
        return this.mContext.getResources().getConfiguration().locale;
    }

    public String getCurrentMonth() {
        return CalendarUtility.getCurrentMonth(this.mCurrentMonthIndex);
    }

    public String getCurrentYear() {
        return String.valueOf(this.mCalendar.get(1));
    }

    public boolean isOverflowDateVisible() {
        return this.mIsOverflowDateVisible;
    }

    public ArrayList<EventInfo> getListSpecialDayOfMonth() {
        return this.listDay;
    }

    public int compareRangeDate(String currentDate, String startDate, String endDate) {
        Log.d("hungkm", "compareRangeDate currentDate: " + currentDate);
        Log.d("hungkm", "compareRangeDate startDate: " + startDate);
        Log.d("hungkm", "compareRangeDate endDate: " + endDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date current = sdf.parse(currentDate.split(" ")[0]);
            Date start = sdf.parse(startDate.split(" ")[0]);
            Date end = sdf.parse(endDate.split(" ")[0]);
            if (current.before(start)) {
                return -1;
            }
            if (current.after(end)) {
                return 1;
            }
            return 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
