package com.example.minhkhai.demobds.loaikhachhang;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DanhSachLoaiKhachHang extends Fragment {

    FloatingActionButton flAdd;
    ListView lvDanhSachLoaiKH;
    ArrayList<LoaiKhachHang> mangLoaiKH;

    //TextView test;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_loai_khach_hang,container,false);
        //test = (TextView) findViewById(R.id.TestTV);
       /* FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.flDanhSachLoaiKH, new NavigationDrawer()).commit();*/

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
                i.putExtra("maLoaiKH", mangLoaiKH.get(position).maLoai);
                startActivity(i);
            }
        });
        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), AppMenu.class);
        startActivity(intent);
        return true;
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
                    mangLoaiKH.add(new LoaiKhachHang(
                            object.getString("TenLoaiKH"),
                            object.getString("MoTa"),
                            object.getInt("MaLoaiKH")

                    ));
                }
                LoaiKhachHangAdapter adapter= new LoaiKhachHangAdapter(getActivity(),
                        R.layout.item_loai_khach_hang, mangLoaiKH);
                lvDanhSachLoaiKH.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}
