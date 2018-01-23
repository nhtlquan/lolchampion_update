package com.example.lequan.lichvannien.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.BaseActivity.DiaryListenner;
import com.example.lequan.lichvannien.adapter.DiaryCustomAdapter;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.model.DiaryCustom;
import com.example.lequan.lichvannien.model.DiaryInfo;
import com.example.lequan.lichvannien.utils.NSDialog;
import com.example.lequan.lichvannien.utils.NSDialog.OnDialogClick;
import com.example.lequan.lichvannien.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class DiaryActivity extends BaseActivity implements OnClickListener, DiaryListenner {
    private DiaryCustomAdapter bgAdapter;
    private Button btnMonth;
    private DiaryCustomAdapter colorAdapter;
    private EditText edtInput;
    private EditText edtTitle;
    private String imagePath = "";
    private RoundedImageView imgAvatar;
    ImageView imgBg;
    private ImageView imgBgCustom;
    private ImageView imgColorCustom;
    ImageView imgSave;
    private ImageView imgTextCustom;
    private boolean isEditable = true;
    private RelativeLayout layoutAvatar;
    private RelativeLayout layoutBg;
    private RelativeLayout layoutColor;
    private LinearLayout layoutCustom;
    private RelativeLayout layoutText;
    private RelativeLayout layoutTotal;
    private ArrayList<DiaryCustom> lsBg = new ArrayList();
    private ArrayList<DiaryCustom> lsColor = new ArrayList();
    private ArrayList<DiaryCustom> lsText = new ArrayList();
    private DiaryInfo mDiaryInfo = new DiaryInfo();
    private RecyclerView rcDiary;
    RelativeLayout rlBack;
    private String str_color = "#660099";
    private String str_font = "fonts/UTMBustamalaka.ttf";
    private String str_img = "tbg_1";
    private DiaryCustomAdapter textAdapter;
    private TextView tvBg;
    private TextView tvColor;
    private TextView tvText;

    class C11411 implements OnClickListener {
        C11411() {
        }

        public void onClick(View v) {
            DiaryActivity.this.onBackPressed();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_diary);
        InitUI();
    }

    private void InitUI() {
        this.imgAvatar = (RoundedImageView) findViewById(R.id.image);
        this.rlBack = (RelativeLayout) findViewById(R.id.img_back);
        this.rlBack.setOnClickListener(new C11411());
        this.imgSave = (ImageView) findViewById(R.id.img_save);
        this.imgBgCustom = (ImageView) findViewById(R.id.img_bg);
        this.imgTextCustom = (ImageView) findViewById(R.id.img_text);
        this.imgColorCustom = (ImageView) findViewById(R.id.img_color);
        this.tvBg = (TextView) findViewById(R.id.tv_bg);
        this.tvText = (TextView) findViewById(R.id.tv_text);
        this.tvColor = (TextView) findViewById(R.id.tv_color);
        this.imgBg = (ImageView) findViewById(R.id.img_bg_diary);
        this.btnMonth = (Button) findViewById(R.id.btnMonth);
        this.edtInput = (EditText) findViewById(R.id.edt_input);
        this.edtTitle = (EditText) findViewById(R.id.edt_title);
        this.layoutAvatar = (RelativeLayout) findViewById(R.id.layout_avatar);
        this.layoutTotal = (RelativeLayout) findViewById(R.id.layoutTotal);
        this.layoutTotal.setVisibility(8);
        this.layoutBg = (RelativeLayout) findViewById(R.id.layout_bg);
        this.layoutCustom = (LinearLayout) findViewById(R.id.layoutCustom);
        this.layoutText = (RelativeLayout) findViewById(R.id.layout_text);
        this.layoutColor = (RelativeLayout) findViewById(R.id.layout_color);
        this.rcDiary = (RecyclerView) findViewById(R.id.rc_diary);
        this.rcDiary.setLayoutManager(new LinearLayoutManager(this, 0, false));
        Typeface type = Typeface.createFromAsset(getAssets(), "fonts/UTMBustamalaka.ttf");
        this.edtTitle.setTypeface(type);
        this.edtInput.setTypeface(type);
        this.imgBg.setImageResource(R.drawable.bg_text_1);
        this.btnMonth.setText(Define.SELECTED_DAY);
        this.imgSave.setOnClickListener(this);
        this.layoutAvatar.setOnClickListener(this);
        this.layoutBg.setOnClickListener(this);
        this.layoutText.setOnClickListener(this);
        this.layoutColor.setOnClickListener(this);
        this.edtInput.setOnClickListener(this);
        processIntent();
        InitData();
        this.bgAdapter = new DiaryCustomAdapter(getApplicationContext(), this.lsBg, 0, this);
        this.textAdapter = new DiaryCustomAdapter(getApplicationContext(), this.lsText, 1, this);
        this.colorAdapter = new DiaryCustomAdapter(getApplicationContext(), this.lsColor, 2, this);
        this.rcDiary.setAdapter(this.bgAdapter);
        setFontSize(this.str_font);
    }

    private void InitData() {
        this.lsBg.add(new DiaryCustom("tbg_1", null, null, true));
        this.lsBg.add(new DiaryCustom("tbg_2", null, null, false));
        this.lsBg.add(new DiaryCustom("tbg_3", null, null, false));
        this.lsBg.add(new DiaryCustom("tbg_4", null, null, false));
        this.lsText.add(new DiaryCustom(null, "fonts/UTMBustamalaka.ttf", null, true));
        this.lsText.add(new DiaryCustom(null, "fonts/UTMCookies.ttf", null, false));
        this.lsText.add(new DiaryCustom(null, "fonts/UTMDinhTran.ttf", null, false));
        this.lsText.add(new DiaryCustom(null, "fonts/UTMFlavour.ttf", null, false));
        this.lsText.add(new DiaryCustom(null, "fonts/UVNHuongQue.TTF", null, false));
        this.lsColor.add(new DiaryCustom(null, null, "#426271", true));
        this.lsColor.add(new DiaryCustom(null, null, "#8e0f56", false));
        this.lsColor.add(new DiaryCustom(null, null, "#0085a6", false));
        this.lsColor.add(new DiaryCustom(null, null, "#5338b9", false));
        this.lsColor.add(new DiaryCustom(null, null, "#058da5", false));
        this.lsColor.add(new DiaryCustom(null, null, "#749405", false));
        this.lsColor.add(new DiaryCustom(null, null, "#c53d2d", false));
        this.lsColor.add(new DiaryCustom(null, null, "#1203a6", false));
        this.lsColor.add(new DiaryCustom(null, null, "#de4300", false));
        this.lsColor.add(new DiaryCustom(null, null, "#01963c", false));
    }

    private void processIntent() {
        Intent mIntent = getIntent();
        if (mIntent != null) {
            DiaryInfo mDiaryInfo = (DiaryInfo) mIntent.getParcelableExtra("diary");
            if (mDiaryInfo != null) {
                this.btnMonth.setText(mDiaryInfo.getDate());
                showImage(this.imgAvatar, mDiaryInfo.getImagePath());
                if (mDiaryInfo.getImagePath().length() > 0) {
                    this.imgAvatar.setBorderWidth(Float.parseFloat((3.0f * getResources().getDisplayMetrics().density) + ""));
                    this.imgAvatar.setCornerRadius(Float.parseFloat((45.0f * getResources().getDisplayMetrics().density) + ""));
                    this.imgAvatar.setScaleType(ScaleType.CENTER_CROP);
                    this.imgAvatar.setLayoutParams(new LayoutParams((int) (getResources().getDisplayMetrics().density * 90.0f), (int) (getResources().getDisplayMetrics().density * 90.0f)));
                }
                this.edtTitle.setText(mDiaryInfo.getTitle());
                this.edtInput.setText(mDiaryInfo.getContent());
                this.imgAvatar.setEnabled(false);
                this.layoutAvatar.setEnabled(false);
                this.edtInput.setEnabled(false);
                this.edtTitle.setEnabled(false);
                this.imgSave.setEnabled(false);
                this.imgSave.setVisibility(8);
                Utils.setFontEditText(getApplicationContext(), this.edtInput, mDiaryInfo.getSetting().split(",")[1]);
                Utils.setFontEditText(getApplicationContext(), this.edtTitle, mDiaryInfo.getSetting().split(",")[1]);
                this.edtInput.setTextColor(Color.parseColor(mDiaryInfo.getSetting().split(",")[2]));
                this.imgBg.setImageResource(Utils.getResourceId(getApplicationContext(), mDiaryInfo.getSetting().split(",")[0].replace("tbg", "bg_text")));
                this.layoutCustom.setVisibility(8);
                findViewById(R.id.relativeLayout3).setVisibility(8);
                setFontSize(mDiaryInfo.getSetting().split(",")[1]);
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                return;
            case R.id.img_save:
                final String title = this.edtTitle.getText().toString().trim();
                final String content = this.edtInput.getText().toString().trim();
                if (title.length() == 0) {
                    showToast(getApplicationContext(), "Vui lòng nhập tiêu đề nhật ký");
                    return;
                } else if (content.length() == 0) {
                    showToast(getApplicationContext(), "Vui lòng nhập nội dung nhật ký");
                    return;
                } else {
                    NSDialog.showDialog(this, "Bạn có chắc chắn muốn lưu nhật ký này?", "Lưu", "Không", new OnDialogClick() {
                        public void onPositive() {
                            DiaryActivity.this.mDiaryInfo.setId(DiaryActivity.this.mDatabaseAccess.getMaxDiaryID() + 1);
                            DiaryActivity.this.mDiaryInfo.setTitle(title);
                            DiaryActivity.this.mDiaryInfo.setContent(content);
                            DiaryActivity.this.mDiaryInfo.setImagePath(DiaryActivity.this.imagePath);
                            DiaryActivity.this.mDiaryInfo.setDate(Utils.converDate(DiaryActivity.this.btnMonth.getText().toString()));
                            DiaryActivity.this.mDiaryInfo.setSetting(DiaryActivity.this.str_img + "," + DiaryActivity.this.str_font + "," + DiaryActivity.this.str_color);
                            DiaryActivity.this.mDatabaseAccess.addDiary(DiaryActivity.this.mDiaryInfo);
                            DiaryActivity.this.showToast(DiaryActivity.this.getApplicationContext(), "Lưu nhật ký thành công !");
                            DiaryActivity.this.onBackPressed();
                        }

                        public void onNegative() {
                        }
                    });
                    return;
                }
            case R.id.layout_bg:
                this.layoutTotal.setVisibility(0);
                this.imgBgCustom.setImageResource(R.drawable.bg_change_active);
                this.imgTextCustom.setImageResource(R.drawable.text_size);
                this.imgColorCustom.setImageResource(R.drawable.text_color);
                this.tvBg.setTextColor(Color.parseColor("#00afda"));
                this.tvText.setTextColor(Color.parseColor("#446371"));
                this.tvColor.setTextColor(Color.parseColor("#446371"));
                this.rcDiary.setAdapter(this.bgAdapter);
                return;
            case R.id.layout_text:
                this.layoutTotal.setVisibility(0);
                this.imgBgCustom.setImageResource(R.drawable.bg_change);
                this.imgTextCustom.setImageResource(R.drawable.text_size_active);
                this.imgColorCustom.setImageResource(R.drawable.text_color);
                this.tvBg.setTextColor(Color.parseColor("#446371"));
                this.tvText.setTextColor(Color.parseColor("#00afda"));
                this.tvColor.setTextColor(Color.parseColor("#446371"));
                this.rcDiary.setAdapter(this.textAdapter);
                return;
            case R.id.layout_color:
                this.layoutTotal.setVisibility(0);
                this.imgBgCustom.setImageResource(R.drawable.bg_change);
                this.imgTextCustom.setImageResource(R.drawable.text_size);
                this.imgColorCustom.setImageResource(R.drawable.text_color_active);
                this.tvBg.setTextColor(Color.parseColor("#446371"));
                this.tvText.setTextColor(Color.parseColor("#446371"));
                this.tvColor.setTextColor(Color.parseColor("#00afda"));
                this.rcDiary.setAdapter(this.colorAdapter);
                return;
            case R.id.edt_input:
                this.layoutTotal.setVisibility(8);
                return;
            case R.id.layout_avatar:
                choosePhoto();
                return;
            default:
                return;
        }
    }

    protected void getBitmap(String uri, Bitmap bm) {
        this.imgAvatar.setImageBitmap(bm);
        this.imagePath = uri;
        this.imgAvatar.setBorderWidth(Float.parseFloat((3.0f * getResources().getDisplayMetrics().density) + ""));
        this.imgAvatar.setCornerRadius(Float.parseFloat((45.0f * getResources().getDisplayMetrics().density) + ""));
        this.imgAvatar.setLayoutParams(new LayoutParams((int) (getResources().getDisplayMetrics().density * 90.0f), (int) (getResources().getDisplayMetrics().density * 90.0f)));
        this.imgAvatar.setScaleType(ScaleType.CENTER_CROP);
    }

    public void onSelectBackground(DiaryCustom mDiaryCustom) {
        this.layoutTotal.setVisibility(8);
        this.str_img = mDiaryCustom.img;
        this.imgBg.setImageResource(Utils.getResourceId(getApplicationContext(), mDiaryCustom.img.replace("tbg", "bg_text")));
    }

    public void onSelectText(DiaryCustom mDiaryCustom) {
        this.layoutTotal.setVisibility(8);
        this.str_font = mDiaryCustom.font;
        Log.d("hungkm", "font : " + this.str_font);
        Utils.setFontEditText(getApplicationContext(), this.edtInput, mDiaryCustom.font);
        Utils.setFontEditText(getApplicationContext(), this.edtTitle, mDiaryCustom.font);
        setFontSize(this.str_font);
    }

    void setFontSize(String str_font) {
        if (str_font.contains("UTMBustamalaka")) {
            this.edtTitle.setTextSize(2, 30.0f);
            this.edtInput.setTextSize(2, 25.0f);
            this.edtTitle.setPadding(0, 1, 0, 0);
        } else if (str_font.contains("UTMCookies")) {
            this.edtTitle.setTextSize(2, 20.0f);
            this.edtInput.setTextSize(2, 18.0f);
            this.edtTitle.setPadding(0, 11, 0, 0);
        } else if (str_font.contains("UTMDinhTran")) {
            this.edtTitle.setTextSize(2, 37.0f);
            this.edtInput.setTextSize(2, 35.0f);
            this.edtTitle.setPadding(0, 0, 0, 0);
        } else if (str_font.contains("UTMFlavour")) {
            this.edtTitle.setTextSize(2, 25.0f);
            this.edtInput.setTextSize(2, 20.0f);
            this.edtTitle.setPadding(0, 7, 0, 0);
        } else if (str_font.contains("UVNHuongQue")) {
            this.edtTitle.setTextSize(2, 23.0f);
            this.edtInput.setTextSize(2, 20.0f);
            this.edtTitle.setPadding(0, 10, 0, 0);
        } else {
            this.edtTitle.setTextSize(2, 30.0f);
            this.edtInput.setTextSize(2, 25.0f);
            this.edtTitle.setPadding(0, 1, 0, 0);
        }
    }

    public void onSelectColor(DiaryCustom mDiaryCustom) {
        this.str_color = mDiaryCustom.color;
        this.layoutTotal.setVisibility(8);
        this.edtInput.setTextColor(Color.parseColor(mDiaryCustom.color));
    }
}
