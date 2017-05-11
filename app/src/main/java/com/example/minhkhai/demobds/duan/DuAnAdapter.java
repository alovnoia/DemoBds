package com.example.minhkhai.demobds.duan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.util.List;

/**
 * Created by minhkhai on 04/05/17.
 */

public class DuAnAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<DuAn> arrayDuAn;

    public DuAnAdapter(Context myContext, int myLayout, List<DuAn> arrayDuAn) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayDuAn = arrayDuAn;
    }

    @Override
    public int getCount() {
        return arrayDuAn.size();
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

        TextView tvDanhSachTenDuAn = (TextView) convertView.findViewById(R.id.tvTenKH);
        tvDanhSachTenDuAn.setText(String.valueOf(arrayDuAn.get(position).getMaDuAn()+". "+
        arrayDuAn.get(position).getTenDuAn()));

        TextView tvDanhSachDiaChi = (TextView) convertView.findViewById(R.id.tvDanhSachDiaChi);
        tvDanhSachDiaChi.setText(arrayDuAn.get(position).getDiaChi());

        return convertView;
    }
}
