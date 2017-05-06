package com.example.minhkhai.demobds.loaikhachhang;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class ChiTietLoaiKhachHang extends AppCompatActivity {
    FloatingActionButton flEdit;
    TextView tvID;
    EditText edtTen, edtMoTa;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_khach_hang);

        Intent i = getIntent();
        id = i.getIntExtra("maLoaiKH", 0);
        //Toast.makeText(getApplicationContext(), id+"", Toast.LENGTH_SHORT).show();



        flEdit = (FloatingActionButton) findViewById(R.id.fab_edit_LoaiSP_ChiTiet);
        tvID = (TextView) findViewById(R.id.tvmaLoaiKHChiTiet);
        edtTen = (EditText) findViewById(R.id.edtTenLoaiKhachHangChiTiet);
        edtMoTa = (EditText) findViewById(R.id.edtMoTaChiTietLoaiKH);

        tvID.setText(id+"");
        //Toast.makeText(getApplicationContext(), id+"", Toast.LENGTH_SHORT).show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LayThongTin().execute("http://10.0.3.2:2347/bds_project/public/LoaiKhachHang/"+id);
            }
        });

        flEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new UpdateThongTin().execute();
                        Intent i = new Intent(ChiTietLoaiKhachHang.this, DanhSachLoaiKhachHang.class);
                        startActivity(i);
                    }
                });
            }
        });
        FloatingActionButton flDel = (FloatingActionButton) findViewById(R.id.fab_del_LoaiSP_ChiTiet);
        flDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaLoaiKH().execute();
                        Intent i = new Intent(ChiTietLoaiKhachHang.this, DanhSachLoaiKhachHang.class);
                        startActivity(i);
                    }
                });
            }
        });

    }

    private  static String GET_URL(String theURL) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(theURL);
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            content.append(line + "\n");
        }
        bufferedReader.close();

        return content.toString();
    }

    class LayThongTin extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return GET_URL(params[0]);
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
                edtTen.setText(object.getString("TenLoaiKH"));
                edtMoTa.setText(object.getString("MoTa"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    class UpdateThongTin extends AsyncTask<String, Integer, String>{

        String tenLKH = edtTen.getText().toString();
        String moTa = edtMoTa.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://10.0.3.2:2347/bds_project/public/LoaiKhachHang/"+id); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiKH", tenLKH);
                postDataParams.put("MoTa", moTa);
                postDataParams.put("_method", "PUT");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Đã sửa loại khách hàng có id = "+id, Toast.LENGTH_SHORT).show();
        }
    }

    class XoaLoaiKH extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://10.0.3.2:2347/bds_project/public/LoaiKhachHang/"+id); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                //postDataParams.put("TenLoaiKH", tenLKH);
                // postDataParams.put("MoTa", moTa);
                postDataParams.put("_method", "DELETE");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), "Đã xóa SP có id "+id, Toast.LENGTH_SHORT).show();
        }
    }
}
