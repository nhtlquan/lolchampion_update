package com.example.lequan.lichvannien.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.InputDeviceCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class LaBanActivity extends BaseActivity implements SensorEventListener {
    static final int MIN_DISTANCE = 50;
    MainApplication application;
    int currentChildView = 0;
    private float currentDegree = 0.0f;
    boolean isZoom = false;
    ImageView ivCompass;
    ImageView ivCompass1;
    ImageView ivZoom;
    OnTouchListener mOnTouchListener = new C11776();
    private SensorManager mSensorManager;
    RelativeLayout rlNext;
    RelativeLayout rlPrev;
    int style = 0;
    String[] styleName = new String[]{"Mặc Định", "24 Sơn", "60 Mạch", "72 Long", "Thiên Địa Nhân"};
    TextView tvHuongContent;
    TextView tvStyle;
    TextView tvToaContent;
    ViewFlipper vfImage;
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    class C11743 implements OnClickListener {
        C11743() {
        }

        public void onClick(View v) {
            if (LaBanActivity.this.style > 0) {
                LaBanActivity laBanActivity = LaBanActivity.this;
                laBanActivity.style--;
                LaBanActivity.this.tvStyle.setText(LaBanActivity.this.styleName[LaBanActivity.this.style]);
                LaBanActivity.this.changeIV();
                LaBanActivity.this.vfImage.setInAnimation(LaBanActivity.this, R.anim.in_from_left_2);
                LaBanActivity.this.vfImage.setOutAnimation(LaBanActivity.this, R.anim.out_to_right_2);
                LaBanActivity.this.vfImage.showNext();
                LaBanActivity.this.currentChildView = LaBanActivity.this.currentChildView == 0 ? 1 : 0;
            }
        }
    }

    class C11754 implements OnClickListener {
        C11754() {
        }

        public void onClick(View v) {
            if (LaBanActivity.this.style < 4) {
                LaBanActivity laBanActivity = LaBanActivity.this;
                laBanActivity.style++;
                LaBanActivity.this.tvStyle.setText(LaBanActivity.this.styleName[LaBanActivity.this.style]);
                LaBanActivity.this.changeIV();
                LaBanActivity.this.vfImage.setInAnimation(LaBanActivity.this, R.anim.in_from_right_2);
                LaBanActivity.this.vfImage.setOutAnimation(LaBanActivity.this, R.anim.out_to_left_2);
                LaBanActivity.this.vfImage.showNext();
                LaBanActivity.this.currentChildView = LaBanActivity.this.currentChildView == 0 ? 1 : 0;
            }
        }
    }

    class C11765 implements OnTouchListener {
        C11765() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            boolean z = false;
            if (event.getAction() == 1) {
                LaBanActivity.this.ivZoom.setImageResource(R.drawable.zoom);
                LayoutParams paramsVF = (LayoutParams) LaBanActivity.this.vfImage.getLayoutParams();
                if (LaBanActivity.this.isZoom) {
                    paramsVF.setMargins(0, 0, 0, 0);
                } else {
                    paramsVF.setMargins(0 - BaseUtils.genpx(LaBanActivity.this, 40), 0, 0 - BaseUtils.genpx(LaBanActivity.this, 40), 0);
                }
                LaBanActivity laBanActivity = LaBanActivity.this;
                if (!LaBanActivity.this.isZoom) {
                    z = true;
                }
                laBanActivity.isZoom = z;
                return true;
            } else if (event.getAction() != 0) {
                return false;
            } else {
                LaBanActivity.this.ivZoom.setImageResource(R.drawable.zoom_selected);
                return true;
            }
        }
    }

    class C11776 implements OnTouchListener {
        C11776() {
        }

        public boolean onTouch(View v, MotionEvent motionEvent) {
            int i = 0;
            switch (motionEvent.getAction()) {
                case 0:
                    LaBanActivity.this.x1 = motionEvent.getX();
                    LaBanActivity.this.y1 = motionEvent.getY();
                    break;
                case 1:
                    LaBanActivity.this.x2 = motionEvent.getX();
                    LaBanActivity.this.y2 = motionEvent.getY();
                    float deltaX = LaBanActivity.this.x2 - LaBanActivity.this.x1;
                    float deltaY = LaBanActivity.this.y2 - LaBanActivity.this.y1;
                    if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) >= 50.0f) {
                        LaBanActivity laBanActivity;
                        if (LaBanActivity.this.x2 <= LaBanActivity.this.x1) {
                            Log.d(BaseActivity.TAG, "next");
                            if (LaBanActivity.this.style < 4) {
                                laBanActivity = LaBanActivity.this;
                                laBanActivity.style++;
                                LaBanActivity.this.tvStyle.setText(LaBanActivity.this.styleName[LaBanActivity.this.style]);
                                LaBanActivity.this.changeIV();
                                LaBanActivity.this.vfImage.setInAnimation(LaBanActivity.this, R.anim.in_from_right_2);
                                LaBanActivity.this.vfImage.setOutAnimation(LaBanActivity.this, R.anim.out_to_left_2);
                                LaBanActivity.this.vfImage.showNext();
                                laBanActivity = LaBanActivity.this;
                                if (LaBanActivity.this.currentChildView == 0) {
                                    i = 1;
                                }
                                laBanActivity.currentChildView = i;
                                break;
                            }
                        }
                        Log.d(BaseActivity.TAG, "prev");
                        if (LaBanActivity.this.style > 0) {
                            laBanActivity = LaBanActivity.this;
                            laBanActivity.style--;
                            LaBanActivity.this.tvStyle.setText(LaBanActivity.this.styleName[LaBanActivity.this.style]);
                            LaBanActivity.this.changeIV();
                            LaBanActivity.this.vfImage.setInAnimation(LaBanActivity.this, R.anim.in_from_left_2);
                            LaBanActivity.this.vfImage.setOutAnimation(LaBanActivity.this, R.anim.out_to_right_2);
                            LaBanActivity.this.vfImage.showNext();
                            laBanActivity = LaBanActivity.this;
                            if (LaBanActivity.this.currentChildView == 0) {
                                i = 1;
                            }
                            laBanActivity.currentChildView = i;
                            break;
                        }
                    } else if (Math.abs(deltaX) <= Math.abs(deltaY) && Math.abs(deltaY) >= 50.0f && LaBanActivity.this.y2 > LaBanActivity.this.y1) {
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_laban);
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
                    LaBanActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        final ImageView ivCamera = (ImageView) findViewById(R.id.btn_camera_iv);
        ((RelativeLayout) findViewById(R.id.btn_camera)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivCamera.setImageResource(R.drawable.camera);
                    if (ContextCompat.checkSelfPermission(LaBanActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        Toast.makeText(LaBanActivity.this, "Hãy cho phép ứng dụng ghi dữ liệu ảnh đã chụp vào thẻ nhớ.", 0).show();
                        ActivityCompat.requestPermissions(LaBanActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 222);
                        return true;
                    }
                    LaBanActivity.this.takeScreenshot();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivCamera.setImageResource(R.drawable.camera_selected);
                    return true;
                }
            }
        });
        ((TextView) findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        this.tvHuongContent = (TextView) findViewById(R.id.tvHuongContent);
        this.tvHuongContent.setTypeface(this.typeBoldNew);
        this.tvToaContent = (TextView) findViewById(R.id.tvToaContent);
        this.tvToaContent.setTypeface(this.typeBoldNew);
        this.tvHuongContent.setTextColor(InputDeviceCompat.SOURCE_ANY);
        this.tvToaContent.setTextColor(InputDeviceCompat.SOURCE_ANY);
        this.ivCompass = (ImageView) findViewById(R.id.imageViewCompass);
        this.ivCompass1 = (ImageView) findViewById(R.id.imageViewCompass1);
        defaultIMG();
        this.mSensorManager = (SensorManager) getSystemService("sensor");
        this.tvStyle = (TextView) findViewById(R.id.tvStyle);
        this.rlPrev = (RelativeLayout) findViewById(R.id.rlPrev);
        this.rlNext = (RelativeLayout) findViewById(R.id.rlNext);
        this.rlPrev.setOnClickListener(new C11743());
        this.rlNext.setOnClickListener(new C11754());
        this.ivZoom = (ImageView) findViewById(R.id.ivZoom);
        ((RelativeLayout) findViewById(R.id.rlZoom)).setOnTouchListener(new C11765());
        ((RelativeLayout) findViewById(R.id.rlLaBan)).setOnTouchListener(this.mOnTouchListener);
        this.vfImage = (ViewFlipper) findViewById(R.id.vfImage);
    }

    protected void onResume() {
        super.onResume();
        if (this.mSensorManager != null) {
            this.mSensorManager.registerListener(this, this.mSensorManager.getDefaultSensor(3), 1);
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.mSensorManager != null) {
            this.mSensorManager.unregisterListener(this);
        }
    }

    public void onSensorChanged(SensorEvent event) {
        float degree = (float) Math.round(event.values[0]);
        this.tvHuongContent.setText("Hướng " + Float.toString(degree) + "° " + getHuong(degree));
        float degree2 = degree + 180.0f;
        if (degree2 >= 360.0f) {
            degree2 -= 360.0f;
        }
        this.tvToaContent.setText("Tọa " + Float.toString(degree2) + "° " + getHuong(degree2));
        RotateAnimation ra = new RotateAnimation(this.currentDegree, -degree, 1, 0.5f, 1, 0.5f);
        ra.setDuration(210);
        ra.setFillAfter(true);
        if (this.currentChildView == 0) {
            this.ivCompass.startAnimation(ra);
        } else {
            this.ivCompass1.startAnimation(ra);
        }
        this.currentDegree = -degree;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public String getHuong(float degree) {
        if (degree > 337.5f || degree < 22.5f) {
            return "Bắc";
        }
        if (degree >= 22.5f && degree < 67.5f) {
            return "Đông Bắc";
        }
        if (degree >= 67.5f && degree < 112.5f) {
            return "Đông";
        }
        if (degree >= 112.5f && degree < 157.5f) {
            return "Đông Nam";
        }
        if (degree >= 157.5f && degree < 202.5f) {
            return "Nam";
        }
        if (degree >= 202.5f && degree < 247.5f) {
            return "Tây Nam";
        }
        if (degree >= 247.5f && degree < 292.5f) {
            return "Tây";
        }
        if (degree < 292.5f || degree >= 337.5f) {
            return "";
        }
        return "Tây Bắc";
    }

    private void takeScreenshot() {
        Date now = new Date();
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            openScreenshot(imageFile);
        } catch (Throwable e) {
            com.example.lequan.lichvannien.base.utils.Log.m1447e("error take screenShot: " + e.getMessage());
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(imageFile), "image/*");
        startActivity(intent);
        Toast.makeText(this, "Ảnh chụp màn hình đã được lưu vào thẻ nhớ.", 0).show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 222:
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    Toast.makeText(this, "Không thể ghi dữ liệu ảnh đã chụp khi không được cấp quyền.", 0).show();
                    return;
                } else {
                    takeScreenshot();
                    return;
                }
            default:
                return;
        }
    }

    private void defaultIMG() {
        String filePath = getFilesDir().getPath() + "/img/laban_" + this.style + ".png";
        if (!new File(filePath).exists()) {
            Glide.with(this.baseActivity).load(filePath).into(this.ivCompass1);
            Glide.with(this.baseActivity).load(filePath).into(this.ivCompass);
        } else if (this.application.srcLaBan.size() > this.style) {
            Glide.with(this.baseActivity).load(this.application.srcLaBan.get(this.style)).into(this.ivCompass1);
            Glide.with(this.baseActivity).load(this.application.srcLaBan.get(this.style)).into(this.ivCompass);
        }
    }

    private void changeIV() {
        String filePath = getFilesDir().getPath() + "/img/laban_" + this.style + ".png";
        if (new File(filePath).exists()) {
            if (this.currentChildView == 0) {
                if (this.application.srcLaBan.size() > this.style) {
                    Glide.with(this.baseActivity).load(this.application.srcLaBan.get(this.style)).into(this.ivCompass1);
                }
            } else if (this.application.srcLaBan.size() > this.style) {
                Glide.with(this.baseActivity).load(this.application.srcLaBan.get(this.style)).into(this.ivCompass);
            }
        } else if (this.currentChildView == 0) {
            Glide.with(this.baseActivity).load(filePath).into(this.ivCompass1);
        } else {
            Glide.with(this.baseActivity).load(filePath).into(this.ivCompass);
        }
    }
}
