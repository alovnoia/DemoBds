package com.example.minhkhai.demobds.loaisp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.ThemLoaiKhachHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class DanhSachLoaiSP extends AppCompatActivity {

    ListView lvLoaiSP;
    ArrayList<LoaiSP> mangLoaiSanPham;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_loai_sp);

        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        lvLoaiSP = (ListView) findViewById(R.id.lvDanhSachLoaiSP);
        mangLoaiSanPham = new ArrayList<LoaiSP>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSach().execute("http://"+API.HOST+"/bds_project/public/LoaiSP");
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachLoaiSP.this, ThemLoaiSP.class);
                startActivity(intent);
            }
        });

        lvLoaiSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachLoaiSP.this, CapNhatLoaiSP.class);
                intent.putExtra("id", mangLoaiSanPham.get(position).getMaLoaiSP());
                startActivity(intent);
            }
        });

        /*lvLoaiSP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DanhSachLoaiSP.this);
                alertDialogBuilder.setMessage("Bán có muốn xóa loại sản phẩm này?");
                alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        runOnUiThread();
                    }
                });
                alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //không làm gì
                    }
                });
                alertDialogBuilder.show();
                return true;
            }
        });*/

    }

    private class LoadDanhSach extends AsyncTask<String, Integer, String>{

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

                    mangLoaiSanPham.add(new LoaiSP(
                            object.getInt("MaLoaiSP"),
                            object.getString("TenLoaiSP"),
                            object.getString("MoTa")
                    ));
                }

                LoaiSPAdapter adapter = new LoaiSPAdapter(DanhSachLoaiSP.this, R.layout.item_loai_sp, mangLoaiSanPham);

                lvLoaiSP.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, AppMenu.class);
        startActivity(intent);
        return true;
    }

}
