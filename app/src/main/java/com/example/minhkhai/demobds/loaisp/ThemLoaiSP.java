package com.example.minhkhai.demobds.loaisp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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

import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class ThemLoaiSP extends AppCompatActivity {

    EditText edtThemTenLoaiSP, edtThemMoTaSP;
    FloatingActionButton fabSaveLoaiSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_sp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtThemTenLoaiSP = (EditText) findViewById(R.id.edtCapNhatTenLoaiSP);
        edtThemMoTaSP = (EditText) findViewById(R.id.edtThemMoTaLoaiSP);
        fabSaveLoaiSP = (FloatingActionButton) findViewById(R.id.fab_save_LoaiSP);

        fabSaveLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new LuuLoaiSP().execute();
                        API.change = true;
                    }
                });
            }
        });

    }

    private class LuuLoaiSP extends AsyncTask<String, Integer, String>{

        String ten = edtThemTenLoaiSP.getText().toString();
        String moTa = edtThemMoTaSP.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://"+API.HOST+"/bds_project/public/LoaiSP"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiSP", ten);
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

            JSONObject object = null;
            int idChiTiet = 0;
            String tenLoMoi = "";

            if (!s.equals("0"))
            {
                try {
                    object = new JSONObject(s);
                    idChiTiet = object.getInt("MaLoaiSP");
                    tenLoMoi = object.getString("TenLoaiSP");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ThemLoaiSP.this, "Đã thêm loại sản phẩm " + tenLoMoi, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ThemLoaiSP.this, CapNhatLoaiSP.class);
                i.putExtra("id", idChiTiet);
                startActivity(i);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
