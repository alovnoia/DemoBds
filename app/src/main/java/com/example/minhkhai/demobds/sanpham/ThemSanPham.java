package com.example.minhkhai.demobds.sanpham;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.ThemKhachHang;
import com.example.minhkhai.demobds.lo.ChiTietLo;
import com.example.minhkhai.demobds.lo.Lo;
import com.example.minhkhai.demobds.lo.ThemLo;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.LoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;

public class ThemSanPham extends AppCompatActivity {

    private ImageView ivAnh;
    private EditText edtSo, edtDienTich, edtGiaBan, edtMoTa;
    private Spinner spLoai, spLo;
    private FloatingActionButton fabSave;
    private TextView tvDuAn;
    String tenDuAn;
    int idDuAn;

    String mediaPath;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivAnh = (ImageView) findViewById(R.id.ivThemSPAnh);
        edtSo = (EditText) findViewById(R.id.edtThemSPSoNha);
        edtDienTich = (EditText) findViewById(R.id.edtThemSPDienTich);
        edtGiaBan = (EditText) findViewById(R.id.edtThemSPGia);
        edtMoTa = (EditText) findViewById(R.id.edtThemSPMoTa);
        spLoai = (Spinner) findViewById(R.id.spThemSPLoai);
        spLo = (Spinner) findViewById(R.id.spThemSPLo);
        fabSave = (FloatingActionButton) findViewById(R.id.fabThemSPSave);
        tvDuAn = (TextView) findViewById(R.id.tvThemSPDuAn);

        Bundle extras = getIntent().getExtras();
        tenDuAn = extras.getString("TenDuAn");
        tvDuAn.setText(tenDuAn);
        idDuAn = extras.getInt("MaDuAn");

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadLoaiSP().execute("http://"+API.HOST+"/bds_project/public/LoaiSP");
                new LoadLo().execute("http://"+API.HOST+"/bds_project/public/getlo/"+idDuAn);
            }
        });

        ivAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        file = new File(mediaPath);
                        new SaveSanPham().execute("http://"+API.HOST+"/bds_project/public/SanPham");
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
                ivAnh.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(this, "Bạn chưa chọn ảnh", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
        }

    }

    private class LoadLoaiSP extends AsyncTask<String, String, String> {
        ArrayList<LoaiSP> arrLoaiKH = new ArrayList<>();
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
                final ArrayAdapter<LoaiSP> adapter = new ArrayAdapter(ThemSanPham.this,
                        android.R.layout.simple_spinner_item, arrLoaiKH);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spLoai.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class LoadLo extends AsyncTask<String, String, String>{
        ArrayList<Lo> arrLo = new ArrayList<>();
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
                            tenDuAn

                    ));
                }
                final ArrayAdapter<Lo> adapter = new ArrayAdapter(ThemSanPham.this,
                        android.R.layout.simple_spinner_item, arrLo);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spLo.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveSanPham extends AsyncTask<String, String, String>{

        LoaiSP loaiSP = (LoaiSP) spLoai.getSelectedItem();
        int maLoai = loaiSP.getMaLoaiSP();
        Lo lo = (Lo) spLo.getSelectedItem();
        int maLo = lo.maLo;
        String soNha = edtSo.getText().toString();
        Float dienTich = Float.parseFloat(edtDienTich.getText().toString());
        String giaBan = edtGiaBan.getText().toString();
        String moTa = edtMoTa.getText().toString();
        String anh = file.getName();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("SoNha", soNha);
                postDataParams.put("Anh", anh);
                postDataParams.put("LoaiSP", maLoai);
                postDataParams.put("DuAn", idDuAn);
                postDataParams.put("Lo", maLo);
                postDataParams.put("DienTich", dienTich);
                postDataParams.put("GiaBan", giaBan);
                postDataParams.put("TinhTrang", "ChuaBan");
                postDataParams.put("MoTa", moTa);

                return API.POST_URL(url, postDataParams);
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("sex", s.toString());
            JSONObject object = null;
            int idChiTiet = 0;
            if (!s.equals("0"))
            {
                try {
                    object = new JSONObject(s);
                    idChiTiet = object.getInt("MaSP");
                    API.uploadFile(file);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),
                        "Đã thêm sản phẩm số "+soNha+" trong lô "+lo.tenLo+" của dự án "+ tenDuAn, Toast.LENGTH_LONG).show();

                Intent i = new Intent(ThemSanPham.this, CapNhatSanPham.class);
                i.putExtra("MaSP", idChiTiet);
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
