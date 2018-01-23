package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.lang.reflect.Method;

public class DatePicker extends RelativeLayout {
    int day = 1;
    private onActionFromDatePicker mOnActionFromDatePicker;
    int maxYear = 2029;
    int minYear = 1930;
    int month = 1;
    public NumberPicker numberPickerDay;
    public NumberPicker numberPickerMonth;
    public NumberPicker numberPickerYear;
    boolean setToDay = true;
    Typeface typeBoldNew;
    Typeface typeRegularNew;
    int year = 1990;

    public interface onActionFromDatePicker {
        void onSelect(String str);
    }

    class C12361 implements OnClickListener {
        C12361() {
        }

        public void onClick(View v) {
            DatePicker.this.setVisibility(8);
            DatePicker.this.numberPickerDay.setVisibility(0);
        }
    }

    class C12372 implements OnClickListener {
        C12372() {
        }

        public void onClick(View v) {
            String[] tempCurrentDay = Utils.getCurrentDay().split("/");
            DatePicker.this.numberPickerDay.setValue(Integer.parseInt(tempCurrentDay[0]));
            DatePicker.this.numberPickerMonth.setValue(Integer.parseInt(tempCurrentDay[1]));
            DatePicker.this.numberPickerYear.setValue(Integer.parseInt(tempCurrentDay[2]));
        }
    }

    class C12383 implements OnClickListener {
        C12383() {
        }

        public void onClick(View v) {
            DatePicker.this.setVisibility(8);
            String date = (DatePicker.this.numberPickerDay.getValue() < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + DatePicker.this.numberPickerDay.getValue() : DatePicker.this.numberPickerDay.getValue() + "") + "/" + (DatePicker.this.numberPickerMonth.getValue() < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + DatePicker.this.numberPickerMonth.getValue() : DatePicker.this.numberPickerMonth.getValue() + "") + "/" + DatePicker.this.numberPickerYear.getValue();
            Log.v(BaseActivity.TAG, "date select: " + date);
            if (DatePicker.this.mOnActionFromDatePicker != null) {
                DatePicker.this.mOnActionFromDatePicker.onSelect(date);
            }
            DatePicker.this.numberPickerDay.setVisibility(0);
        }
    }

    class C12394 implements Formatter {
        C12394() {
        }

        public String format(int value) {
            return "Ng. " + value;
        }
    }

    class C12405 implements Formatter {
        C12405() {
        }

        public String format(int value) {
            return "Th. " + value;
        }
    }

    public DatePicker(Context context) {
        super(context);
        initView();
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DatePicker);
            if (a.hasValue(25)) {
                this.maxYear = a.getInt(25, 2029);
            }
            if (a.hasValue(26)) {
                this.minYear = a.getInt(26, 1930);
            }
            if (a.hasValue(27)) {
                this.setToDay = a.getBoolean(27, true);
            }
        }
        initView();
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DatePicker);
            if (a.hasValue(25)) {
                this.maxYear = a.getInt(25, 2029);
            }
            if (a.hasValue(26)) {
                this.minYear = a.getInt(26, 1930);
            }
            if (a.hasValue(27)) {
                this.setToDay = a.getBoolean(27, true);
            }
        }
        initView();
    }

    void initView() {
        this.typeRegularNew = Typeface.createFromAsset(getContext().getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(getContext().getAssets(), "fonts/UTM HelveBold.ttf");
        View mView = inflate(getContext(), R.layout.date_picker, null);
        addView(mView);
        ((RelativeLayout) mView.findViewById(R.id.rlCancel)).setOnClickListener(new C12361());
        ((TextView) findViewById(R.id.btToDayDatePicker)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.btToDayDatePicker)).setOnClickListener(new C12372());
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setOnClickListener(new C12383());
        this.numberPickerDay = (NumberPicker) findViewById(R.id.numberPickerDay);
        this.numberPickerDay.setFormatter(new C12394());
        this.numberPickerDay.setMaxValue(31);
        this.numberPickerDay.setMinValue(1);
        this.numberPickerDay.setWrapSelectorWheel(false);
        this.numberPickerMonth = (NumberPicker) findViewById(R.id.numberPickerMonth);
        this.numberPickerMonth.setFormatter(new C12405());
        this.numberPickerMonth.setMaxValue(12);
        this.numberPickerMonth.setMinValue(1);
        this.numberPickerMonth.setWrapSelectorWheel(false);
        this.numberPickerYear = (NumberPicker) findViewById(R.id.numberPickerYear);
        this.numberPickerYear.setMaxValue(this.maxYear);
        this.numberPickerYear.setMinValue(this.minYear);
        this.numberPickerYear.setWrapSelectorWheel(false);
        try {
            Method method = this.numberPickerDay.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerDay, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e) {
        }
        try {
            Method   method = this.numberPickerMonth.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerMonth, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e2) {
        }
        try {
            Method  method = this.numberPickerYear.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerYear, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e3) {
        }
        if (this.setToDay) {
            String[] tempCurrentDay = Utils.getCurrentDay().split("/");
            this.numberPickerDay.setValue(Integer.parseInt(tempCurrentDay[0]));
            this.numberPickerMonth.setValue(Integer.parseInt(tempCurrentDay[1]));
            this.numberPickerYear.setValue(Integer.parseInt(tempCurrentDay[2]));
            return;
        }
        this.numberPickerDay.setValue(this.day);
        this.numberPickerMonth.setValue(this.month);
        this.numberPickerYear.setValue(this.year);
    }

    public void setOnActionFromDatePicker(onActionFromDatePicker listener) {
        this.mOnActionFromDatePicker = listener;
    }
}
