package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.NumberPicker;
import com.example.lequan.lichvannien.R;

public class NumberPickerNew extends NumberPicker {
    public NumberPickerNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    public void addView(View child, int index, LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    public void addView(View child, LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if (view instanceof EditText) {
            ((EditText) view).setTextSize(18.0f);
            ((EditText) view).setTextColor(getResources().getColor(R.color.colorBlue));
        }
    }
}
