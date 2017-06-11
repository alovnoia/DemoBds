package com.example.minhkhai.demobds.hopdong;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
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

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DanhSachHopDong extends Fragment {

    ListView lvDanhSach;
    ArrayList<HopDong> mangHopDong;
    ArrayList<HopDong> mangHopDongGoc = new ArrayList<>();
    HopDongAdapter adapter = null;

    FloatingActionButton fab_Add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_hop_dong,container,false);
        setHasOptionsMenu(true);
        mangHopDong = new ArrayList<HopDong>();
        lvDanhSach = (ListView) view.findViewById(R.id.lvDanhSachHopDong);
        fab_Add = (FloatingActionButton) view.findViewById(R.id.fab_ThemHopDong);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+ API.HOST+"/bds_project/public/HopDong");
            }
        });

        fab_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ThemHopDong.class);
                startActivity(i);
            }
        });

        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ChiTietHopDong.class);
                i.putExtra("id", mangHopDong.get(position).maHopDong);
                i.putExtra("TaiKhoan", mangHopDong.get(position).maTaiKhoan);
                i.putExtra("trangThai", mangHopDong.get(position).TrangThai);
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

            if (s!=null){
                try {
                    JSONArray array = new JSONArray(s);

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        if (API.quyen.equals("NVBH")){
                            if  (API.idUser.equals(object.getString("TaiKhoan")))
                            {
                                mangHopDongGoc.add(new HopDong(
                                        object.getInt("MaHopDong"),
                                        object.getInt("TaiKhoan"),
                                        object.getInt("KhachHang"),
                                        object.getInt("MaDuAn"),
                                        object.getInt("SanPham"),
                                        object.getString("TenKhachHang"),
                                        object.getString("NgayKy"),
                                        object.getString("TrangThai")
                                ));
                            }
                        }else {
                            mangHopDongGoc.add(new HopDong(
                                    object.getInt("MaHopDong"),
                                    object.getInt("TaiKhoan"),
                                    object.getInt("KhachHang"),
                                    object.getInt("MaDuAn"),
                                    object.getInt("SanPham"),
                                    object.getString("TenKhachHang"),
                                    object.getString("NgayKy"),
                                    object.getString("TrangThai")
                            ));
                        }
                    }
                    mangHopDong = new ArrayList<>(mangHopDongGoc);
                    adapter = new HopDongAdapter(getActivity(), R.layout.item_hop_dong, mangHopDong);
                    lvDanhSach.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            mangHopDong.clear();

            List<HopDong> lstHopDongAll = new ArrayList<HopDong>(mangHopDongGoc);

            for (HopDong hh : lstHopDongAll) {
                if ((hh.getTenKhachHang().toLowerCase()).contains(newText.toLowerCase()) ||
                        hh.getTrangThai().toLowerCase().contains(newText.toLowerCase())) {
                    mangHopDong.add(hh);
                }
            }
        } else {
            mangHopDong.clear();
            for (HopDong hh : mangHopDongGoc) {
                mangHopDong.add(hh);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
