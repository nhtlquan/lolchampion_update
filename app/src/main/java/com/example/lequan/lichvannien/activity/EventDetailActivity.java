package com.example.lequan.lichvannien.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsConstants;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.alert.AlarmHandler;
import com.example.lequan.lichvannien.model.Alert;
import com.example.lequan.lichvannien.utils.DateUtils;
import com.example.lequan.lichvannien.utils.NSDialog;
import com.example.lequan.lichvannien.utils.NSDialog.OnDialogClick;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.ConvertCalendar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Lunar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Solar;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.Calendar;

public class EventDetailActivity extends BaseActivity implements OnClickListener {
    private static final int DAY = 86400000;
    private static final int HOUR = 3600000;
    private static final int MINUTE = 60000;
    private static final int SECOND = 1000;
    private MainApplication application;
    private Button btnSave;
    ImageView imgType;
    private boolean isStart = true;
    private AlarmHandler mAlarmHandler;
    private EventInfo mEventInfo;
    private String repeat = "";
    private int report = 0;
    TextView tvAddress;
    TextView tvDayEnd;
    TextView tvDayStart;
    private TextView tvFullDay;
    TextView tvHourEnd;
    TextView tvHourStart;
    TextView tvName;
    TextView tvNote;
    TextView tvRepeat;
    TextView tvReport;
    TextView tvSolar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_event_detail);
        this.application = (MainApplication) getApplication();
        InitUI();
    }

    @SuppressLint("WrongConstant")
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
                    EventDetailActivity.this.setResult(-1);
                    EventDetailActivity.this.onBackPressed();
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
        final ImageView ivDelete = (ImageView) findViewById(R.id.btn_delete_iv);
        ((RelativeLayout) findViewById(R.id.btn_delete)).setOnTouchListener(new OnTouchListener() {

            class C11541 implements OnDialogClick {
                C11541() {
                }

                public void onPositive() {
                    Alert mAlert = (Alert) new Gson().fromJson(EventDetailActivity.this.mEventInfo.getAlert(), Alert.class);
                    EventDetailActivity.this.mDatabaseAccess.deleteEvent(EventDetailActivity.this.mEventInfo.getId() + "");
                    EventDetailActivity.this.mAlarmHandler.stopAlert(mAlert);
                    EventDetailActivity.this.showToast(EventDetailActivity.this.getApplicationContext(), "Xóa sự kiện thành công !");
                    EventDetailActivity.this.setResult(-1);
                    EventDetailActivity.this.onBackPressed();
                }

                public void onNegative() {
                }
            }

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivDelete.setImageResource(R.drawable.header_delete);
                    NSDialog.showDialog(EventDetailActivity.this, "Bạn có muốn xóa sự kiện không ?", "Có", "Không", new C11541());
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivDelete.setImageResource(R.drawable.header_delete_select);
                    return true;
                }
            }
        });
        ((TextView) findViewById(R.id.tv_fullday_title)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView16)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView1611)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView16r)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView16raa)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView165)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView14565)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textViewddd14565)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_note)).setTypeface(this.typeRegularNew);
        processIntent();
        this.mAlarmHandler = new AlarmHandler(this);
        this.tvName = (TextView) findViewById(R.id.edt_name);
        this.tvName.setTypeface(this.typeBoldNew);
        this.tvAddress = (TextView) findViewById(R.id.edt_address);
        this.tvAddress.setTypeface(this.typeBoldNew);
        this.tvNote = (TextView) findViewById(R.id.edt_note);
        this.tvNote.setTypeface(this.typeBoldNew);
        this.tvFullDay = (TextView) findViewById(R.id.tv_fullday);
        this.tvFullDay.setTypeface(this.typeBoldNew);
        this.tvDayStart = (TextView) findViewById(R.id.tv_day_start);
        this.tvDayStart.setTypeface(this.typeBoldNew);
        this.tvHourStart = (TextView) findViewById(R.id.tv_hour_start);
        this.tvHourStart.setTypeface(this.typeBoldNew);
        this.tvDayEnd = (TextView) findViewById(R.id.tv_day_end);
        this.tvDayEnd.setTypeface(this.typeBoldNew);
        this.tvHourEnd = (TextView) findViewById(R.id.tv_hour_end);
        this.tvHourEnd.setTypeface(this.typeBoldNew);
        this.tvReport = (TextView) findViewById(R.id.tv_report);
        this.tvReport.setTypeface(this.typeBoldNew);
        this.tvRepeat = (TextView) findViewById(R.id.tv_repeat);
        this.tvRepeat.setTypeface(this.typeBoldNew);
        this.tvSolar = (TextView) findViewById(R.id.tv_solar);
        this.tvSolar.setTypeface(this.typeRegularNew);
        this.btnSave = (Button) findViewById(R.id.btn_save);
        this.imgType = (ImageView) findViewById(R.id.img_cate);
        this.tvDayStart.setOnClickListener(this);
        this.tvHourStart.setOnClickListener(this);
        this.tvDayEnd.setOnClickListener(this);
        this.tvHourEnd.setOnClickListener(this);
        this.tvReport.setOnClickListener(this);
        this.tvRepeat.setOnClickListener(this);
        this.btnSave.setOnClickListener(this);
        if (this.mEventInfo != null) {
            Log.d("hungkm", "mEvent info : " + this.mEventInfo.toString());
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            if (this.mEventInfo.getAlert() != null) {
                Alert mAlert = (Alert) new Gson().fromJson(this.mEventInfo.getAlert(), Alert.class);
                startTime.setTimeInMillis(mAlert.getStartTimeMillis());
                DateUtils.increaTime(startTime, mAlert);
                endTime.setTimeInMillis(mAlert.getEndTimeMillis());
                DateUtils.increaTime(endTime, mAlert);
            } else {
                startTime.setTime(DateUtils.convertStringToDate(this.mEventInfo.getStart().trim(), DateUtils.DATE_FORMAT2));
                endTime.setTime(DateUtils.convertStringToDate(this.mEventInfo.getEnd().trim(), DateUtils.DATE_FORMAT2));
                endTime.add(5, 1);
                if (this.mEventInfo.getSolar() == 0) {
                    Lunar startLunar = new Lunar();
                    Lunar endLunar = new Lunar();
                    startLunar.lunarDay = startTime.get(5);
                    startLunar.lunarMonth = startTime.get(2);
                    startLunar.lunarYear = startTime.get(1);
                    Solar startSolar = ConvertCalendar.LunarToSolar(startLunar);
                    if (startSolar.solarMonth >= 12) {
                        startTime.set(startSolar.solarYear, startSolar.solarMonth, startSolar.solarDay);
                    } else {
                        startTime.set(startSolar.solarYear, startSolar.solarMonth - 1, startSolar.solarDay);
                    }
                    endLunar.lunarDay = endTime.get(5);
                    endLunar.lunarMonth = endTime.get(2);
                    endLunar.lunarYear = endTime.get(1);
                    Solar endSolar = ConvertCalendar.LunarToSolar(endLunar);
                    if (endSolar.solarMonth >= 12) {
                        endTime.set(endSolar.solarYear, endSolar.solarMonth, endSolar.solarDay);
                    } else {
                        endTime.set(endSolar.solarYear, endSolar.solarMonth - 1, endSolar.solarDay);
                    }
                }
            }
            if (System.currentTimeMillis() >= startTime.getTimeInMillis() && System.currentTimeMillis() <= endTime.getTimeInMillis()) {
                this.tvFullDay.setText("Đang diễn ra");
            } else if (System.currentTimeMillis() > endTime.getTimeInMillis()) {
                this.tvFullDay.setText("Đã diễn ra");
            } else {
                long ms = Math.abs(System.currentTimeMillis() - startTime.getTimeInMillis());
                StringBuffer text = new StringBuffer("");
                if (ms > 86400000) {
                    text.append(ms / 86400000).append(" ngày ");
                    ms %= 86400000;
                } else {
                    if (ms > 3600000) {
                        text.append(ms / 3600000).append(" giờ ");
                        ms %= 3600000;
                    }
                    if (ms > 60000) {
                        text.append(ms / 60000).append(" phút ");
                        ms %= 60000;
                    }
                }
                this.tvFullDay.setText("" + text + " nữa");
            }
            String[] splitStart = this.mEventInfo.getStart().split(" ");
            String[] splitEnd = this.mEventInfo.getEnd().split(" ");
            if (splitStart.length == 2) {
                this.tvDayStart.setText(splitStart[0]);
                this.tvHourStart.setText(splitStart[1]);
            }
            if (splitEnd.length == 2) {
                this.tvDayEnd.setText(splitEnd[0]);
                this.tvHourEnd.setText(splitEnd[1]);
            }
            if (this.mEventInfo.getNote() == null) {
                this.btnSave.setVisibility(8);
                this.tvReport.setText("Hàng năm");
                this.tvRepeat.setText("Lúc xảy ra sự kiện");
                this.tvSolar.setText(this.mEventInfo.getSolar() == 1 ? "Dương lịch" : "Âm lịch");
                this.tvName.setText(this.mEventInfo.getName());
                ((RelativeLayout) findViewById(R.id.btn_delete)).setVisibility(8);
                return;
            }
            String report = this.mEventInfo.getReport();
            int obj = -1;
            switch (report.hashCode()) {
                case 48:
                    if (report.equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                        obj = 0;
                        break;
                    }
                    break;
                case 49:
                    if (report.equals("1")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 50:
                    if (report.equals("2")) {
                        obj = 2;
                        break;
                    }
                    break;
                case 51:
                    if (report.equals("3")) {
                        obj = 3;
                        break;
                    }
                    break;
                case 52:
                    if (report.equals("4")) {
                        obj = 4;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case 0:
                    this.tvReport.setText("Không lặp");
                    break;
                case 1:
                    this.tvReport.setText("Hàng ngày");
                    break;
                case 2:
                    this.tvReport.setText("Hàng tuần");
                    break;
                case 3:
                    this.tvReport.setText("Hàng tháng");
                    break;
                case 4:
                    this.tvReport.setText("Hàng năm");
                    break;
            }
            if (this.mEventInfo.getRepeat().contains(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                this.repeat += "Lúc xảy ra sự kiện,";
            }
            if (this.mEventInfo.getRepeat().contains("1")) {
                this.repeat += "Trước 5 phút,";
            }
            if (this.mEventInfo.getRepeat().contains("2")) {
                this.repeat += "Trước 15 phút,";
            }
            if (this.mEventInfo.getRepeat().contains("3")) {
                this.repeat += "Trước 30 phút,";
            }
            if (this.mEventInfo.getRepeat().contains("4")) {
                this.repeat += "Trước 1 giờ,";
            }
            if (this.repeat.endsWith(",")) {
                this.repeat = this.repeat.substring(0, this.repeat.length() - 1);
            }
            this.tvRepeat.setText(this.repeat);
            this.tvSolar.setText(this.mEventInfo.getSolar() == 1 ? "Dương lịch" : "Âm lịch");
            if (this.mEventInfo.getSolar() == 1) {
                this.imgType.setImageResource(R.drawable.duong_lich);
            } else {
                this.imgType.setImageResource(R.drawable.am_lich);
            }
            this.tvName.setText(this.mEventInfo.getName());
            if (!this.mEventInfo.getAddress().equals("")) {
                this.tvAddress.setText(this.mEventInfo.getAddress());
            }
            if (!this.mEventInfo.getAddress().equals("")) {
                this.tvNote.setText(this.mEventInfo.getNote());
            }
        }
    }

    private void processIntent() {
        Intent mIntent = getIntent();
        if (mIntent != null) {
            this.mEventInfo = (EventInfo) mIntent.getParcelableExtra("event_infos");
            if (this.mEventInfo == null && mIntent.getStringExtra("alertid") != null) {
                this.mEventInfo = this.mDatabaseAccess.getEventbyId(mIntent.getStringExtra("alertid"));
            }
        }
    }

    public void onBackPressed() {
        setResult(-1);
        super.onBackPressed();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                Intent mIntent = new Intent(getApplicationContext(), EventActivity.class);
                mIntent.putExtra("event_infos", this.mEventInfo);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                return;
            default:
                return;
        }
    }
}
