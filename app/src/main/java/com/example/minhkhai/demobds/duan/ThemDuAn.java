package com.example.minhkhai.demobds.duan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaikhachhang.ThemLoaiKhachHang;

import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThemDuAn extends AppCompatActivity {

    EditText edtThemTenDuAn, edtThemDiaChi, edtThemDienTich, edtThemGiayPhep, edtThemNgayCap, edtThemSoLuongSP, edtThemMoTaDuAn;
    Calendar ngayGioHienTai = Calendar.getInstance();
    FloatingActionButton fabThemDuAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_du_an);

        /*FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.flDanhSachLoaiKH, new NavigationDrawer()).commit();*/

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fabThemDuAn = (FloatingActionButton) findViewById(R.id.fabThemDuAn);
        edtThemTenDuAn = (EditText) findViewById(R.id.edtThemTenDuAn);
        edtThemDiaChi = (EditText) findViewById(R.id.edtThemDiaChi);
        edtThemDienTich = (EditText) findViewById(R.id.edtThemDienTich);
        edtThemGiayPhep = (EditText) findViewById(R.id.edtThemGiayPhep);
        edtThemSoLuongSP = (EditText) findViewById(R.id.edtThemSoLuongSP);
        edtThemMoTaDuAn = (EditText) findViewById(R.id.edtThemMoTaDuAn);
        edtThemNgayCap = (EditText) findViewById(R.id.edtThemNgayCap);

        edtThemNgayCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        fabThemDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SaveDuAnMoi().execute("http://"+API.HOST+"/bds_project/public/DuAn");
                    }
                });
            }
        });
    }

    private class SaveDuAnMoi extends AsyncTask<String, String, String>{
        String tenDuAn = edtThemTenDuAn.getText().toString();
        String diaChi = edtThemDiaChi.getText().toString();
        String giayPhep = edtThemGiayPhep.getText().toString();
        String moTa = edtThemMoTaDuAn.getText().toString();
        String[] ngayCap = edtThemNgayCap.getText().toString().split("/");
        float dienTich = Float.parseFloat(edtThemDienTich.getText().toString());
        int soLuongSP = Integer.parseInt(edtThemSoLuongSP.getText().toString());
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenDuAn", tenDuAn);
                postDataParams.put("DiaChi", diaChi);
                postDataParams.put("TongDienTich", dienTich);
                postDataParams.put("GiayPhep", giayPhep);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                postDataParams.put("NgayCap", format.format(format.parse(ngayCap[2]+"-"+ngayCap[1]+"-"+ngayCap[0])));
                postDataParams.put("SoLuongSanPham", soLuongSP);
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
            Toast.makeText(ThemDuAn.this, s, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ThemDuAn.this, MainActivity.class);
            intent.putExtra("key", "DuAn");
            startActivity(intent);
        }
    }

    public void datePicker(){
        DatePickerDialog date = new DatePickerDialog(ThemDuAn.this,
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edtThemNgayCap.setText(dayOfMonth + "/" + (month+1) + "/" + year);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
