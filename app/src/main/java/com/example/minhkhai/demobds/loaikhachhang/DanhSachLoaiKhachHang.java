package com.example.minhkhai.demobds.loaikhachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.hotro.menu.NavigationDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DanhSachLoaiKhachHang extends AppCompatActivity {

    FloatingActionButton flAdd;
    ListView lvDanhSachLoaiKH;
    ArrayList<LoaiKhachHang> mangLoaiKH;

    //TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_loai_khach_hang);
        //test = (TextView) findViewById(R.id.TestTV);
       /* FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.flDanhSachLoaiKH, new NavigationDrawer()).commit();*/

        lvDanhSachLoaiKH= (ListView) findViewById(R.id.lvDanhSachLoaiKH);
        mangLoaiKH = new ArrayList<LoaiKhachHang>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang");
            }
        });

        flAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        flAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(DanhSachLoaiKhachHang.this, ThemLoaiKhachHang.class);
                startActivity(i);
            }
        });

        lvDanhSachLoaiKH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(DanhSachLoaiKhachHang.this, ChiTietLoaiKhachHang.class);
                i.putExtra("maLoaiKH", mangLoaiKH.get(position).maLoai);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent = new Intent(DanhSachLoaiKhachHang.this, MainActivity.class);
        startActivity(intent);*/
        switch (item.getItemId()) {
            case R.id.duAn:
                return true;
            case R.id.loaiKH:
                return true;
            case R.id.loaiSanPham:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                LoaiKhachHangAdapter adapter= new LoaiKhachHangAdapter(DanhSachLoaiKhachHang.this,
                        R.layout.item_loai_khach_hang, mangLoaiKH);
                lvDanhSachLoaiKH.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}
