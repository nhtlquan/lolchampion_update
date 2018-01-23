package com.example.lequan.lichvannien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.lequan.lichvannien.dao.DAOTuViSoMenh;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class AdapterTuViSoMenhList extends BaseAdapter {
    Context context;
    ArrayList<DAOTuViSoMenh> list = new ArrayList();

    static class ViewHolder {
        TextView tvName;

        ViewHolder() {
        }
    }

    public AdapterTuViSoMenhList(Context a, ArrayList<DAOTuViSoMenh> list) {
        this.context = a;
        this.list = list;
    }

    public int getCount() {
        return this.list.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.activity_tuvi_somenh_list_row, null);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        }
        holder.tvName.setText(((DAOTuViSoMenh) this.list.get(position)).getName());
        return convertView;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
}
