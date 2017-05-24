package com.example.minhkhai.demobds.sanpham;

import android.graphics.Bitmap;

/**
 * Created by minhkhai on 14/05/17.
 */

public class SanPham {
    int maSP;
    String tenSP, loaiSP;
    int giaSP, duAn;
    String anhSP;

    public SanPham(int maSP, String tenSP, String loaiSP, int giaSP, int duAn) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
        this.duAn = duAn;
    }

    public SanPham(int maSP, String tenSP, String loaiSP, int giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
    }

    public SanPham(int maSP, String tenSP, String loaiSP, int giaSP, String anhSP) {
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

    public int getDuAn() {
        return duAn;
    }

    public void setDuAn(int duAn) {
        this.duAn = duAn;
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

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }
}
