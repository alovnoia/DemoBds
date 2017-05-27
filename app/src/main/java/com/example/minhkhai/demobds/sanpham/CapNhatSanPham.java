package com.example.minhkhai.demobds.sanpham;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.CapNhatDuAn;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.example.minhkhai.demobds.lo.ChiTietLo;
import com.example.minhkhai.demobds.lo.DanhSachLo;
import com.example.minhkhai.demobds.lo.Lo;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.LoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CapNhatSanPham extends AppCompatActivity {

    private ImageView ivAnh;
    private EditText edtSo, edtDienTich, edtGiaBan, edtMoTa;
    private Spinner spLoai, spLo, spDuAn;
    private FloatingActionButton fabSave;
    private TextView tvMaSP;
    Button btnXoa;
    int id, idDuAn;
    ArrayList<LoaiSP> arrLoaiKH = new ArrayList<>();
    ArrayAdapter<LoaiSP> adapterLoaiSP;
    ArrayList<DuAn> mangDuAn = new ArrayList<>();
    ArrayAdapter<DuAn> adapterDuAn;
    ArrayList<Lo> arrLo = new ArrayList<>();
    ArrayAdapter<Lo> adapterLo;
    public JSONObject sanPham = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_san_pham);

        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.add(R.id.flLo, new FragmentCapNhatLo()).commit();*/
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("MaSP");
        idDuAn = extras.getInt("MaDuAn");

        ivAnh = (ImageView) findViewById(R.id.ivCNSPAnh);
        edtSo = (EditText) findViewById(R.id.edtCNSoNha);
        edtDienTich = (EditText) findViewById(R.id.edtCNDienTich);
        edtGiaBan = (EditText) findViewById(R.id.edtCNGia);
        edtMoTa = (EditText) findViewById(R.id.edtCNMoTa);
        spLoai = (Spinner) findViewById(R.id.spCNLoai);
        spDuAn = (Spinner) findViewById(R.id.spCNDuAn);
        spLo = (Spinner) findViewById(R.id.spCNLo);
        fabSave = (FloatingActionButton) findViewById(R.id.fabCNSave);
        btnXoa = (Button) findViewById(R.id.btnXoaSP);
        tvMaSP = (TextView) findViewById(R.id.tvCNMaSP);

        if (API.quyen.equals("NVBH")) {
            btnXoa.setVisibility(View.GONE);
            fabSave.setVisibility(View.GONE);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLoaiSP().execute("http://"+API.HOST+"/bds_project/public/LoaiSP");
                new LoadDuAn().execute("http://"+API.HOST+"/bds_project/public/DuAn");
                new LoadLo().execute("http://"+API.HOST+"/bds_project/public/getlo/"+idDuAn);
                new LoadChiTietSP().execute("http://"+API.HOST+"/bds_project/public/SanPham/"+id);
            }
        });

        spDuAn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DuAn duAn = (DuAn) spDuAn.getSelectedItem();
                arrLo.clear();
                spLo.setAdapter(null);
                new LoadLo().execute("http://"+ API.HOST+"/bds_project/public/getlo/"+duAn.getMaDuAn());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SaveCapNhat().execute("http://"+ API.HOST+"/bds_project/public/SanPham/"+id);
                API.change = true;
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaSP().execute("http://"+ API.HOST+"/bds_project/public/SanPham/"+id);
                    }
                });
            }
        });
    }

    private class LoadLoaiSP extends AsyncTask<String, String, String> {
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
                    arrLoaiKH.add(new LoaiSP(
                            object.getInt("MaLoaiSP"),
                            object.getString("TenLoaiSP")

                    ));
                    //arrLoaiKH.add(String.valueOf(object.getInt("MaLoaiKH")) + ". " + object.getString("TenLoaiKH"));
                }
                adapterLoaiSP = new ArrayAdapter(CapNhatSanPham.this,
                        android.R.layout.simple_spinner_item, arrLoaiKH);
                adapterLoaiSP.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spLoai.setAdapter(adapterLoaiSP);
            } catch (JSONException e) {
                e.printStackTrace();
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

            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    mangDuAn.add(new DuAn(
                            object.getInt("MaDuAn"),
                            object.getString("TenDuAn"),
                            object.getString("DiaChi")
                    ));
                    adapterDuAn = new ArrayAdapter(CapNhatSanPham.this,
                            android.R.layout.simple_spinner_item, mangDuAn);
                    adapterDuAn.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                    spDuAn.setAdapter(adapterDuAn);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class LoadLo extends AsyncTask<String, String, String>{
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
                    arrLo.add(new Lo(
                            object.getInt("MaLo"),
                            object.getString("TenLo"),
                            object.getInt("DuAn")

                    ));
                }
                adapterLo = new ArrayAdapter(CapNhatSanPham.this,
                        android.R.layout.simple_spinner_item, arrLo);
                adapterLo.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spLo.setAdapter(adapterLo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadChiTietSP extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String a = null;
            try {
                a = API.GET_URL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject obj = new JSONObject(s);
                try {
                    edtSo.setText(obj.getString("SoNha"));
                    edtDienTich.setText(obj.getInt("DienTich") + "");
                    edtGiaBan.setText(obj.getInt("GiaBan") + "");
                    edtMoTa.setText(obj.getString("MoTa"));
                    tvMaSP.setText("Sản phẩm mã " + obj.getInt("MaSP"));
                    for (int i = 0; i < adapterDuAn.getCount(); i++){
                        if (obj.getInt("DuAn") == adapterDuAn.getItem(i).getMaDuAn()){
                            spDuAn.setSelection(i);
                            break;
                        }
                    }
                    for (int i = 0; i < adapterLoaiSP.getCount(); i++){
                        if (obj.getInt("LoaiSP") == adapterLoaiSP.getItem(i).getMaLoaiSP()){
                            spLoai.setSelection(i);
                            break;
                        }
                    }
                    for (int i = 0; i < adapterLo.getCount(); i++){
                        if (obj.getInt("Lo") == adapterLo.getItem(i).maLo){
                            spLo.setSelection(i);
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //new LoadLo().execute("http://"+API.HOST+"/bds_project/public/Lo");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveCapNhat extends AsyncTask<String, String, String>{

        LoaiSP loaiSP = (LoaiSP) spLoai.getSelectedItem();
        int maLoai = loaiSP.getMaLoaiSP();
        DuAn thuocduAn = (DuAn) spDuAn.getSelectedItem();
        int maDA = thuocduAn.getMaDuAn();
        Lo lo = (Lo) spLo.getSelectedItem();
        int maLo = lo.maLo;
        String soNha = edtSo.getText().toString();
        Float dienTich = Float.parseFloat(edtDienTich.getText().toString());
        int giaBan = Integer.parseInt(edtGiaBan.getText().toString());
        String moTa = edtMoTa.getText().toString();
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("SoNha", soNha);
                postDataParams.put("Anh", "s");
                postDataParams.put("LoaiSP", maLoai);
                postDataParams.put("DuAn", maDA);
                postDataParams.put("Lo", maLo);
                postDataParams.put("DienTich", dienTich);
                postDataParams.put("GiaBan", giaBan);
                postDataParams.put("TinhTrang", "ChuaBan");
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
            Toast.makeText(CapNhatSanPham.this, "Đã cập nhật sản phẩm "+id, Toast.LENGTH_SHORT).show();
        }
    }

    private class XoaSP extends AsyncTask<String, String, String>{

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
            Toast.makeText(CapNhatSanPham.this, "Đã xóa sản phẩm có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatSanPham.this, MainActivity.class);
            intent.putExtra("key", "SanPham");
            startActivity(intent);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(CapNhatSanPham.this, MainActivity.class);
                i.putExtra("key", "SanPham");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
