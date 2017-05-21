package com.example.minhkhai.demobds.thongtincanhan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThongTinCaNhan extends AppCompatActivity {
    TextView txtTaiKhoan, txtChucVu;
    EditText edtTen, edtPass, edtNgay, edtDienThoai, edtDiaChi, edtCMT, edtThongTin;
    ImageView imgAnh;
    FloatingActionButton fab_Save;
    Calendar ngayGioHienTai = Calendar.getInstance();
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);

        txtTaiKhoan = (TextView) findViewById(R.id.tvTaiKhoanTTCN);
        txtChucVu = (TextView) findViewById(R.id.tvChucVuTTCN);
        edtTen = (EditText) findViewById(R.id.edtHoTenTTCN);
        edtPass = (EditText) findViewById(R.id.edtMatKhauTTCN);


        edtNgay = (EditText) findViewById(R.id.edtNgaySinhTTCN);
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        edtDienThoai = (EditText) findViewById(R.id.edtDienThoaiTTCN);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChiTTCN);
        edtCMT = (EditText) findViewById(R.id.edtCMTTTCN);
        edtThongTin = (EditText) findViewById(R.id.edtThongTinKhacTTCN);
        imgAnh = (ImageView) findViewById(R.id.imgAnhTTCN);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveTTCN);

        id = API.idUser;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadTTCN().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new CapNhatTTCN().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
                    }
                });
            }
        });
    }

    private class CapNhatTTCN extends AsyncTask<String, Integer, String> {

        String hoTen = edtTen.getText().toString();
        String chucVu = txtChucVu.getText().toString();
        String anh = "Layout_4.png";
        String pass = edtPass.getText().toString();
        String[] ngaySinh = edtNgay.getText().toString().split("/");
        String sDT = edtDienThoai.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String cMT = edtCMT.getText().toString();
        String more = edtThongTin.getText().toString();


        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                JSONObject object = new JSONObject();
                object.put("Anh", anh);
                object.put("ChucVu", chucVu);
                object.put("HoTen", hoTen);
                object.put("MatKhau", pass);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                object.put("NgaySinh", format.format(format.parse(ngaySinh[2]+"-"+ngaySinh[1]+"-"+ngaySinh[0])));
                object.put("SoDienThoai", sDT);
                object.put("DiaChi", diaChi);
                object.put("CMT", cMT);
                object.put("ThongTinKhac", more);
                object.put("_method", "PUT");

                return API.POST_URL(url, object);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Đã sửa", Toast.LENGTH_SHORT).show();
        }
    }
    private class LoadTTCN extends AsyncTask<String, Integer, String>{
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

                String tenHinh = object.getString("Anh");
                Picasso.with(ThongTinCaNhan.this).load("http://"+API.HOST+"/bds_project/img/"+ tenHinh).into(imgAnh);

                txtTaiKhoan.setText(object.getString("TenTaiKhoan"));
                txtChucVu.setText(object.getString("ChucVu"));
                edtTen.setText(object.getString("HoTen"));
                edtPass.setText(object.getString("MatKhau"));
                edtDiaChi.setText(object.getString("DiaChi"));

                String[] ngay = object.get("NgaySinh").toString().split("-");
                edtNgay.setText(ngay[2]+"/"+ngay[1]+"/"+ngay[0]);

                edtDienThoai.setText(object.getString("SoDienThoai"));
                edtCMT.setText(object.getString("CMT"));
                edtThongTin.setText(object.getString("ThongTinKhac"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void datePicker(){
        DatePickerDialog date = new DatePickerDialog(ThongTinCaNhan.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgay.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ThongTinCaNhan.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/
}
