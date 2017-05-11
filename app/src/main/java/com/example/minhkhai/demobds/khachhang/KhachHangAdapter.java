package com.example.minhkhai.demobds.khachhang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.util.List;

/**
 * Created by minhkhai on 11/05/17.
 */

public class KhachHangAdapter extends BaseAdapter {

    Context context;
    int myLayout;
    List<KhachHang> arrKhachHang;

    public KhachHangAdapter(Context context, int myLayout, List<KhachHang> arrKhachHang) {
        this.context = context;
        this.myLayout = myLayout;
        this.arrKhachHang = arrKhachHang;
    }

    @Override
    public int getCount() {
        return arrKhachHang.size();
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

        TextView tenKhachHang = (TextView) convertView.findViewById(R.id.tvTenKH);
        tenKhachHang.setText(String.valueOf(arrKhachHang.get(position).getMaKhachHang())+". "+
        arrKhachHang.get(position).getTenKhachHang());

        TextView tenLoaiKH = (TextView) convertView.findViewById(R.id.tvTenLoaiKH);
        tenLoaiKH.setText(String.valueOf(arrKhachHang.get(position).getTenLoaiKhachHang()));

        return convertView;
    }
}
