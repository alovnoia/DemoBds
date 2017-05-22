package com.example.minhkhai.demobds.lo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ThemLo extends AppCompatActivity {

    EditText edtTen;
    TextView tvTenDuAn;
    FloatingActionButton fab_Save;
    int id =0;
    String tenDuAn;
    int maDuAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lo);

        edtTen = (EditText) findViewById(R.id.edtTenThemLo);
        tvTenDuAn = (TextView) findViewById(R.id.tvTenDuAnThemLo);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveThemLo);

        Bundle extras = getIntent().getExtras();
        tenDuAn = extras.getString("TenDuAn");
        maDuAn = extras.getInt("MaDuAn");
        tvTenDuAn.setText(tenDuAn);


        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Save().execute("http://"+API.HOST+"/bds_project/public/Lo");
                    }
                });
            }
        });
    }

    private class Save extends AsyncTask<String, Integer, String>{

        String tenLo = edtTen.getText().toString();
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                JSONObject object = new JSONObject();
                object.put("TenLo", tenLo);
                object.put("DuAn", maDuAn);

                return API.POST_URL(url,object);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            JSONObject object = null;

            if (!s.equals("0"))
            {
                id = Integer.parseInt(s);
                Toast.makeText(getApplicationContext(),
                        "Đã thêm lô "+tenLo+" mới trong dự án "+ tenDuAn, Toast.LENGTH_LONG).show();

                Intent i = new Intent(ThemLo.this, ChiTietLo.class);
                i.putExtra("id", id);
                startActivity(i);
            }

        }
    }
}
