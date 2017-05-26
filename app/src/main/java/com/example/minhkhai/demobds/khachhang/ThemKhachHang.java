package com.example.minhkhai.demobds.khachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ThemKhachHang extends AppCompatActivity {

    EditText edtThemKHTen, edtThemKHNghe, edtThemKHHoKhau, edtThemKHDienThoai, edtThemKHEmail, edtThemKHThanhPho,
            edtThemKHCMT, edtThemKHLienHe, edttEdtThemKHDienThoaiLienHe, edtThemKHThongTinKhac;
    Spinner spThemKHLoai;
    FloatingActionButton fabLuuKHMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_khach_hang);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtThemKHTen = (EditText) findViewById(R.id.edtThemKHTenKH);
        edtThemKHNghe = (EditText) findViewById(R.id.edtThemKHNghe);
        edtThemKHHoKhau = (EditText) findViewById(R.id.edtThemKHHoKhau);
        edtThemKHDienThoai = (EditText) findViewById(R.id.edtThemKHDienThoai);
        edtThemKHEmail = (EditText) findViewById(R.id.edtThemKHEmail);
        edtThemKHThanhPho = (EditText) findViewById(R.id.edtThemKHThanhPho);
        edtThemKHCMT = (EditText) findViewById(R.id.edtThemKHCMT);
        edtThemKHLienHe = (EditText) findViewById(R.id.edtThemKHLienHe);
        edttEdtThemKHDienThoaiLienHe = (EditText) findViewById(R.id.edtThemKHDienThoaiLienHe);
        edtThemKHThongTinKhac = (EditText) findViewById(R.id.edtThemKHThongTinKhac);
        fabLuuKHMoi = (FloatingActionButton) findViewById(R.id.fabThemKHSave);
        spThemKHLoai = (Spinner) findViewById(R.id.spThemKHLoaiKH);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLoaiKH().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang");
            }
        });

        fabLuuKHMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SaveThongTin().execute("http://"+API.HOST+"/bds_project/public/KhachHang");
                    }
                });
            }
        });

    }

    private class LoadLoaiKH extends AsyncTask<String, String, String>{
        ArrayList<LoaiKhachHang> arrLoaiKH = new ArrayList<>();
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
                JSONArray array= new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    arrLoaiKH.add(new LoaiKhachHang(
                            object.getString("TenLoaiKH"),
                            object.getString("MoTa"),
                            object.getInt("MaLoaiKH")

                    ));
                    //arrLoaiKH.add(String.valueOf(object.getInt("MaLoaiKH")) + ". " + object.getString("TenLoaiKH"));
                }
                final ArrayAdapter<LoaiKhachHang> adapter = new ArrayAdapter(ThemKhachHang.this,
                        android.R.layout.simple_spinner_item, arrLoaiKH);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spThemKHLoai.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveThongTin extends AsyncTask<String, String, String>{

        String tenKH = edtThemKHTen.getText().toString();
        LoaiKhachHang loaiKH = (LoaiKhachHang) spThemKHLoai.getSelectedItem();
        int maLoaiKH = loaiKH.getMaLoai();
        String nghe = edtThemKHNghe.getText().toString();
        String hoKhau = edtThemKHHoKhau.getText().toString();
        String sdt = edtThemKHDienThoai.getText().toString();
        String email = edtThemKHEmail.getText().toString();
        String thanhPho = edtThemKHThanhPho.getText().toString();
        String cmt = edtThemKHCMT.getText().toString();
        String lienHe = edtThemKHLienHe.getText().toString();
        String dtLienHe = edttEdtThemKHDienThoaiLienHe.getText().toString();
        String thongTinKhac = edtThemKHThongTinKhac.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenKhachHang", tenKH);
                postDataParams.put("LoaiKhachHang", maLoaiKH);
                postDataParams.put("NganhNghe", nghe);
                postDataParams.put("HoKhau", hoKhau);
                postDataParams.put("DienThoai", sdt);
                postDataParams.put("Email", email);
                postDataParams.put("ThanhPho", thanhPho);
                postDataParams.put("CMTND", cmt);
                postDataParams.put("LienHe", lienHe);
                postDataParams.put("DienThoaiLienHe", dtLienHe);
                postDataParams.put("ThongTinKhac", thongTinKhac);

                return API.POST_URL(url, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            int idChiTiet = 0;
            try {
                JSONObject object = new JSONObject(s);
                idChiTiet = object.getInt("MaKhachHang");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Toast.makeText(ThemKhachHang.this, "Đã thêm khách hàng "+tenKH, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ThemKhachHang.this, CapNhatKhachHang.class);
            intent.putExtra("id", idChiTiet);
            startActivity(intent);
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
