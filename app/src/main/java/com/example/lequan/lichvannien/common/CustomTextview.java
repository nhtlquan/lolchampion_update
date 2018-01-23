package com.example.lequan.lichvannien.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextview extends TextView {
    public CustomTextview(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomTextview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
    }
}
