package com.example.minhkhai.demobds.khachhang;

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
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.duan.DuAnAdapter;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.ThemLoaiKhachHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;

public class DanhSachKhachHang extends AppCompatActivity {

    ListView lvKhachHang;
    ArrayList<KhachHang> arrKhachHang;
    FloatingActionButton fabAddKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_khach_hang);

        arrKhachHang = new ArrayList<KhachHang>();
        lvKhachHang = (ListView) findViewById(R.id.lvKhachHang);
        fabAddKhachHang = (FloatingActionButton) findViewById(R.id.fabThemKhachHang);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSachKH().execute("http://"+API.HOST+"/bds_project/public/KhachHang");
            }
        });

        fabAddKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachKhachHang.this, ThemKhachHang.class);
                startActivity(intent);
            }
        });

        lvKhachHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachKhachHang.this, CapNhatKhachHang.class);
                intent.putExtra("id", arrKhachHang.get(position).getMaKhachHang());
                startActivity(intent);
            }
        });
    }

    private class LoadDanhSachKH extends AsyncTask<String, String, String>{

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

                    arrKhachHang.add(new KhachHang(
                            object.getInt("MaKhachHang"),
                            object.getInt("LoaiKhachHang"),
                            object.getString("TenKhachHang"),
                            object.getString("TenLoaiKH")
                    ));
                }

                KhachHangAdapter adapter = new KhachHangAdapter(DanhSachKhachHang.this, R.layout.item_khach_hang, arrKhachHang);

                lvKhachHang.setAdapter(adapter);

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
        Intent intent = new Intent(DanhSachKhachHang.this, AppMenu.class);
        startActivity(intent);
        return true;
    }

}
