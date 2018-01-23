package com.example.lequan.lichvannien.activity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.R;
import java.util.Random;

public class GieoQueActivity extends BaseActivity {
    private Button btnGieoQue;
    private ImageView imgGieoQue;
    private boolean isGieo = false;

    class C11601 implements OnClickListener {
        C11601() {
        }

        public void onClick(View v) {
            GieoQueActivity.this.onBackPressed();
        }
    }

    class C11622 implements OnClickListener {

        class C11611 implements Runnable {
            C11611() {
            }

            public void run() {
                GieoQueActivity.this.getQue();
            }
        }

        C11622() {
        }

        public void onClick(View v) {
            if (!GieoQueActivity.this.isGieo) {
                GieoQueActivity.this.imgGieoQue.setImageResource(R.drawable.gieo_que);
                if (VERSION.SDK_INT >= 21) {
                    ((Animatable) GieoQueActivity.this.imgGieoQue.getDrawable()).start();
                }
                new Handler().postDelayed(new C11611(), 3300);
                GieoQueActivity.this.isGieo = true;
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_gieo_que);
        InitUI();
    }

    private void InitUI() {
        ((RelativeLayout) findViewById(R.id.img_back)).setOnClickListener(new C11601());
        this.imgGieoQue = (ImageView) findViewById(R.id.img_gieo_que);
        this.btnGieoQue = (Button) findViewById(R.id.btn_gieo_que);
        this.imgGieoQue.setImageResource(R.drawable.frame_1);
        this.btnGieoQue.setOnClickListener(new C11622());
    }

    private void getQue() {
        int i1 = new Random().nextInt(63);
        Intent mIntent = new Intent(this, QueDetailActivity.class);
        mIntent.putExtra("que", i1);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    protected void onResume() {
        super.onResume();
        this.isGieo = false;
    }
}
