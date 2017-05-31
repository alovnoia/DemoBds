package com.example.minhkhai.demobds.sanpham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.MenuItem;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.lo.Lo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by minhkhai on 22/05/17.
 */

public class DuAnLoAdapter extends BaseExpandableListAdapter {

    static final String TAG = "DuAnLoAdapter";
    Context mContext;
    List<String> mHeaderGroup;
    HashMap<String, List<Lo>> mDataChild;

    public DuAnLoAdapter(Context mContext, List<String> mHeaderGroup, HashMap<String, List<Lo>> mDataChild) {
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
            convertView = li.inflate(R.layout.group_duan, parent, false);
        }

        TextView tvHeader = (TextView) convertView.findViewById(R.id.tvGroupDuAn);
        tvHeader.setText(mHeaderGroup.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            convertView = li.inflate(R.layout.item_chon_du_an, parent, false);
        }

        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvChildLo);
        tvItemName.setText(((Lo) getChild(groupPosition, childPosition)).tenLo);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
