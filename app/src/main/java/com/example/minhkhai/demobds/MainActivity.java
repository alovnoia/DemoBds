package com.example.minhkhai.demobds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = new Intent(this, DanhSachLoaiSP.class);
        startActivity(intent);*/
    }
}
