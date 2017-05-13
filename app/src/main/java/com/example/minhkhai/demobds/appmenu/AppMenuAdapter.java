package com.example.minhkhai.demobds.appmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.menu.Drawer;

import java.util.List;

/**
 * Created by minhkhai on 13/05/17.
 */

public class AppMenuAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<MenuItem> arrItem;

    public AppMenuAdapter(Context myContext, int myLayout, List<MenuItem> arrItem) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrItem = arrItem;
    }

    @Override
    public int getCount() {
        return arrItem.size();
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

        ImageView hinhAnh = (ImageView) convertView.findViewById(R.id.ivAppMenu);
        TextView noiDung = (TextView) convertView.findViewById(R.id.tvAppMenu);

        MenuItem item = arrItem.get(position);
        hinhAnh.setImageResource(item.getAnh());
        noiDung.setText(item.getTenChucNang());

        return convertView;
    }
}
