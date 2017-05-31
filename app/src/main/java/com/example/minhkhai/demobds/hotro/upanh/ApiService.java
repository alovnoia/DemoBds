package com.example.minhkhai.demobds.hotro.upanh;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by hiep on 05/30/2017.
 */

public interface ApiService {
    @Multipart
    @POST("/bds_project/UpLoad.php")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);
}
