package com.example.minhkhai.demobds.sanpham;

import android.graphics.Bitmap;

/**
 * Created by minhkhai on 14/05/17.
 */

public class SanPham {
    int maSP;
    String tenSP, loaiSP;
    int duAn;
    String giaSP, anhSP, tenDuAn;

    public SanPham(int maSP, String tenSP, String loaiSP, String giaSP, int duAn, String tenDuAn) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
        this.duAn = duAn;
        this.tenDuAn = tenDuAn;
    }

    public SanPham(int maSP, String tenSP, String loaiSP, String giaSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
    }

    public SanPham(int maSP, String tenSP, String loaiSP, String giaSP, String anhSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.loaiSP = loaiSP;
        this.giaSP = giaSP;
        this.anhSP = anhSP;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
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

    public String getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(String giaSP) {
        this.giaSP = giaSP;
    }

    public String toString(){
        return this.loaiSP +" - " +this.tenSP;
    }
}
