package com.example.minhkhai.demobds.duan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CapNhatDuAn extends AppCompatActivity {

    EditText edtCapNhatTenDuAn, edtCapNhatDiaChi, edtCapNhatDienTich, edtCapNhatGiayPhep,
            edtCapNhatNgayCap, edtCapNhatSoLuongSP, edtCapNhatMoTaDuAn;
    TextView tvCapNhatDuAn;
    int id = 0;
    Calendar ngayGioHienTai = Calendar.getInstance();
    FloatingActionButton fabCapNhatDuAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_du_an);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        fabCapNhatDuAn = (FloatingActionButton) findViewById(R.id.fabCapNhatDuAn);
        edtCapNhatTenDuAn = (EditText) findViewById(R.id.edtCapNhatTenDuAn);
        edtCapNhatDiaChi = (EditText) findViewById(R.id.edtCapNhatDiaChi);
        edtCapNhatDienTich = (EditText) findViewById(R.id.edtCapNhatDienTich);
        edtCapNhatGiayPhep = (EditText) findViewById(R.id.edtCapNhatGiayPhep);
        edtCapNhatSoLuongSP = (EditText) findViewById(R.id.edtCapNhatSoLuongSP);
        edtCapNhatMoTaDuAn = (EditText) findViewById(R.id.edtCapNhatMoTaDuAn);
        edtCapNhatNgayCap = (EditText) findViewById(R.id.edtCapNhatNgayCap);
        tvCapNhatDuAn = (TextView) findViewById(R.id.tvCapNhatDuAn);
        tvCapNhatDuAn.setText("Cập nhật dự án "+id);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadChiTietDuAn().execute("http://"+API.HOST+"/bds_project/public/DuAn/"+id);
            }
        });

        edtCapNhatNgayCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        fabCapNhatDuAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new SaveEditDuAn().execute("http://"+API.HOST+"/bds_project/public/DuAn/"+id);
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(CapNhatDuAn.this, DanhSachDuAn.class);
        startActivity(intent);
    }

    private class LoadChiTietDuAn extends AsyncTask<String, String, String>{

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

                edtCapNhatTenDuAn.setText(obj.getString("TenDuAn"));
                edtCapNhatDiaChi.setText(obj.getString("DiaChi"));
                edtCapNhatDienTich.setText(obj.getString("TongDienTich"));
                edtCapNhatGiayPhep.setText(obj.getString("GiayPhep"));
                String[] ngay = obj.get("NgayCap").toString().split("-");
                edtCapNhatNgayCap.setText(ngay[2]+"/"+ngay[1]+"/"+ngay[0]);
                edtCapNhatSoLuongSP.setText(obj.getString("SoLuongSanPham"));
                edtCapNhatMoTaDuAn.setText(obj.getString("MoTa"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class SaveEditDuAn extends AsyncTask<String, String, String>{
        String tenDuAn = edtCapNhatTenDuAn.getText().toString();
        String diaChi = edtCapNhatDiaChi.getText().toString();
        String giayPhep = edtCapNhatGiayPhep.getText().toString();
        String moTa = edtCapNhatMoTaDuAn.getText().toString();
        String[] ngayCap = edtCapNhatNgayCap.getText().toString().split("/");
        float dienTich = Float.parseFloat(edtCapNhatDienTich.getText().toString());
        int soLuongSP = Integer.parseInt(edtCapNhatSoLuongSP.getText().toString());
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

            Toast.makeText(CapNhatDuAn.this, "Đã cập nhật dự án "+id, Toast.LENGTH_SHORT).show();
        }
    }

    public void datePicker(){
        DatePickerDialog date = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtCapNhatNgayCap.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                //Định dạng ngày tháng năm
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }

}
