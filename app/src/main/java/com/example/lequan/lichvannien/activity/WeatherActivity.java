package com.example.lequan.lichvannien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.bumptech.glide.Glide;
import com.facebook.share.internal.ShareConstants;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.dao.DAOListCity;
import com.example.lequan.lichvannien.dao.DAOWeather;
import com.example.lequan.lichvannien.utils.Utils;
import com.squareup.picasso.Picasso;
import com.example.lequan.lichvannien.R;
import java.io.File;
import okhttp3.Request;
import okhttp3.Request.Builder;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherActivity extends BaseActivity {
    String API_WEATHER_NATIVE = "http://ios.hdvietpro.com/Mini_Project/AppLich/public/api_thoi_tiet.php?codeId=xxxxxx&os=android";
    private MainApplication application;
    Button btReload;
    DAOListCity daoListCity = new DAOListCity();
    Handler handler = new Handler();
    ImageView ivFull;
    ImageView ivPlay;
    ImageView ivVV;
    LinearLayout llThoiTiet10;
    ProgressBar pb;
    ProgressBar pbVV;
    RelativeLayout rlToDay;
    RelativeLayout rlVV;
    RelativeLayout rlVVTitle;
    Runnable runnableHidenController = new Runnable() {
        public void run() {
            WeatherActivity.this.ivPlay.setVisibility(8);
            WeatherActivity.this.ivFull.setVisibility(8);
            WeatherActivity.this.sb.setVisibility(8);
        }
    };
    Runnable runnableSB = new Runnable() {
        public void run() {
            WeatherActivity.this.sb.setProgress(WeatherActivity.this.vv.getCurrentPosition());
            WeatherActivity.this.handler.postDelayed(this, 1000);
        }
    };
    SeekBar sb;
    ScrollView sv;
    TextView tvCityName;
    TextView tvError;
    TextView tvToDayDoAm;
    TextView tvToDayMatTroiMoc;
    TextView tvToDayStatus;
    TextView tvToDayTemp;
    TextView tvToDayTempTo;
    TextView tvToDayTime;
    TextView tvToDayTitle;
    TextView tvToDayTocDoGio;
    VideoView vv;

    class C12141 implements OnClickListener {
        C12141() {
        }

        public void onClick(View v) {
            new LoadWeather().execute(new Void[0]);
        }
    }

    class C12163 implements OnClickListener {
        C12163() {
        }

        public void onClick(View v) {
            String[] listName = WeatherActivity.this.daoListCity.getListName();
            DAOListCity dAOListCity = WeatherActivity.this.daoListCity;
            SharedPreferences sharedPreferences = WeatherActivity.this.application.pref;
            WeatherActivity.this.showChooseSingle("Lựa chọn thành phố", listName, dAOListCity.getIndex(sharedPreferences.getString(MainApplication.KEY_PREF_CITY_CODE, "2347727")));
        }
    }

    class C12174 implements OnClickListener {
        C12174() {
        }

        public void onClick(View v) {
            if (WeatherActivity.this.application.url_video_weather.equals("")) {
                Toast.makeText(WeatherActivity.this, "Không thể phát video!", 0).show();
            } else if (WeatherActivity.this.vv.isPlaying()) {
                WeatherActivity.this.vv.pause();
                WeatherActivity.this.ivPlay.setImageResource(R.drawable.vv_play);
            } else if (WeatherActivity.this.ivVV.isShown()) {
                WeatherActivity.this.vv.setVideoURI(Uri.parse(WeatherActivity.this.application.url_video_weather));
                WeatherActivity.this.vv.requestFocus();
                WeatherActivity.this.pbVV.setVisibility(0);
            } else {
                WeatherActivity.this.vv.start();
                WeatherActivity.this.ivPlay.setImageResource(R.drawable.vv_pause);
            }
        }
    }

    class C12185 implements OnClickListener {
        C12185() {
        }

        public void onClick(View v) {
            WeatherActivity.this.vv.pause();
            Intent playVideo = new Intent("android.intent.action.VIEW");
            playVideo.setDataAndType(Uri.parse(WeatherActivity.this.application.url_video_weather), "video/*");
            WeatherActivity.this.startActivity(playVideo);
        }
    }

    class C12196 implements OnPreparedListener {
        C12196() {
        }

        public void onPrepared(MediaPlayer mp) {
            Log.d(BaseActivity.TAG, "onPrepared");
            WeatherActivity.this.vv.setBackgroundColor(0);
            mp.start();
            WeatherActivity.this.ivPlay.setImageResource(R.drawable.vv_pause);
            WeatherActivity.this.ivPlay.setVisibility(8);
            WeatherActivity.this.ivFull.setVisibility(8);
            WeatherActivity.this.sb.setMax(mp.getDuration());
            WeatherActivity.this.handler.post(WeatherActivity.this.runnableSB);
            WeatherActivity.this.ivVV.setVisibility(8);
            WeatherActivity.this.pbVV.setVisibility(8);
            WeatherActivity.this.sb.setVisibility(8);
        }
    }

    class C12207 implements OnCompletionListener {
        C12207() {
        }

        public void onCompletion(MediaPlayer mp) {
            WeatherActivity.this.ivPlay.setImageResource(R.drawable.vv_play);
            WeatherActivity.this.vv.seekTo(0);
        }
    }

    class C12218 implements OnClickListener {
        C12218() {
        }

        public void onClick(View v) {
            WeatherActivity.this.ivFull.setVisibility(0);
            WeatherActivity.this.ivPlay.setVisibility(0);
            WeatherActivity.this.sb.setVisibility(0);
            WeatherActivity.this.handler.removeCallbacks(WeatherActivity.this.runnableHidenController);
            WeatherActivity.this.handler.postDelayed(WeatherActivity.this.runnableHidenController, 3000);
        }
    }

    class C12229 implements OnSeekBarChangeListener {
        C12229() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                WeatherActivity.this.vv.seekTo(seekBar.getProgress());
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    class LoadWeather extends AsyncTask<Void, Void, Boolean> {
        LoadWeather() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            WeatherActivity.this.pb.setVisibility(0);
            WeatherActivity.this.btReload.setVisibility(8);
            WeatherActivity.this.tvError.setVisibility(8);
        }

        protected Boolean doInBackground(Void... voids) {
            try {
                SharedPreferences sharedPreferences = WeatherActivity.this.application.pref;
                String urlWeatherNative = WeatherActivity.this.API_WEATHER_NATIVE.replace("xxxxxx", sharedPreferences.getString(MainApplication.KEY_PREF_CITY_CODE, "2347727"));
                Request request = new Builder().url(urlWeatherNative).build();
                Log.i(BaseActivity.TAG, "url weather native: " + urlWeatherNative);
                JSONObject jsonObject = new JSONObject(WeatherActivity.this.application.getOkHttpClient().newCall(request).execute().body().string());
                try {
                    WeatherActivity.this.application.url_thumbnail_weather = jsonObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("thumbnail");
                    WeatherActivity.this.application.url_video_weather = jsonObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getString("videoUrl");
                } catch (Exception e) {
                }
                JSONArray jsonArray = jsonObject.getJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA).getJSONArray("weathers");
                WeatherActivity.this.application.listWeather.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    WeatherActivity.this.application.listWeather.add(new Gson().fromJson(jsonArray.getString(i), DAOWeather.class));
                }
                return Boolean.valueOf(true);
            } catch (Exception e2) {
                Log.e(BaseActivity.TAG, "error get weather: " + e2.getMessage());
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            WeatherActivity.this.pb.setVisibility(8);
            if (!aBoolean.booleanValue()) {
                WeatherActivity.this.sv.setVisibility(8);
                WeatherActivity.this.btReload.setVisibility(0);
                WeatherActivity.this.tvError.setVisibility(0);
                Toast.makeText(WeatherActivity.this, "Không lấy được dữ liệu.", 0).show();
            } else if (WeatherActivity.this.application.listWeather.size() > 0) {
                WeatherActivity.this.sv.setVisibility(0);
                WeatherActivity.this.btReload.setVisibility(8);
                WeatherActivity.this.tvError.setVisibility(8);
                WeatherActivity.this.initData();
            } else {
                WeatherActivity.this.sv.setVisibility(8);
                WeatherActivity.this.btReload.setVisibility(0);
                WeatherActivity.this.tvError.setVisibility(0);
                Toast.makeText(WeatherActivity.this, "Không lấy được dữ liệu.", 0).show();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_weather);
        this.application = (MainApplication) getApplication();
        InitUI();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void InitUI() {
        this.sv = (ScrollView) findViewById(R.id.sv);
        this.tvError = (TextView) findViewById(R.id.tvError);
        this.btReload = (Button) findViewById(R.id.btReload);
        this.btReload.setOnClickListener(new C12141());
        int positionBG = this.baseApplication.pref.getInt(AnhNenActivity.KEY_PREF_BG_POSITION, 0);
        String filePath = getFilesDir().getPath() + "/img/bg_noi_dung_" + positionBG + ".png";
        if (new File(filePath).exists()) {
            Glide.with(this.baseActivity).load(filePath).into((ImageView) findViewById(R.id.ivBG));
        } else if (this.application.listBGNoiDung.size() > positionBG) {
            Glide.with(this.baseActivity).load(this.application.listBGNoiDung.get(positionBG)).into((ImageView) findViewById(R.id.ivBG));
        }
        ((TextView) findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        final ImageView ivBack = (ImageView) findViewById(R.id.btn_back_iv);
        ((RelativeLayout) findViewById(R.id.btn_back)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivBack.setImageResource(R.drawable.ic_back);
                    WeatherActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        this.tvCityName = (TextView) findViewById(R.id.tvCityName);
        this.tvCityName.setTypeface(this.typeBoldNew);
        ((RelativeLayout) findViewById(R.id.rlCitySelect)).setOnClickListener(new C12163());
        this.rlToDay = (RelativeLayout) findViewById(R.id.rlToDay);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        this.tvToDayTitle = (TextView) findViewById(R.id.tvToDayTitle);
        this.tvToDayTitle.setTypeface(this.typeBoldNew);
        this.tvToDayTime = (TextView) findViewById(R.id.tvToDayTime);
        this.tvToDayTime.setTypeface(this.typeRegularNew);
        this.tvToDayTemp = (TextView) findViewById(R.id.tvToDayTemp);
        this.tvToDayTemp.setTypeface(this.typeBoldNew);
        ((TextView) findViewById(R.id.tv_DoAm)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_toc_do_gio)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_mat_troi_moc)).setTypeface(this.typeRegularNew);
        this.tvToDayDoAm = (TextView) findViewById(R.id.tv_DoAmContent);
        this.tvToDayTocDoGio = (TextView) findViewById(R.id.tv_toc_do_gioContent);
        this.tvToDayMatTroiMoc = (TextView) findViewById(R.id.tv_mat_troi_mocContent);
        this.tvToDayDoAm.setTypeface(this.typeRegularNew);
        this.tvToDayTocDoGio.setTypeface(this.typeRegularNew);
        this.tvToDayMatTroiMoc.setTypeface(this.typeRegularNew);
        this.tvToDayStatus = (TextView) findViewById(R.id.tvToDayStatus);
        this.tvToDayStatus.setTypeface(this.typeRegularNew);
        this.tvToDayTempTo = (TextView) findViewById(R.id.tvToDayTempTo);
        this.tvToDayTempTo.setTypeface(this.typeBoldNew);
        this.pb = (ProgressBar) findViewById(R.id.pb);
        ((TextView) findViewById(R.id.tvThoiTiet10Title)).setTypeface(this.typeBoldNew);
        this.llThoiTiet10 = (LinearLayout) findViewById(R.id.llThoiTiet10);
        this.rlVV = (RelativeLayout) findViewById(R.id.rlVV);
        this.vv = (VideoView) findViewById(R.id.videoView);
        this.ivVV = (ImageView) findViewById(R.id.rlVV_iv);
        this.ivPlay = (ImageView) findViewById(R.id.ivPlay);
        this.ivFull = (ImageView) findViewById(R.id.ivFull);
        this.pbVV = (ProgressBar) findViewById(R.id.pbVV);
        this.rlVVTitle = (RelativeLayout) findViewById(R.id.rlVVTitle);
        this.sb = (SeekBar) findViewById(R.id.sb);
        this.ivPlay.setOnClickListener(new C12174());
        this.ivFull.setOnClickListener(new C12185());
        this.vv.setOnPreparedListener(new C12196());
        this.vv.setOnCompletionListener(new C12207());
        this.rlVV.setOnClickListener(new C12218());
        this.sb.setOnSeekBarChangeListener(new C12229());
        LayoutParams params = (LayoutParams) this.rlVV.getLayoutParams();
        int w = displaymetrics.widthPixels - BaseUtils.genpx(this, 20);
        int h = (w * 9) / 16;
        params.width = w;
        params.height = h;
        initData();
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            this.handler.removeCallbacksAndMessages(null);
        } catch (Exception e) {
        }
    }

    void initData() {
        TextView textView = this.tvCityName;
        DAOListCity dAOListCity = this.daoListCity;
        SharedPreferences sharedPreferences = this.application.pref;
        MainApplication mainApplication = this.application;
        textView.setText(dAOListCity.getCityName(sharedPreferences.getString(MainApplication.KEY_PREF_CITY_CODE, "2347727")));
        if (this.application.listWeather.size() == 0) {
            new LoadWeather().execute(new Void[0]);
            return;
        }
        this.sv.setVisibility(0);
        if (this.application.url_thumbnail_weather.equals("")) {
            this.rlVV.setVisibility(8);
            this.rlVVTitle.setVisibility(8);
        } else {
            Picasso.with(this).load(this.application.url_thumbnail_weather).into(this.ivVV);
        }
        DAOWeather daoWeatherToDay = (DAOWeather) this.application.listWeather.get(0);
        this.tvToDayTitle.setText(daoWeatherToDay.getFullDate());
        this.tvToDayTime.setText("Cập nhật: " + daoWeatherToDay.getTime());
        this.tvToDayTemp.setText(daoWeatherToDay.getLow() + "° - " + daoWeatherToDay.getHigh() + "°");
        this.tvToDayDoAm.setText(daoWeatherToDay.getHumidity() + "%");
        this.tvToDayTocDoGio.setText(daoWeatherToDay.getWind() + " m/s");
        this.tvToDayMatTroiMoc.setText(daoWeatherToDay.getSunrise());
        this.tvToDayStatus.setText(daoWeatherToDay.getStatus());
        this.tvToDayTempTo.setText(daoWeatherToDay.getTemperature() + "°");
        this.llThoiTiet10.removeAllViews();
        for (int i = 1; i < this.application.listWeather.size(); i++) {
            View v = LayoutInflater.from(this).inflate(R.layout.item_thoitiet, null);
            TextView tvTemp = (TextView) v.findViewById(R.id.tvTemp);
            TextView tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            ((TextView) v.findViewById(R.id.tvDay)).setTypeface(this.typeRegularNew);
            tvTemp.setTypeface(this.typeRegularNew);
            tvStatus.setTypeface(this.typeRegularNew);
            DAOWeather daoWeather = (DAOWeather) this.application.listWeather.get(i);
            TextView tvDay1 = (TextView) v.findViewById(R.id.tvDay1);
            TextView tvDay2 = (TextView) v.findViewById(R.id.tvDay2);
            tvDay1.setTypeface(this.typeRegularNew);
            tvDay2.setTypeface(this.typeRegularNew);
            String[] fullDate = daoWeather.getFullDate().split(" ");
            String fullDateString = fullDate[0];
            for (int j = 1; j < fullDate.length - 1; j++) {
                fullDateString = fullDateString + " " + fullDate[j];
            }
            tvDay1.setText(fullDateString);
            tvDay2.setText(fullDate[fullDate.length - 1]);
            tvTemp.setText(daoWeather.getLow() + "° - " + daoWeather.getHigh() + "°");
            tvStatus.setText(daoWeather.getStatus());
            ((ImageView) v.findViewById(R.id.ivStatus)).setImageResource(Utils.getIconWeatherChiTiet(1));
            this.llThoiTiet10.addView(v);
        }
    }

    protected void onChoise(String value, int index) {
        super.onChoise(value, index);
        Log.v(BaseActivity.TAG, "index: " + index + " cityName: " + value + " cityCode: " + this.daoListCity.getCityCode(index));
        Editor editor = this.application.editor;
        MainApplication mainApplication = this.application;
        editor.putString(MainApplication.KEY_PREF_CITY_CODE, this.daoListCity.getCityCode(index));
        this.application.editor.commit();
        new LoadWeather().execute(new Void[0]);
    }
}
