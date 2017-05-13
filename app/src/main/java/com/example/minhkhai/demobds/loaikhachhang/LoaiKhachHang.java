package com.example.minhkhai.demobds.loaikhachhang;

/**
 * Created by minhkhai on 06/05/17.
 */

public class LoaiKhachHang {
    public String ten;
    public String moTa;

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int maLoai;

    public LoaiKhachHang(String ten, String moTa, int maLoai) {
        this.ten = ten;
        this.moTa = moTa;
        this.maLoai = maLoai;
    }

    public String toString() {
        return this.ten;            // What to display in the Spinner list.
    }
}
