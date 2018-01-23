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
import com.example.lequan.lichvannien.activity.DiaryActivity;
import com.example.lequan.lichvannien.model.DiaryInfo;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class DiaryAdapter extends Adapter<DiaryAdapter.MyViewHolder> {
    View itemView;
    private ArrayList<DiaryInfo> listDiary = new ArrayList();
    private Context mContext;
    Typeface typeBoldNew;
    Typeface typeRegularNew;

    public class MyViewHolder extends ViewHolder {
        private RelativeLayout layoutHeader;
        private RelativeLayout rlClick;
        private TextView tvDate;
        private TextView tvMonth;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvName.setTypeface(DiaryAdapter.this.typeBoldNew);
            this.tvDate = (TextView) view.findViewById(R.id.tv_day);
            this.tvDate.setTypeface(DiaryAdapter.this.typeRegularNew);
            this.tvMonth = (TextView) view.findViewById(R.id.tv_month);
            this.tvMonth.setTypeface(DiaryAdapter.this.typeBoldNew);
            ((TextView) view.findViewById(R.id.tv_edit)).setVisibility(8);
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
            DiaryInfo mDiaryInfo = (DiaryInfo) DiaryAdapter.this.listDiary.get(this.position);
            if (mDiaryInfo != null) {
                Intent mIntent = new Intent(DiaryAdapter.this.mContext, DiaryActivity.class);
                mIntent.putExtra("diary", mDiaryInfo);
                ((Activity) DiaryAdapter.this.mContext).startActivity(mIntent);
                ((Activity) DiaryAdapter.this.mContext).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        }
    }

    public DiaryAdapter(Context mContext, ArrayList<DiaryInfo> listDiary) {
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
        DiaryInfo mDiaryInfo = (DiaryInfo) this.listDiary.get(position);
        if (mDiaryInfo != null) {
            if (position == 0) {
                holder.layoutHeader.setVisibility(0);
                holder.tvMonth.setText(getCurrentMonth(mDiaryInfo.getDate()));
            } else {
                holder.layoutHeader.setVisibility(8);
            }
            if (position > 0 && position < this.listDiary.size()) {
                if (getCurrentMonth(mDiaryInfo.getDate()).equalsIgnoreCase(getCurrentMonth(((DiaryInfo) this.listDiary.get(position - 1)).getDate()))) {
                    holder.layoutHeader.setVisibility(8);
                } else {
                    holder.layoutHeader.setVisibility(0);
                    holder.tvMonth.setText(getCurrentMonth(mDiaryInfo.getDate()));
                }
            }
            holder.tvName.setText(mDiaryInfo.getTitle());
            holder.tvDate.setText(Utils.converDate(mDiaryInfo.getDate()));
            holder.rlClick.setOnClickListener(new impleOnClick(position));
        }
    }

    public int getItemCount() {
        return this.listDiary.size();
    }

    private String getCurrentMonth(String date) {
        String[] arr = Utils.converDate(date).split("/");
        return arr[1] + "/" + arr[2];
    }
}
