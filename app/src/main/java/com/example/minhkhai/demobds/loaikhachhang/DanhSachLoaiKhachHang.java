package com.example.minhkhai.demobds.loaikhachhang;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DanhSachLoaiKhachHang extends Fragment {

    FloatingActionButton flAdd;
    ListView lvDanhSachLoaiKH;
    ArrayList<LoaiKhachHang> mangLoaiKH;
    ArrayList<LoaiKhachHang> mangLoaiKHGoc = new ArrayList<>();
    LoaiKhachHangAdapter adapter = null;

    //TextView test;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_loai_khach_hang,container,false);
        setHasOptionsMenu(true);

        lvDanhSachLoaiKH= (ListView) view.findViewById(R.id.lvDanhSachLoaiKH);
        mangLoaiKH = new ArrayList<LoaiKhachHang>();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang");
            }
        });

        flAdd = (FloatingActionButton) view.findViewById(R.id.fab_add);
        flAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), ThemLoaiKhachHang.class);
                startActivity(i);
            }
        });

        lvDanhSachLoaiKH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(getActivity(), ChiTietLoaiKhachHang.class);
                i.putExtra("maLoaiKH", mangLoaiKH.get(position).maLoaiKH);
                startActivity(i);
            }
        });
        return view;
    }

    private class LoadDanhSach extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return API.GET_URL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //test.append("Chan");
            try {
                JSONArray array= new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    mangLoaiKHGoc.add(new LoaiKhachHang(
                            object.getString("TenLoaiKH"),
                            object.getString("MoTa"),
                            object.getInt("MaLoaiKH")

                    ));
                }
                mangLoaiKH = new ArrayList<>(mangLoaiKHGoc);
                adapter= new LoaiKhachHangAdapter(getActivity(),
                        R.layout.item_loai_khach_hang, mangLoaiKH);
                lvDanhSachLoaiKH.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TimKiem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TimKiem(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void TimKiem (String newText) {
        if (!newText.isEmpty() && newText.length() > 0) {
            mangLoaiKH.clear();

            List<LoaiKhachHang> lstLoaiKHAll = new ArrayList<LoaiKhachHang>(mangLoaiKHGoc);

            for (LoaiKhachHang hh : lstLoaiKHAll) {
                if ((hh.getTen().toLowerCase()).contains(newText.toLowerCase())) {
                    mangLoaiKH.add(hh);
                }
            }
        } else {
            mangLoaiKH.clear();
            for (LoaiKhachHang hh : mangLoaiKHGoc) {
                mangLoaiKH.add(hh);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
