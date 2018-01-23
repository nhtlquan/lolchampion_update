package com.example.lequan.lichvannien.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.lang.reflect.Method;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.RequestBody;

public class DatePickerCenter extends RelativeLayout {
    String API_DAT_TEN = "http://tracuu.tuvisomenh.com/dat-ten-cho-con/dat-ten-theo-ngu-hanh";
    String API_GIO_TOT_TRONG_NGAY = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-gio-tot-trong-ngay";
    String API_NGAY_TOTXAU = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau";
    String API_NGAY_TOT_TRONG_THANG = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-tot-trong-thang";
    String API_PHONG_THUY_HOANGOCKIMLAU = "http://tracuu.tuvisomenh.com/tool/bangtamtaihoangockimlau?yearView=";
    String API_PHONG_THUY_TAM_TAI = "http://tracuu.tuvisomenh.com/tool/xemhantamtai?day=dddddd&month=mmmmmm&year=";
    String API_PHONG_THUY_XAY_NHA = "http://tracuu.tuvisomenh.com/tool/xemtuoixaynha?day=dddddd&month=mmmmmm&year=yyyyyy&yearView=";
    String API_PHONG_THUY_XEM_HUONG = "http://tracuu.tuvisomenh.com/tool/xemhuongnha?dd=dddddd&mm=mmmmmm&yy=yyyyyy&sex=";
    String API_SIM = "http://tracuu.tuvisomenh.com/sim-so-dep/sim-phong-thuy-xxxxxx-hop-tuoi-sinh-hhhhhh-gio-0-phut-ngay-dddddd-thang-mmmmmm-nam-";
    String API_XEM_HOP_TUOI = "http://tracuu.tuvisomenh.com/tool/xemhoptuoi?name=xxxxxx&sex=yyyyyy&day=dddddd&month=mmmmmm&year=";
    String API_XEM_NGAY_CHUYEN_NHA = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-chuyen-nha/tuoi-chuyen-nha-dddddd-thang-mmmmmm-nam-";
    String API_XEM_NGAY_CUOI = "http://tracuu.tuvisomenh.com/tool/chonngaycuoi?md=dddddd&mm=mmmmmm&my=yyyyyy&wd=ddd&wm=mmm&wy=yyy&solarYear=";
    String API_XEM_NGAY_DO_TRAN = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-do-tran-nha/tuoi-lam-nha-dddddd-thang-mmmmmm-nam-yyyyyy/ngay-dinh-do-tran-ddd-thang-mmm-nam-";
    String API_XEM_NGAY_KHAI_TRUONG = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-khai-truong/tuoi-khai-truong-dddddd-thang-mmmmmm-nam-";
    String API_XEM_NGAY_KY_KET_HOP_DONG = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-ky-ket-hop-dong";
    String API_XEM_NGAY_LAM_NHA = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-lam-nha/tuoi-lam-nha-dddddd-thang-mmmmmm-nam-";
    String API_XEM_NGAY_MUA_NHA = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-mua-nha/tuoi-mua-nha-dddddd-thang-mmmmmm-nam-";
    String API_XEM_NGAY_MUA_XE = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-mua-xe/tuoi-mua-xe-dddddd-thang-mmmmmm-nam-";
    String API_XEM_NGAY_TRUNG_TANG = "http://tracuu.tuvisomenh.com/tool/tinhtrungtang?dd=dddddd&mm=mmmmmm&yy=yyyyyy&th=hhh&td=ddd&tm=mmm&ty=yyy&sex=";
    String API_XEM_NGAY_XUAT_HANH = "http://tracuu.tuvisomenh.com/tool/xuathanh?dd=dddddd&mm=";
    String API_XEM_SAO = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-sao-han/sao-han-xxxxxx-mang-sinh-ngay-";
    String API_XEM_TUOI_KET_HON = "http://tracuu.tuvisomenh.com/xem-tuoi/xem-tuoi-ket-hon-nam-sinh-ngay-dddddd-thang-mmmmmm-nam-yyyyyy-voi-nu-sinh-ngay-ddd-thang-mmm-nam-";
    String API_XEM_TUOI_LAM_AN = "http://tracuu.tuvisomenh.com/xem-tuoi/xem-tuoi-lam-an/sinh-ngay-dddddd-mmmmmm-yyyyyy-hop-tac-kinh-doanh-voi-nhung-tuoi-nao";
    String API_XEM_TUOI_SINH_CON = "http://tracuu.tuvisomenh.com/tool/xemnamsinhcon?fd=dddddd&fm=mmmmmm&fy=yyyyyy&md=ddddd&mm=mmmmm&my=yyyyy&cd=dddd&cm=mmmm&cy=";
    String API_XEM_TUOI_VO_CHONG = "http://tracuu.tuvisomenh.com/xem-tuoi/xem-tuoi-vo-chong-nam-hhhhhh-gio-0-phut-ngay-dddddd-thang-mmmmmm-nam-yyyyyy-voi-nu-hhh-gio-0-phut-ngay-ddd-thang-mmm-nam-";
    int day = 1;
    EditText et;
    private onActionFromDatePicker mOnActionFromDatePicker;
    int maxYear = 2029;
    int minYear = 1930;
    int month = 1;
    public NumberPicker numberPickerDay;
    public NumberPicker numberPickerHour;
    public NumberPicker numberPickerMonth;
    public NumberPicker numberPickerSex;
    public NumberPicker numberPickerYear;
    boolean setToDay = true;
    String tempAPI = "";
    int type = 1;
    Typeface typeBoldNew;
    Typeface typeRegularNew;
    int year = 1990;

    public interface onActionFromDatePicker {
        void onSelectedGET(String str);

        void onSelectedPOST(String str, RequestBody requestBody);
    }

    class C12431 implements OnClickListener {

        class C12421 implements OnClickListener {

            class C12411 implements OnClickListener {
                C12411() {
                }

                public void onClick(View v) {
                    DatePickerCenter.this.setVisibility(8);
                    StringBuilder stringBuilder = new StringBuilder();
                    DatePickerCenter datePickerCenter = DatePickerCenter.this;
                    datePickerCenter.tempAPI = stringBuilder.append(datePickerCenter.tempAPI).append(DatePickerCenter.this.numberPickerYear.getValue()).toString();
                    if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                        DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                    }
                    DatePickerCenter.this.initType5();
                }
            }

            C12421() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("ddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
                ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn năm cưới dự kiến");
                Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn năm cưới dự kiện!", 0).show();
                DatePickerCenter.this.numberPickerDay.setVisibility(8);
                DatePickerCenter.this.numberPickerMonth.setVisibility(8);
                DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
                TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
                tvSelectDatePicker.setText("XEM KẾT QUẢ");
                tvSelectDatePicker.setOnClickListener(new C12411());
            }
        }

        C12431() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_XEM_NGAY_CUOI.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh cô dâu");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày sinh cô dâu!", 0).show();
            DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
            DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            ((TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker)).setOnClickListener(new C12421());
        }
    }

    class C12452 implements OnClickListener {

        class C12441 implements OnClickListener {
            C12441() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.setVisibility(8);
                DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("ddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue();
                if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                    DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                }
                DatePickerCenter.this.initType8();
            }
        }

        C12452() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_XEM_NGAY_DO_TRAN.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày dự kiến làm");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày dự kiến làm!", 0).show();
            DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
            DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
            tvSelectDatePicker.setText("XEM KẾT QUẢ");
            tvSelectDatePicker.setOnClickListener(new C12441());
        }
    }

    class C12473 implements OnClickListener {

        class C12461 implements OnClickListener {
            C12461() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.setVisibility(8);
                DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("hhh", DatePickerCenter.this.numberPickerHour.getValue() + "").replace("ddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
                if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                    DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                }
                DatePickerCenter.this.initType14();
            }
        }

        C12473() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_XEM_NGAY_TRUNG_TANG.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "") + (DatePickerCenter.this.numberPickerSex.getValue() == 1 ? "True" : "False");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày mất");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày mất!", 0).show();
            DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
            DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            DatePickerCenter.this.numberPickerSex.setVisibility(8);
            DatePickerCenter.this.numberPickerHour.setVisibility(0);
            TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
            tvSelectDatePicker.setText("XEM KẾT QUẢ");
            tvSelectDatePicker.setOnClickListener(new C12461());
        }
    }

    class C12494 implements OnClickListener {

        class C12481 implements OnClickListener {
            C12481() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.setVisibility(8);
                DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("ddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue();
                if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                    DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                }
                DatePickerCenter.this.initType17();
            }
        }

        C12494() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_XEM_TUOI_KET_HON.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh cô dâu");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày sinh cô dâu!", 0).show();
            DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
            DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
            tvSelectDatePicker.setText("XEM KẾT QUẢ");
            tvSelectDatePicker.setOnClickListener(new C12481());
        }
    }

    class C12515 implements OnClickListener {

        class C12501 implements OnClickListener {
            C12501() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.setVisibility(8);
                DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("hhh", DatePickerCenter.this.numberPickerHour.getValue() + "").replace("ddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue();
                if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                    DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                }
                DatePickerCenter.this.initType18();
            }
        }

        C12515() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_XEM_TUOI_VO_CHONG.replace("hhhhhh", DatePickerCenter.this.numberPickerHour.getValue() + "").replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh vợ");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày sinh vợ!", 0).show();
            DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
            DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
            tvSelectDatePicker.setText("XEM KẾT QUẢ");
            tvSelectDatePicker.setOnClickListener(new C12501());
        }
    }

    class C12546 implements OnClickListener {

        class C12531 implements OnClickListener {

            class C12521 implements OnClickListener {
                C12521() {
                }

                public void onClick(View v) {
                    DatePickerCenter.this.setVisibility(8);
                    DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("dddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue();
                    if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                        DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                    }
                    DatePickerCenter.this.initType19();
                }
            }

            C12531() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.tempAPI = DatePickerCenter.this.tempAPI.replace("ddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
                ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh con");
                Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày sinh con!", 0).show();
                DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
                DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
                DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
                TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
                tvSelectDatePicker.setText("XEM KẾT QUẢ");
                tvSelectDatePicker.setOnClickListener(new C12521());
            }
        }

        C12546() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_XEM_TUOI_SINH_CON.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh mẹ");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn ngày sinh mẹ!", 0).show();
            DatePickerCenter.this.numberPickerDay.setValue(DatePickerCenter.this.day);
            DatePickerCenter.this.numberPickerMonth.setValue(DatePickerCenter.this.month);
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            ((TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker)).setOnClickListener(new C12531());
        }
    }

    class C12567 implements OnClickListener {

        class C12551 implements OnClickListener {
            C12551() {
            }

            public void onClick(View v) {
                DatePickerCenter.this.setVisibility(8);
                StringBuilder stringBuilder = new StringBuilder();
                DatePickerCenter datePickerCenter = DatePickerCenter.this;
                datePickerCenter.tempAPI = stringBuilder.append(datePickerCenter.tempAPI).append(DatePickerCenter.this.numberPickerYear.getValue()).toString();
                if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                    DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.tempAPI);
                }
                DatePickerCenter.this.initType22();
            }
        }

        C12567() {
        }

        public void onClick(View v) {
            DatePickerCenter.this.tempAPI = DatePickerCenter.this.API_PHONG_THUY_XAY_NHA.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "");
            ((TextView) DatePickerCenter.this.findViewById(R.id.tvTitle)).setText("Lựa chọn năm khởi công");
            Toast.makeText(DatePickerCenter.this.getContext(), "Lựa chọn năm khởi công!", 0).show();
            DatePickerCenter.this.numberPickerYear.setValue(DatePickerCenter.this.year);
            DatePickerCenter.this.numberPickerMonth.setVisibility(8);
            DatePickerCenter.this.numberPickerDay.setVisibility(8);
            TextView tvSelectDatePicker = (TextView) DatePickerCenter.this.findViewById(R.id.btSelectDatePicker);
            tvSelectDatePicker.setText("XEM KẾT QUẢ");
            tvSelectDatePicker.setOnClickListener(new C12551());
        }
    }

    class C12578 implements OnClickListener {
        C12578() {
        }

        public void onClick(View v) {
        }
    }

    class C12589 implements OnClickListener {
        C12589() {
        }

        public void onClick(View v) {
        }
    }

    public DatePickerCenter(Context context) {
        super(context);
        initView();
    }

    public DatePickerCenter(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DatePicker);
            if (a.hasValue(25)) {
                this.maxYear = a.getInt(25, 2029);
            }
            if (a.hasValue(26)) {
                this.minYear = a.getInt(26, 1930);
            }
            if (a.hasValue(27)) {
                this.setToDay = a.getBoolean(27, true);
            }
        }
        initView();
    }

    public DatePickerCenter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DatePicker);
            if (a.hasValue(25)) {
                this.maxYear = a.getInt(25, 2029);
            }
            if (a.hasValue(26)) {
                this.minYear = a.getInt(26, 1930);
            }
            if (a.hasValue(27)) {
                this.setToDay = a.getBoolean(27, true);
            }
        }
        initView();
    }

    public void setType(int type) {
        this.type = type;
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTypeface(this.typeBoldNew);
        tvTitle.setText("Lựa chọn ngày sinh Dương Lịch");
        switch (type) {
            case 1:
                this.numberPickerSex.setVisibility(0);
                return;
            case 3:
                tvTitle.setText("Chọn ngày cần xem");
                return;
            case 5:
                initType5();
                return;
            case 6:
                this.numberPickerYear.setVisibility(8);
                return;
            case 8:
                initType8();
                return;
            case 14:
                initType14();
                return;
            case 15:
                tvTitle.setText("Lựa chọn thời điểm sinh của con");
                this.numberPickerHour.setVisibility(0);
                this.numberPickerSex.setVisibility(0);
                String[] tempCurrentDay = Utils.getCurrentDay().split("/");
                this.numberPickerDay.setValue(Integer.parseInt(tempCurrentDay[0]));
                this.numberPickerMonth.setValue(Integer.parseInt(tempCurrentDay[1]));
                this.numberPickerYear.setValue(Integer.parseInt(tempCurrentDay[2]));
                return;
            case 17:
                initType17();
                return;
            case 18:
                initType18();
                return;
            case 19:
                initType19();
                return;
            case 20:
                this.numberPickerSex.setVisibility(0);
                this.et.setVisibility(0);
                this.et.setHint("Nhập họ tên");
                this.et.setInputType(1);
                return;
            case 21:
                tvTitle.setText("Lựa chọn ngày sinh Dương Lịch và số điện thoại");
                this.et.setVisibility(0);
                this.numberPickerHour.setVisibility(0);
                return;
            case 22:
                initType22();
                return;
            case 23:
                ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn tuổi gia chủ");
                this.numberPickerSex.setVisibility(0);
                return;
            case 25:
                ((TextView) findViewById(R.id.tvTitle)).setText("Năm cần xem");
                this.numberPickerDay.setVisibility(8);
                this.numberPickerMonth.setVisibility(8);
                return;
            default:
                return;
        }
    }

    void initType5() {
        this.numberPickerDay.setVisibility(0);
        this.numberPickerMonth.setVisibility(0);
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh chú rể");
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12431());
    }

    void initType8() {
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh Dương Lịch");
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12452());
    }

    void initType14() {
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh người quá cố");
        this.numberPickerSex.setVisibility(0);
        this.numberPickerHour.setVisibility(8);
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12473());
    }

    void initType17() {
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh chú rể");
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12494());
    }

    void initType18() {
        this.numberPickerHour.setVisibility(0);
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh chồng");
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12515());
    }

    void initType19() {
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh cha");
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12546());
    }

    void initType22() {
        ((TextView) findViewById(R.id.tvTitle)).setText("Lựa chọn ngày sinh gia chủ");
        this.numberPickerMonth.setVisibility(0);
        this.numberPickerDay.setVisibility(0);
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setText("TIẾP THEO");
        tvSelectDatePicker.setOnClickListener(new C12567());
    }

    void initView() {
        this.typeRegularNew = Typeface.createFromAsset(getContext().getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(getContext().getAssets(), "fonts/UTM HelveBold.ttf");
        View mView = inflate(getContext(), R.layout.date_picker_center, null);
        addView(mView);
        ((RelativeLayout) mView.findViewById(R.id.rlCancel)).setOnClickListener(new C12578());
        ((RelativeLayout) mView.findViewById(R.id.rlCancel2)).setOnClickListener(new C12589());
        TextView tvSelectDatePicker = (TextView) findViewById(R.id.btSelectDatePicker);
        tvSelectDatePicker.setTypeface(this.typeRegularNew);
        tvSelectDatePicker.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                switch (DatePickerCenter.this.type) {
                    case 1:
                        String url = DatePickerCenter.this.API_XEM_SAO.replace("xxxxxx", DatePickerCenter.this.numberPickerSex.getValue() == 1 ? "nam" : "nu") + DatePickerCenter.this.numberPickerDay.getValue() + "-" + DatePickerCenter.this.numberPickerMonth.getValue() + "-" + DatePickerCenter.this.numberPickerYear.getValue();
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(url);
                            break;
                        }
                        break;
                    case 3:
                        RequestBody formBody = new Builder().setType(MultipartBody.FORM).addFormDataPart("sday", DatePickerCenter.this.numberPickerDay.getValue() + "").addFormDataPart("smonth", DatePickerCenter.this.numberPickerMonth.getValue() + "").addFormDataPart("syear", DatePickerCenter.this.numberPickerYear.getValue() + "").build();
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedPOST(DatePickerCenter.this.API_GIO_TOT_TRONG_NGAY, formBody);
                            break;
                        }
                        break;
                    case 6:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_NGAY_XUAT_HANH.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "") + DatePickerCenter.this.numberPickerMonth.getValue());
                            break;
                        }
                        break;
                    case 7:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_NGAY_LAM_NHA.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                    case 9:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_NGAY_MUA_NHA.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                    case 10:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_NGAY_CHUYEN_NHA.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                    case 11:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_NGAY_KHAI_TRUONG.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                    case 12:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_NGAY_MUA_XE.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                    case 15:
                        RequestBody formBodyDatTen = new Builder().setType(MultipartBody.FORM).addFormDataPart("sday", DatePickerCenter.this.numberPickerDay.getValue() + "").addFormDataPart("smonth", DatePickerCenter.this.numberPickerMonth.getValue() + "").addFormDataPart("syear", DatePickerCenter.this.numberPickerYear.getValue() + "").addFormDataPart("shour", DatePickerCenter.this.numberPickerHour.getValue() + "").addFormDataPart("sminute", "00").addFormDataPart("sex", DatePickerCenter.this.numberPickerSex.getValue() == 1 ? "True" : "False").build();
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedPOST(DatePickerCenter.this.API_DAT_TEN, formBodyDatTen);
                            break;
                        }
                        break;
                    case 16:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_TUOI_LAM_AN.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + ""));
                            break;
                        }
                        break;
                    case 20:
                        if (!DatePickerCenter.this.et.getText().toString().equals("")) {
                            String tempSex20 = DatePickerCenter.this.numberPickerSex.getValue() == 1 ? "True" : "False";
                            if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                                DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_XEM_HOP_TUOI.replace("xxxxxx", DatePickerCenter.this.et.getText().toString().replaceAll(" ", "+")).replace("yyyyyy", tempSex20).replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            }
                            DatePickerCenter.this.setVisibility(8);
                            break;
                        }
                        Toast.makeText(DatePickerCenter.this.getContext(), "Hãy điền đầy đủ thông tin!", 0).show();
                        break;
                    case 21:
                        if (!DatePickerCenter.this.et.getText().toString().equals("")) {
                            if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                                DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_SIM.replace("xxxxxx", DatePickerCenter.this.et.getText().toString()).replace("hhhhhh", DatePickerCenter.this.numberPickerHour.getValue() + "").replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            }
                            DatePickerCenter.this.setVisibility(8);
                            break;
                        }
                        Toast.makeText(DatePickerCenter.this.getContext(), "Hãy điền đầy đủ thông tin!", 0).show();
                        break;
                    case 23:
                        String tempSex = DatePickerCenter.this.numberPickerSex.getValue() == 1 ? "True" : "False";
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_PHONG_THUY_XEM_HUONG.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "").replace("yyyyyy", DatePickerCenter.this.numberPickerYear.getValue() + "") + tempSex);
                            break;
                        }
                        break;
                    case 24:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_PHONG_THUY_TAM_TAI.replace("dddddd", DatePickerCenter.this.numberPickerDay.getValue() + "").replace("mmmmmm", DatePickerCenter.this.numberPickerMonth.getValue() + "") + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                    case 25:
                        if (DatePickerCenter.this.mOnActionFromDatePicker != null) {
                            DatePickerCenter.this.mOnActionFromDatePicker.onSelectedGET(DatePickerCenter.this.API_PHONG_THUY_HOANGOCKIMLAU + DatePickerCenter.this.numberPickerYear.getValue());
                            break;
                        }
                        break;
                }
                if (DatePickerCenter.this.type != 20 && DatePickerCenter.this.type != 21) {
                    DatePickerCenter.this.setVisibility(8);
                }
            }
        });
        this.numberPickerDay = (NumberPicker) findViewById(R.id.numberPickerDay);
        this.numberPickerDay.setFormatter(new Formatter() {
            public String format(int value) {
                return "Ng. " + value;
            }
        });
        this.numberPickerDay.setMaxValue(31);
        this.numberPickerDay.setMinValue(1);
        this.numberPickerDay.setWrapSelectorWheel(false);
        this.numberPickerMonth = (NumberPicker) findViewById(R.id.numberPickerMonth);
        this.numberPickerMonth.setFormatter(new Formatter() {
            public String format(int value) {
                return "Th. " + value;
            }
        });
        this.numberPickerMonth.setMaxValue(12);
        this.numberPickerMonth.setMinValue(1);
        this.numberPickerMonth.setWrapSelectorWheel(false);
        this.numberPickerYear = (NumberPicker) findViewById(R.id.numberPickerYear);
        this.numberPickerYear.setMaxValue(this.maxYear);
        this.numberPickerYear.setMinValue(this.minYear);
        this.numberPickerYear.setWrapSelectorWheel(false);
        this.numberPickerSex = (NumberPicker) findViewById(R.id.numberPickerSex);
        this.numberPickerSex.setFormatter(new Formatter() {
            public String format(int value) {
                if (value == 1) {
                    return "Nam";
                }
                return "Nữ";
            }
        });
        this.numberPickerSex.setMaxValue(2);
        this.numberPickerSex.setMinValue(1);
        this.numberPickerSex.setWrapSelectorWheel(false);
        this.numberPickerHour = (NumberPicker) findViewById(R.id.numberPickerHour);
        this.numberPickerHour.setFormatter(new Formatter() {
            public String format(int value) {
                return value + " giờ";
            }
        });
        this.numberPickerHour.setMaxValue(23);
        this.numberPickerHour.setMinValue(0);
        this.numberPickerHour.setWrapSelectorWheel(false);
        try {
            Method method = this.numberPickerDay.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerDay, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e) {
        }
        try {
           Method method = this.numberPickerMonth.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerMonth, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e2) {
        }
        try {
            Method  method = this.numberPickerSex.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerSex, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e3) {
        }
        try {
            Method method = this.numberPickerHour.getClass().getDeclaredMethod("changeValueByOne", new Class[]{Boolean.TYPE});
            method.setAccessible(true);
            method.invoke(this.numberPickerHour, new Object[]{Boolean.valueOf(true)});
        } catch (Exception e4) {
        }
        if (this.setToDay) {
            String[] tempCurrentDay = Utils.getCurrentDay().split("/");
            this.numberPickerDay.setValue(Integer.parseInt(tempCurrentDay[0]));
            this.numberPickerMonth.setValue(Integer.parseInt(tempCurrentDay[1]));
            this.numberPickerYear.setValue(Integer.parseInt(tempCurrentDay[2]));
        } else {
            this.numberPickerDay.setValue(this.day);
            this.numberPickerMonth.setValue(this.month);
            this.numberPickerYear.setValue(this.year);
        }
        this.et = (EditText) findViewById(R.id.editText);
    }

    public void reload() {
        switch (this.type) {
            case 1:
                String url = this.API_XEM_SAO.replace("xxxxxx", this.numberPickerSex.getValue() == 1 ? "nam" : "nu") + this.numberPickerDay.getValue() + "-" + this.numberPickerMonth.getValue() + "-" + this.numberPickerYear.getValue();
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(url);
                    return;
                }
                return;
            case 2:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_NGAY_TOTXAU);
                    return;
                }
                return;
            case 3:
                RequestBody formBody = new Builder().setType(MultipartBody.FORM).addFormDataPart("sday", this.numberPickerDay.getValue() + "").addFormDataPart("smonth", this.numberPickerMonth.getValue() + "").addFormDataPart("syear", this.numberPickerYear.getValue() + "").build();
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedPOST(this.API_GIO_TOT_TRONG_NGAY, formBody);
                    return;
                }
                return;
            case 4:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_NGAY_TOT_TRONG_THANG);
                    return;
                }
                return;
            case 5:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 6:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_XUAT_HANH.replace("dddddd", this.numberPickerDay.getValue() + "") + this.numberPickerMonth.getValue());
                    return;
                }
                return;
            case 7:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_LAM_NHA.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 8:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 9:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_MUA_NHA.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 10:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_CHUYEN_NHA.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 11:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_KHAI_TRUONG.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 12:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_MUA_XE.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 13:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_NGAY_KY_KET_HOP_DONG);
                    return;
                }
                return;
            case 14:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 15:
                RequestBody formBodyDatTen = new Builder().setType(MultipartBody.FORM).addFormDataPart("sday", this.numberPickerDay.getValue() + "").addFormDataPart("smonth", this.numberPickerMonth.getValue() + "").addFormDataPart("syear", this.numberPickerYear.getValue() + "").addFormDataPart("shour", this.numberPickerHour.getValue() + "").addFormDataPart("sminute", "00").addFormDataPart("sex", this.numberPickerSex.getValue() == 1 ? "True" : "False").build();
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedPOST(this.API_DAT_TEN, formBodyDatTen);
                    return;
                }
                return;
            case 16:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_TUOI_LAM_AN.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "").replace("yyyyyy", this.numberPickerYear.getValue() + ""));
                    return;
                }
                return;
            case 17:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 18:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 19:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 20:
                String tempSex20 = this.numberPickerSex.getValue() == 1 ? "True" : "False";
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_XEM_HOP_TUOI.replace("xxxxxx", this.et.getText().toString().replaceAll(" ", "+")).replace("yyyyyy", tempSex20).replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 21:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_SIM.replace("xxxxxx", this.et.getText().toString()).replace("hhhhhh", this.numberPickerHour.getValue() + "").replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 22:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.tempAPI);
                    return;
                }
                return;
            case 23:
                String tempSex = this.numberPickerSex.getValue() == 1 ? "True" : "False";
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_PHONG_THUY_XEM_HUONG.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "").replace("yyyyyy", this.numberPickerYear.getValue() + "") + tempSex);
                    return;
                }
                return;
            case 24:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_PHONG_THUY_TAM_TAI.replace("dddddd", this.numberPickerDay.getValue() + "").replace("mmmmmm", this.numberPickerMonth.getValue() + "") + this.numberPickerYear.getValue());
                    return;
                }
                return;
            case 25:
                if (this.mOnActionFromDatePicker != null) {
                    this.mOnActionFromDatePicker.onSelectedGET(this.API_PHONG_THUY_HOANGOCKIMLAU + this.numberPickerYear.getValue());
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void setOnActionFromDatePicker(onActionFromDatePicker listener) {
        this.mOnActionFromDatePicker = listener;
    }
}
