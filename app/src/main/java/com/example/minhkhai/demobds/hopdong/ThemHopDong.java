package com.example.minhkhai.demobds.hopdong;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.KhachHang;
import com.example.minhkhai.demobds.lo.Lo;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.sanpham.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemHopDong extends AppCompatActivity {

    Spinner spnLoaiKH, spnKhachHang, spnDuAn, spnLo, spnSanPham, spnKieuThanhToan;
    EditText edtNgayKy, edtNgayBanGiao, edtTienCoc, edtGhiChu;
    FloatingActionButton fab_Save;

    ArrayList<LoaiKhachHang> mangLoaiKH;
    ArrayList<KhachHang> mangKhachHang;
    ArrayList<DuAn> mangDuAn;
    ArrayList<Lo> mangLo;
    ArrayList<SanPham> mangSanPham;

    LoaiKhachHang loaiKH;
    DuAn duAn;
    Lo lo;

    Calendar ngayGioHienTai = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hop_dong);

        mangLoaiKH = new ArrayList<LoaiKhachHang>();
        mangKhachHang = new ArrayList<KhachHang>();
        mangDuAn = new ArrayList<DuAn>();
        mangLo = new ArrayList<Lo>();
        mangSanPham = new ArrayList<SanPham>();

        spnLoaiKH = (Spinner) findViewById(R.id.spnLoaiKHThemHopDong);
        spnKhachHang = (Spinner) findViewById(R.id.spnKhachHangThemHopDong);
        spnDuAn = (Spinner) findViewById(R.id.spnDuAnThemHopDong);
        spnLo = (Spinner) findViewById(R.id.spnLoThemHopDong);
        spnSanPham = (Spinner) findViewById(R.id.spnSanPhamThemHopDong);
        edtNgayKy = (EditText) findViewById(R.id.edtNgayKyThemHopDong);
        edtNgayBanGiao = (EditText) findViewById(R.id.edtNgayBanGiaoThemHopDong);
        spnKieuThanhToan = (Spinner) findViewById(R.id.spnKieuThanhToanThemHopDong);
        edtTienCoc = (EditText) findViewById(R.id.edtTienCocThemHopDong);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChuThemHopDong);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveThemHopDong);

        edtNgayKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(edtNgayKy);
            }
        });
        edtNgayBanGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(edtNgayBanGiao);
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLoaiKH().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang");
                new LoadDuAn().execute("http://"+ API.HOST+"/bds_project/public/DuAn");
            }
        });

        spnLoaiKH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loaiKH = (LoaiKhachHang) spnLoaiKH.getSelectedItem();
                new LoadKhachHang().execute("http://"+API.HOST+"/bds_project/public/KhachHang");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnDuAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duAn = (DuAn) spnDuAn.getSelectedItem();
                new LoadLo().execute("http://"+ API.HOST+"/bds_project/public/getlo/"+duAn.getMaDuAn());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnLo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lo = (Lo) spnLo.getSelectedItem();
                new LoadSanPham().execute("http://"+API.HOST+"/bds_project/public/SanPham");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> listKieuThanhToan = new ArrayList<>();
        listKieuThanhToan.add("Một lần");
        listKieuThanhToan.add("Trả góp");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listKieuThanhToan);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnKieuThanhToan.setAdapter(adapter);

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Save().execute("http://"+API.HOST+"/bds_project/public/HopDong");
            }
        });
    }

    private class LoadLoaiKH extends AsyncTask<String, Integer,String> {
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

            if (s!=null){
                try {
                    JSONArray array = new JSONArray(s);

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        mangLoaiKH.add(new LoaiKhachHang(
                                object.getInt("MaLoaiKH"),
                                object.getString("TenLoaiKH")
                        ));
                    }

                    ArrayAdapter<LoaiKhachHang> adapter = new ArrayAdapter(ThemHopDong.this,
                            android.R.layout.simple_spinner_item, mangLoaiKH);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnLoaiKH.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class LoadKhachHang extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            try {
                return  API.GET_URL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s!=null){
                mangKhachHang.clear();
                try {
                    JSONArray array = new JSONArray(s);

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        if (loaiKH.maLoaiKH == object.getInt("LoaiKhachHang")){
                            mangKhachHang.add(new KhachHang(
                                    object.getInt("MaKhachHang"),
                                    object.getString("TenKhachHang")
                            ));
                        }
                    }

                    ArrayAdapter<KhachHang> adapter = new ArrayAdapter<KhachHang>(ThemHopDong.this,
                            android.R.layout.simple_spinner_item, mangKhachHang);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnKhachHang.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class LoadDuAn extends AsyncTask<String, Integer, String> {
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
            if (s!=null)
            {
                try {
                    JSONArray array = new JSONArray(s);

                    for (int i = 0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        mangDuAn.add(new DuAn(
                                object.getInt("MaDuAn"),
                                object.getString("TenDuAn"),
                                object.getString("DiaChi")
                        ));
                    }
                    ArrayAdapter<DuAn> adapter = new ArrayAdapter<DuAn>(ThemHopDong.this,
                            android.R.layout.simple_spinner_item, mangDuAn);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnDuAn.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class LoadLo extends AsyncTask<String, Integer,String>{
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
            if (s!=null){
                try {
                    mangLo.clear();
                    JSONArray array= new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        mangLo.add(new Lo(
                                object.getInt("MaLo"),
                                object.getString("TenLo")
                        ));
                    }

                    ArrayAdapter<Lo> adapter = new ArrayAdapter<Lo>(ThemHopDong.this, android.R.layout.simple_spinner_item, mangLo);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnLo.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class LoadSanPham extends AsyncTask<String, Integer, String>{
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
            if (s!=null){
                try {
                    mangSanPham.clear();
                    JSONArray array = new JSONArray(s);

                    for (int i=0; i<array.length(); i++){
                        JSONObject object = array.getJSONObject(i);
                        if (lo.maLo == object.getInt("Lo")
                                && object.getString("TinhTrang").equals("ChuaBan")){
                            mangSanPham.add(new SanPham(
                                    object.getInt("MaSP"),
                                    object.getString("SoNha"),
                                    object.getString("TenLoaiSP"),
                                    object.getInt("GiaBan")
                            ));
                        }
                    }


                    ArrayAdapter<SanPham> adapter = new ArrayAdapter<SanPham>(
                            ThemHopDong.this, android.R.layout.simple_spinner_item, mangSanPham);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnSanPham.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Save extends AsyncTask<String, Integer, String>{
        KhachHang khachHang = (KhachHang) spnKhachHang.getSelectedItem();
        int maKhachHang = khachHang.getMaKhachHang();
        String maTaiKhoan = API.idUser;
        SanPham sanPham = (SanPham) spnSanPham.getSelectedItem();
        int maSanPham = sanPham.getMaSP();
        String[] ngayKy = edtNgayKy.getText().toString().split("/");
        String[] ngayBanGiao = edtNgayBanGiao.getText().toString().split("/");
        String kieuThanhToan = spnKieuThanhToan.getSelectedItem().toString();
        String datCoc = edtTienCoc.getText().toString();
        String ghiChu = edtGhiChu.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                JSONObject obj= new JSONObject();

                if (kieuThanhToan.equals("Trả góp"))
                {
                    kieuThanhToan = "TraGop";
                }else{
                    kieuThanhToan = "MotLan";
                }
                obj.put("KhachHang", maKhachHang);
                obj.put("TaiKhoan", maTaiKhoan);
                obj.put("SanPham", maSanPham);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                obj.put("NgayKy", format.format(format.parse(ngayKy[2]+"-"+ngayKy[1]+"-"+ngayKy[0])));
                obj.put("NgayBanGiao", format.format(format.parse(ngayBanGiao[2]+"-"+ngayBanGiao[1]+"-"+ngayBanGiao[0])));
                obj.put("KieuThanhToan", kieuThanhToan);
                obj.put("DatCoc", datCoc);
                obj.put("GhiChu", ghiChu);

                return API.POST_URL(url, obj);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s!= null){
                try {
                    JSONObject obj = new JSONObject(s);
                    Toast.makeText(getApplicationContext(), "Thêm hợp đồng thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ThemHopDong.this, ChiTietHopDong.class);
                    i.putExtra("id", obj.getInt("MaHopDong"));
                    i.putExtra("trangThai", obj.getString("TrangThai"));
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Chưa thêm được hợp đồng", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void datePicker(final EditText editText){
        DatePickerDialog date = new DatePickerDialog(ThemHopDong.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editText.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }
}
