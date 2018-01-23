package com.example.lequan.lichvannien.common;

import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.model.Quotation;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import java.util.ArrayList;

public class Define {
    public static final String API_DETAIL = "http://apalon1.accu-weather.com/widget/apalon1/weather-data.asp?location=cityid:%s&metric=1&langid=1";
    public static final String API_FEEDBACK = "http://ios.hdvietpro.com/ios-manager/feedback.php";
    public static final String API_GET_PLACE = "http://api.accuweather.com/locations/v1/cities/geoposition/search.json?q=%s,%s&apikey=eef88a8fb2cb407a9fbd2ebdf138d7e6&language=vi&details=true";
    public static final String API_ZODIAC = "http://ios.hdvietpro.com/ios-manager/get_cung_hoang_dao.php";
    public static String KEY_PREF_START_APP = "time_start_app";
    public static String KEY_PREF_TIME_SHOW_POPUP = "time_show_popup";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 42;
    public static final int REQUEST_WRITE_STORAGE = 112;
    public static String SELECTED_DAY = "";
    public static final String TIME_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT_EVENT = "dd-MM-yyyy";
    public static final String TIME_FORMAT_NEW = "yyyy/MM/dd";
    public static boolean isReset = true;
    public static final double lattitude = 21.037018d;
    public static ArrayList<DayInfo> listDay = new ArrayList();
    public static ArrayList<Quotation> listQuotation = new ArrayList();
    public static ArrayList<EventInfo> listSpecial = new ArrayList();
    public static final double longtitude = 105.811611d;
}
