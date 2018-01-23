package com.example.lequan.lichvannien.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.adapter.AnhNenAdapter;
import com.example.lequan.lichvannien.R;

public class AnhNenActivity extends BaseActivity {
    public static String KEY_PREF_BG_POSITION = "key_pref_bg_position";
    private AnhNenAdapter anhNenAdapter;
    private RecyclerView rcView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_anhnen);
        InitUI();
        this.rcView = (RecyclerView) findViewById(R.id.rcView);
        this.rcView.setHasFixedSize(true);
        this.anhNenAdapter = new AnhNenAdapter(this);
        this.rcView.setLayoutManager(new GridLayoutManager(this, 2));
        this.rcView.setAdapter(this.anhNenAdapter);
    }

    private void InitUI() {
        final ImageView ivBack = (ImageView) findViewById(R.id.btn_back_iv);
        ((RelativeLayout) findViewById(R.id.btn_back)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivBack.setImageResource(R.drawable.ic_back);
                    AnhNenActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
    }

    public void onBackPressed() {
        setResult(-10);
        super.onBackPressed();
    }
}
