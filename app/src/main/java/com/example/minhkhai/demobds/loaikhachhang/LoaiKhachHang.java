package com.example.minhkhai.demobds.loaikhachhang;

/**
 * Created by minhkhai on 06/05/17.
 */

public class LoaiKhachHang {
    public  int maLoaiKH;
    public String ten;
    public String moTa;
    public boolean check;

    public int getMaLoaiKH() {
        return maLoaiKH;
    }

    public void setMaLoaiKH(int maLoaiKH) {
        this.maLoaiKH = maLoaiKH;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

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

    public LoaiKhachHang(int maLoaiKH, String ten, boolean check) {
        this.maLoaiKH = maLoaiKH;
        this.ten = ten;
        this.check = check;
    }

    public LoaiKhachHang(String ten, String moTa, int maLoai) {
        this.ten = ten;
        this.moTa = moTa;
        this.maLoai = maLoai;
    }

    public String toString() {
        return this.ten;            // What to display in the Spinner list.
    }
}
