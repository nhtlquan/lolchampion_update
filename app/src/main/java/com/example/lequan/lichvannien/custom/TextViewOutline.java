package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.TextView;
import com.example.lequan.lichvannien.R;

public class TextViewOutline extends TextView {
    private static final int DEFAULT_OUTLINE_COLOR = 0;
    private static final int DEFAULT_OUTLINE_SIZE = 0;
    private int mOutlineColor;
    private int mOutlineSize;
    private int mShadowColor;
    private float mShadowDx;
    private float mShadowDy;
    private float mShadowRadius;
    private int mTextColor;

    public TextViewOutline(Context context) {
        this(context, null);
    }

    public TextViewOutline(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttributes(attrs);
    }

    private void setAttributes(AttributeSet attrs) {
        this.mOutlineSize = 0;
        this.mOutlineColor = 0;
        this.mTextColor = getCurrentTextColor();
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ActionBar);
            if (a.hasValue(4)) {
                this.mOutlineSize = (int) a.getDimension(4, 0.0f);
            }
            if (a.hasValue(5)) {
                this.mOutlineColor = a.getColor(5, 0);
            }
            if (a.hasValue(3) || a.hasValue(1) || a.hasValue(2) || a.hasValue(0)) {
                this.mShadowRadius = a.getFloat(3, 0.0f);
                this.mShadowDx = a.getFloat(1, 0.0f);
                this.mShadowDy = a.getFloat(2, 0.0f);
                this.mShadowColor = a.getColor(0, 0);
            }
        }
    }

    private void setPaintToOutline() {
        Paint paint = getPaint();
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth((float) this.mOutlineSize);
        super.setTextColor(this.mOutlineColor);
        super.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, this.mShadowColor);
    }

    private void setPaintToRegular() {
        Paint paint = getPaint();
        paint.setStyle(Style.FILL);
        paint.setStrokeWidth(0.0f);
        super.setTextColor(this.mTextColor);
        super.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setPaintToOutline();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setTextColor(int color) {
        super.setTextColor(color);
        this.mTextColor = color;
    }

    public void setShadowLayer(float radius, float dx, float dy, int color) {
        super.setShadowLayer(radius, dx, dy, color);
        this.mShadowRadius = radius;
        this.mShadowDx = dx;
        this.mShadowDy = dy;
        this.mShadowColor = color;
    }

    public void setOutlineSize(int size) {
        this.mOutlineSize = size;
    }

    public void setOutlineColor(int color) {
        this.mOutlineColor = color;
    }

    protected void onDraw(Canvas canvas) {
        setPaintToOutline();
        super.onDraw(canvas);
        setPaintToRegular();
        super.onDraw(canvas);
    }
}
