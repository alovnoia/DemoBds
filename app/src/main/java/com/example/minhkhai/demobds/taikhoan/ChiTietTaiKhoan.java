package com.example.minhkhai.demobds.taikhoan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.squareup.picasso.Picasso;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class ChiTietTaiKhoan extends AppCompatActivity {
    TextView tvID, tvUsername;
    ImageView imgAnh;
    EditText edtTen, edtNgaySinh, edtDienThoai, edtDiaChi, edtThongtin;
    Spinner spnChucVu;
    FloatingActionButton fab_Save, fab_Xoa;

    Calendar ngayGioHienTai = Calendar.getInstance();
    int id = 0;
    List<String> listChucVu = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tai_khoan);

        tvID = (TextView) findViewById(R.id.tvIDTaiKhoan);
        tvUsername = (TextView) findViewById(R.id.tvTenDangNhapChiTietTK);
        imgAnh = (ImageView) findViewById(R.id.imgAnhChiTietTK);
        edtTen = (EditText) findViewById(R.id.edtTenChiTietTK);
        edtNgaySinh = (EditText) findViewById(R.id.edtNgaySinhChiTietTK);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChiChiTietTK);
        edtDienThoai = (EditText) findViewById(R.id.edtSDTChiTietTK);
        edtThongtin = (EditText) findViewById(R.id.edtThongTinKhacChiTietTk);
        spnChucVu = (Spinner) findViewById(R.id.spnChucVuChiTietTK);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveCapNhatTK);
        fab_Xoa = (FloatingActionButton) findViewById(R.id.fab_XoaTaiKhoan);
        //Test de push
        Intent myIntent = getIntent();
        id = myIntent.getIntExtra("id", 0);

        tvID.setText(id+"");

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });


        listChucVu.add("Nhân viên quản lý");
        listChucVu.add("Nhân viên bán hàng");

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listChucVu);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChucVu.setAdapter(adapter);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LayThongTinTK().execute("http://10.0.3.2:2347/bds_project/public/TaiKhoan/"+id);
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SaveUpdate().execute("http://10.0.3.2:2347/bds_project/public/TaiKhoan/"+id);
                        Intent i = new Intent(ChiTietTaiKhoan.this, DanhSachTaiKhoan.class);
                        startActivity(i);
                    }
                });
            }
        });

        fab_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaTaiKhoan().execute("http://10.0.3.2:2347/bds_project/public/TaiKhoan/"+id);
                        Intent i = new Intent(ChiTietTaiKhoan.this, DanhSachTaiKhoan.class);
                        startActivity(i);
                    }
                });
            }
        });


    }

    private class XoaTaiKhoan extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("_method", "DELETE");

                return API.POST_URL(url, postDataParams);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Đã xóa Tài khoản có id "+id, Toast.LENGTH_SHORT).show();

        }
    }

    private class LayThongTinTK extends AsyncTask<String, String, String>{

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

            JSONObject object = null;
            try {
                object = new JSONObject(s);
                String tenHinh = object.getString("Anh");
                Picasso.with(ChiTietTaiKhoan.this).load("http://10.0.3.2:2347/bds_project/img/"+ tenHinh).into(imgAnh);

                edtTen.setText(object.getString("HoTen"));
                tvUsername.setText(object.getString("TenTaiKhoan"));

                String[] ngay = object.get("NgaySinh").toString().split("-");
                edtNgaySinh.setText(ngay[2]+"/"+ngay[1]+"/"+ngay[0]);

                edtDienThoai.setText(object.getString("SoDienThoai"));
                edtDiaChi.setText(object.getString("DiaChi"));

                String temp =object.getString("ChucVu");
                for (int i = 0; i < adapter.getCount(); i++){
                    String t = adapter.getItem(i);
                    if (temp.equals("NVQL") && t.equals("Nhân viên quản lý")){
                        spnChucVu.setSelection(i);
                        break;
                    }
                    if (temp.equals("NVBH") && t.equals("Nhân viên bán hàng")){
                        spnChucVu.setSelection(i);
                        break;
                    }
                }

                edtThongtin.setText(object.getString("ThongTinKhac"));
            } catch (JSONException e) {
                e.printStackTrace();
            }




        }
    }

    private class SaveUpdate extends AsyncTask<String, String, String>{
        String hoTen = edtTen.getText().toString();
        String anh = "Hình ảnh";
        String[] ngaySinh = edtNgaySinh.getText().toString().split("/");
        String sDT = edtDienThoai.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String chucVu = spnChucVu.getSelectedItem().toString();
        String more = edtThongtin.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                JSONObject object = new JSONObject();
                object.put("Anh", anh);
                object.put("HoTen", hoTen);
                object.put("DiaChi", diaChi);
                object.put("SoDienThoai", sDT);
                if (chucVu.equals("Nhân viên quản lý")){
                    object.put("ChucVu", "NVQL");
                }
                else
                {
                    object.put("ChucVu", "NVBH");
                }
                object.put("ThongTinKhac", more);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                object.put("NgaySinh", format.format(format.parse(ngaySinh[2]+"-"+ngaySinh[1]+"-"+ngaySinh[0])));
                object.put("_method", "PUT");

                return API.POST_URL(url, object);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Đã sửa tài khoản có id = " + id, Toast.LENGTH_SHORT).show();
        }
    }

    public void datePicker(){
        DatePickerDialog date = new DatePickerDialog(ChiTietTaiKhoan.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgaySinh.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }
}
