package com.example.minhkhai.demobds.sanpham;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.ThemKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ThemSanPham extends AppCompatActivity {

    private ImageView ivAnh;
    private EditText edtTen, edtSo, edtDienTich, edtGiaBan, edtMoTa;
    private Spinner spLoai, spDuAn;
    private FloatingActionButton fabSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);

        ivAnh = (ImageView) findViewById(R.id.ivThemSPAnh);
        edtTen = (EditText) findViewById(R.id.edtThemSPTen);
        edtSo = (EditText) findViewById(R.id.edtThemSPTen);
        edtDienTich = (EditText) findViewById(R.id.edtThemSPTen);
        edtGiaBan = (EditText) findViewById(R.id.edtThemSPTen);
        edtMoTa = (EditText) findViewById(R.id.edtThemSPTen);
        spLoai = (Spinner) findViewById(R.id.spThemSPLoai);
        spDuAn = (Spinner) findViewById(R.id.spThemSPDuAn);
        fabSave = (FloatingActionButton) findViewById(R.id.fabThemSPSave);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLoaiKH().execute("http://"+API.HOST+"/bds_project/public/LoaiKhachHang");
            }
        });
    }

    private class LoadLoaiKH extends AsyncTask<String, String, String> {
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
                final ArrayAdapter<LoaiKhachHang> adapter = new ArrayAdapter(ThemSanPham.this,
                        android.R.layout.simple_spinner_item, arrLoaiKH);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spLoai.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
