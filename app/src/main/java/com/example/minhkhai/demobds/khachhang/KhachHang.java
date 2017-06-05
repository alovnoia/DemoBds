package com.example.minhkhai.demobds.khachhang;

import android.widget.BaseAdapter;

/**
 * Created by minhkhai on 11/05/17.
 */

public class KhachHang {
    private int maKhachHang, maLoaiKhachHang;
    private String tenKhachHang, tenLoaiKhachHang;

    public KhachHang(int maKhachHang, int maLoaiKhachHang, String tenKhachHang, String tenLoaiKhachHang) {
        this.maKhachHang = maKhachHang;
        this.maLoaiKhachHang = maLoaiKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.tenLoaiKhachHang = tenLoaiKhachHang;
    }

    public KhachHang (int ma, String ten){
        this.maKhachHang = ma;
        this.tenKhachHang = ten;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaLoaiKhachHang() {
        return maLoaiKhachHang;
    }

    public void setMaLoaiKhachHang(int maLoaiKhachHang) {
        this.maLoaiKhachHang = maLoaiKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenLoaiKhachHang() {
        return tenLoaiKhachHang;
    }

    public void setTenLoaiKhachHang(String tenLoaiKhachHang) {
        this.tenLoaiKhachHang = tenLoaiKhachHang;
    }

    public String toString(){
        return this.tenKhachHang;
    }
}
