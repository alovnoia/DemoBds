package com.example.minhkhai.demobds.taikhoan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
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

public class DanhSachTaiKhoan extends Fragment {

    ListView lvList;
    ArrayList<TaiKhoan> mangTaiKhoan;
    FloatingActionButton fab_Them;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_tai_khoan,container,false);

        lvList = (ListView) view.findViewById(R.id.lvDanhSachTaiKhoan);
        mangTaiKhoan = new ArrayList<TaiKhoan>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSachTK().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan");
            }
        });

        fab_Them = (FloatingActionButton) view.findViewById(R.id.fab_ThemTaiKhoan);
        fab_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemTaiKhoan.class);
                startActivity(intent);
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietTaiKhoan.class);

                intent.putExtra("id", mangTaiKhoan.get(position).maTaiKhoan);

                startActivity(intent);
            }
        });
        return view;
    }

    private class LoadDanhSachTK extends AsyncTask<String, Integer, String> {

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

                for (int i = 0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    mangTaiKhoan.add(new TaiKhoan(
                            object.getInt("MaTaiKhoan"),
                            object.getString("TenTaiKhoan"),
                            object.getString("MatKhau"),
                            object.getString("HoTen"),
                            object.getString("DiaChi"),
                            object.getString("NgaySinh"),
                            object.getString("SoDienThoai"),
                            object.getString("Anh"),
                            object.getString("ChucVu"),
                            object.getString("ThongTinKhac")
                    ));
                }

                TaiKhoanAdapter adapter =new TaiKhoanAdapter(getActivity(), R.layout.item_taikhoan, mangTaiKhoan);
                lvList.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DanhSachTaiKhoan.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/
}
