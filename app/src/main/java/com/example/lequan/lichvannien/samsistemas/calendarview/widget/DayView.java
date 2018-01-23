package com.example.lequan.lichvannien.samsistemas.calendarview.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.lequan.lichvannien.samsistemas.calendarview.decor.DayDecorator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayView extends TextView {
    private Date mDate;
    private List<DayDecorator> mDayDecoratorList;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (VERSION.SDK_INT >= 3 && !isInEditMode()) {
        }
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.mDayDecoratorList = decorators;
        this.mDate = date;
        setText(String.valueOf(Integer.parseInt(new SimpleDateFormat("d", Locale.getDefault()).format(date))));
    }

    public void decorate() {
        if (this.mDayDecoratorList != null) {
            for (DayDecorator decorator : this.mDayDecoratorList) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return this.mDate;
    }
}
