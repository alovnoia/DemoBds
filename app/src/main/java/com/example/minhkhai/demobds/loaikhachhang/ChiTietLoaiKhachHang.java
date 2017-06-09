package com.example.minhkhai.demobds.loaikhachhang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.CapNhatDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaisp.CapNhatLoaiSP;
import com.example.minhkhai.demobds.uudai.CapNhatUuDai;

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
    String lstUuDai;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_khach_hang);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                        API.change = true;
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
                lstUuDai = object.getString("UuDai");
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
            Toast.makeText(getApplicationContext(), "Đã sửa loại khách hàng có id "+id, Toast.LENGTH_SHORT).show();
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
                Intent i = new Intent(ChiTietLoaiKhachHang.this, MainActivity.class);
                i.putExtra("key", "LoaiKhachHang");
                startActivity(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(ChiTietLoaiKhachHang.this, MainActivity.class);
                i.putExtra("key", "LoaiKhachHang");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        } else if (idItem == R.id.delete1) {
            // Show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietLoaiKhachHang.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa loại khách hàng này?");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new XoaLoaiKH().execute();
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
        } else if (idItem == R.id.xemuudai || idItem == R.id.xemUuDaiNVBH) {
            // Show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(ChiTietLoaiKhachHang.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_xem_uudai, null);
            builder.setView(dialogView);
            if (!lstUuDai.equals("")) {
                builder.setTitle("Ưu đãi đang áp dụng");
                ListView lvXemUuDai = (ListView) dialogView.findViewById(R.id.lvXemUuDai);

                String[] arrUuDai = lstUuDai.substring(0, lstUuDai.length()-2).split(", ");
                Log.i("xemuudai", arrUuDai[0] + arrUuDai[1]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ChiTietLoaiKhachHang.this, android.R.layout.simple_list_item_1, arrUuDai);
                lvXemUuDai.setAdapter(adapter);
            } else {
                builder.setMessage("Chưa có ưu đãi nào!");
            }

            builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (API.quyen.equals("NVQL")) {
            ChiTietLoaiKhachHang.this.getMenuInflater().inflate(R.menu.menu_xem_uudai, menu);
        } else {
            ChiTietLoaiKhachHang.this.getMenuInflater().inflate(R.menu.xem_uudai_nvbh, menu);
        }
        return true;
    }
}
