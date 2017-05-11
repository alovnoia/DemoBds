package com.example.minhkhai.demobds.loaisp;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.util.List;

/**
 * Created by minhkhai on 02/05/17.
 */

public class LoaiSPAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<LoaiSP> arrayLoaiSP;

    public LoaiSPAdapter(Context myContext, int myLayout, List<LoaiSP> arrayLoaiSP) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayLoaiSP = arrayLoaiSP;
    }

    @Override
    public int getCount() {
        return arrayLoaiSP.size();
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
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        TextView tvTenLoaiSP = (TextView) convertView.findViewById(R.id.tvTenKH);
        tvTenLoaiSP.setText(String.valueOf(arrayLoaiSP.get(position).getMaLoaiSP())+". "+
                arrayLoaiSP.get(position).getTenLoaiSP());

        TextView tvMoTaLoaiSP = (TextView) convertView.findViewById(R.id.tvMoTaLoaiSP);
        tvMoTaLoaiSP.setText(Html.fromHtml(arrayLoaiSP.get(position).getMoTaLoaiSP()));

        return convertView;
    }
}
