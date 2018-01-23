package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageThums extends ImageView {
    public ImageThums(Context context) {
        super(context);
    }

    public ImageThums(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageThums(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec * 2);
    }
}
