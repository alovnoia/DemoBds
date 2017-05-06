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
    private class ThemLoaiKH extends AsyncTask<String, Integer, String>
    {
        String ten = edtTenLoaiKH.getText().toString();
        String moTa = edtMoTa.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://10.0.3.2:2347/bds_project/public/LoaiKhachHang"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiKH", ten);
                postDataParams.put("MoTa", moTa);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false : "+responseCode);
                }
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

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
