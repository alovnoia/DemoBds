package com.example.minhkhai.demobds.loaikhachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;

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
                        Intent i = new Intent(ThemLoaiKhachHang.this, DanhSachLoaiKhachHang.class);
                        startActivity(i);
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
            Toast.makeText(ThemLoaiKhachHang.this, "Đã thêm loại khách hàng", Toast.LENGTH_SHORT).show();
        }
    }


}
