package com.example.minhkhai.demobds.loaikhachhang;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.util.List;

/**
 * Created by minhkhai on 06/05/17.
 */

public class LoaiKhachHangAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<LoaiKhachHang> arrayKhachHang;


    public LoaiKhachHangAdapter(Context context, int layout, List<LoaiKhachHang> KhachHangList){
        myContext=context;
        myLayout= layout;
        arrayKhachHang=KhachHangList;
    }
    @Override
    public int getCount() {
        return arrayKhachHang.size();
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
        LayoutInflater inflater= (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);

        //Ánh xạ gán giá trị
        TextView tvTen = (TextView) convertView.findViewById(R.id.tvTen);
        tvTen.setText(arrayKhachHang.get(position).ten);

        TextView tvMoTa= (TextView) convertView.findViewById(R.id.tvND);
        tvMoTa.setText(Html.fromHtml(arrayKhachHang.get(position).moTa));

        return convertView;

    }
}
