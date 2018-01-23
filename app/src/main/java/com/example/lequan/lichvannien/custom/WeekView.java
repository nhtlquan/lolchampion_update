package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.rey.material.util.ThemeUtil;
import com.rey.material.widget.CircleCheckedTextView;
import com.rey.material.widget.CircleCheckedTextView.OnCheckedChangeListener;
import com.example.lequan.lichvannien.R;

public class WeekView extends FrameLayout {
    private static final String BASE_TEXT = "WWW";
    private ColorStateList mBackgroundColors;
    private OnCheckedChangeListener mCheckListener = new C12611();
    private OnClickListener mClickListener = new C12622();
    private int mCurrentBackgroundColor;
    private int mFirstDayOfWeek;
    private int mHorizontalPadding;
    private OnDaySelectionChangedListener mOnDaySelectionChangedListener;
    private float mOriginalTextSize;
    private Paint mPaint;
    private int mVerticalPadding;

    class C12611 implements OnCheckedChangeListener {
        C12611() {
        }

        public void onCheckedChanged(CircleCheckedTextView view, boolean checked) {
            WeekView.this.onDaySelectionChanged(((Integer) view.getTag()).intValue(), checked);
        }
    }

    class C12622 implements OnClickListener {
        C12622() {
        }

        public void onClick(View v) {
            CircleCheckedTextView child = (CircleCheckedTextView) v;
            child.setChecked(!child.isChecked());
        }
    }

    public interface OnDaySelectionChangedListener {
        void onDaySelectionChanged(int i, boolean z);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C12631();
        int selected;

        static class C12631 implements Creator<SavedState> {
            C12631() {
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.selected = in.readInt();
        }

        public void writeToParcel(@NonNull Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.selected);
        }

        public String toString() {
            return "WeekView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + ";selected=" + this.selected + "}";
        }
    }

    public WeekView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public WeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public WeekView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mPaint = new Paint(1);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar, defStyleAttr, defStyleRes);
        this.mVerticalPadding = a.getDimensionPixelOffset(1, ThemeUtil.dpToPx(context, 8));
        this.mHorizontalPadding = a.getDimensionPixelOffset(2, ThemeUtil.dpToPx(context, 8));
        int animDuration = a.getInteger(3, context.getResources().getInteger(17694721));
        this.mBackgroundColors = a.getColorStateList(0);
        a.recycle();
        if (this.mBackgroundColors == null) {
            int[][]  states = new int[2][];
            states[0] = new int[]{-16842910};
            states[1] = new int[]{16842910};
            this.mBackgroundColors = new ColorStateList(states, new int[]{ThemeUtil.colorControlNormal(context, -16777216), ThemeUtil.colorControlActivated(context, -16777216)});
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setEnabled(enabled);
        }
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state = super.onCreateDrawableState(extraSpace);
        updateBackgroundColor(state);
        return state;
    }

    public void refreshDrawableState() {
        updateBackgroundColor(getDrawableState());
        super.refreshDrawableState();
    }

    private void updateBackgroundColor(int[] state) {
        int color = this.mBackgroundColors.getColorForState(state, this.mBackgroundColors.getDefaultColor());
        if (this.mCurrentBackgroundColor != color) {
            this.mCurrentBackgroundColor = color;
            for (int i = 0; i < getChildCount(); i++) {
                ((CircleCheckedTextView) getChildAt(i)).setBackgroundColor(color);
            }
            invalidate();
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childSize;
        int width;
        int height;
        boolean isPortrait = true;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (getContext().getResources().getConfiguration().orientation != 1) {
            isPortrait = false;
        }
        if (isPortrait) {
            childSize = (((widthSize - getPaddingLeft()) - getPaddingRight()) - (this.mHorizontalPadding * 3)) / 4;
            width = widthSize;
            height = (((childSize * 2) + this.mVerticalPadding) + getPaddingTop()) + getPaddingBottom();
        } else {
            childSize = (((widthSize - getPaddingLeft()) - getPaddingRight()) - (this.mHorizontalPadding * 6)) / 7;
            width = widthSize;
            height = (getPaddingTop() + childSize) + getPaddingBottom();
        }
        int spec = MeasureSpec.makeMeasureSpec(childSize, 1073741824);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(spec, spec);
        }
        setMeasuredDimension(width, height);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mPaint.setTextSize(this.mOriginalTextSize);
        float baseWidth = this.mPaint.measureText(BASE_TEXT);
        float realWidth = (float) (getChildAt(0).getMeasuredWidth() - this.mHorizontalPadding);
        if (realWidth < baseWidth) {
            float textSize = (this.mOriginalTextSize * realWidth) / baseWidth;
            for (int i = 0; i < getChildCount(); i++) {
                ((CircleCheckedTextView) getChildAt(i)).setTextSize(0, textSize);
            }
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        boolean isPortrait = true;
        if (getContext().getResources().getConfiguration().orientation != 1) {
            isPortrait = false;
        }
        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();
        for (int i = 0; i < 7; i++) {
            View v = getChildAt(i);
            v.layout(childLeft, childTop, v.getMeasuredWidth() + childLeft, v.getMeasuredHeight() + childTop);
            if (i == 3 && isPortrait) {
                childLeft = getPaddingLeft();
                childTop = (getPaddingTop() + v.getMeasuredHeight()) + this.mVerticalPadding;
            } else {
                childLeft += v.getMeasuredWidth() + this.mHorizontalPadding;
            }
        }
    }

    public void clearSelection(boolean immediately) {
        for (int i = 0; i < getChildCount(); i++) {
            if (immediately) {
                ((CircleCheckedTextView) getChildAt(i)).setCheckedImmediately(false);
            } else {
                ((CircleCheckedTextView) getChildAt(i)).setChecked(false);
            }
        }
    }

    public void setSelected(int dayOfWeek, boolean selected, boolean immediately) {
        CircleCheckedTextView view = (CircleCheckedTextView) getChildAt(dayOfWeek >= this.mFirstDayOfWeek ? dayOfWeek - this.mFirstDayOfWeek : (dayOfWeek + 7) - this.mFirstDayOfWeek);
        if (immediately) {
            view.setCheckedImmediately(selected);
        } else {
            view.setChecked(selected);
        }
    }

    public boolean isSelected(int dayOfWeek) {
        return ((CircleCheckedTextView) getChildAt(dayOfWeek >= this.mFirstDayOfWeek ? dayOfWeek - this.mFirstDayOfWeek : (dayOfWeek + 7) - this.mFirstDayOfWeek)).isChecked();
    }

    public void setOnDaySelectionChangedListener(OnDaySelectionChangedListener listener) {
        this.mOnDaySelectionChangedListener = listener;
    }

    private void onDaySelectionChanged(int dayOfWeek, boolean selected) {
        if (this.mOnDaySelectionChangedListener != null) {
            this.mOnDaySelectionChangedListener.onDaySelectionChanged(dayOfWeek, selected);
        }
    }

    protected Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.selected = 0;
        int mask = 1;
        for (int i = 1; i <= 7; i++) {
            if (isSelected(i)) {
                ss.selected += mask;
            }
            mask <<= 1;
        }
        return ss;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        int val = ss.selected;
        for (int i = 0; i < 7; i++) {
            setSelected(i + 1, val % 2 == 1, true);
            val >>= 1;
        }
        requestLayout();
    }
}
