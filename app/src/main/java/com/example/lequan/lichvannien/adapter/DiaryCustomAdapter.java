package com.example.lequan.lichvannien.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.lequan.lichvannien.BaseActivity.DiaryListenner;
import com.example.lequan.lichvannien.model.DiaryCustom;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;
import java.util.Iterator;

public class DiaryCustomAdapter extends Adapter<DiaryCustomAdapter.MyViewHolder> {
    private ArrayList<DiaryCustom> listDiary = new ArrayList();
    private Context mContext;
    private DiaryListenner mDiaryListenner;
    private int type = 0;

    public class MyViewHolder extends ViewHolder {
        private TextView tvContent;

        public MyViewHolder(View view) {
            super(view);
            this.tvContent = (TextView) view.findViewById(R.id.tvContent);
        }
    }

    public DiaryCustomAdapter(Context mContext, ArrayList<DiaryCustom> listDiary, int type, DiaryListenner mDiaryListenner) {
        this.listDiary = listDiary;
        this.mContext = mContext;
        this.type = type;
        this.mDiaryListenner = mDiaryListenner;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_diary_custom, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DiaryCustom mDiaryCustom = (DiaryCustom) this.listDiary.get(position);
        if (this.type == 0) {
            if (mDiaryCustom.isSelect) {
                holder.tvContent.setBackgroundResource(Utils.getResourceId(this.mContext, mDiaryCustom.img + "_active"));
            } else {
                holder.tvContent.setBackgroundResource(Utils.getResourceId(this.mContext, mDiaryCustom.img));
            }
        } else if (this.type == 1) {
            holder.tvContent.setText("ABC");
            Utils.setFontTextview(this.mContext, holder.tvContent, mDiaryCustom.font);
            if (mDiaryCustom.isSelect) {
                holder.tvContent.setTextColor(Color.parseColor("#02b2db"));
            } else {
                holder.tvContent.setTextColor(Color.parseColor("#939496"));
            }
        } else {
            holder.tvContent.setBackgroundColor(Color.parseColor(mDiaryCustom.color));
        }
        holder.tvContent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Iterator it;
                switch (DiaryCustomAdapter.this.type) {
                    case 0:
                        it = DiaryCustomAdapter.this.listDiary.iterator();
                        while (it.hasNext()) {
                            ((DiaryCustom) it.next()).isSelect = false;
                        }
                        mDiaryCustom.isSelect = true;
                        DiaryCustomAdapter.this.notifyDataSetChanged();
                        if (DiaryCustomAdapter.this.mDiaryListenner != null) {
                            DiaryCustomAdapter.this.mDiaryListenner.onSelectBackground(mDiaryCustom);
                            return;
                        }
                        return;
                    case 1:
                        it = DiaryCustomAdapter.this.listDiary.iterator();
                        while (it.hasNext()) {
                            ((DiaryCustom) it.next()).isSelect = false;
                        }
                        mDiaryCustom.isSelect = true;
                        if (DiaryCustomAdapter.this.mDiaryListenner != null) {
                            DiaryCustomAdapter.this.mDiaryListenner.onSelectText(mDiaryCustom);
                            return;
                        }
                        return;
                    case 2:
                        it = DiaryCustomAdapter.this.listDiary.iterator();
                        while (it.hasNext()) {
                            ((DiaryCustom) it.next()).isSelect = false;
                        }
                        mDiaryCustom.isSelect = true;
                        if (DiaryCustomAdapter.this.mDiaryListenner != null) {
                            DiaryCustomAdapter.this.mDiaryListenner.onSelectColor(mDiaryCustom);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public int getItemCount() {
        return this.listDiary.size();
    }
}
