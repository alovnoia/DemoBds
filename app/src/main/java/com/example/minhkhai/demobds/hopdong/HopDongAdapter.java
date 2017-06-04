package com.example.minhkhai.demobds.hopdong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.util.List;

/**
 * Created by hiep on 06/01/2017.
 */

public class HopDongAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<HopDong> arrayHopDong;

    public HopDongAdapter(Context myContext, int myLayout, List<HopDong> arrayHopDong) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayHopDong = arrayHopDong;
    }

    @Override
    public int getCount() {
        return arrayHopDong.size();
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
        LayoutInflater layoutInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(myLayout, null);

        TextView tvMaHopDong = (TextView) convertView.findViewById(R.id.tvMaHopDongItem);
        tvMaHopDong.setText(arrayHopDong.get(position).maHopDong+"");

        TextView tvKhachHang = (TextView) convertView.findViewById(R.id.tvKhachHangHopDongItem);
        tvKhachHang.setText(String.valueOf(arrayHopDong.get(position).maKhachHang+". "+arrayHopDong.get(position).TenKhachHang));

        TextView tvNgayKy = (TextView) convertView.findViewById(R.id.tvNgayKyHopDongItem);
        tvNgayKy.setText(arrayHopDong.get(position).NgayKy);

        TextView tvTrangThai = (TextView) convertView.findViewById(R.id.tvTrangThaiHopDongItem);
        if (arrayHopDong.get(position).TrangThai.equals("ChuaDuyet"))
        {
            tvTrangThai.setText("Chưa duyệt");
        }
        else if (arrayHopDong.get(position).TrangThai.equals("DaDuyet"))
        {
            tvTrangThai.setText("Đã duyệt");
        }

        return convertView;
    }
}
