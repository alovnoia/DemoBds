package com.example.minhkhai.demobds.sanpham;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ChonDuAn extends AppCompatActivity {

    Spinner spChonDuAn;
    Button btnTiep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_du_an);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        spChonDuAn = (Spinner) findViewById(R.id.spThemSPDuAn);
        btnTiep = (Button) findViewById(R.id.btnTiep);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+ API.HOST+"/bds_project/public/DuAn");
            }
        });

        btnTiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DuAn duAn = (DuAn) spChonDuAn.getSelectedItem();
                Intent intent = new Intent(ChonDuAn.this, ThemSanPham.class);
                intent.putExtra("MaDuAn", duAn.getMaDuAn());
                intent.putExtra("TenDuAn", duAn.getTenDuAn());
                startActivity(intent);
                Toast.makeText(ChonDuAn.this, "đã chọn dự án "+duAn.getTenDuAn(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class LoadDanhSach extends AsyncTask<String, Integer, String> {
        ArrayList<DuAn> mangDuAn = new ArrayList<>();
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

                    mangDuAn.add(new DuAn(
                            object.getInt("MaDuAn"),
                            object.getString("TenDuAn"),
                            object.getString("DiaChi")
                    ));
                    final ArrayAdapter<DuAn> adapter = new ArrayAdapter(ChonDuAn.this,
                            android.R.layout.simple_spinner_item, mangDuAn);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spChonDuAn.setAdapter(adapter);
                }

            } catch (JSONException e) {
                e.printStackTrace();
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
