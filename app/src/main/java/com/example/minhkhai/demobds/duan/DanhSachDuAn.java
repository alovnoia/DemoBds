package com.example.minhkhai.demobds.duan;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.example.minhkhai.demobds.hotro.API;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.menu.NavigationDrawer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DanhSachDuAn extends AppCompatActivity{

    ListView lvDuAn;
    ArrayList<DuAn> mangDuAn;
    FloatingActionButton fabThemDuAn;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_du_an);

        mangDuAn = new ArrayList<DuAn>();
        lvDuAn = (ListView) findViewById(R.id.lvDuAn);
        fabThemDuAn = (FloatingActionButton) findViewById(R.id.fabThemDuAn);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+API.HOST+"/bds_project/public/DuAn");
            }
        });

        fabThemDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachDuAn.this, ThemDuAn.class);
                startActivity(intent);
            }
        });

        lvDuAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachDuAn.this, CapNhatDuAn.class);
                intent.putExtra("id", mangDuAn.get(position).getMaDuAn());
                startActivity(intent);
            }
        });

        /*FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.flDanhSachDuAn, new NavigationDrawer()).commit();*/
    }

    private class LoadDanhSach extends AsyncTask<String, Integer, String> {

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
                }

                DuAnAdapter adapter = new DuAnAdapter(DanhSachDuAn.this, R.layout.item_du_an, mangDuAn);

                lvDuAn.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
