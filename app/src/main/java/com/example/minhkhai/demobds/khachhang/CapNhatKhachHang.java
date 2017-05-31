package com.example.minhkhai.demobds.khachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
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
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.CapNhatDuAn;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.ChiTietLoaiKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.ThemLoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.CapNhatLoaiSP;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CapNhatKhachHang extends AppCompatActivity {

    EditText edtCNKHTen, edtCNKHNghe, edtCNKHHoKhau, edtCNKHDienThoai, edtCNKHEmail, edtCNKHThanhPho,
            edtCNKHCMT, edtCNKHLienHe, edtCNKHDienThoaiLienHe, edtCNKHThongTinKhac;
    Spinner spCNKHLoai;
    Button btnXoaKH;
    FloatingActionButton fabLuuCapNhat;
    TextView tvMaKhachHang;
    int id;
    ArrayList<LoaiKhachHang> arrLoaiKH;
    ArrayAdapter<LoaiKhachHang> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_khach_hang);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        arrLoaiKH = new ArrayList<>();

        edtCNKHTen = (EditText) findViewById(R.id.edtCNKHTenKH);
        edtCNKHNghe = (EditText) findViewById(R.id.edtCNKHNghe);
        edtCNKHHoKhau = (EditText) findViewById(R.id.edtCNKHHoKhau);
        edtCNKHDienThoai = (EditText) findViewById(R.id.edtCNKHDienThoai);
        edtCNKHEmail = (EditText) findViewById(R.id.edtCNKHEmail);
        edtCNKHThanhPho = (EditText) findViewById(R.id.edtCNKHThanhPho);
        edtCNKHCMT = (EditText) findViewById(R.id.edtCNKHCMT);
        edtCNKHLienHe = (EditText) findViewById(R.id.edtCNKHLienHe);
        edtCNKHDienThoaiLienHe = (EditText) findViewById(R.id.edtCNKHDienThoaiLienHe);
        edtCNKHThongTinKhac = (EditText) findViewById(R.id.edtCNKHThongTinKhac);
        fabLuuCapNhat = (FloatingActionButton) findViewById(R.id.fabLuuCapNhat);
        spCNKHLoai = (Spinner) findViewById(R.id.spCNKHLoaiKH);
        btnXoaKH = (Button) findViewById(R.id.btnXoaKhachHang);
        tvMaKhachHang = (TextView) findViewById(R.id.tvCNMaKhachHang);
        tvMaKhachHang.setText("Thông tin khách hàng mã "+id);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLoaiKH().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang");
                new LoadChiTietKhachHang().execute("http://"+API.HOST+"/bds_project/public/KhachHang/"+id);
            }
        });

        fabLuuCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SaveCapNhat().execute("http://"+API.HOST+"/bds_project/public/KhachHang/"+id);
                        API.change = true;
                    }
                });
            }
        });

        btnXoaKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaKhachHang().execute("http://"+API.HOST+"/bds_project/public/KhachHang/"+id);
                    }
                });
            }
        });
    }

    private class LoadLoaiKH extends AsyncTask<String, String, String>{

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
                adapter = new ArrayAdapter(CapNhatKhachHang.this,
                        android.R.layout.simple_spinner_item, arrLoaiKH);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCNKHLoai.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadChiTietKhachHang extends AsyncTask<String, String, String>{

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
                JSONObject obj = new JSONObject(s);

                edtCNKHTen.setText(obj.getString("TenKhachHang"));
                edtCNKHNghe.setText(obj.getString("NganhNghe"));
                edtCNKHHoKhau.setText(obj.getString("HoKhau"));
                edtCNKHDienThoai.setText(obj.getString("DienThoai"));
                edtCNKHEmail.setText(obj.getString("Email"));
                edtCNKHThanhPho.setText(obj.getString("ThanhPho"));
                edtCNKHCMT.setText(obj.getString("CMTND"));
                edtCNKHLienHe.setText(obj.getString("LienHe"));
                edtCNKHDienThoaiLienHe.setText(obj.getString("DienThoaiLienHe"));
                edtCNKHThongTinKhac.setText(obj.getString("ThongTinKhac"));
                // tim va gan gia tri mac dinh cho spinner = voi gia tri lay tu db
                for (int i = 0; i < adapter.getCount(); i++){
                    if (obj.getInt("LoaiKhachHang") == adapter.getItem(i).maLoai){
                        spCNKHLoai.setSelection(i);
                        break;
                    }
                }
                //spCNKHLoai.setText(obj.getString("TenKhachHang"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveCapNhat extends AsyncTask<String, String, String>{

        String tenKH = edtCNKHTen.getText().toString();
        LoaiKhachHang loaiKH = (LoaiKhachHang) spCNKHLoai.getSelectedItem();
        int maLoaiKH = loaiKH.getMaLoai();
        String nghe = edtCNKHNghe.getText().toString();
        String hoKhau = edtCNKHHoKhau.getText().toString();
        String sdt = edtCNKHDienThoai.getText().toString();
        String email = edtCNKHEmail.getText().toString();
        String thanhPho = edtCNKHThanhPho.getText().toString();
        String cmt = edtCNKHCMT.getText().toString();
        String lienHe = edtCNKHLienHe.getText().toString();
        String dtLienHe = edtCNKHDienThoaiLienHe.getText().toString();
        String thongTinKhac = edtCNKHThongTinKhac.getText().toString();
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
            Toast.makeText(CapNhatKhachHang.this, "Đã cập nhật khách hàng "+id, Toast.LENGTH_SHORT).show();
        }
    }

    private class XoaKhachHang extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            try {

                URL myUrl = new URL(params[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("_method", "DELETE");

                return API.POST_URL(myUrl, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(CapNhatKhachHang.this, "Đã xóa khách có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatKhachHang.this, MainActivity.class);
            intent.putExtra("key", "KhachHang");
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(CapNhatKhachHang.this, MainActivity.class);
                i.putExtra("key", "KhachHang");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
