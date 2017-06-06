package com.example.minhkhai.demobds.no;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by minhkhai on 05/06/17.
 */

public class NoAdapter extends BaseAdapter {

    Context context;
    int myLayout;
    List<No> lstNo;

    public NoAdapter(Context context, int myLayout, List<No> lstNo) {
        this.lstNo = lstNo;
        this.context = context;
        this.myLayout = myLayout;
    }

    @Override
    public int getCount() {
        return lstNo.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(myLayout, null);

        No no = lstNo.get(position);
        TextView tvDot = (TextView) convertView.findViewById(R.id.tvDot);
        tvDot.setText(no.getDotTra() + " - " + no.getTenKhachHang());

        TextView tvNgayTra = (TextView) convertView.findViewById(R.id.tvNgayTra);
        tvNgayTra.setText(API.convertDate(no.getNgayTra()));

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        TextView tvTien = (TextView) convertView.findViewById(R.id.tvSoTienNo);
        tvTien.setText(formatter.format(no.getTien())+" ₫");

        return convertView;
    }
}
