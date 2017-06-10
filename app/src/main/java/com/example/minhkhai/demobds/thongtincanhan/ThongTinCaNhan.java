package com.example.minhkhai.demobds.thongtincanhan;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ThongTinCaNhan extends Fragment {
    TextView txtTaiKhoan, txtChucVu;
    EditText edtTen, edtPass, edtNgay, edtDienThoai, edtDiaChi, edtCMT, edtThongTin;
    ImageView imgAnh;
    FloatingActionButton fab_Save;
    Calendar ngayGioHienTai = Calendar.getInstance();
    String id;

    String mediaPath = "";
    File file;
    String tenHinh = "";
    JSONObject taiKhoan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_thong_tin_ca_nhan,container,false);

        txtTaiKhoan = (TextView) view.findViewById(R.id.tvTaiKhoanTTCN);
        txtChucVu = (TextView) view.findViewById(R.id.tvChucVuTTCN);
        edtTen = (EditText) view.findViewById(R.id.edtHoTenTTCN);
        edtPass = (EditText) view.findViewById(R.id.edtMatKhauTTCN);


        edtNgay = (EditText) view.findViewById(R.id.edtNgaySinhTTCN);
        edtNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        edtDienThoai = (EditText) view.findViewById(R.id.edtDienThoaiTTCN);
        edtDiaChi = (EditText) view.findViewById(R.id.edtDiaChiTTCN);
        edtCMT = (EditText) view.findViewById(R.id.edtCMTTTCN);
        edtThongTin = (EditText) view.findViewById(R.id.edtThongTinKhacTTCN);
        imgAnh = (ImageView) view.findViewById(R.id.imgAnhTTCN);
        fab_Save = (FloatingActionButton) view.findViewById(R.id.fab_SaveTTCN);

        id = API.idUser;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadTTCN().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
            }
        });

        imgAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 0);
                //onActivityResult(0, 1 ,i);
            }
        });

        fab_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mediaPath != "")
                        {
                            file = new File(mediaPath);
                            tenHinh = file.getName();
                            API.uploadFile(file);
                        }else{
                            try {
                                tenHinh = taiKhoan.getString("Anh");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        new CapNhatTTCN().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan/"+id);
                    }
                });
            }
        });
        return view;
    }

    //Hiển thị ảnh khi chọn
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == 0 && resultCode == Activity.RESULT_OK && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                assert cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                mediaPath = cursor.getString(columnIndex);
                // Set the Image in ImageView for Previewing the Media
                imgAnh.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                cursor.close();

            } else {
                Toast.makeText(getActivity(), "Bạn chưa chọn ảnh", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Đã xảy ra lỗi!", Toast.LENGTH_LONG).show();
        }

    }

    private class CapNhatTTCN extends AsyncTask<String, Integer, String> {

        String hoTen = edtTen.getText().toString();
        String chucVu = txtChucVu.getText().toString();
        String pass = edtPass.getText().toString();
        String[] ngaySinh = edtNgay.getText().toString().split("/");
        String sDT = edtDienThoai.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String cMT = edtCMT.getText().toString();
        String more = edtThongTin.getText().toString();


        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);

                JSONObject object = new JSONObject();
                object.put("Anh", tenHinh);
                object.put("ChucVu", chucVu);
                object.put("HoTen", hoTen);
                object.put("MatKhau", pass);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                object.put("NgaySinh", format.format(format.parse(ngaySinh[2]+"-"+ngaySinh[1]+"-"+ngaySinh[0])));
                object.put("SoDienThoai", sDT);
                object.put("DiaChi", diaChi);
                object.put("CMT", cMT);
                object.put("ThongTinKhac", more);
                object.put("_method", "PUT");

                return API.POST_URL(url, object);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "Đã cập nhật thông tin mới", Toast.LENGTH_SHORT).show();
        }
    }
    private class LoadTTCN extends AsyncTask<String, Integer, String>{
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
                JSONObject object = new JSONObject(s);
                taiKhoan = object;
                String tenHinh = object.getString("Anh");
                Picasso.with(getActivity())
                        .load("http://"+API.HOST+"/bds_project/data/"+ tenHinh)
                        .placeholder(R.drawable.ic_users)
                        .error(R.drawable.ic_error_img)
                        .into(imgAnh);

                txtTaiKhoan.setText(object.getString("TenTaiKhoan"));
                txtChucVu.setText(object.getString("ChucVu"));
                edtTen.setText(object.getString("HoTen"));
                edtPass.setText(object.getString("MatKhau"));
                edtDiaChi.setText(object.getString("DiaChi"));

                String[] ngay = object.get("NgaySinh").toString().split("-");
                edtNgay.setText(ngay[2]+"/"+ngay[1]+"/"+ngay[0]);

                edtDienThoai.setText(object.getString("SoDienThoai"));
                edtCMT.setText(object.getString("CMT"));
                edtThongTin.setText(object.getString("ThongTinKhac"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void datePicker(){
        DatePickerDialog date = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtNgay.setText(dayOfMonth + "/" + (month+1) + "/" + year);
                    }
                },
                ngayGioHienTai.get(Calendar.YEAR),
                ngayGioHienTai.get(Calendar.MONTH),
                ngayGioHienTai.get(Calendar.DAY_OF_MONTH));
        date.show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ThongTinCaNhan.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/
}
