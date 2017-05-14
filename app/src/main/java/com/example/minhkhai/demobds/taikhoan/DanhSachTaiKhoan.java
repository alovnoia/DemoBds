package com.example.minhkhai.demobds.taikhoan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DanhSachTaiKhoan extends AppCompatActivity {

    ListView lvList;
    ArrayList<TaiKhoan> mangTaiKhoan;
    FloatingActionButton fab_Them;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tai_khoan);

        lvList = (ListView) findViewById(R.id.lvDanhSachTaiKhoan);
        mangTaiKhoan = new ArrayList<TaiKhoan>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSachTK().execute("http://10.0.3.2:2347/bds_project/public/TaiKhoan");
            }
        });

        fab_Them = (FloatingActionButton) findViewById(R.id.fab_ThemTaiKhoan);
        fab_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachTaiKhoan.this, ThemTaiKhoan.class);
                startActivity(intent);
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachTaiKhoan.this, ChiTietTaiKhoan.class);

                intent.putExtra("id", mangTaiKhoan.get(position).maTaiKhoan);

                startActivity(intent);
            }
        });
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

                TaiKhoanAdapter adapter =new TaiKhoanAdapter(DanhSachTaiKhoan.this, R.layout.item_taikhoan, mangTaiKhoan);
                lvList.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DanhSachTaiKhoan.this, AppMenu.class);
        startActivity(intent);
        return true;
    }
}
