package com.example.lequan.lichvannien.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.CallbackManager.Factory;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.base.utils.Log;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.common.Prefs;
import com.example.lequan.lichvannien.R;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import org.json.JSONObject;

public class SplashActivity extends Activity {
    String API_LOGIN = "http://api.bigcoin.vn/api/service/check_share_bigcoin.php";
    String KEY_PREF_IS_FB_LOGIN = "key_pref_is_fb_facebook";
    String KEY_PREF_LOGIN = "key_pref_is_login";
    String KEY_PREF_REGISTER = "key_pref_is_register";
    MainApplication application;
    CallbackManager callbackManager;
    LoginButton loginButton;
    ProgressBar pb;
    private TextView tvDieuKhoan;

    class C11911 implements FacebookCallback<LoginResult> {
        C11911() {
        }

        public void onSuccess(LoginResult loginResult) {
            Log.m1449v("success login facebook");
            SplashActivity.this.pb.setVisibility(0);
            SplashActivity.this.getTotalCountFriends();
        }

        public void onCancel() {
            Log.m1449v("cancel login facebook");
        }

        public void onError(FacebookException error) {
            Log.m1449v("error login facebook");
        }
    }

    class C11922 implements Callback {
        C11922() {
        }

        public void onCompleted(GraphResponse response) {
            try {
                Log.m1449v("response list friends login: " + response.getRawResponse());
                int totalCountFriends = 0;
                try {
                    totalCountFriends = response.getJSONObject().getJSONObject("summary").getInt("total_count");
                } catch (Exception e) {
                    Log.m1447e("error get totalCount friends: " + e.getMessage());
                }
                new Register().execute(new String[]{AccessToken.getCurrentAccessToken().getUserId(), String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(500, 500)), totalCountFriends + ""});
            } catch (Exception e2) {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                SplashActivity.this.finish();
            }
        }
    }

    class Register extends AsyncTask<String, Void, Void> {
        String API_REGISTER = "http://sdk.hdvietpro.com/android/apps/register.php";

        Register() {
        }

        protected Void doInBackground(String... params) {
            String resultIpInfo = "";
            try {
                resultIpInfo = SplashActivity.this.application.getOkHttpClient().newCall(new Builder().url("https://ipinfo.io/json").build()).execute().body().string();
                BaseUtils.setCountry(SplashActivity.this, new JSONObject(resultIpInfo).getString("country"));
            } catch (Exception e) {
                Log.m1447e("error progress ipInfo: " + e.getMessage());
            }
            try {
                SplashActivity.this.application.editor.putBoolean(SplashActivity.this.KEY_PREF_REGISTER, new JSONObject(SplashActivity.this.application.getOkHttpClient().newCall(new Builder().url(this.API_REGISTER).method(HttpRequest.METHOD_POST, RequestBody.create(null, new byte[0])).post(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("deviceID", BaseUtils.getDeviceID(SplashActivity.this)).addFormDataPart("network", BaseUtils.getNetwork(SplashActivity.this)).addFormDataPart("phone_name", BaseUtils.getDeviceName()).addFormDataPart("code", SplashActivity.this.getResources().getString(R.string.code_app)).addFormDataPart("country", SplashActivity.this.application.pref.getString(BaseConstant.KEY_COUNTRY_REQUEST, "VN")).addFormDataPart("os", AbstractSpiCall.ANDROID_CLIENT_TYPE).addFormDataPart("fbID", params[0]).addFormDataPart(Prefs.KEY_AVATAR, params[1]).addFormDataPart("numberFriends", params[2]).addFormDataPart("id_campaign", "10180").addFormDataPart("ipInfo", resultIpInfo).build()).build()).execute().body().string()).getBoolean("status"));
                SplashActivity.this.application.editor.commit();
            } catch (Exception e2) {
                Log.m1447e("error register: " + e2.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            SplashActivity.this.finish();
        }
    }

    class requestLogin extends AsyncTask<Void, Void, Boolean> {

        class C11931 implements FacebookCallback<LoginResult> {
            C11931() {
            }

            public void onSuccess(LoginResult loginResult) {
                Log.m1449v("success login facebook");
                SplashActivity.this.pb.setVisibility(0);
                SplashActivity.this.getTotalCountFriends();
            }

            public void onCancel() {
                Log.m1449v("cancel login facebook");
            }

            public void onError(FacebookException error) {
                Log.m1447e("error login facebook: " + error.getMessage());
            }
        }

        requestLogin() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            SplashActivity.this.pb.setVisibility(0);
        }

        protected Boolean doInBackground(Void... params) {
            SplashActivity.this.application.editor.putBoolean(SplashActivity.this.KEY_PREF_LOGIN, false);
            SplashActivity.this.application.editor.commit();
            try {
                String resultIpInfo = "";
                try {
                    resultIpInfo = SplashActivity.this.application.getOkHttpClient().newCall(new Builder().url("https://ipinfo.io/json").build()).execute().body().string();
                    BaseUtils.setCountry(SplashActivity.this, new JSONObject(resultIpInfo).getString("country"));
                } catch (Exception e) {
                    Log.m1447e("error progress ipInfo: " + e.getMessage());
                }
                String result = SplashActivity.this.application.getOkHttpClient().newCall(new Builder().url(SplashActivity.this.API_LOGIN + "?key=s!kpxaO2^OyStQzT&code=" + SplashActivity.this.getResources().getString(R.string.code_app) + "&deviceID=" + BaseUtils.getDeviceID(SplashActivity.this) + "&id_campaign=10180&ipInfo=" + resultIpInfo).build()).execute().body().string();
                Log.m1449v("result login: " + result);
                if (new JSONObject(result).getInt("status") == 1) {
                    return Boolean.valueOf(true);
                }
            } catch (Exception e2) {
                Log.m1447e("error login: " + e2.getMessage());
            }
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            SplashActivity.this.application.editor.putBoolean(SplashActivity.this.KEY_PREF_IS_FB_LOGIN, aBoolean.booleanValue());
            SplashActivity.this.application.editor.commit();
            SplashActivity.this.pb.setVisibility(8);
            if (aBoolean.booleanValue()) {
                SplashActivity.this.loginButton.setVisibility(0);
                SplashActivity.this.tvDieuKhoan.setVisibility(0);
                LoginManager.getInstance().registerCallback(SplashActivity.this.callbackManager, new C11931());
                return;
            }
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            SplashActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.m1450w("SplashActivity");
        this.application = (MainApplication) getApplication();
        this.application.downloadIMG();
        this.tvDieuKhoan = (TextView) findViewById(R.id.tvDieuKhoan);
        this.tvDieuKhoan.setText("Đăng nhập để sử dụng " + getResources().getString(R.string.app_name));
        ((TextView) findViewById(R.id.tvAppName)).setText(getResources().getString(R.string.app_name));
        this.loginButton = (LoginButton) findViewById(R.id.login_button);
        List permissionsRequest = new ArrayList();
        permissionsRequest.add("public_profile");
        permissionsRequest.add("user_friends");
        this.loginButton.setReadPermissions(permissionsRequest);
        this.callbackManager = Factory.create();
        this.pb = (ProgressBar) findViewById(R.id.pb);
        if (this.application.pref.getBoolean(this.KEY_PREF_LOGIN, true)) {
            new requestLogin().execute(new Void[0]);
        } else if (!this.application.pref.getBoolean(this.KEY_PREF_IS_FB_LOGIN, false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else if (AccessToken.getCurrentAccessToken() == null || Profile.getCurrentProfile() == null) {
            this.loginButton.setVisibility(0);
            this.tvDieuKhoan.setVisibility(0);
            LoginManager.getInstance().registerCallback(this.callbackManager, new C11911());
        } else {
            Log.m1446d("da dang nhap facebook tu trước");
            if (this.application.pref.getBoolean(this.KEY_PREF_REGISTER, false)) {
                Log.m1446d("da register");
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return;
            }
            getTotalCountFriends();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    void getTotalCountFriends() {
        this.pb.setVisibility(0);
        new GraphRequest(AccessToken.getCurrentAccessToken(), "/" + AccessToken.getCurrentAccessToken().getUserId() + "/friends", null, HttpMethod.GET, new C11922()).executeAsync();
    }
}
