package com.example.lequan.lichvannien.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.activity.EventDetailActivity;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.ConvertCalendar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Lunar;
import com.example.lequan.lichvannien.samsistemas.calendarview.utility.Solar;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class SpecialDayAdapter extends Adapter<SpecialDayAdapter.MyViewHolder> {
    private View itemView;
    private ArrayList<EventInfo> listEvent = new ArrayList();
    private Context mContext;
    String month;
    private String year;

    public class MyViewHolder extends ViewHolder {
        private ImageView imgLeft;
        private RelativeLayout layoutHeader;
        private RelativeLayout lnClick;
        TextView tvDate;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.imgLeft = (ImageView) view.findViewById(R.id.img_left);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvDate = (TextView) view.findViewById(R.id.tv_day);
            this.layoutHeader = (RelativeLayout) view.findViewById(R.id.layout_header);
            this.lnClick = (RelativeLayout) view.findViewById(R.id.ln_click);
        }
    }

    public SpecialDayAdapter(Context mContext, ArrayList<EventInfo> listEvent, String year, String month) {
        this.listEvent = listEvent;
        this.mContext = mContext;
        this.year = year;
        this.month = month;
        Log.i(BaseActivity.TAG, "thang / nam duong truyen vao: " + this.month + " / " + this.year);
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_special_day, parent, false);
        return new MyViewHolder(this.itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        final EventInfo mSpecialDay = (EventInfo) this.listEvent.get(position);
        if (mSpecialDay != null) {
            holder.tvName.setText(mSpecialDay.getName());
            if (position == this.listEvent.size() - 1) {
                holder.imgLeft.setImageResource(R.drawable.time_line_bottom);
            } else {
                holder.imgLeft.setImageResource(R.drawable.time_line_center);
            }
            if (mSpecialDay.getNote() == null) {
                String isLunar = mSpecialDay.getSolar() == 1 ? "" : "(Âm lịch)";
                String dayOfWeek = "";
                if (mSpecialDay.getSolar() == 1) {
                    Solar startSolar = new Solar();
                    if (Integer.parseInt(mSpecialDay.getStart().split("/")[1]) == 12 && this.month.equals("1")) {
                        startSolar.solarYear = Integer.parseInt(this.year) - 1;
                    } else if (Integer.parseInt(mSpecialDay.getStart().split("/")[1]) == 1 && this.month.equals("12")) {
                        startSolar.solarYear = Integer.parseInt(this.year) + 1;
                    } else {
                        startSolar.solarYear = Integer.parseInt(this.year);
                    }
                    startSolar.solarMonth = Integer.parseInt(mSpecialDay.getStart().split("/")[1]);
                    startSolar.solarDay = Integer.parseInt(mSpecialDay.getStart().split("/")[0]);
                    Lunar startLunar = ConvertCalendar.SolarToLunar(startSolar);
                    isLunar = "(" + startLunar.lunarDay + "/" + startLunar.lunarMonth + " Âm lịch)";
                    dayOfWeek = Utils.getDayofWeek2(startSolar.solarDay + "/" + startSolar.solarMonth + "/" + startSolar.solarYear);
                    Log.i(BaseActivity.TAG, "duong solar: " + startSolar.toString() + " lunar: " + startLunar.toString());
                }
                if (mSpecialDay.getSolar() == 0) {
                    Lunar mLunar = new Lunar();
                    mLunar.lunarDay = Integer.parseInt(mSpecialDay.getStart().split("/")[0]);
                    mLunar.lunarMonth = Integer.parseInt(mSpecialDay.getStart().split("/")[1]);
                    if (Integer.parseInt(mSpecialDay.getStart().split("/")[1]) > Integer.parseInt(this.month)) {
                        mLunar.lunarYear = Integer.parseInt(this.year) - 1;
                    } else {
                        mLunar.lunarYear = Integer.parseInt(this.year);
                    }
                    Solar mSolar = ConvertCalendar.LunarToSolar(mLunar);
                    dayOfWeek = Utils.getDayofWeek2(mSolar.solarDay + "/" + mSolar.solarMonth + "/" + mSolar.solarYear);
                    Log.i(BaseActivity.TAG, "am solar: " + mSolar.toString() + " lunar: " + mLunar.toString());
                }
                Log.d(BaseActivity.TAG, "mSpecialDay : " + mSpecialDay.toString());
                holder.tvDate.setText(dayOfWeek + " " + mSpecialDay.getStart() + " " + isLunar);
            } else {
                String startDay = mSpecialDay.getStart().split(" ")[0];
                String startHour = mSpecialDay.getStart().split(" ")[1];
                String endDay = mSpecialDay.getEnd().split(" ")[0];
                String endHour = mSpecialDay.getEnd().split(" ")[1];
                if (mSpecialDay.getFullDay() != 1) {
                    holder.tvDate.setText(Utils.getDayofWeek(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + ") " + startHour + " - " + Utils.getDayofWeek(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + ") " + endHour);
                } else if (mSpecialDay.getStart().equalsIgnoreCase(mSpecialDay.getEnd())) {
                    holder.tvDate.setText(Utils.getDayofWeek(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + ")");
                } else {
                    holder.tvDate.setText(Utils.getDayofWeek(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + ") " + startHour + " - " + Utils.getDayofWeek(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + ") " + endHour);
                }
            }
            if (position == 0) {
                holder.layoutHeader.setVisibility(0);
            } else {
                holder.layoutHeader.setVisibility(8);
            }
            holder.lnClick.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    EventInfo eventInfo = (EventInfo) new Gson().fromJson(mSpecialDay.toString(), EventInfo.class);
                    Intent mIntent = new Intent(SpecialDayAdapter.this.mContext, EventDetailActivity.class);
                    if (eventInfo.getNote() == null) {
                        int addYear;
                        int currentMonth = Integer.parseInt(SpecialDayAdapter.this.month);
                        int eventMonth = Integer.parseInt(eventInfo.getStart().split("/")[1]);
                        if (currentMonth >= 11 && eventMonth <= 2) {
                            addYear = 1;
                        } else if (currentMonth > 2 || eventMonth < 11) {
                            addYear = 0;
                        } else {
                            addYear = -1;
                        }
                        String startDate = Utils.converDate(eventInfo.getStart() + "/" + (Integer.parseInt(SpecialDayAdapter.this.year) + addYear)) + " 00:00";
                        eventInfo.setStart(startDate);
                        eventInfo.setEnd(startDate);
                    }
                    mIntent.putExtra("event_infos", eventInfo);
                    ((Activity) SpecialDayAdapter.this.mContext).startActivity(mIntent);
                    ((Activity) SpecialDayAdapter.this.mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            });
        }
    }

    public int getItemCount() {
        return this.listEvent.size();
    }
}
