package com.example.minhkhai.demobds.sanpham;

import android.graphics.Bitmap;

/**
 * Created by minhkhai on 14/05/17.
 */

public class SanPham {
    int maSP;
    String tenSP, loaiSP;
    float giaSP;
    String anhSP;

    public SanPham(int maSP, String tenSP, String loaiSP, float giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
    }

    public SanPham(int maSP, String tenSP, String loaiSP, float giaSP, String anhSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
        this.anhSP = anhSP;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getLoaiSP() {
        return loaiSP;
    }

    public void setLoaiSP(String loaiSP) {
        this.loaiSP = loaiSP;
    }

    public float getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(float giaSP) {
        this.giaSP = giaSP;
    }
}
