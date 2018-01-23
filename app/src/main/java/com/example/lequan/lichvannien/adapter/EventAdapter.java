package com.example.lequan.lichvannien.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.lequan.lichvannien.activity.EventActivity;
import com.example.lequan.lichvannien.activity.EventDetailActivity;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class EventAdapter extends Adapter<EventAdapter.MyViewHolder> {
    View itemView;
    private ArrayList<EventInfo> listDiary = new ArrayList();
    private Context mContext;
    Typeface typeBoldNew;
    Typeface typeRegularNew;

    public class MyViewHolder extends ViewHolder {
        private RelativeLayout layoutHeader;
        private RelativeLayout rlClick;
        private TextView tvDate;
        private TextView tvEdit;
        private TextView tvMonth;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvName.setTypeface(EventAdapter.this.typeBoldNew);
            this.tvDate = (TextView) view.findViewById(R.id.tv_day);
            this.tvDate.setTypeface(EventAdapter.this.typeRegularNew);
            this.tvMonth = (TextView) view.findViewById(R.id.tv_month);
            this.tvMonth.setTypeface(EventAdapter.this.typeBoldNew);
            this.tvEdit = (TextView) view.findViewById(R.id.tv_edit);
            this.tvEdit.setTypeface(EventAdapter.this.typeRegularNew);
            this.layoutHeader = (RelativeLayout) view.findViewById(R.id.layout_header);
            this.rlClick = (RelativeLayout) view.findViewById(R.id.rlClick);
        }
    }

    class impleOnClick implements OnClickListener {
        int position = 0;

        public impleOnClick(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            EventInfo mEventInfo = (EventInfo) EventAdapter.this.listDiary.get(this.position);
            if (mEventInfo != null) {
                Intent mIntent = new Intent(EventAdapter.this.mContext, EventDetailActivity.class);
                mIntent.putExtra("event_infos", mEventInfo);
                ((Activity) EventAdapter.this.mContext).startActivity(mIntent);
                ((Activity) EventAdapter.this.mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        }
    }

    class impleOnClickEdit implements OnClickListener {
        int position = 0;

        public impleOnClickEdit(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            EventInfo mEventInfo = (EventInfo) EventAdapter.this.listDiary.get(this.position);
            if (mEventInfo != null) {
                Intent mIntent = new Intent(EventAdapter.this.mContext, EventActivity.class);
                mIntent.putExtra("event_infos", mEventInfo);
                ((Activity) EventAdapter.this.mContext).startActivity(mIntent);
                ((Activity) EventAdapter.this.mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        }
    }

    public EventAdapter(Context mContext, ArrayList<EventInfo> listDiary) {
        this.listDiary = listDiary;
        this.mContext = mContext;
        this.typeRegularNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM HelveBold.ttf");
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_diary, parent, false);
        return new MyViewHolder(this.itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        EventInfo mEventInfo = (EventInfo) this.listDiary.get(position);
        if (mEventInfo != null) {
            if (position == 0) {
                holder.layoutHeader.setVisibility(0);
                holder.tvMonth.setText("Tháng " + getCurrentMonth(mEventInfo.getStart()));
            } else {
                holder.layoutHeader.setVisibility(8);
            }
            if (position > 0 && position < this.listDiary.size()) {
                if (getCurrentMonth(mEventInfo.getStart()).equalsIgnoreCase(getCurrentMonth(((EventInfo) this.listDiary.get(position - 1)).getStart()))) {
                    holder.layoutHeader.setVisibility(8);
                } else {
                    holder.layoutHeader.setVisibility(0);
                    holder.tvMonth.setText("Tháng " + getCurrentMonth(mEventInfo.getStart()));
                }
            }
            holder.tvName.setText(mEventInfo.getName());
            String startDay = mEventInfo.getStart().split(" ")[0];
            String startHour = mEventInfo.getStart().split(" ")[1];
            String endDay = mEventInfo.getEnd().split(" ")[0];
            String endHour = mEventInfo.getEnd().split(" ")[1];
            if (mEventInfo.getFullDay() != 1) {
                holder.tvDate.setText(Utils.getDayofWeek(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + ") " + startHour + " - " + Utils.getDayofWeek(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + ") " + endHour);
            } else if (mEventInfo.getStart().equalsIgnoreCase(mEventInfo.getEnd())) {
                holder.tvDate.setText(Utils.getDayofWeek(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + ")");
            } else {
                holder.tvDate.setText(Utils.getDayofWeek(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(startDay.substring(0, 10).replace("-", "/"))) + ") " + startHour + " - " + Utils.getDayofWeek(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + "(" + Utils.getDayMonth(Utils.converDate(endDay.substring(0, 10).replace("-", "/"))) + ") " + endHour);
            }
            holder.rlClick.setOnClickListener(new impleOnClick(position));
            holder.tvEdit.setOnClickListener(new impleOnClickEdit(position));
        }
    }

    public int getItemCount() {
        return this.listDiary.size();
    }

    private String getCurrentMonth(String date) {
        String[] arr = Utils.converDateEvent(date.split(" ")[0]).split("-");
        return arr[1] + "/" + arr[2];
    }
}
