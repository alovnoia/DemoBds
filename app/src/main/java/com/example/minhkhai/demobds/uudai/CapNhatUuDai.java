package com.example.minhkhai.demobds.uudai;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.CapNhatDuAn;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.sanpham.CapNhatSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CapNhatUuDai extends AppCompatActivity {
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
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_uu_dai);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("id");

        edtTen = (EditText) findViewById(R.id.edtTenUuDai);
        edtBatDau = (EditText) findViewById(R.id.edtBatDauUuDai);
        edtKetThuc = (EditText) findViewById(R.id.edtKetThucUuDai);
        edtTienTru = (EditText) findViewById(R.id.edtTruTien);
        fabSave = (FloatingActionButton) findViewById(R.id.fabThemUuDai);
        //btnXoaUuDai = (Button) findViewById(R.id.btnXoaUuDai);
        lvChonDuAn = (ListView) findViewById(R.id.lvChonDuAn);
        lvChonLoaiKH = (ListView) findViewById(R.id.lvChonLoaiKH);

        if (API.quyen.equals("NVBH")) {
            fabSave.setVisibility(View.GONE);
        }

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

        lvChonDuAn.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvChonLoaiKH.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        TaoTab();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadChiTietUuDai().execute("http://"+ API.HOST+"/bds_project/public/UuDai/"+id);
                new LoadDuAn().execute("http://"+ API.HOST+"/bds_project/public/DuAn");
                new LoadLoaiKH().execute("http://"+ API.HOST+"/bds_project/public/LoaiKhachHang");
            }
        });
        lvChonDuAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DuAn duAn = (DuAn) lvChonDuAn.getItemAtPosition(position);
                lvChonDuAn.setItemChecked(position, !duAn.getCheck());
                duAn.setCheck(!duAn.getCheck());
                chonDuAnAdapter.notifyDataSetChanged();
            }
        });
        lvChonLoaiKH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoaiKhachHang loaiKH = (LoaiKhachHang) lvChonLoaiKH.getItemAtPosition(position);
                lvChonLoaiKH.setItemChecked(position, !loaiKH.isCheck());
                loaiKH.setCheck(!loaiKH.isCheck());
                chonLoaiKHAdapter.notifyDataSetChanged();
            }
        });

        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonDuAn = new JSONArray();
                jsonLoaiKH = new JSONArray();
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
                        new SaveUuDai().execute("http://"+ API.HOST+"/bds_project/public/UuDai/"+id);
                        API.change = true;
                    }
                });
            }
        });

        /*btnXoaUuDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaUuDai().execute("http://"+ API.HOST+"/bds_project/public/UuDai/"+id);
                    }
                });
            }
        });*/
    }
    private class LoadChiTietUuDai extends AsyncTask<String, String, String> {

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

                edtTen.setText(obj.getString("TenUuDai"));
                edtBatDau.setText(API.convertDate(obj.getString("NgayBatDau")));
                edtKetThuc.setText(API.convertDate(obj.getString("NgayKetThuc")));
                edtTienTru.setText(obj.getLong("SoTien")+"");
                jsonDuAn = new JSONArray(obj.getString("DuAn"));
                jsonLoaiKH = new JSONArray(obj.getString("LoaiKhachHang"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class XoaUuDai extends AsyncTask<String, String, String>{

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
            Toast.makeText(CapNhatUuDai.this, "Đã xóa ưu đãi có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatUuDai.this, MainActivity.class);
            intent.putExtra("key", "uuDai");
            startActivity(intent);
        }
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
            Toast.makeText(CapNhatUuDai.this, "Đã cập nhật ưu đãi "+ten, Toast.LENGTH_SHORT).show();
        }
    }

    private class LoadDuAn extends AsyncTask<String, String, String> {

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
                    boolean check = false;
                    for (int j = 0; j < jsonDuAn.length(); j++) {
                        if (object.getInt("MaDuAn") == jsonDuAn.getInt(j)){
                            check = true;
                            break;
                        }
                    }
                    mangDuAn.add(new DuAn(
                            object.getInt("MaDuAn"),
                            object.getString("TenDuAn"),
                            check
                    ));
                }
                chonDuAnAdapter = new ArrayAdapter<DuAn>(CapNhatUuDai.this,
                        android.R.layout.simple_list_item_multiple_choice, mangDuAn);
                lvChonDuAn.setAdapter(chonDuAnAdapter);
                for (int i = 0; i < lvChonDuAn.getCount(); i++) {
                    DuAn duAn = (DuAn) lvChonDuAn.getItemAtPosition(i);
                    lvChonDuAn.setItemChecked(i, duAn.getCheck());
                    chonDuAnAdapter.notifyDataSetChanged();
                }

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
                    boolean check = false;
                    JSONObject object = array.getJSONObject(i);
                    for (int j = 0; j < jsonLoaiKH.length(); j++) {
                        if (object.getInt("MaLoaiKH") == jsonLoaiKH.getInt(j)){
                            check = true;
                            break;
                        }
                    }
                    arrLoaiKH.add(new LoaiKhachHang(
                            object.getInt("MaLoaiKH"),
                            object.getString("TenLoaiKH"),
                            check

                    ));
                }
                chonLoaiKHAdapter = new ArrayAdapter<LoaiKhachHang>(CapNhatUuDai.this,
                        android.R.layout.simple_list_item_multiple_choice, arrLoaiKH);
                lvChonLoaiKH.setAdapter(chonLoaiKHAdapter);
                for (int i = 0; i < lvChonLoaiKH.getCount(); i++) {
                    LoaiKhachHang loaiKhachHang = (LoaiKhachHang) lvChonLoaiKH.getItemAtPosition(i);
                    lvChonLoaiKH.setItemChecked(i, loaiKhachHang.isCheck());
                    chonLoaiKHAdapter.notifyDataSetChanged();
                }
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
        spec.setIndicator("Ưu đãi "+id);
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
        DatePickerDialog date = new DatePickerDialog(CapNhatUuDai.this,
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
                Intent i = new Intent(CapNhatUuDai.this, MainActivity.class);
                i.putExtra("key", "uuDai");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.delete) {
            // Show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(CapNhatUuDai.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa dự án này?");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new XoaUuDai().execute("http://"+ API.HOST+"/bds_project/public/UuDai/"+id);
                        }
                    });
                }
            });
            builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (API.quyen.equals("NVQL")) {
            CapNhatUuDai.this.getMenuInflater().inflate(R.menu.menu, menu);
        }
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(CapNhatUuDai.this, MainActivity.class);
                i.putExtra("key", "uuDai");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }*/
}
