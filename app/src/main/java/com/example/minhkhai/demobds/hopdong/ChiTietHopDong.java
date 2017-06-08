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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.example.minhkhai.demobds.khachhang.KhachHang;
import com.example.minhkhai.demobds.lo.ChiTietLo;
import com.example.minhkhai.demobds.lo.Lo;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.sanpham.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChiTietHopDong extends AppCompatActivity {

    TextView tvChiTiet;
    Spinner spnLoaiKH, spnKhachHang, spnDuAn, spnLo, spnSanPham, spnKieuThanhToan;
    EditText edtNgayKy, edtNgayBanGiao, edtTienCoc, edtGhiChu;
    Button btnXoa, btnDuyet;
    FloatingActionButton fab_Save;

    ArrayList<LoaiKhachHang> mangLoaiKH = new ArrayList<LoaiKhachHang>();
    ArrayList<KhachHang> mangKhachHang = new ArrayList<KhachHang>();
    ArrayList<DuAn> mangDuAn = new ArrayList<DuAn>();
    ArrayList<Lo> mangLo = new ArrayList<Lo>();
    ArrayList<SanPham> mangSanPham = new ArrayList<SanPham>();

    ArrayAdapter<LoaiKhachHang> adapterLoaiKhachHang;
    ArrayAdapter<DuAn> adapterDuAn;
    ArrayAdapter<Lo> adapterLo;
    ArrayAdapter<String> mangKieuThanhToan;

    LoaiKhachHang loaiKH;
    DuAn duAn;
    Lo lo;
    JSONObject objHopDong=null;
    String tThai = "";

    int id;

    Calendar ngayGioHienTai = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hop_dong);

        AnhXa();

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

        List<String> listKieuThanhToan = new ArrayList<>();
        listKieuThanhToan.add("Một lần");
        listKieuThanhToan.add("Trả góp");

        mangKieuThanhToan = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listKieuThanhToan);
        mangKieuThanhToan.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnKieuThanhToan.setAdapter(mangKieuThanhToan);


        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");
        tvChiTiet.setText("Chi tiết hợp đồng: "+id);
        tThai = extras.getString("trangThai");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadChiTiet().execute("http://"+ API.HOST+"/bds_project/public/HopDong/"+id);
                new LoadLoaiKH().execute("http://"+ API.HOST+"/bds_project/public/LoaiKhachHang");
                new LoadDuAn().execute("http://"+ API.HOST+"/bds_project/public/DuAn");
            }
        });

        if (API.quyen.equals("NVBH")) {
            btnDuyet.setVisibility(View.GONE);
        }

        if (tThai.equals("DaDuyet")){
            fab_Save.setVisibility(View.GONE);
        }
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

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Xoa().execute("http://"+API.HOST+"/bds_project/public/HopDong/"+id);
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Save().execute("http://"+API.HOST+"/bds_project/public/HopDong/"+id);
            }
        });

        btnDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tThai.equals("ChuaDuyet") ){
                    new Duyet().execute("http://"+API.HOST+"/bds_project/public/duyet/"+id);
                }
                else {
                    new HuyDuyet().execute("http://"+API.HOST+"/bds_project/public/huyduyet/"+id);
                }
                Intent i =  new Intent(ChiTietHopDong.this, MainActivity.class);
                i.putExtra("key", "HopDong");
                startActivity(i);
            }
        });
    }

    private void    AnhXa(){
        tvChiTiet = (TextView) findViewById(R.id.tvChiTietHopDong);
        spnLoaiKH = (Spinner) findViewById(R.id.spnLoaiKHChiTietHopDong);
        spnKhachHang = (Spinner) findViewById(R.id.spnKhachHangChiTietHopDong);
        spnDuAn = (Spinner) findViewById(R.id.spnDuAnChiTietHopDong);
        spnLo = (Spinner) findViewById(R.id.spnLoChiTietHopDong);
        spnSanPham = (Spinner) findViewById(R.id.spnSanPhamChiTietHopDong);
        edtNgayKy = (EditText) findViewById(R.id.edtNgayKyChiTietHopDong);
        edtNgayBanGiao = (EditText) findViewById(R.id.edtNgayBanGiaoChiTietHopDong);
        spnKieuThanhToan = (Spinner) findViewById(R.id.spnKieuThanhToanChiTietHopDong);
        edtTienCoc = (EditText) findViewById(R.id.edtCocChiTietHopDong);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChuChiTietHopDong);
        btnDuyet = (Button) findViewById(R.id.btnDuyetHopDong);
        btnXoa = (Button) findViewById(R.id.btnXoaHopDong);
        fab_Save = (FloatingActionButton) findViewById(R.id.fab_SaveChiTietHopDong);
    }
    public void datePicker(final EditText editText){
        DatePickerDialog date = new DatePickerDialog(ChiTietHopDong.this,
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

    //ok
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

                    ArrayAdapter<LoaiKhachHang> adapterLoaiKH = new ArrayAdapter(ChiTietHopDong.this,
                            android.R.layout.simple_spinner_item, mangLoaiKH);
                    adapterLoaiKH.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnLoaiKH.setAdapter(adapterLoaiKH);

                    for (int j = 0; j < adapterLoaiKH.getCount(); j++){
                        if (objHopDong.getInt("MaLoaiKH") == adapterLoaiKH.getItem(j).getMaLoaiKH()){
                            spnLoaiKH.setSelection(j);
                            break;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //ok
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

                        if (loaiKH.maLoaiKH == object.getInt("LoaiKhachHang") || loaiKH==null){
                            mangKhachHang.add(new KhachHang(
                                    object.getInt("MaKhachHang"),
                                    object.getString("TenKhachHang")
                            ));
                        }
                    }

                    ArrayAdapter<KhachHang> adapter = new ArrayAdapter<KhachHang>(ChiTietHopDong.this,
                            android.R.layout.simple_spinner_item, mangKhachHang);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnKhachHang.setAdapter(adapter);

                    for (int i=0; i<adapter.getCount(); i++){
                        if (objHopDong.getInt("KhachHang")==adapter.getItem(i).getMaKhachHang()){
                            spnKhachHang.setSelection(i);
                            break;
                        }
                    }

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //ok
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
                    adapterDuAn = new ArrayAdapter<DuAn>(ChiTietHopDong.this,
                            android.R.layout.simple_spinner_item, mangDuAn);
                    adapterDuAn.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnDuAn.setAdapter(adapterDuAn);

                    for (int i= 0; i<adapterDuAn.getCount(); i++){
                        if (adapterDuAn.getItem(i).getMaDuAn()==objHopDong.getInt("MaDuAn")){
                            spnDuAn.setSelection(i);
                            break;
                        }
                    }
                    adapterDuAn.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //ok
    private class LoadLo extends AsyncTask<String, Integer,String>{
        @Override
        protected String doInBackground(String... params) {
            try {
                return API.GET_URL(params[0]);
            } catch (Exception e) {
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

                    ArrayAdapter<Lo> adapter = new ArrayAdapter<Lo>(ChiTietHopDong.this, android.R.layout.simple_spinner_item, mangLo);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnLo.setAdapter(adapter);

                    for (int j =0; j<adapter.getCount(); j++){
                        if (adapter.getItem(j).maLo == objHopDong.getInt("MaLo")){
                            spnLo.setSelection(j);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    //ok
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
                        if ((lo.maLo == object.getInt("Lo")|| lo==null)
                                && object.getString("TinhTrang").equals("ChuaBan")){
                            mangSanPham.add(new SanPham(
                                    object.getInt("MaSP"),
                                    object.getString("SoNha"),
                                    object.getString("TenLoaiSP"),
                                    object.getString("GiaBan")
                            ));
                        }
                    }


                    ArrayAdapter<SanPham> adapter = new ArrayAdapter<SanPham>(
                            ChiTietHopDong.this, android.R.layout.simple_spinner_item, mangSanPham);
                    adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spnSanPham.setAdapter(adapter);

                    for (int i= 0; i<adapter.getCount(); i++){
                        if  (adapter.getItem(i).getMaSP()== objHopDong.getInt("SanPham")){
                            spnSanPham.setSelection(i);
                            break;
                        }
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Load chi tiết ok
    public class LoadChiTiet extends AsyncTask<String, Integer, String>{

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
                    objHopDong = new JSONObject(s);

                    String kieu = objHopDong.getString("KieuThanhToan");
                    for (int i = 0; i<mangKieuThanhToan.getCount(); i++){
                        if (kieu.equals("TraGop") && mangKieuThanhToan.getItem(i).equals("Trả góp")){
                            spnKieuThanhToan.setSelection(i);
                            break;
                        } else if (kieu.equals("MotLan") && mangKieuThanhToan.getItem(i).equals("Một lần")){
                            spnKieuThanhToan.setSelection(i);
                            break;
                        }
                    }

                    String[] ngayK = objHopDong.get("NgayKy").toString().split("-");
                    edtNgayKy.setText(ngayK[2]+"/"+ngayK[1]+"/"+ngayK[0]);

                    String[] ngayBG = objHopDong.get("NgayKy").toString().split("-");
                    edtNgayBanGiao.setText(ngayBG[2]+"/"+ngayBG[1]+"/"+ngayBG[0]);

                    edtTienCoc.setText(objHopDong.getString("DatCoc"));
                    edtGhiChu.setText(objHopDong.getString("GhiChu"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //Sửa chưa làm
    public class Save extends AsyncTask<String, Integer, String>{
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
        JSONObject object;
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]); // here is your URL path

                object = new JSONObject();
                object.put("KhachHang", maKhachHang);
                object.put("TaiKhoan", maTaiKhoan);
                object.put("SanPham", maSanPham);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                object.put("NgayKy", format.format(format.parse(ngayKy[2]+"-"+ngayKy[1]+"-"+ngayKy[0])));
                object.put("NgayBanGiao", format.format(format.parse(ngayBanGiao[2]+"-"+ngayBanGiao[1]+"-"+ngayBanGiao[0])));
                if (kieuThanhToan.equals("Trả góp"))
                {
                    kieuThanhToan = "TraGop";
                }else{
                    kieuThanhToan = "MotLan";
                }
                object.put("KieuThanhToan", kieuThanhToan);
                object.put("DatCoc", datCoc);
                object.put("GhiChu", ghiChu);
                object.put("_method", "PUT");

                return API.POST_URL(url, object);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null) {
                Toast.makeText(ChiTietHopDong.this, "Đã cập nhật hợp đồng " + id, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Xóa ok
    public class Xoa extends AsyncTask<String, Integer, String>{
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
            Toast.makeText(getApplicationContext(), "Bạn vừa xóa hợp đồng "+ id, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(ChiTietHopDong.this, MainActivity.class);
            i.putExtra("key", "HopDong");
            startActivity(i);
        }
    }

    //Duyệt ok
    public class Duyet extends AsyncTask<String, Integer, String>{
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
            Toast.makeText(getApplicationContext(), "Đã duyệt hợp đồng" +id, Toast.LENGTH_SHORT).show();
        }
    }
    //Hủy Duyệt ok
    public class HuyDuyet extends AsyncTask<String, Integer, String>{
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
            Toast.makeText(getApplicationContext(), "Đã hủy duyệt hợp đồng" +id, Toast.LENGTH_SHORT).show();
        }
    }


}
