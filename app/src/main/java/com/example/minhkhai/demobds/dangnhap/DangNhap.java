package com.example.minhkhai.demobds.dangnhap;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.DanhSachLoaiKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHangAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DangNhap extends AppCompatActivity {

    EditText edtTaiKhoan, edtMatKhau;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        edtTaiKhoan = (EditText) findViewById(R.id.edtTaiKhoan);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new KiemTraDangNhap().execute("http://"+API.HOST+"/bds_project/public/login");
                    }
                });
            }
        });

    }

    private class KiemTraDangNhap extends AsyncTask<String, String, String>{

        String taiKhoan = edtTaiKhoan.getText().toString();
        String matKhau = edtMatKhau.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL myUrl = new URL(params[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenTaiKhoan", taiKhoan);
                postDataParams.put("MatKhau", matKhau);

                return API.POST_URL(myUrl, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                if (s != ""){
                    JSONObject obj = new JSONObject(s);

                    Intent intent = new Intent(DangNhap.this, AppMenu.class);
                    API.idUser = obj.getInt("MaTaiKhoan");
                    startActivity(intent);
                } else {
                    Toast.makeText(DangNhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
