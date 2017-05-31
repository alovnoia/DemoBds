package com.example.minhkhai.demobds.uudai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by minhkhai on 29/05/17.
 */

public class UuDaiAdapter extends BaseAdapter{

    Context myContext;
    int myLayout;
    ArrayList<UuDai> arrUuDai;

    public UuDaiAdapter(Context myContext, int myLayout, ArrayList<UuDai> arrUuDai) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrUuDai = arrUuDai;
    }

    @Override
    public int getCount() {
        return arrUuDai.size();
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

        UuDai item = arrUuDai.get(position);
        TextView tenUuDai = (TextView) convertView.findViewById(R.id.tvTenUuDai);
        tenUuDai.setText("Mã " + item.getMaUuDai() + " - " + item.getTenUuDai());

        TextView thoiHan = (TextView) convertView.findViewById(R.id.tvThoiHan);
            thoiHan.setText(API.convertDate(item.getBatDau()) + " - "
                    + API.convertDate(item.getKetThuc()));

        return convertView;
    }
}
