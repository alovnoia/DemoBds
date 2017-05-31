package com.example.minhkhai.demobds.loaisp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.example.minhkhai.demobds.khachhang.DanhSachKhachHang;
import com.example.minhkhai.demobds.lo.ChiTietLo;

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

public class CapNhatLoaiSP extends AppCompatActivity {

    EditText edtCapNhatTenLoaiSP, edtCapNhatMoTaLoaiSP;
    FloatingActionButton fabSaveLoaiSP;
    Button btnXoaLoaiSP;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_loai_sp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtCapNhatTenLoaiSP = (EditText) findViewById(R.id.edtCapNhatTenLoaiSP);
        edtCapNhatMoTaLoaiSP = (EditText) findViewById(R.id.edtCapNhatMoTaLoaiSP);
        btnXoaLoaiSP = (Button) findViewById(R.id.btnXoaLoaiSP);
        fabSaveLoaiSP = (FloatingActionButton) findViewById(R.id.fab_save_LoaiSP);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        if (API.quyen.equals("NVBH")) {
            btnXoaLoaiSP.setVisibility(View.GONE);
            fabSaveLoaiSP.setVisibility(View.GONE);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocLoaiSP().execute("http://"+API.HOST+"/bds_project/public/LoaiSP/"+id);
            }
        });

        fabSaveLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new CapNhatSuaLoaiSP().execute();
                    }
                });
                API.change = true;
            }
        });

        btnXoaLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaLoaiSP().execute("http://"+API.HOST+"/bds_project/public/LoaiSP/"+id);
                    }
                });
            }
        });
    }

    private class DocLoaiSP extends AsyncTask<String, String, String>{

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

                edtCapNhatTenLoaiSP.setText(object.getString("TenLoaiSP"));
                edtCapNhatMoTaLoaiSP.setText(object.getString("MoTa"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class CapNhatSuaLoaiSP extends AsyncTask<String, String, String>{

        String ten = edtCapNhatTenLoaiSP.getText().toString();
        String moTa = edtCapNhatMoTaLoaiSP.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://"+API.HOST+"/bds_project/public/LoaiSP/"+id); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiSP", ten);
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
            Toast.makeText(CapNhatLoaiSP.this, "Đã câp nhật loại sản phẩm có id "+id, Toast.LENGTH_SHORT).show();
        }
    }

    private class XoaLoaiSP extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            try {

                URL myUrl = new URL(params[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("_method", "DELETE");

                return API.POST_URL(myUrl, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(CapNhatLoaiSP.this, "Đã xóa SP có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatLoaiSP.this, MainActivity.class);
            intent.putExtra("key", "LoaiSP");
            startActivity(intent);
        }
    }

    /*@Override
    *//*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(CapNhatLoaiSP.this, MainActivity.class);
                i.putExtra("key", "LoaiSP");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }


}

