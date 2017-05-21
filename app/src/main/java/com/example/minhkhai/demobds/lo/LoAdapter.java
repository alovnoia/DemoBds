package com.example.minhkhai.demobds.lo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.minhkhai.demobds.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hiep on 05/21/2017.
 */

public class LoAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<Lo> arrayLo;


    public LoAdapter(Context myContext, int myLayout, List<Lo> arrayLo) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayLo = arrayLo;
    }

    @Override
    public int getCount() {
        return arrayLo.size();
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

        TextView tvLo = (TextView) convertView.findViewById(R.id.tvItemLo);
        tvLo.setText(arrayLo.get(position).tenLo);

        TextView tvDuAn = (TextView) convertView.findViewById(R.id.tvItemLoTenDuAn);
        tvDuAn.setText(arrayLo.get(position).tenDuAn);

        return convertView;
    }
}
