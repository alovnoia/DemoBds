package com.example.minhkhai.demobds.lo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.CapNhatDuAn;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ChiTietLo extends AppCompatActivity {

    TextView tvMa;
    EditText edtTen;
    Spinner spnDuAn;
    FloatingActionButton fab_Save;
    int id;
    String tenChiTietLo, tenDuAnChiTietLo;
    ArrayAdapter<DuAn> adapter;
    ArrayList<DuAn> mangDuAn = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_lo);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tvMa = (TextView) findViewById(R.id.tvMaLoChiTiet);
        edtTen = (EditText) findViewById(R.id.edtTenChiTietLo);
        spnDuAn = (Spinner) findViewById(R.id.spnDuAnChiTietLo);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveChiTietLo);

        Bundle extras = getIntent().getExtras();
        id= extras.getInt("id");
        tenChiTietLo = extras.getString("TenLo");
        tenDuAnChiTietLo = extras.getString("TenDuAn");

        if (API.quyen.equals("NVBH")) {
            fab_Save.setVisibility(View.GONE);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvMa.setText(id+"");
                edtTen.setText(tenChiTietLo);
                new LoadDuAn().execute("http://"+API.HOST+"/bds_project/public/DuAn");
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Save().execute("http://"+API.HOST+"/bds_project/public/Lo/"+id);
                API.change = true;
            }
        });
    }

    private class LoadDuAn extends AsyncTask<String, Integer, String>{

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

                adapter = new ArrayAdapter(ChiTietLo.this,
                        android.R.layout.simple_spinner_item, mangDuAn);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDuAn.setAdapter(adapter);

                for (int i =0; i < mangDuAn.size(); i++){
                    if (tenDuAnChiTietLo.equals(adapter.getItem(i).getTenDuAn())){
                        spnDuAn.setSelection(i);
                        break;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class Save extends AsyncTask<String, Integer, String>{
        String ten= edtTen.getText().toString();
        DuAn duAn = (DuAn) spnDuAn.getSelectedItem();
        int maDuAn = duAn.getMaDuAn();
        String tenDuAnSave = duAn.getTenDuAn();
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                JSONObject object = new JSONObject();

                object.put("TenLo", ten);
                object.put("DuAn", maDuAn);
                object.put("_method", "PUT");

                return API.POST_URL(url, object);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getApplicationContext(), "Đã sửa lô có tên: "+ten, Toast.LENGTH_SHORT).show();
        }
    }

    private class Xoa extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                JSONObject object = new JSONObject();
                object.put("_method", "DELETE");
                return API.POST_URL(url, object);

            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Bạn vừa xóa lô có id "+id, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ChiTietLo.this, MainActivity.class);
            i.putExtra("key", "Lo");
            i.putExtra("TenDuAn", tenDuAnChiTietLo);
            startActivity(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(ChiTietLo.this, MainActivity.class);
                i.putExtra("key", "Lo");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.delete) {
            // Show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietLo.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa lô này?");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new Xoa().execute("http://"+API.HOST+"/bds_project/public/Lo/"+id);
                        }
                    });
                }
            });
            builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (API.quyen.equals("NVQL")) {
            ChiTietLo.this.getMenuInflater().inflate(R.menu.menu, menu);
        }
        return true;
    }
}
