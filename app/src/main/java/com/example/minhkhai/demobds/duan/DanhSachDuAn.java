package com.example.minhkhai.demobds.duan;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
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
import android.widget.ListView;

import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.menu.NavigationDrawer;
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

public class DanhSachDuAn extends Fragment{

    ListView lvDuAn;
    ArrayList<DuAn> mangDuAn;
    ArrayList<DuAn> mangDuAnGoc = new ArrayList<>();
    FloatingActionButton fabThemDuAn;
    DuAnAdapter adapter = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_du_an,container,false);
        setHasOptionsMenu(true);
        mangDuAn = new ArrayList<DuAn>();
        lvDuAn = (ListView) view.findViewById(R.id.lvDuAn);
        fabThemDuAn = (FloatingActionButton) view.findViewById(R.id.fabThemDuAn);

        if (API.quyen.equals("NVBH")) {
            fabThemDuAn.setVisibility(View.GONE);
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+API.HOST+"/bds_project/public/DuAn");
            }
        });

        fabThemDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemDuAn.class);
                startActivity(intent);
            }
        });

        lvDuAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CapNhatDuAn.class);
                intent.putExtra("id", mangDuAn.get(position).getMaDuAn());
                startActivity(intent);
            }
        });
        return view;
    }

    public class LoadDanhSach extends AsyncTask<String, Integer, String> {

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

                    mangDuAnGoc.add(new DuAn(
                            object.getInt("MaDuAn"),
                            object.getString("TenDuAn"),
                            object.getString("DiaChi")
                    ));
                }
                mangDuAn = new ArrayList<>(mangDuAnGoc);
                adapter = new DuAnAdapter(getActivity(), R.layout.item_du_an, mangDuAn);

                lvDuAn.setAdapter(adapter);

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
            mangDuAn.clear();

            List<DuAn> lstDuAnAll = new ArrayList<DuAn>(mangDuAnGoc);

            for (DuAn hh : lstDuAnAll) {
                if ((hh.getTenDuAn().toLowerCase()).contains(newText.toLowerCase()) ||
                        hh.getDiaChi().toLowerCase().contains(newText.toLowerCase())) {
                    mangDuAn.add(hh);
                }
            }
        } else {
            mangDuAn.clear();
            for (DuAn hh : mangDuAnGoc) {
                if ((hh.getTenDuAn().toLowerCase()).contains(newText.toLowerCase())) {
                    mangDuAn.add(hh);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

}
