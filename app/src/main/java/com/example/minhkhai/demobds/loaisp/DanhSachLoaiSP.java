package com.example.minhkhai.demobds.loaisp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.KhachHang;
import com.example.minhkhai.demobds.lo.Lo;
import com.example.minhkhai.demobds.loaikhachhang.ThemLoaiKhachHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DanhSachLoaiSP extends Fragment {

    ListView lvLoaiSP;
    ArrayList<LoaiSP> mangLoaiSanPham;
    ArrayList<LoaiSP> mangLoaiSanPhamGoc = new ArrayList<>();
    FloatingActionButton fab_add;
    LoaiSPAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_loai_sp,container,false);
        setHasOptionsMenu(true);

        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        lvLoaiSP = (ListView) view.findViewById(R.id.lvDanhSachLoaiSP);
        mangLoaiSanPham = new ArrayList<LoaiSP>();

        if (API.quyen.equals("NVBH")) {
            fab_add.setVisibility(View.GONE);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+API.HOST+"/bds_project/public/LoaiSP");
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemLoaiSP.class);
                startActivity(intent);
            }
        });

        lvLoaiSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CapNhatLoaiSP.class);
                intent.putExtra("id", mangLoaiSanPham.get(position).getMaLoaiSP());
                startActivity(intent);
            }
        });

        return view;

    }

    private class LoadDanhSach extends AsyncTask<String, Integer, String>{

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

            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    mangLoaiSanPhamGoc.add(new LoaiSP(
                            object.getInt("MaLoaiSP"),
                            object.getString("TenLoaiSP"),
                            object.getString("MoTa")
                    ));
                }
                mangLoaiSanPham = new ArrayList<>(mangLoaiSanPhamGoc);
                adapter = new LoaiSPAdapter(getActivity(), R.layout.item_loai_sp, mangLoaiSanPham);

                lvLoaiSP.setAdapter(adapter);

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
            mangLoaiSanPham.clear();

            List<LoaiSP> lstLoaiSPAll = new ArrayList<LoaiSP>(mangLoaiSanPhamGoc);

            for (LoaiSP hh : lstLoaiSPAll) {
                if ((hh.getTenLoaiSP().toLowerCase()).contains(newText.toLowerCase())) {
                    mangLoaiSanPham.add(hh);
                }
            }
        } else {
            mangLoaiSanPham.clear();
            for (LoaiSP hh : mangLoaiSanPhamGoc) {
                mangLoaiSanPham.add(hh);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
