package com.example.minhkhai.demobds.uudai;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.CapNhatDuAn;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.duan.ThemDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHangAdapter;
import com.example.minhkhai.demobds.sanpham.ChonDuAn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThemUuDai extends AppCompatActivity {

    EditText edtTen, edtBatDau, edtKetThuc, edtTienTru;
    FloatingActionButton fabSave;
    Button btnXoaUuDai;
    ListView lvChonDuAn, lvChonLoaiKH;
    ArrayList<DuAn> mangDuAn = new ArrayList<>();
    ArrayAdapter<DuAn> chonDuAnAdapter = null;
    ArrayList<LoaiKhachHang> arrLoaiKH = new ArrayList<>();
    ArrayAdapter<LoaiKhachHang> chonLoaiKHAdapter = null;
    Calendar ngayGioHienTai = Calendar.getInstance();
    JSONArray jsonDuAn = new JSONArray(), jsonLoaiKH = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_uu_dai);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        edtTen = (EditText) findViewById(R.id.edtTenUuDai);
        edtBatDau = (EditText) findViewById(R.id.edtBatDauUuDai);
        edtKetThuc = (EditText) findViewById(R.id.edtKetThucUuDai);
        edtTienTru = (EditText) findViewById(R.id.edtTruTien);
        fabSave = (FloatingActionButton) findViewById(R.id.fabThemUuDai);
        btnXoaUuDai = (Button) findViewById(R.id.btnXoaUuDai);
        lvChonDuAn = (ListView) findViewById(R.id.lvChonDuAn);
        lvChonLoaiKH = (ListView) findViewById(R.id.lvChonLoaiKH);

        btnXoaUuDai.setVisibility(View.GONE);

        edtBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker("BatDau");
            }
        });
        edtKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker("KetThuc");
            }
        });

        TaoTab();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDuAn().execute("http://"+ API.HOST+"/bds_project/public/DuAn");
                new LoadLoaiKH().execute("http://"+ API.HOST+"/bds_project/public/LoaiKhachHang");
            }
        });
        lvChonDuAn.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChonDuAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DuAn duAn = (DuAn) lvChonDuAn.getItemAtPosition(position);
                lvChonDuAn.setItemChecked(position, !duAn.getCheck());
                duAn.setCheck(!duAn.getCheck());
                //Toast.makeText(ThemUuDai.this, duAn.getCheck().toString(), Toast.LENGTH_SHORT).show();
                chonDuAnAdapter.notifyDataSetChanged();
            }
        });
        lvChonLoaiKH.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChonLoaiKH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiKhachHang loaiKH = (LoaiKhachHang) lvChonLoaiKH.getItemAtPosition(position);
                lvChonLoaiKH.setItemChecked(position, !loaiKH.isCheck());
                loaiKH.setCheck(!loaiKH.isCheck());
                //Toast.makeText(ThemUuDai.this, loaiKH.isCheck().toString(), Toast.LENGTH_SHORT).show();
                chonLoaiKHAdapter.notifyDataSetChanged();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mangDuAn.size(); i++) {
                    DuAn duAn = mangDuAn.get(i);
                    if (duAn.getCheck()) {
                        jsonDuAn.put(duAn.getMaDuAn()+"");
                    }
                }
                for (int i = 0; i < arrLoaiKH.size(); i++) {
                    LoaiKhachHang loaiKhachHang = arrLoaiKH.get(i);
                    if (loaiKhachHang.isCheck()) {
                        jsonLoaiKH.put(loaiKhachHang.getMaLoaiKH()+"");
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SaveUuDai().execute("http://"+ API.HOST+"/bds_project/public/UuDai");
                        API.change = true;
                    }
                });
            }
        });
    }

    private class SaveUuDai extends AsyncTask<String, String, String>{
        String ten = edtTen.getText().toString();
        String[] batDau = edtBatDau.getText().toString().split("/");
        String[] ketThuc = edtKetThuc.getText().toString().split("/");
        Long soTien = Long.parseLong(edtTienTru.getText().toString());
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenUuDai", ten);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                postDataParams.put("NgayBatDau", format.format(format.parse(batDau[2]+"-"+batDau[1]+"-"+batDau[0])));
                postDataParams.put("NgayKetThuc", format.format(format.parse(ketThuc[2]+"-"+ketThuc[1]+"-"+ketThuc[0])));
                postDataParams.put("SoTien", soTien);
                postDataParams.put("DuAn", jsonDuAn.toString());
                postDataParams.put("LoaiKhachHang", jsonLoaiKH.toString());

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
            JSONObject object = null;

            if (!s.equals("0"))
            {
                try {
                    object = new JSONObject(s);
                    idChiTiet = object.getInt("MaUuDai");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(),
                        "Đã thêm ưu đãi "+ten, Toast.LENGTH_LONG).show();

                Intent i = new Intent(ThemUuDai.this, CapNhatUuDai.class);
                i.putExtra("id", idChiTiet);
                startActivity(i);
            }
        }
    }

    private class LoadDuAn extends AsyncTask<String, String, String>{

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
                            false
                    ));
                }
                chonDuAnAdapter = new ArrayAdapter<DuAn>(ThemUuDai.this,
                        android.R.layout.simple_list_item_multiple_choice, mangDuAn);
                lvChonDuAn.setAdapter(chonDuAnAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class LoadLoaiKH extends AsyncTask<String, String, String> {

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
            //test.append("Chan");
            try {
                JSONArray array= new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    arrLoaiKH.add(new LoaiKhachHang(
                            object.getInt("MaLoaiKH"),
                            object.getString("TenLoaiKH"),
                            false

                    ));
                }
                chonLoaiKHAdapter = new ArrayAdapter<LoaiKhachHang>(ThemUuDai.this,
                        android.R.layout.simple_list_item_multiple_choice, arrLoaiKH);
                lvChonLoaiKH.setAdapter(chonLoaiKHAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void TaoTab(){
        TabHost tabHost = (TabHost) findViewById(R.id.tabhostUuDai);
        tabHost.setup();
        TabHost.TabSpec spec;
        //tao tab 1
        spec = tabHost.newTabSpec("t1");
        spec.setContent(R.id.tabThongTinUuDai);
        spec.setIndicator("Thông tin");
        tabHost.addTab(spec);
        //tao tab 2
        spec = tabHost.newTabSpec("t2");
        spec.setContent(R.id.tabChonDuAn);
        spec.setIndicator("Dự án");
        tabHost.addTab(spec);
        //tao tab 3
        spec = tabHost.newTabSpec("t3");
        spec.setContent(R.id.tabChonLoaiKH);
        spec.setIndicator("Loại khách");
        tabHost.addTab(spec);
    }

    public void datePicker(final String loai){
        DatePickerDialog date = new DatePickerDialog(ThemUuDai.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if (loai.equals("BatDau")) {
                            edtBatDau.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        } else {
                            edtKetThuc.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                        }
                    }
                },
                //Định dạng ngày tháng năm
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(ThemUuDai.this, MainActivity.class);
                i.putExtra("key", "uuDai");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
