package com.example.minhkhai.demobds.loaikhachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;

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
        lvDanhSachLoaiKH= (ListView) findViewById(R.id.lvDanhSachLoaiKH);
        mangLoaiKH = new ArrayList<LoaiKhachHang>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://10.0.3.2:2347/bds_project/public/LoaiKhachHang");
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

    private class LoadDanhSach extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return GET_URL(params[0]);
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


    private  static String GET_URL(String theURL) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(theURL);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            content.append(line + "\n");
        }
        bufferedReader.close();

        return content.toString();
    }
}
