package com.example.minhkhai.demobds.no;

/**
 * Created by minhkhai on 05/06/17.
 */

public class No {

    private int maNo, maHopDong;
    private String ngayTra, trangThai, tenKhachHang, dotTra;
    private float tien;
    private Boolean check;

    public No(int maNo, int maHopDong, String dotTra, String ngayTra, String trangThai, float tien, String tenKhachHang) {
        this.maNo = maNo;
        this.maHopDong = maHopDong;
        this.dotTra = dotTra;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.tenKhachHang = tenKhachHang;
        this.tien = tien;
    }

    public No(int maNo, int maHopDong, String ngayTra, String trangThai, String dotTra, float tien) {
        this.maNo = maNo;
        this.maHopDong = maHopDong;
        this.ngayTra = ngayTra;
        this.trangThai = trangThai;
        this.dotTra = dotTra;
        this.tien = tien;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public int getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(int maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public int getMaNo() {
        return maNo;
    }

    public void setMaNo(int maNo) {
        this.maNo = maNo;
    }

    public String getDotTra() {
        return dotTra;
    }

    public void setDotTra(String dotTra) {
        this.dotTra = dotTra;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public float getTien() {
        return tien;
    }

    public void setTien(float tien) {
        this.tien = tien;
    }

    public String toString() {
        return getDotTra();            // What to display in the Spinner list.
    }
}
