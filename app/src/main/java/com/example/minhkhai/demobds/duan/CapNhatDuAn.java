package com.example.minhkhai.demobds.duan;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.CapNhatKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.ThemLoaiKhachHang;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class CapNhatDuAn extends AppCompatActivity {

    EditText edtCapNhatTenDuAn, edtCapNhatDiaChi, edtCapNhatDienTich, edtCapNhatGiayPhep,
            edtCapNhatNgayCap, edtCapNhatSoLuongSP, edtCapNhatMoTaDuAn;
    TextView tvCapNhatDuAn;
    String lstUuDai = null;
    int id = 0;
    Calendar ngayGioHienTai = Calendar.getInstance();
    FloatingActionButton fabCapNhatDuAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_du_an);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        fabCapNhatDuAn = (FloatingActionButton) findViewById(R.id.fabCNDuAn);
        edtCapNhatTenDuAn = (EditText) findViewById(R.id.edtCNTenDuAn);
        edtCapNhatDiaChi = (EditText) findViewById(R.id.edtCNDiaChi);
        edtCapNhatDienTich = (EditText) findViewById(R.id.edtCNDienTich);
        edtCapNhatGiayPhep = (EditText) findViewById(R.id.edtCNGiayPhep);
        //edtCapNhatSoLuongSP = (EditText) findViewById(R.id.edtCNSoLuongSP);
        edtCapNhatMoTaDuAn = (EditText) findViewById(R.id.edtCNMoTaDuAn);
        edtCapNhatNgayCap = (EditText) findViewById(R.id.edtCNNgayCap);
        //btnXoaDuAn = (Button) findViewById(R.id.btnXoaDuAn);
        tvCapNhatDuAn = (TextView) findViewById(R.id.tvCapNhatDuAn);

        if (API.quyen.equals("NVBH")) {
            //btnXoaDuAn.setVisibility(View.GONE);
            fabCapNhatDuAn.setVisibility(View.GONE);
        }

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
                        API.change = true;
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
                tvCapNhatDuAn.setText("Cập nhật dự án "+id+" - " + obj.getInt("SoLuongSanPham")+" sản phẩm");
                lstUuDai = obj.getString("UuDai");
                //edtCapNhatSoLuongSP.setText(obj.getString("SoLuongSanPham"));
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
        //int soLuongSP = Integer.parseInt(edtCapNhatSoLuongSP.getText().toString());
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
                //postDataParams.put("SoLuongSanPham", soLuongSP);
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

    private class XoaDuAn extends AsyncTask<String, String, String>{

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
            Toast.makeText(CapNhatDuAn.this, "Đã xóa dự án có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatDuAn.this, MainActivity.class);
            intent.putExtra("key", "DuAn");
            startActivity(intent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(CapNhatDuAn.this, MainActivity.class);
                i.putExtra("key", "DuAn");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        } else if (item.getItemId() == R.id.delete1) {
            // Show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(CapNhatDuAn.this);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn xóa dự án này?");
            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            new XoaDuAn().execute("http://"+API.HOST+"/bds_project/public/DuAn/"+id);
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

        } else if (idItem == R.id.xemuudai || idItem == R.id.xemUuDaiNVBH) {
            // Show dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(CapNhatDuAn.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_xem_uudai, null);
            builder.setView(dialogView);
            if (!lstUuDai.equals("")) {
                builder.setTitle("Ưu đãi đang áp dụng");
                ListView lvXemUuDai = (ListView) dialogView.findViewById(R.id.lvXemUuDai);

                String[] arrUuDai = lstUuDai.substring(0, lstUuDai.length()-2).split(", ");
                Log.i("xemuudai", arrUuDai[0] + arrUuDai[1]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CapNhatDuAn.this, android.R.layout.simple_list_item_1, arrUuDai);
                lvXemUuDai.setAdapter(adapter);
            } else {
                builder.setMessage("Chưa có ưu đãi nào!");
            }

            builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (API.quyen.equals("NVQL")) {
            CapNhatDuAn.this.getMenuInflater().inflate(R.menu.menu_xem_uudai, menu);
        } else {
            CapNhatDuAn.this.getMenuInflater().inflate(R.menu.xem_uudai_nvbh, menu);
        }
        return true;
    }

}
