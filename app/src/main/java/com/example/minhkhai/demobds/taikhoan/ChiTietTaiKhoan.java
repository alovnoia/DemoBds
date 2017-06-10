package com.example.minhkhai.demobds.taikhoan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

    String mediaPath = "";
    File file;
    String tenHinh;
    String pass;

    JSONObject taiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tai_khoan);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
        //fab_Xoa = (FloatingActionButton) findViewById(R.id.fab_XoaTaiKhoan);

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
                new LayThongTinTK().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
            }
        });

        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPath != "")
                        {
                            file = new File(mediaPath);
                            tenHinh = file.getName();
                            API.uploadFile(file);
                        }else{
                            try {
                                tenHinh = taiKhoan.getString("Anh");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        new SaveUpdate().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
                        API.change = true;
                    }
                });
            }
        });

        /*fab_Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaTaiKhoan().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
                        Intent i = new Intent(ChiTietTaiKhoan.this, MainActivity.class);
                        i.putExtra("key", "TaiKhoan");
                        startActivity(i);
                    }
                });
            }
        });*/


    }

    //Hiển thị ảnh khi chọn
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imgAnh.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "Bạn chưa chọn ảnh", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
        }

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
            taiKhoan = object;
            try {
                object = new JSONObject(s);

                tenHinh = object.getString("Anh");
                Picasso.with(ChiTietTaiKhoan.this)
                        .load("http://"+API.HOST+"/bds_project/data/"+ tenHinh)
                        .placeholder(R.drawable.ic_users)
                        .error(R.drawable.ic_error_img)
                        .into(imgAnh);

                edtTen.setText(object.getString("HoTen"));
                tvUsername.setText(object.getString("TenTaiKhoan"));
                pass = object.getString("MatKhau");

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
        String tenDangNhap = tvUsername.getText().toString();
        String hoTen = edtTen.getText().toString();
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
                object.put("Anh", tenHinh);
                object.put("TenTaiKhoan", tenDangNhap);
                object.put("MatKhau", pass);
                object.put("HoTen", hoTen);
                object.put("DiaChi", diaChi);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                object.put("NgaySinh", format.format(format.parse(ngaySinh[2]+"-"+ngaySinh[1]+"-"+ngaySinh[0])));
                String chucVuPost = "NVBH";
                if (chucVu.equals("Nhân viên quản lý")){
                    chucVuPost = "NVQL";
                }
                object.put("ChucVu", chucVuPost);
                object.put("SoDienThoai", sDT);
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
            Toast.makeText(getApplicationContext(), "Đã sửa tài khoản có id " + id, Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(ChiTietTaiKhoan.this, MainActivity.class);
                i.putExtra("key", "TaiKhoan");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
