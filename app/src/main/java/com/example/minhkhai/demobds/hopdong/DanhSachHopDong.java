package com.example.minhkhai.demobds.hopdong;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DanhSachHopDong extends Fragment {

    ListView lvDanhSach;
    ArrayList<HopDong> mangHopDong;

    FloatingActionButton fab_Add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_hop_dong,container,false);

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
                                mangHopDong.add(new HopDong(
                                        object.getInt("MaHopDong"),
                                        object.getInt("MaLoaiKH"),
                                        object.getInt("KhachHang"),
                                        object.getInt("MaDuAn"),
                                        object.getInt("MaLo"),
                                        object.getInt("SanPham"),
                                        object.getString("TenKhachHang"),
                                        object.getString("NgayKy"),
                                        object.getString("TrangThai")
                                ));
                            }
                        }else {
                            mangHopDong.add(new HopDong(
                                    object.getInt("MaHopDong"),
                                    object.getInt("MaLoaiKH"),
                                    object.getInt("KhachHang"),
                                    object.getInt("MaDuAn"),
                                    object.getInt("MaLo"),
                                    object.getInt("SanPham"),
                                    object.getString("TenKhachHang"),
                                    object.getString("NgayKy"),
                                    object.getString("TrangThai")
                            ));
                        }
                    }

                    HopDongAdapter adapter = new HopDongAdapter(getActivity(), R.layout.item_hop_dong, mangHopDong);
                    lvDanhSach.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
