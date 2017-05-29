package com.example.minhkhai.demobds.hotro.upanh;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hiep on 05/30/2017.
 */

public class ServerResponse {

    boolean success;
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }
}
