package com.example.minhkhai.demobds.hotro;

import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.upanh.ApiClient;
import com.example.minhkhai.demobds.hotro.upanh.ApiService;
import com.example.minhkhai.demobds.hotro.upanh.ServerResponse;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by minhkhai on 05/05/17.
 */

public class API {

    public static String HOST = "10.0.3.2:2347";
    //public static String HOST = "10.0.3.2:8080";

    public static String idUser;

    public static String quyen="";

    public static boolean change = false;

    public static String username = "";

    public static String POST_URL(URL url, JSONObject object) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(15000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        try {
            writer.write(getPostDataString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public static String getPostDataString(JSONObject params) throws Exception {

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

    public static String GET_URL(String theURL) throws IOException {
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

    public static String convertDate(String date){
        String[] ngay = date.split("-");
        return ngay[2] + "/" + ngay[1] + "/" + ngay[0];
    }

    public static void uploadFile(File file) {
        // Map is used to multipart the file using okhttp3.RequestBody
        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());


        ApiService getResponse = ApiClient.getClient().create(ApiService.class);
        Call<ServerResponse> call = getResponse.uploadFile(fileToUpload, filename);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
}
