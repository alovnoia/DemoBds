package com.example.minhkhai.demobds.taikhoan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hiep on 05/14/2017.
 */

public class TaiKhoanAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<TaiKhoan> arrayTaiKhoan;

    public TaiKhoanAdapter(Context context, int layout, List<TaiKhoan> listTaiKhoan) {
        myContext = context;
        myLayout = layout;
        arrayTaiKhoan = listTaiKhoan;
    }

    @Override
    public int getCount() {
        return arrayTaiKhoan.size();
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

        TextView tvHoTen = (TextView) convertView.findViewById(R.id.tvHoTenTaiKhoan);
        tvHoTen.setText(arrayTaiKhoan.get(position).hoTen);

        TextView tvChucVu = (TextView) convertView.findViewById(R.id.tvChucVuTaiKhoan);
        tvChucVu.setText(arrayTaiKhoan.get(position).chucVu);

        ImageView imgAnh = (ImageView) convertView.findViewById(R.id.imgAnhTaiKhoanOnList);
        /*Picasso.with(DanhSachTaiKhoan.this)
                .load("http://10.0.3.2:2347/bds_project/img/Layout_4.png"+ arrayTaiKhoan.get(position).anh)
                .into(imgAnh);*/

        return convertView;
    }
}
