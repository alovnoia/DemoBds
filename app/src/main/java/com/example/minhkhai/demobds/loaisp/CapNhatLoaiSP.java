package com.example.minhkhai.demobds.loaisp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CapNhatLoaiSP extends AppCompatActivity {

    EditText edtCapNhatTenLoaiSP, edtCapNhatMoTaLoaiSP;
    FloatingActionButton fabSaveLoaiSP;
    Button btnXoaLoaiSP;

    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat_loai_sp);

        edtCapNhatTenLoaiSP = (EditText) findViewById(R.id.edtCapNhatTenLoaiSP);
        edtCapNhatMoTaLoaiSP = (EditText) findViewById(R.id.edtCapNhatMoTaLoaiSP);
        btnXoaLoaiSP = (Button) findViewById(R.id.btnXoaLoaiSP);
        fabSaveLoaiSP = (FloatingActionButton) findViewById(R.id.fab_save_LoaiSP);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocLoaiSP().execute("http://10.0.3.2:2347/bds_project/public/LoaiSP/"+id);
            }
        });

        fabSaveLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new CapNhatSuaLoaiSP().execute();
                    }
                });
            }
        });

        btnXoaLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new XoaLoaiSP().execute("http://10.0.3.2:2347/bds_project/public/LoaiSP/"+id);
                    }
                });
            }
        });
    }

    private class DocLoaiSP extends AsyncTask<String, String, String>{

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

                edtCapNhatTenLoaiSP.setText(object.getString("TenLoaiSP"));
                edtCapNhatMoTaLoaiSP.setText(object.getString("MoTa"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class CapNhatSuaLoaiSP extends AsyncTask<String, String, String>{

        String ten = edtCapNhatTenLoaiSP.getText().toString();
        String moTa = edtCapNhatMoTaLoaiSP.getText().toString();

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://10.0.3.2:2347/bds_project/public/LoaiSP/"+id); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("TenLoaiSP", ten);
                postDataParams.put("MoTa", moTa);
                postDataParams.put("_method", "PUT");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
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
            Toast.makeText(CapNhatLoaiSP.this, "Đã câph nhật loại sản phẩm có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatLoaiSP.this, DanhSachLoaiSP.class);
            startActivity(intent);
        }
    }

    private class XoaLoaiSP extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            try {

                URL myUrl = new URL(params[0]);

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("_method", "DELETE");

                HttpURLConnection conn = (HttpURLConnection) myUrl.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
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
            Toast.makeText(CapNhatLoaiSP.this, "Đã xóa SP có id "+id, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CapNhatLoaiSP.this, DanhSachLoaiSP.class);
            startActivity(intent);
        }
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
}
