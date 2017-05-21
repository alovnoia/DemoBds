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
    Spinner spnDuAn;
    FloatingActionButton fab_Save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lo);

        edtTen = (EditText) findViewById(R.id.edtTenThemLo);
        spnDuAn = (Spinner) findViewById(R.id.spnDuAnThemLo);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveThemLo);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDuAn().execute("http://"+ API.HOST+"/bds_project/public/DuAn");
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Save().execute("http://"+API.HOST+"/bds_project/public/Lo");
                        Intent i = new Intent(ThemLo.this, MainActivity.class);
                        i.putExtra("key", "Lo");
                        startActivity(i);
                    }
                });
            }
        });
    }

    public class LoadDuAn extends AsyncTask<String, Integer, String>{
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

                for (int i=0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    mangDuAn.add(new DuAn(
                            object.getInt("MaDuAn"),
                            object.getString("TenDuAn"),
                            object.getString("DiaChi")
                    ));
                }

                final ArrayAdapter<DuAn> adapter = new ArrayAdapter(ThemLo.this,
                        android.R.layout.simple_spinner_item, mangDuAn);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDuAn.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class Save extends AsyncTask<String, Integer, String>{

        String tenLo = edtTen.getText().toString();
        DuAn duAn = (DuAn) spnDuAn.getSelectedItem();
        int maDuAn = duAn.getMaDuAn();
        String tenDuAn = duAn.getTenDuAn();
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
            Toast.makeText(getApplicationContext(),
                    "Đã thêm lô "+tenLo+" mới trong dự án "+ tenDuAn, Toast.LENGTH_LONG).show();
        }
    }
}
