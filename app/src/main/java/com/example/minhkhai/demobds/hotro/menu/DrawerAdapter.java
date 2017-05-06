package com.example.minhkhai.demobds.hotro.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.util.List;

/**
 * Created by minhkhai on 05/05/17.
 */

public class DrawerAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<Drawer> arrayDrawer;

    public DrawerAdapter(Context myContext, int myLayout, List<Drawer> arrayDrawer) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayDrawer = arrayDrawer;
    }

    @Override
    public int getCount() {
        return arrayDrawer.size();
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

        ImageView hinhAnh = (ImageView) convertView.findViewById(R.id.ivChucNang);
        TextView noiDung = (TextView) convertView.findViewById(R.id.tvChucNang);

        Drawer item = arrayDrawer.get(position);
        hinhAnh.setImageResource(item.getHinhAnh());
        noiDung.setText(item.getTenMenu());

        return convertView;
    }
}
