package com.example.minhkhai.demobds.appmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.menu.Drawer;

import java.util.HashMap;
import java.util.List;

/**
 * Created by minhkhai on 13/05/17.
 */

public class AppMenuAdapter extends BaseExpandableListAdapter {

    /*Context myContext;
    int myLayout;
    List<MenuItem> arrItem;*/

    static final String TAG = "AppMenuAdapter";
    Context mContext;
    List<String> mHeaderGroup;
    HashMap<String, List<MenuItem>> mDataChild;

    public AppMenuAdapter(Context mContext, List<String> mHeaderGroup, HashMap<String, List<MenuItem>> mDataChild) {
        this.mContext = mContext;
        this.mHeaderGroup = mHeaderGroup;
        this.mDataChild = mDataChild;
    }

    @Override
    public int getGroupCount() {
        return mHeaderGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mHeaderGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataChild.get(mHeaderGroup.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.menu_group_layout, parent, false);
        }

        TextView tvHeader = (TextView) convertView.findViewById(R.id.tvHeaderGroup);
        tvHeader.setText(mHeaderGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.item_row, parent, false);
        }

        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        tvItemName.setText(((MenuItem) getChild(groupPosition, childPosition)).getTenChucNang());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /*public AppMenuAdapter(Context myContext, int myLayout, List<MenuItem> arrItem) {
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
    }*/
}
