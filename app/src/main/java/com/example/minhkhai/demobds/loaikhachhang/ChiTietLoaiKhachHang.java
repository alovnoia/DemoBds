package com.example.minhkhai.demobds.loaikhachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class ChiTietLoaiKhachHang extends AppCompatActivity {
    FloatingActionButton flEdit;
    TextView tvID;
    EditText edtTen, edtMoTa;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_khach_hang);

        Bundle i = getIntent().getExtras();
        id = i.getInt("maLoaiKH");


        flEdit = (FloatingActionButton) findViewById(R.id.fab_edit_LoaiSP_ChiTiet);
        tvID = (TextView) findViewById(R.id.tvmaLoaiKHChiTiet);
        edtTen = (EditText) findViewById(R.id.edtTenLoaiKhachHangChiTiet);
        edtMoTa = (EditText) findViewById(R.id.edtMoTaChiTietLoaiKH);

        tvID.setText(id+"");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LayThongTin().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang/"+id);
            }
        });

        flEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new UpdateThongTin().execute();
                    }
                });
            }
        });
        FloatingActionButton flDel = (FloatingActionButton) findViewById(R.id.fab_del_LoaiSP_ChiTiet);
        flDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaLoaiKH().execute();
                        Intent i = new Intent(ChiTietLoaiKhachHang.this, MainActivity.class);
                        i.putExtra("key", "LoaiKhachHang");
                        startActivity(i);
                    }
                });
            }
        });

    }


    class LayThongTin extends AsyncTask<String, Integer, String> {

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
                JSONObject object = new JSONObject(s);
                edtTen.setText(object.getString("TenLoaiKH"));
                edtMoTa.setText(object.getString("MoTa"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class UpdateThongTin extends AsyncTask<String, Integer, String>{

        String tenLKH = edtTen.getText().toString();
        String moTa = edtMoTa.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://"+API.HOST+"/bds_project/public/LoaiKhachHang/"+id); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiKH", tenLKH);
                postDataParams.put("MoTa", moTa);
                postDataParams.put("_method", "PUT");

                return API.POST_URL(url, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Đã sửa loại khách hàng có id = "+id, Toast.LENGTH_SHORT).show();
        }
    }

    class XoaLoaiKH extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://"+API.HOST+"/bds_project/public/LoaiKhachHang/"+id); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                //postDataParams.put("TenLoaiKH", tenLKH);
                // postDataParams.put("MoTa", moTa);
                postDataParams.put("_method", "DELETE");

                return API.POST_URL(url, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject postDataParams = new JSONObject(s);
                Toast.makeText(getApplicationContext(), "Đã xóa loại khách hàng id " + id, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(ChiTietLoaiKhachHang.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/

}
