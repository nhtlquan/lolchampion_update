package com.example.lequan.lichvannien.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.common.Prefs;
import com.example.lequan.lichvannien.custom.DatePicker;
import com.example.lequan.lichvannien.custom.DatePicker.onActionFromDatePicker;
import com.squareup.picasso.Picasso;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends BaseActivity {
    private MainApplication application;
    EditText btnDob;
    Button btnUpdate;
    CallbackManager callbackManager;
    DatePicker datePicker;
    Dialog dialog;
    private EditText edtName;
    private ImageView imgAvatar;
    private ImageView imgClose;
    ImageView imgEditAvatar;
    private TextView tvDob;
    private TextView tvName;
    TextView tvNhatKy;
    TextView tvSuKien;

    class C11833 implements OnClickListener {
        C11833() {
        }

        public void onClick(View v) {
            PersonalActivity.this.changeActivity(ListEventActivity.class);
        }
    }

    class C11844 implements OnClickListener {
        C11844() {
        }

        public void onClick(View v) {
            PersonalActivity.this.changeActivity(ListDiaryActivity.class);
        }
    }

    class C11855 implements OnClickListener {
        C11855() {
        }

        public void onClick(View v) {
            EventActivity.type = 0;
            PersonalActivity.this.changeActivity(EventActivity.class);
        }
    }

    class C11866 implements OnClickListener {
        C11866() {
        }

        public void onClick(View v) {
            EventActivity.type = 2;
            PersonalActivity.this.changeActivity(EventActivity.class);
        }
    }

    class C11877 implements OnClickListener {
        C11877() {
        }

        public void onClick(View v) {
            EventActivity.type = 1;
            PersonalActivity.this.changeActivity(EventActivity.class);
        }
    }

    class C11888 implements OnClickListener {
        C11888() {
        }

        public void onClick(View v) {
            PersonalActivity.this.changeActivity(DiaryActivity.class);
        }
    }

    class C11899 implements OnClickListener {
        C11899() {
        }

        public void onClick(View v) {
            EventActivity.type = 3;
            PersonalActivity.this.changeActivity(EventActivity.class);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_personal);
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
                    PersonalActivity.this.setResult(-1);
                    PersonalActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        final ImageView ivEdit = (ImageView) findViewById(R.id.btn_edit_iv);
        ((RelativeLayout) findViewById(R.id.btn_edit)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivEdit.setImageResource(R.drawable.header_edit);
                    PersonalActivity.this.popupEditProfile();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivEdit.setImageResource(R.drawable.header_edit_select);
                    return true;
                }
            }
        });
        ((RelativeLayout) findViewById(R.id.btn_edit)).setVisibility(8);
        ((TextView) findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        this.tvName = (TextView) findViewById(R.id.tv_name);
        this.tvDob = (TextView) findViewById(R.id.tv_dob);
        this.tvName.setTypeface(this.typeBoldNew);
        this.tvDob.setTypeface(this.typeRegularNew);
        this.tvSuKien = (TextView) findViewById(R.id.tv_sukien);
        this.tvNhatKy = (TextView) findViewById(R.id.tv_nhatky);
        this.tvSuKien.setTypeface(this.typeBoldNew);
        this.tvNhatKy.setTypeface(this.typeBoldNew);
        this.tvSuKien.setOnClickListener(new C11833());
        this.tvNhatKy.setOnClickListener(new C11844());
        this.imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        if (!(AccessToken.getCurrentAccessToken() == null || Profile.getCurrentProfile() == null)) {
            ((RelativeLayout) findViewById(R.id.rlInfomation)).setVisibility(0);
            if (!Profile.getCurrentProfile().getProfilePictureUri(500, 500).equals("")) {
                Picasso.with(this).load(Profile.getCurrentProfile().getProfilePictureUri(500, 500)).into(this.imgAvatar);
            }
            this.tvName.setText(Profile.getCurrentProfile().getName());
            this.tvName.setTypeface(this.typeBoldNew);
            this.tvDob.setText("");
        }
        ((ImageView) findViewById(R.id.ivGiaDinh)).setOnClickListener(new C11855());
        ((ImageView) findViewById(R.id.ivSinhNhat)).setOnClickListener(new C11866());
        ((ImageView) findViewById(R.id.ivCongViec)).setOnClickListener(new C11877());
        ((ImageView) findViewById(R.id.ivNhatKy)).setOnClickListener(new C11888());
        ((ImageView) findViewById(R.id.ivCaNhan)).setOnClickListener(new C11899());
    }

    protected void getBitmap(String uri, Bitmap bm) {
        Prefs.setValue(this, Prefs.KEY_AVATAR, uri);
        this.imgEditAvatar.setImageBitmap(bm);
    }

    public void popupEditProfile() {
        this.dialog = new Dialog(this);
        this.dialog.setCancelable(true);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.dialog_edit_profile);
        this.imgClose = (ImageView) this.dialog.findViewById(R.id.img_close);
        this.imgEditAvatar = (ImageView) this.dialog.findViewById(R.id.img_avatar);
        this.edtName = (EditText) this.dialog.findViewById(R.id.edt_name);
        this.edtName.setText(Prefs.getValue(getApplicationContext(), "name"));
        this.btnDob = (EditText) this.dialog.findViewById(R.id.btn_dob);
        String dob = Prefs.getValue(getApplicationContext(), Prefs.KEY_DOB);
        if (dob.length() == 0) {
            this.btnDob.setText("Chọn ngày sinh");
        } else {
            this.btnDob.setText(dob);
        }
        showImage(this.imgEditAvatar, Prefs.getValue(this, Prefs.KEY_AVATAR));
        this.btnUpdate = (Button) this.dialog.findViewById(R.id.btn_update);
        this.btnUpdate.setTypeface(this.typeRegularNew);
        this.datePicker = (DatePicker) this.dialog.findViewById(R.id.datePicker);
        this.datePicker.setOnActionFromDatePicker(new onActionFromDatePicker() {
            public void onSelect(String date) {
                try {
                    PersonalActivity.this.btnDob.setText(date);
                } catch (Exception e) {
                }
            }
        });
        this.imgEditAvatar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PersonalActivity.this.choosePhoto();
            }
        });
        this.imgClose.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PersonalActivity.this.dialog.dismiss();
            }
        });
        this.btnDob.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PersonalActivity.this.datePicker.setVisibility(0);
            }
        });
        this.btnUpdate.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (PersonalActivity.this.edtName.getText().length() == 0) {
                    PersonalActivity.this.showToast(PersonalActivity.this.getApplicationContext(), "Vui lòng nhập tên của bạn !");
                } else if (PersonalActivity.this.btnDob.getText().toString().equalsIgnoreCase("Chọn ngày sinh")) {
                    PersonalActivity.this.showToast(PersonalActivity.this.getApplicationContext(), "Vui lòng nhập ngày sinh của bạn !");
                } else {
                    ((RelativeLayout) PersonalActivity.this.findViewById(R.id.rlInfomation)).setVisibility(0);
                    Prefs.setValue(PersonalActivity.this.getApplicationContext(), "name", PersonalActivity.this.edtName.getText().toString());
                    Prefs.setValue(PersonalActivity.this.getApplicationContext(), Prefs.KEY_DOB, PersonalActivity.this.btnDob.getText().toString());
                    PersonalActivity.this.tvName.setText(Prefs.getValue(PersonalActivity.this.getApplicationContext(), "name"));
                    PersonalActivity.this.tvDob.setText(Prefs.getValue(PersonalActivity.this.getApplicationContext(), Prefs.KEY_DOB));
                    if (!(AccessToken.getCurrentAccessToken() == null || Profile.getCurrentProfile() == null || Profile.getCurrentProfile().getProfilePictureUri(500, 500) == null)) {
                        Prefs.setValue(PersonalActivity.this.getApplicationContext(), Prefs.KEY_AVATAR, Profile.getCurrentProfile().getProfilePictureUri(500, 500).toString());
                    }
                    PersonalActivity.this.showImage(PersonalActivity.this.imgAvatar, Prefs.getValue(PersonalActivity.this, Prefs.KEY_AVATAR));
                    PersonalActivity.this.dialog.dismiss();
                }
            }
        });
        LoginButton loginButton = (LoginButton) this.dialog.findViewById(R.id.login_button);
        loginButton.setTypeface(this.typeRegularNew);
        List permissionsRequest = new ArrayList();
        permissionsRequest.add("public_profile");
        loginButton.setReadPermissions(permissionsRequest);
        this.callbackManager = Factory.create();
        LoginManager.getInstance().registerCallback(this.callbackManager, new FacebookCallback<LoginResult>() {

            class C11801 implements Runnable {
                C11801() {
                }

                public void run() {
                    try {
                        if (PersonalActivity.this.dialog.isShowing()) {
                            Log.v(BaseActivity.TAG, "name: " + Profile.getCurrentProfile().getName());
                            if (PersonalActivity.this.edtName != null) {
                                PersonalActivity.this.edtName.setText(Profile.getCurrentProfile().getName());
                            }
                            Log.v(BaseActivity.TAG, "avatar: " + Profile.getCurrentProfile().getProfilePictureUri(500, 500));
                            if (!Profile.getCurrentProfile().getProfilePictureUri(500, 500).equals("") && PersonalActivity.this.imgEditAvatar != null) {
                                Picasso.with(PersonalActivity.this).load(Profile.getCurrentProfile().getProfilePictureUri(500, 500)).into(PersonalActivity.this.imgEditAvatar);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(BaseActivity.TAG, "error gen data to dialog: " + e.getMessage());
                    }
                }
            }

            public void onSuccess(LoginResult loginResult) {
                Log.v(BaseActivity.TAG, "success login facebook");
                new Handler().postDelayed(new C11801(), 1000);
            }

            public void onCancel() {
                Log.v(BaseActivity.TAG, "cancel login facebook");
            }

            public void onError(FacebookException error) {
                Log.v(BaseActivity.TAG, "error login facebook");
            }
        });
        this.dialog.show();
    }

    protected void onSelectedDayPicker(String day) {
        this.btnDob.setText(day);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showDialogMessage(String message) {
        Builder builder = new Builder(this);
        builder.setMessage((CharSequence) message);
        builder.setPositiveButton((CharSequence) "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }
}
