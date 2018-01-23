package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SquareRelative extends RelativeLayout {
    public SquareRelative(Context context) {
        super(context);
    }

    public SquareRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelative(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
