package com.example.lequan.lichvannien.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.ConvertCalendar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Lunar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Solar;
import com.facebook.appevents.AppEventsConstants;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.alert.AlarmHandler;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.custom.DatePicker;
import com.example.lequan.lichvannien.custom.DatePicker.onActionFromDatePicker;
import com.example.lequan.lichvannien.model.Alert;
import com.example.lequan.lichvannien.utils.DateUtils;
import com.example.lequan.lichvannien.utils.NSDialog;
import com.example.lequan.lichvannien.utils.NSDialog.OnDialogClick;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class EventActivity extends BaseActivity implements OnClickListener {
    public static int type = 3;
    private MainApplication application;
    private Button btnSave;
    DatePicker datePicker;
    private EditText edtAddress;
    private EditText edtName;
    private EditText edtNote;
    private String endH = "08:00";
    private ImageView imgCate;
    private boolean isCate = false;
    private boolean isFullDay = false;
    private boolean isReport = true;
    private boolean isSolar = true;
    private boolean isStart = true;
    ImageView ivTickAmLich;
    ImageView ivTickCaNgay;
    ImageView ivTickDuongLich;
    private RelativeLayout layoutCategory;
    private AlarmHandler mAlarmHandler;
    private Alert mAlert;
    private Calendar mEndCalendar;
    private EventInfo mEventInfo;
    private Calendar mStartCalendar;
    private String repeat = AppEventsConstants.EVENT_PARAM_VALUE_NO;
    private int report = 0;
    private String startH = "08:00";
    TextView tvCate;
    TextView tvDayEnd;
    TextView tvDayStart;
    TextView tvHourEnd;
    TextView tvHourStart;
    private TextView tvLunar;
    TextView tvRepeat;
    TextView tvReport;
    private TextView tvSolar;

    class C11431 implements onActionFromDatePicker {
        C11431() {
        }

        public void onSelect(String date) {
            Date selectdDate = DateUtils.convertStringToDate(date, Define.TIME_FORMAT);
            Calendar tempCalendar = Calendar.getInstance();
            tempCalendar.setTime(selectdDate);
            if (EventActivity.this.isStart) {
                EventActivity.this.tvDayStart.setText(date.replace("/", "-"));
                EventActivity.this.mStartCalendar.set(tempCalendar.get(1), tempCalendar.get(2), tempCalendar.get(5));
                return;
            }
            EventActivity.this.tvDayEnd.setText(date.replace("/", "-"));
            EventActivity.this.mEndCalendar.set(tempCalendar.get(1), tempCalendar.get(2), tempCalendar.get(5));
            if (EventActivity.this.mEndCalendar.before(EventActivity.this.mStartCalendar)) {
                EventActivity.this.showToast(EventActivity.this, "Vui lòng chọn thời gian kết thúc sau thời gian bắt đầu.");
                EventActivity.this.mEndCalendar.setTimeInMillis(EventActivity.this.mStartCalendar.getTimeInMillis());
                EventActivity.this.tvDayEnd.setText(DateUtils.convertDateToString(EventActivity.this.mEndCalendar.getTime(), "dd-MM-yyyy"));
                EventActivity.this.tvHourEnd.setText(DateUtils.convertDateToString(EventActivity.this.mEndCalendar.getTime(), DateUtils.TIME_FORMAT1));
            }
        }
    }

    class C11474 implements OnClickListener {
        C11474() {
        }

        public void onClick(View v) {
            EventActivity.this.isSolar = true;
            EventActivity.this.ivTickDuongLich.setImageResource(R.drawable.taosukien_ic_chon);
            EventActivity.this.ivTickAmLich.setImageResource(R.drawable.taosukien_ic_khong_chon);
        }
    }

    class C11485 implements OnClickListener {
        C11485() {
        }

        public void onClick(View v) {
            EventActivity.this.isSolar = false;
            EventActivity.this.ivTickDuongLich.setImageResource(R.drawable.taosukien_ic_khong_chon);
            EventActivity.this.ivTickAmLich.setImageResource(R.drawable.taosukien_ic_chon);
        }
    }

    class C11496 implements OnClickListener {
        C11496() {
        }

        public void onClick(View v) {
            EventActivity.this.isStart = true;
            EventActivity.this.showTimePicker();
        }
    }

    class C11507 implements OnClickListener {
        C11507() {
        }

        public void onClick(View v) {
            EventActivity.this.isStart = false;
            EventActivity.this.showTimePicker();
        }
    }

    class C11518 implements OnClickListener {
        C11518() {
        }

        public void onClick(View v) {
            EventActivity.this.isStart = true;
            EventActivity.this.datePicker.setVisibility(0);
        }
    }

    class C11529 implements OnClickListener {
        C11529() {
        }

        public void onClick(View v) {
            EventActivity.this.isStart = false;
            EventActivity.this.datePicker.setVisibility(0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_event);
        this.application = (MainApplication) getApplication();
        InitUI();
    }

    private void InitUI() {
        this.datePicker = (DatePicker) findViewById(R.id.datePicker);
        this.datePicker.setOnActionFromDatePicker(new C11431());
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
                    EventActivity.this.onBackPressed();
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
        final ImageView ivSave = (ImageView) findViewById(R.id.btnSave_iv);
        ((RelativeLayout) findViewById(R.id.btnSave)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int i = 0;
                if (event.getAction() == 1) {
                    ivSave.setImageResource(R.drawable.header_save);
                    String name = EventActivity.this.edtName.getText().toString();
                    if (name.length() == 0) {
                        NSDialog.showDialogBasic(EventActivity.this, "Vui lòng nhập  sự kiện !");
                        return true;
                    }
                    int i2;
                    String msg = EventActivity.this.mEventInfo == null ? "Bạn có muốn thêm sự kiện không ?" : "Bạn có muốn cập nhật sự kiện không ?";
                    String startHour = EventActivity.this.tvHourStart.getText().toString().length() == 0 ? "08:00" : EventActivity.this.tvHourStart.getText().toString();
                    String endHour = EventActivity.this.tvHourEnd.getText().toString().length() == 0 ? "08:00" : EventActivity.this.tvHourEnd.getText().toString();
                    final EventInfo mEvent = new EventInfo();
                    mEvent.setType(EventActivity.type);
                    mEvent.setName(name);
                    mEvent.setReport(String.valueOf(EventActivity.this.report));
                    mEvent.setRepeat(EventActivity.this.repeat);
                    if (EventActivity.this.isFullDay) {
                        i2 = 1;
                    } else {
                        i2 = 0;
                    }
                    mEvent.setFullDay(i2);
                    if (EventActivity.this.isSolar) {
                        i = 1;
                    }
                    mEvent.setSolar(i);
                    mEvent.setStart(Utils.converDateEvent(EventActivity.this.tvDayStart.getText().toString()) + " " + startHour);
                    mEvent.setEnd(Utils.converDateEvent(EventActivity.this.tvDayEnd.getText().toString()) + " " + endHour);
                    mEvent.setAddress(EventActivity.this.edtAddress.getText().toString().trim());
                    mEvent.setNote(EventActivity.this.edtNote.getText().toString().trim());
                    EventActivity.this.mAlert.setLabel(name);
                    EventActivity.this.mAlert.setVibrate(true);
                    EventActivity.this.mAlert.setOn(true);
                    NSDialog.showDialog(EventActivity.this, msg, "Có", "Không", new OnDialogClick() {
                        public void onPositive() {
                            if (EventActivity.this.mEventInfo != null) {
                                if (!EventActivity.this.isSolar) {
                                    Lunar startLunar = new Lunar();
                                    startLunar.lunarDay = EventActivity.this.mStartCalendar.get(5);
                                    startLunar.lunarMonth = EventActivity.this.mStartCalendar.get(2);
                                    startLunar.lunarYear = EventActivity.this.mStartCalendar.get(1);
                                    Log.d("hungkm", "startLunar : " + startLunar.lunarDay + "-" + startLunar.lunarMonth + "-" + startLunar.lunarYear);
                                    Solar startSolar = ConvertCalendar.LunarToSolar(startLunar);
                                    Log.d("hungkm", "startSolar : " + startSolar.solarDay + "-" + startSolar.solarMonth + "-" + startSolar.solarYear);
                                    EventActivity.this.mStartCalendar.set(startSolar.solarYear, startSolar.solarMonth, startSolar.solarDay);
                                    Lunar endLunar = new Lunar();
                                    endLunar.lunarDay = EventActivity.this.mEndCalendar.get(5);
                                    endLunar.lunarMonth = EventActivity.this.mEndCalendar.get(2);
                                    endLunar.lunarYear = EventActivity.this.mEndCalendar.get(1);
                                    Solar endSolar = ConvertCalendar.LunarToSolar(endLunar);
                                    EventActivity.this.mEndCalendar.set(endSolar.solarYear, endSolar.solarMonth, endSolar.solarDay);
                                }
                                EventActivity.this.mAlert.setStartTime(EventActivity.this.mStartCalendar.getTimeInMillis());
                                EventActivity.this.mAlert.setEndTime(EventActivity.this.mEndCalendar.getTimeInMillis());
                                int id = EventActivity.this.mEventInfo.getId();
                                mEvent.setId(id);
                                EventActivity.this.mAlert.setId(id);
                                mEvent.setAlert(EventActivity.this.mAlert.toString());
                                Log.d("hungkm", "update saved : " + mEvent.toString());
                                EventActivity.this.mDatabaseAccess.updateEvent(mEvent);
                                EventActivity.this.mAlarmHandler.startAlert(EventActivity.this.mAlert);
                                EventActivity.this.showToast(EventActivity.this.getApplicationContext(), "Cập nhật sự kiện thành công !");
                                EventActivity.this.onBackPressed();
                                EventActivity.this.finish();
                                return;
                            }
                            if (!EventActivity.this.isSolar) {
                                Lunar startLunar = new Lunar();
                                startLunar.lunarDay = EventActivity.this.mStartCalendar.get(5);
                                startLunar.lunarMonth = EventActivity.this.mStartCalendar.get(2);
                                startLunar.lunarYear = EventActivity.this.mStartCalendar.get(1);
                                Log.d("hungkm", "startLunar : " + startLunar.lunarDay + "-" + startLunar.lunarMonth + "-" + startLunar.lunarYear);
                                Solar startSolar = ConvertCalendar.LunarToSolar(startLunar);
                                Log.d("hungkm", "startSolar : " + startSolar.solarDay + "-" + startSolar.solarMonth + "-" + startSolar.solarYear);
                                EventActivity.this.mStartCalendar.set(startSolar.solarYear, startSolar.solarMonth, startSolar.solarDay);
                                Lunar endLunar = new Lunar();
                                endLunar.lunarDay = EventActivity.this.mEndCalendar.get(5);
                                endLunar.lunarMonth = EventActivity.this.mEndCalendar.get(2);
                                endLunar.lunarYear = EventActivity.this.mEndCalendar.get(1);
                                Solar endSolar = ConvertCalendar.LunarToSolar(endLunar);
                                EventActivity.this.mEndCalendar.set(endSolar.solarYear, endSolar.solarMonth, endSolar.solarDay);
                            }
                            EventActivity.this.mAlert.setStartTime(EventActivity.this.mStartCalendar.getTimeInMillis());
                            EventActivity.this.mAlert.setEndTime(EventActivity.this.mEndCalendar.getTimeInMillis());
                            int id = EventActivity.this.mDatabaseAccess.getMaxEventID() + 1;
                            mEvent.setId(id);
                            EventActivity.this.mAlert.setId(id);
                            mEvent.setAlert(EventActivity.this.mAlert.toString());
                            Log.d("hungkm", "alert saved : " + mEvent.toString());
                            EventActivity.this.mDatabaseAccess.addEvent(mEvent);
                            EventActivity.this.mAlarmHandler.startAlert(EventActivity.this.mAlert);
                            EventActivity.this.showToast(EventActivity.this.getApplicationContext(), "Lưu sự kiện thành công !");
                            EventActivity.this.onBackPressed();
                            EventActivity.this.finish();
                        }

                        public void onNegative() {
                        }
                    });
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivSave.setImageResource(R.drawable.header_save_seleted);
                    return true;
                }
            }
        });
        processIntent();
        this.mAlarmHandler = new AlarmHandler(this);
        this.mAlert = new Alert();
        this.mStartCalendar = Calendar.getInstance();
        this.mEndCalendar = Calendar.getInstance();
        this.mStartCalendar.set(13, 0);
        this.mStartCalendar.set(14, 0);
        this.mEndCalendar.set(13, 0);
        this.mEndCalendar.set(14, 0);
        this.layoutCategory = (RelativeLayout) findViewById(R.id.layout_category);
        this.edtName = (EditText) findViewById(R.id.edt_name);
        this.edtAddress = (EditText) findViewById(R.id.edt_address);
        this.edtNote = (EditText) findViewById(R.id.edt_note);
        this.ivTickDuongLich = (ImageView) findViewById(R.id.ivTickDuongLich);
        this.ivTickAmLich = (ImageView) findViewById(R.id.ivTickAmLich);
        this.ivTickCaNgay = (ImageView) findViewById(R.id.ivTickCaNgay);
        ((RelativeLayout) findViewById(R.id.rlDuongLich)).setOnClickListener(new C11474());
        ((RelativeLayout) findViewById(R.id.rlAmLich)).setOnClickListener(new C11485());
        this.tvSolar = (TextView) findViewById(R.id.tv_solar);
        this.tvSolar.setTypeface(this.typeRegularNew);
        this.tvLunar = (TextView) findViewById(R.id.tv_lunar);
        this.tvLunar.setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_fullday)).setTypeface(this.typeRegularNew);
        this.tvDayStart = (TextView) findViewById(R.id.tv_day_start);
        this.tvDayStart.setTypeface(this.typeRegularNew);
        this.tvHourStart = (TextView) findViewById(R.id.tv_hour_start);
        this.tvHourStart.setTypeface(this.typeRegularNew);
        this.tvDayEnd = (TextView) findViewById(R.id.tv_day_end);
        this.tvDayEnd.setTypeface(this.typeRegularNew);
        this.tvHourEnd = (TextView) findViewById(R.id.tv_hour_end);
        this.tvHourEnd.setTypeface(this.typeRegularNew);
        this.tvReport = (TextView) findViewById(R.id.tv_report);
        this.tvReport.setTypeface(this.typeRegularNew);
        this.tvRepeat = (TextView) findViewById(R.id.tv_repeat);
        this.tvRepeat.setTypeface(this.typeRegularNew);
        this.imgCate = (ImageView) findViewById(R.id.img_cate);
        this.tvCate = (TextView) findViewById(R.id.tv_cate_content);
        this.tvCate.setTypeface(this.typeBoldNew);
        ((TextView) findViewById(R.id.tv_cate)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView16)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView16r)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView165)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView14565)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textViewddd14565)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_note)).setTypeface(this.typeRegularNew);
        this.btnSave = (Button) findViewById(R.id.btn_save);
        this.layoutCategory.setOnClickListener(this);
        ((LinearLayout) findViewById(R.id.llTimeStart)).setOnClickListener(new C11496());
        ((LinearLayout) findViewById(R.id.llTimeEnd)).setOnClickListener(new C11507());
        ((LinearLayout) findViewById(R.id.llDayStart)).setOnClickListener(new C11518());
        ((LinearLayout) findViewById(R.id.llDayEnd)).setOnClickListener(new C11529());
        ((LinearLayout) findViewById(R.id.llReport)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.isCate = false;
                EventActivity.this.showChooseSingle("Nhắc nhở", new String[]{"Không lặp", "Hàng ngày", "Hàng tuần", "Hàng tháng", "Hàng năm"});
            }
        });
        ((RelativeLayout) findViewById(R.id.rlReport)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.isCate = false;
                EventActivity.this.showChooseSingle("Nhắc nhở", new String[]{"Không lặp", "Hàng ngày", "Hàng tuần", "Hàng tháng", "Hàng năm"});
            }
        });
        ((RelativeLayout) findViewById(R.id.rlRepeat1)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.showChooseMutil("Nhắc nhở", new String[]{"Lúc xảy ra sự kiện", "Trước 5 phút", "Trước 15 phút", "Trước 30 phút", "Trước 1 giờ"});
            }
        });
        ((RelativeLayout) findViewById(R.id.rlRepeat)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.showChooseMutil("Nhắc nhở", new String[]{"Lúc xảy ra sự kiện", "Trước 5 phút", "Trước 15 phút", "Trước 30 phút", "Trước 1 giờ"});
            }
        });
        ((RelativeLayout) findViewById(R.id.rlCaNgay)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EventActivity.this.isFullDay) {
                    EventActivity.this.isFullDay = false;
                    EventActivity.this.ivTickCaNgay.setImageResource(R.drawable.taosukien_ic_khong_chon);
                    EventActivity.this.tvHourStart.setEnabled(true);
                    EventActivity.this.tvHourEnd.setEnabled(true);
                    EventActivity.this.tvHourStart.setText(EventActivity.this.startH);
                    EventActivity.this.tvHourEnd.setText(EventActivity.this.endH);
                    EventActivity.this.mStartCalendar.set(11, Integer.parseInt(EventActivity.this.startH.split(":")[0]));
                    EventActivity.this.mStartCalendar.set(12, Integer.parseInt(EventActivity.this.startH.split(":")[1]));
                    EventActivity.this.mEndCalendar.set(11, Integer.parseInt(EventActivity.this.endH.split(":")[0]));
                    EventActivity.this.mEndCalendar.set(12, Integer.parseInt(EventActivity.this.endH.split(":")[1]));
                    return;
                }
                EventActivity.this.isFullDay = true;
                EventActivity.this.ivTickCaNgay.setImageResource(R.drawable.taosukien_ic_chon);
                EventActivity.this.tvHourStart.setEnabled(false);
                EventActivity.this.tvHourEnd.setEnabled(false);
                EventActivity.this.tvHourEnd.setText("");
                EventActivity.this.tvHourStart.setText("");
                EventActivity.this.mStartCalendar.set(11, Integer.parseInt("08:00".split(":")[0]));
                EventActivity.this.mStartCalendar.set(12, Integer.parseInt("08:00".split(":")[1]));
                EventActivity.this.mEndCalendar.set(11, Integer.parseInt("08:00".split(":")[0]));
                EventActivity.this.mEndCalendar.set(12, Integer.parseInt("08:00".split(":")[1]));
            }
        });
        this.tvReport.setOnClickListener(this);
        this.tvRepeat.setOnClickListener(this);
        this.btnSave.setOnClickListener(this);
        if (this.mEventInfo == null) {
            this.mStartCalendar.set(1, Calendar.getInstance().get(1));
            this.mStartCalendar.set(2, Calendar.getInstance().get(2));
            this.mStartCalendar.set(5, Calendar.getInstance().get(5));
            this.mStartCalendar.set(11, Calendar.getInstance().get(11));
            this.mStartCalendar.set(12, Calendar.getInstance().get(12));
            this.mStartCalendar.add(12, 5);
            this.mEndCalendar.set(1, Calendar.getInstance().get(1));
            this.mEndCalendar.set(2, Calendar.getInstance().get(2));
            this.mEndCalendar.set(5, Calendar.getInstance().get(5));
            this.mEndCalendar.set(11, Calendar.getInstance().get(11));
            this.mEndCalendar.set(12, Calendar.getInstance().get(12));
            this.mEndCalendar.add(12, 35);
            this.tvDayStart.setText(DateUtils.convertDateToString(new Date(this.mStartCalendar.getTimeInMillis()), "dd-MM-yyyy"));
            this.tvDayEnd.setText(DateUtils.convertDateToString(new Date(this.mEndCalendar.getTimeInMillis()), "dd-MM-yyyy"));
            this.mAlert.resetArrTimeDistance();
            this.mAlert.setDistanceIndexEnable(4);
            this.mAlert.setDistanceIndexEnable(0);
            if (type == 0 || type == 1 || type == 3) {
                this.isFullDay = false;
                this.ivTickCaNgay.setImageResource(R.drawable.taosukien_ic_khong_chon);
                this.tvHourStart.setText(DateUtils.convertDateToString(new Date(this.mStartCalendar.getTimeInMillis()), DateUtils.TIME_FORMAT1));
                this.tvHourEnd.setText(DateUtils.convertDateToString(new Date(this.mEndCalendar.getTimeInMillis()), DateUtils.TIME_FORMAT1));
                this.startH = this.tvHourStart.getText().toString();
                this.endH = this.tvHourEnd.getText().toString();
                this.tvReport.setText("Không lặp");
                this.tvRepeat.setText("Lúc xảy ra sự kiện");
                if (type == 0) {
                    this.imgCate.setImageResource(R.drawable.gia_dinh_mini);
                    this.tvCate.setText("Gia Đình");
                } else if (type == 1) {
                    this.imgCate.setImageResource(R.drawable.cong_viec_mini);
                    this.tvCate.setText("Công việc");
                } else {
                    this.imgCate.setImageResource(R.drawable.ca_nhan_mini);
                    this.tvCate.setText("Cá nhân");
                }
            } else {
                this.isFullDay = true;
                this.ivTickCaNgay.setImageResource(R.drawable.taosukien_ic_chon);
                this.tvHourStart.setText("");
                this.tvHourEnd.setText("");
                this.tvReport.setText("Hàng năm");
                this.tvRepeat.setText("Lúc xảy ra sự kiện");
                if (type == 2) {
                    this.imgCate.setImageResource(R.drawable.sinh_nhat_mini);
                    this.tvCate.setText("Sinh nhật");
                }
            }
            this.ivTickDuongLich.setImageResource(R.drawable.taosukien_ic_chon);
            this.ivTickAmLich.setImageResource(R.drawable.taosukien_ic_khong_chon);
            return;
        }
        type = this.mEventInfo.getType();
        if (type == 0) {
            this.imgCate.setImageResource(R.drawable.gia_dinh_mini);
            this.tvCate.setText("Gia Đình");
        } else if (type == 1) {
            this.imgCate.setImageResource(R.drawable.cong_viec_mini);
            this.tvCate.setText("Công việc");
        } else if (type == 2) {
            this.imgCate.setImageResource(R.drawable.sinh_nhat_mini);
            this.tvCate.setText("Sinh nhật");
        } else {
            this.imgCate.setImageResource(R.drawable.ca_nhan_mini);
            this.tvCate.setText("Cá nhân");
        }
        this.isSolar = this.mEventInfo.getSolar() == 1;
        if (this.isSolar) {
            this.mAlert = (Alert) new Gson().fromJson(this.mEventInfo.getAlert(), Alert.class);
            this.mStartCalendar.setTimeInMillis(this.mAlert.getStartTimeMillis());
            this.mEndCalendar.setTimeInMillis(this.mAlert.getEndTimeMillis());
        } else {
            this.mAlert = (Alert) new Gson().fromJson(this.mEventInfo.getAlert(), Alert.class);
            this.mStartCalendar.setTimeInMillis(DateUtils.convertStringToDate(this.mEventInfo.getStart(), "yyyy-MM-dd HH:mm").getTime());
            this.mEndCalendar.setTimeInMillis(DateUtils.convertStringToDate(this.mEventInfo.getEnd(), "yyyy-MM-dd HH:mm").getTime());
        }
        this.ivTickDuongLich.setImageResource(R.drawable.taosukien_ic_khong_chon);
        this.ivTickAmLich.setImageResource(R.drawable.taosukien_ic_chon);
        this.isFullDay = this.mEventInfo.getFullDay() == 1;
        String[] splitStart = this.mEventInfo.getStart().split(" ");
        String[] splitEnd = this.mEventInfo.getEnd().split(" ");
        if (splitStart.length == 2) {
            this.tvDayStart.setText(Utils.converDateEvent(splitStart[0]));
            this.tvHourStart.setText(splitStart[1]);
        }
        if (splitEnd.length == 2) {
            this.tvDayEnd.setText(Utils.converDateEvent(splitEnd[0]));
            this.tvHourEnd.setText(splitEnd[1]);
        }
        if (this.isFullDay) {
            this.ivTickCaNgay.setImageResource(R.drawable.taosukien_ic_chon);
            this.tvHourStart.setEnabled(false);
            this.tvHourEnd.setEnabled(false);
            this.tvHourEnd.setText("");
            this.tvHourStart.setText("");
        } else {
            this.ivTickCaNgay.setImageResource(R.drawable.taosukien_ic_khong_chon);
            this.tvHourStart.setEnabled(true);
            this.tvHourEnd.setEnabled(true);
        }
        this.report = Integer.parseInt(this.mEventInfo.getReport());
        switch (this.report) {
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
        this.repeat = this.mEventInfo.getRepeat();
        String repeat_str = "";
        if (this.mEventInfo.getRepeat().contains(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
            repeat_str = repeat_str + "Lúc xảy ra sự kiện,";
        }
        if (this.mEventInfo.getRepeat().contains("1")) {
            repeat_str = repeat_str + "Trước 5 phút,";
        }
        if (this.mEventInfo.getRepeat().contains("2")) {
            repeat_str = repeat_str + "Trước 15 phút,";
        }
        if (this.mEventInfo.getRepeat().contains("3")) {
            repeat_str = repeat_str + "Trước 30 phút,";
        }
        if (this.mEventInfo.getRepeat().contains("4")) {
            repeat_str = repeat_str + "Trước 1 giờ,";
        }
        if (repeat_str.endsWith(",")) {
            repeat_str = repeat_str.substring(0, repeat_str.length() - 1);
        }
        this.tvRepeat.setText(repeat_str);
        this.edtName.setText(this.mEventInfo.getName());
        this.edtAddress.setText(this.mEventInfo.getAddress());
        this.edtNote.setText(this.mEventInfo.getNote());
        this.startH = this.tvHourStart.getText().toString();
        this.endH = this.tvHourEnd.getText().toString();
    }

    public void onClick(View v) {
        int i = 0;
        switch (v.getId()) {
            case R.id.btnSave:
            case R.id.btn_save:
                String name = this.edtName.getText().toString();
                if (name.length() == 0) {
                    NSDialog.showDialogBasic(this, "Vui lòng nhập  sự kiện !");
                    return;
                }
                int i2;
                String msg = this.mEventInfo == null ? "Bạn có muốn thêm sự kiện không ?" : "Bạn có muốn cập nhật sự kiện không ?";
                String startHour = this.tvHourStart.getText().toString().length() == 0 ? "08:00" : this.tvHourStart.getText().toString();
                String endHour = this.tvHourEnd.getText().toString().length() == 0 ? "08:00" : this.tvHourEnd.getText().toString();
                final EventInfo mEvent = new EventInfo();
                mEvent.setType(type);
                mEvent.setName(name);
                mEvent.setReport(String.valueOf(this.report));
                mEvent.setRepeat(this.repeat);
                if (this.isFullDay) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                mEvent.setFullDay(i2);
                if (this.isSolar) {
                    i = 1;
                }
                mEvent.setSolar(i);
                mEvent.setStart(Utils.converDateEvent(this.tvDayStart.getText().toString()) + " " + startHour);
                mEvent.setEnd(Utils.converDateEvent(this.tvDayEnd.getText().toString()) + " " + endHour);
                mEvent.setAddress(this.edtAddress.getText().toString().trim());
                mEvent.setNote(this.edtNote.getText().toString().trim());
                this.mAlert.setLabel(name);
                this.mAlert.setVibrate(true);
                this.mAlert.setOn(true);
                NSDialog.showDialog(this, msg, "Có", "Không", new OnDialogClick() {
                    public void onPositive() {
                        if (EventActivity.this.mEventInfo != null) {
                            if (!EventActivity.this.isSolar) {
                                Lunar startLunar = new Lunar();
                                startLunar.lunarDay = EventActivity.this.mStartCalendar.get(5);
                                startLunar.lunarMonth = EventActivity.this.mStartCalendar.get(2);
                                startLunar.lunarYear = EventActivity.this.mStartCalendar.get(1);
                                Log.d("hungkm", "startLunar : " + startLunar.lunarDay + "-" + startLunar.lunarMonth + "-" + startLunar.lunarYear);
                                Solar startSolar = ConvertCalendar.LunarToSolar(startLunar);
                                Log.d("hungkm", "startSolar : " + startSolar.solarDay + "-" + startSolar.solarMonth + "-" + startSolar.solarYear);
                                EventActivity.this.mStartCalendar.set(startSolar.solarYear, startSolar.solarMonth, startSolar.solarDay);
                                Lunar endLunar = new Lunar();
                                endLunar.lunarDay = EventActivity.this.mEndCalendar.get(5);
                                endLunar.lunarMonth = EventActivity.this.mEndCalendar.get(2);
                                endLunar.lunarYear = EventActivity.this.mEndCalendar.get(1);
                                Solar endSolar = ConvertCalendar.LunarToSolar(endLunar);
                                EventActivity.this.mEndCalendar.set(endSolar.solarYear, endSolar.solarMonth, endSolar.solarDay);
                            }
                            EventActivity.this.mAlert.setStartTime(EventActivity.this.mStartCalendar.getTimeInMillis());
                            EventActivity.this.mAlert.setEndTime(EventActivity.this.mEndCalendar.getTimeInMillis());
                            int id = EventActivity.this.mEventInfo.getId();
                            mEvent.setId(id);
                            EventActivity.this.mAlert.setId(id);
                            mEvent.setAlert(EventActivity.this.mAlert.toString());
                            Log.d("hungkm", "update saved : " + mEvent.toString());
                            EventActivity.this.mDatabaseAccess.updateEvent(mEvent);
                            EventActivity.this.mAlarmHandler.startAlert(EventActivity.this.mAlert);
                            EventActivity.this.showToast(EventActivity.this.getApplicationContext(), "Cập nhật sự kiện thành công !");
                            EventActivity.this.onBackPressed();
                            EventActivity.this.finish();
                            return;
                        }
                        if (!EventActivity.this.isSolar) {
                            Lunar startLunar = new Lunar();
                            startLunar.lunarDay = EventActivity.this.mStartCalendar.get(5);
                            startLunar.lunarMonth = EventActivity.this.mStartCalendar.get(2);
                            startLunar.lunarYear = EventActivity.this.mStartCalendar.get(1);
                            Log.d("hungkm", "startLunar : " + startLunar.lunarDay + "-" + startLunar.lunarMonth + "-" + startLunar.lunarYear);
                            Solar startSolar = ConvertCalendar.LunarToSolar(startLunar);
                            Log.d("hungkm", "startSolar : " + startSolar.solarDay + "-" + startSolar.solarMonth + "-" + startSolar.solarYear);
                            EventActivity.this.mStartCalendar.set(startSolar.solarYear, startSolar.solarMonth, startSolar.solarDay);
                            Lunar endLunar = new Lunar();
                            endLunar.lunarDay = EventActivity.this.mEndCalendar.get(5);
                            endLunar.lunarMonth = EventActivity.this.mEndCalendar.get(2);
                            endLunar.lunarYear = EventActivity.this.mEndCalendar.get(1);
                            Solar endSolar = ConvertCalendar.LunarToSolar(endLunar);
                            EventActivity.this.mEndCalendar.set(endSolar.solarYear, endSolar.solarMonth, endSolar.solarDay);
                        }
                        EventActivity.this.mAlert.setStartTime(EventActivity.this.mStartCalendar.getTimeInMillis());
                        EventActivity.this.mAlert.setEndTime(EventActivity.this.mEndCalendar.getTimeInMillis());
                        int id = EventActivity.this.mDatabaseAccess.getMaxEventID() + 1;
                        mEvent.setId(id);
                        EventActivity.this.mAlert.setId(id);
                        mEvent.setAlert(EventActivity.this.mAlert.toString());
                        Log.d("hungkm", "alert saved : " + mEvent.toString());
                        EventActivity.this.mDatabaseAccess.addEvent(mEvent);
                        EventActivity.this.mAlarmHandler.startAlert(EventActivity.this.mAlert);
                        EventActivity.this.showToast(EventActivity.this.getApplicationContext(), "Lưu sự kiện thành công !");
                        EventActivity.this.onBackPressed();
                        EventActivity.this.finish();
                    }

                    public void onNegative() {
                    }
                });
                return;
            case R.id.layout_category:
                this.isCate = true;
                onShowPopupWindow(v, type);
                return;
            default:
                return;
        }
    }

    public void onShowPopupWindow(View v, int type) {
        final PopupWindow popup = new PopupWindow(this);
        View layout = getLayoutInflater().inflate(R.layout.popup_choose_event_type, null);
        popup.setContentView(layout);
        popup.setHeight(-1);
        popup.setWidth(-1);
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        ((LinearLayout) layout.findViewById(R.id.ln_close_popup)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        RelativeLayout lnCongViec = (RelativeLayout) layout.findViewById(R.id.ln_type_cong_viec);
        RelativeLayout lnGiaDinh = (RelativeLayout) layout.findViewById(R.id.ln_type_gia_dinh);
        RelativeLayout lnSinhNhat = (RelativeLayout) layout.findViewById(R.id.ln_type_sinh_nhat);
        RelativeLayout lnCaNhan = (RelativeLayout) layout.findViewById(R.id.ln_type_ca_nhan);
        RelativeLayout lnNhatKy = (RelativeLayout) layout.findViewById(R.id.ln_type_nhat_ky);
        RelativeLayout rlCongViec = (RelativeLayout) layout.findViewById(R.id.ln_line_cong_viec);
        RelativeLayout rlGiaDinh = (RelativeLayout) layout.findViewById(R.id.ln_line_gia_dinh);
        RelativeLayout rlSinhNhat = (RelativeLayout) layout.findViewById(R.id.ln_line_sinh_nhat);
        RelativeLayout rlCaNhan = (RelativeLayout) layout.findViewById(R.id.ln_line_ca_nhan);
        RelativeLayout rlNhatKy = (RelativeLayout) layout.findViewById(R.id.ln_line_nhat_ky);
        ((TextView) layout.findViewById(R.id.tvCongViec)).setTypeface(this.typeBoldNew);
        ((TextView) layout.findViewById(R.id.tvCaNhan)).setTypeface(this.typeBoldNew);
        ((TextView) layout.findViewById(R.id.tvGiaDinh)).setTypeface(this.typeBoldNew);
        ((TextView) layout.findViewById(R.id.tvSinhNhat)).setTypeface(this.typeBoldNew);
        if (type == 0) {
            lnGiaDinh.setVisibility(8);
            rlGiaDinh.setVisibility(8);
        } else if (type == 1) {
            lnCongViec.setVisibility(8);
            rlCongViec.setVisibility(8);
        } else if (type == 2) {
            lnSinhNhat.setVisibility(8);
            rlSinhNhat.setVisibility(8);
        } else {
            lnCaNhan.setVisibility(8);
            rlCaNhan.setVisibility(8);
        }
        lnGiaDinh.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.onChoiseType("Gia Đình", 0);
                popup.dismiss();
            }
        });
        lnCongViec.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.onChoiseType("Công Việc", 1);
                popup.dismiss();
            }
        });
        lnSinhNhat.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.onChoiseType("Sinh Nhật", 2);
                popup.dismiss();
            }
        });
        lnCaNhan.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EventActivity.this.onChoiseType("Cá Nhân", 3);
                popup.dismiss();
            }
        });
        popup.showAsDropDown(v);
    }

    protected void onMutilChoise(CharSequence[] value, int[] index) {
        int i;
        this.mAlert.resetArrTimeDistance();
        for (i = 0; i < index.length; i++) {
            Log.d("hungkm", "onMutilChoise  index: " + index[i]);
            this.mAlert.setDistanceIndexEnable(4 - index[i]);
        }
        for (i = 0; i < this.mAlert.getArrTimeDistance().length; i++) {
            Log.d("hungkm", "after onMutilChoise  index " + i + ": " + this.mAlert.isEnableTimeDistance(i));
        }
        String listIndex = "";
        this.repeat = "";
        for (i = 0; i < value.length; i++) {
            if (i == value.length - 1) {
                this.repeat += value[i];
                listIndex = listIndex + index[i];
            } else {
                this.repeat += value[i] + ",";
                listIndex = listIndex + index[i] + ",";
            }
        }
        if (this.repeat.endsWith(",")) {
            this.repeat = this.repeat.substring(0, this.repeat.length() - 1);
        }
        this.tvRepeat.setText(this.repeat);
        this.repeat = listIndex;
    }

    protected void onChoiseType(String value, int index) {
        if (this.isCate) {
            type = index;
            if (type == 0) {
                this.imgCate.setImageResource(R.drawable.gia_dinh_mini);
                this.tvCate.setText("Gia Đình");
                return;
            } else if (type == 1) {
                this.imgCate.setImageResource(R.drawable.cong_viec_mini);
                this.tvCate.setText("Công việc");
                return;
            } else if (type == 2) {
                this.imgCate.setImageResource(R.drawable.sinh_nhat_mini);
                this.tvCate.setText("Sinh nhật");
                return;
            } else {
                this.imgCate.setImageResource(R.drawable.ca_nhan_mini);
                this.tvCate.setText("Cá nhân");
                return;
            }
        }
        this.tvReport.setText(value);
        this.report = index;
        this.mAlert.setFrequency(index);
    }

    protected void onChoise(String value, int index) {
        super.onChoise(value, index);
        this.tvReport.setText(value);
        this.report = index;
        this.mAlert.setFrequency(index);
    }

    protected void onSelectedTimePicker(String time) {
        super.onSelectedTimePicker(time);
        if (this.isStart) {
            this.tvHourStart.setText(time.substring(0, 5));
            this.startH = this.tvHourStart.getText().toString();
            this.mStartCalendar.set(11, Integer.parseInt(time.split(":")[0]));
            this.mStartCalendar.set(12, Integer.parseInt(time.split(":")[1]));
            this.mEndCalendar.set(11, Integer.parseInt(time.split(":")[0]));
            this.mEndCalendar.set(12, Integer.parseInt(time.split(":")[1]));
            this.mEndCalendar.add(12, 30);
            this.tvHourEnd.setText(this.mEndCalendar.get(11) < 10 ? AppEventsConstants.EVENT_PARAM_VALUE_NO + this.mEndCalendar.get(11) : this.mEndCalendar.get(11) + ":" + this.mEndCalendar.get(12));
            Log.v(BaseActivity.TAG, "time selected: " + time);
        } else {
            this.tvHourEnd.setText(time.substring(0, 5));
            this.endH = this.tvHourEnd.getText().toString();
            this.mEndCalendar.set(11, Integer.parseInt(time.split(":")[0]));
            this.mEndCalendar.set(12, Integer.parseInt(time.split(":")[1]));
            if (this.mEndCalendar.before(this.mStartCalendar)) {
                showToast(this, "Vui lòng chọn thời gian kết thúc sau thời gian bắt đầu.");
                this.mEndCalendar.setTimeInMillis(this.mStartCalendar.getTimeInMillis());
                this.tvDayEnd.setText(DateUtils.convertDateToString(this.mEndCalendar.getTime(), "dd-MM-yyyy"));
                this.tvHourEnd.setText(DateUtils.convertDateToString(this.mEndCalendar.getTime(), DateUtils.TIME_FORMAT1));
            }
        }
        Log.d("hungkm", "pciker time: " + this.mStartCalendar.toString());
    }

    protected void onSelectedDayPicker(String day) {
        Date selectdDate = DateUtils.convertStringToDate(day, Define.TIME_FORMAT);
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(selectdDate);
        if (this.isStart) {
            this.tvDayStart.setText(day.replace("/", "-"));
            this.mStartCalendar.set(tempCalendar.get(1), tempCalendar.get(2), tempCalendar.get(5));
        } else {
            this.tvDayEnd.setText(day.replace("/", "-"));
            this.mEndCalendar.set(tempCalendar.get(1), tempCalendar.get(2), tempCalendar.get(5));
            if (this.mEndCalendar.before(this.mStartCalendar)) {
                showToast(this, "Vui lòng chọn thời gian kết thúc sau thời gian bắt đầu.");
                this.mEndCalendar.setTimeInMillis(this.mStartCalendar.getTimeInMillis());
                this.tvDayEnd.setText(DateUtils.convertDateToString(this.mEndCalendar.getTime(), "dd-MM-yyyy"));
                this.tvHourEnd.setText(DateUtils.convertDateToString(this.mEndCalendar.getTime(), DateUtils.TIME_FORMAT1));
            }
        }
        Log.d("hungkm", "pciker date: " + this.mStartCalendar.toString());
    }

    private void processIntent() {
        Intent mIntent = getIntent();
        if (mIntent != null) {
            this.mEventInfo = (EventInfo) mIntent.getParcelableExtra("event_infos");
        }
    }
}
