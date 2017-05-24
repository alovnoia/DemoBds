package com.example.minhkhai.demobds.loaikhachhang;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.lo.ChiTietLo;
import com.example.minhkhai.demobds.lo.ThemLo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class ThemLoaiKhachHang extends AppCompatActivity {
    EditText edtTenLoaiKH, edtMoTa;
    FloatingActionButton flSave;
    FragmentManager fragmentManager = getFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_khach_hang);

        edtTenLoaiKH = (EditText) findViewById(R.id.edtThemTenLoaiKH);
        edtMoTa = (EditText) findViewById(R.id.edtThemMoTaLoaiKH);
        flSave = (FloatingActionButton) findViewById(R.id.fab_save_LoaiSP);


        flSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new ThemLoaiKH().execute();

                    }
                });
            }
        });
    }
    private class ThemLoaiKH extends AsyncTask<String, Integer, String> {
        String ten = edtTenLoaiKH.getText().toString();
        String moTa = edtMoTa.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://"+API.HOST+"/bds_project/public/LoaiKhachHang"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiKH", ten);
                postDataParams.put("MoTa", moTa);

                return API.POST_URL(url, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int idChiTiet = 0;
            try {
                JSONObject object = new JSONObject(s);
                idChiTiet = object.getInt("MaLoaiKH");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(ThemLoaiKhachHang.this, "Đã thêm loại khách hàng", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ThemLoaiKhachHang.this, ChiTietLoaiKhachHang.class);
            i.putExtra("maLoaiKH", idChiTiet);
            startActivity(i);
        }
    }

    /*@Override
    *//*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ThemLoaiKhachHang.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/

}
