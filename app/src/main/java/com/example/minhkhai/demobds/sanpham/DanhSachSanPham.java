package com.example.minhkhai.demobds.sanpham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.DanhSachKhachHang;
import com.example.minhkhai.demobds.khachhang.KhachHang;
import com.example.minhkhai.demobds.khachhang.KhachHangAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachSanPham extends AppCompatActivity {

    ListView lvSanPham;
    ArrayList<SanPham> arrSanPham;
    FloatingActionButton fabThemSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);

        arrSanPham = new ArrayList<>();
        lvSanPham = (ListView) findViewById(R.id.lvSanPham);
        fabThemSP = (FloatingActionButton) findViewById(R.id.fabThemSP);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSachSP().execute("http://"+API.HOST+"/bds_project/public/SanPham");
            }
        });

        fabThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachSanPham.this, ThemSanPham.class);
                startActivity(intent);
            }
        });

    }

    private class LoadDanhSachSP extends AsyncTask<String, String, String>{

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

                    arrSanPham.add(new SanPham(
                            object.getInt("MaSP"),
                            //object.getString("TenSP"),
                            "Sản phẩm",
                            object.getString("TenLoaiSP"),
                            object.getLong("GiaBan")
                    ));
                }

                SanPhamAdapter adapter = new SanPhamAdapter(DanhSachSanPham.this, R.layout.item_sp, arrSanPham);

                lvSanPham.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*@Override
    *//*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DanhSachSanPham.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/


}
