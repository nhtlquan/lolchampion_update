package com.example.lequan.lichvannien.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.adapter.DiaryAdapter;
import com.example.lequan.lichvannien.model.DiaryInfo;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.ArrayList;

public class ListDiaryActivity extends BaseActivity {
    private MainApplication application;
    private ArrayList<DiaryInfo> listDiary = new ArrayList();
    private DiaryAdapter mDiaryAdapter;
    private RecyclerView rcDiary;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_list_diary);
        this.application = (MainApplication) getApplication();
        InitUI();
    }

    private void InitUI() {
        int positionBG = this.baseApplication.pref.getInt(AnhNenActivity.KEY_PREF_BG_POSITION, 0);
        String filePath = getFilesDir().getPath() + "/img/bg_noi_dung_" + positionBG + ".png";
        if (new File(filePath).exists()) {
            Glide.with(this.baseActivity).load(filePath).into((ImageView) findViewById(R.id.ivBG));
        } else if (this.application.listBGNoiDung.size() > positionBG) {
            Glide.with(this.baseActivity).load(this.application.listBGNoiDung.get(positionBG)).into((ImageView) findViewById(R.id.ivBG));
        }
        final ImageView ivBack = (ImageView) findViewById(R.id.btn_back_iv);
        ((RelativeLayout) findViewById(R.id.btn_back)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivBack.setImageResource(R.drawable.ic_back);
                    ListDiaryActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        ((TextView) findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        this.rcDiary = (RecyclerView) findViewById(R.id.rc_diary);
        this.rcDiary.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.mDiaryAdapter = new DiaryAdapter(this, this.listDiary);
        this.rcDiary.setAdapter(this.mDiaryAdapter);
    }

    protected void onResume() {
        super.onResume();
        this.listDiary.clear();
        this.listDiary.addAll(this.mDatabaseAccess.getDiary());
        this.mDiaryAdapter.notifyDataSetChanged();
    }
}
