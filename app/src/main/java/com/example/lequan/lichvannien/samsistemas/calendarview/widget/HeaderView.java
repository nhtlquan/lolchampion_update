package com.example.lequan.lichvannien.samsistemas.calendarview.widget;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lequan.lichvannien.R;

public class HeaderView extends RelativeLayout {
    private static final String LOG = HeaderView.class.getSimpleName();
    private int currentMonthIndex;
    private ImageView mBackButton;
    private TextView mMonthTitleView;
    private ImageView mNextButton;

    public HeaderView(Context context) {
        this(context, null, 0);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleArr) {
        super(context, attrs, defStyleArr);
        this.currentMonthIndex = 0;
        if (VERSION.SDK_INT < 3 || !isInEditMode()) {
            getAttributes(context, attrs);
            init(context);
        }
    }

    private void getAttributes(Context context, AttributeSet attrs) {
    }

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.material_calendar_title, this, false);
        initBackButton(root);
        initMonthTitleView(root);
        initNextButton(root);
    }

    private void initMonthTitleView(View root) {
        if (root == null) {
        }
    }

    private void initBackButton(View root) {
        if (root == null) {
        }
    }

    private void initNextButton(View root) {
        if (root == null) {
        }
    }
}
