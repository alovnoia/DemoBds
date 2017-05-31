package com.example.minhkhai.demobds.taikhoan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.hotro.upanh.ApiClient;
import com.example.minhkhai.demobds.hotro.upanh.ApiService;
import com.example.minhkhai.demobds.hotro.upanh.ServerResponse;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.example.minhkhai.demobds.khachhang.ThemKhachHang;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTaiKhoan extends AppCompatActivity {
    ImageView imgAnh;
    EditText edtTen, edtUsername, edtNgaySinh, edtSDT, edtDiaChi, edtThongTinKhac;
    Spinner spnChucVu;
    FloatingActionButton fab_SaveThem;

    Calendar ngayGioHienTai = Calendar.getInstance();

    String mediaPath;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tai_khoan);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imgAnh = (ImageView) findViewById(R.id.imgAnhThemTK);
        edtTen = (EditText) findViewById(R.id.edtTenThemTK);
        edtUsername = (EditText) findViewById(R.id.edtUsernameThemTK);
        edtNgaySinh = (EditText) findViewById(R.id.edtNgaySinhThemTK);
        edtSDT = (EditText) findViewById(R.id.edtSDTThemTK);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChiThemTK);
        spnChucVu = (Spinner) findViewById(R.id.spnChucVuThemTK);
        edtThongTinKhac = (EditText) findViewById(R.id.edtThongTinThemTK);
        fab_SaveThem = (FloatingActionButton) findViewById(R.id.fab_SaveThemTK);

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        List<String> listChucVu = new ArrayList<>();
        listChucVu.add("Nhân viên bán hàng");
        listChucVu.add("Nhân viên quản lý");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listChucVu);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnChucVu.setAdapter(adapter);

        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });

        fab_SaveThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        file = new File(mediaPath);
                        new LuuThemTaiKhoan().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan");
                        API.change = true;
                    }
                });
            }
        });

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

    public void datePicker(){
        DatePickerDialog date = new DatePickerDialog(ThemTaiKhoan.this,
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

    private class LuuThemTaiKhoan extends AsyncTask<String, Integer, String> {
        String hoTen = edtTen.getText().toString();
        String taiKhoan = edtUsername.getText().toString();
        String[] ngaySinh = edtNgaySinh.getText().toString().split("/");
        String dienThoai = edtSDT.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String chucVu = spnChucVu.getSelectedItem().toString();
        String thongTinThem = edtThongTinKhac.getText().toString();
        String anh = file.getName();
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                JSONObject post = new JSONObject();

                post.put("TenTaiKhoan", taiKhoan);
                post.put("MatKhau", "123");
                post.put("HoTen", hoTen);
                post.put("DiaChi", diaChi);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                post.put("NgaySinh", format.format(format.parse(ngaySinh[2]+"-"+ngaySinh[1]+"-"+ngaySinh[0])));
                post.put("SoDienThoai", dienThoai);
                post.put("Anh", anh);
                if (chucVu=="Nhân viên bán hàng"){
                    post.put("ChucVu", "NVBH");
                }else
                {
                    post.put("ChucVu", "NVQL");
                }
                //post.put("CMT", "0000");
                post.put("ThongTinKhac", thongTinThem);
                return API.POST_URL(url, post);

            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            int idChiTiet = 0;
            if (!s.equals("0"))
            {
                try {
                    JSONObject object = new JSONObject(s);
                    idChiTiet = object.getInt("MaTaiKhoan");
                    API.uploadFile(file);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ThemTaiKhoan.this, "Đã thêm tài khoản " + hoTen, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ThemTaiKhoan.this, ChiTietTaiKhoan.class);
                i.putExtra("id", idChiTiet);
                startActivity(i);
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
