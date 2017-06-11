package com.example.minhkhai.demobds.hopdong;

/**
 * Created by hiep on 06/01/2017.
 */

public class HopDong {
    public int maHopDong, maTaiKhoan, maLoaiKhachHang, maKhachHang, maDuAn, maLo, maSanPham;
    public String TenKhachHang, NgayKy;
    public String TrangThai;

    public HopDong(int maHopDong, int maTaiKhoan, int maLoaiKhachHang,
                   int maKhachHang, int maDuAn, int maLo, int maSanPham,
                   String tenKhachHang, String ngayKy, String trangThai) {
        this.maHopDong = maHopDong;
        this.maTaiKhoan = maTaiKhoan;
        this.maLoaiKhachHang = maLoaiKhachHang;
        this.maKhachHang = maKhachHang;
        this.maDuAn = maDuAn;
        this.maLo = maLo;
        this.maSanPham = maSanPham;
        TenKhachHang = tenKhachHang;
        NgayKy = ngayKy;
        TrangThai = trangThai;
    }

    public HopDong(int maHopDong, int tk, int maKhachHang, int maDuAn, int maSanPham, String tenKhachHang, String ngayKy, String trangThai) {
        this.maHopDong = maHopDong;
        this.maTaiKhoan = tk;
        this.maKhachHang = maKhachHang;
        this.maDuAn = maDuAn;
        this.maSanPham = maSanPham;
        TenKhachHang = tenKhachHang;
        NgayKy = ngayKy;
        TrangThai = trangThai;
    }

    public int getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(int maHopDong) {
        this.maHopDong = maHopDong;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public int getMaLoaiKhachHang() {
        return maLoaiKhachHang;
    }

    public void setMaLoaiKhachHang(int maLoaiKhachHang) {
        this.maLoaiKhachHang = maLoaiKhachHang;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaDuAn() {
        return maDuAn;
    }

    public void setMaDuAn(int maDuAn) {
        this.maDuAn = maDuAn;
    }

    public int getMaLo() {
        return maLo;
    }

    public void setMaLo(int maLo) {
        this.maLo = maLo;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenKhachHang() {
        return TenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        TenKhachHang = tenKhachHang;
    }

    public String getNgayKy() {
        return NgayKy;
    }

    public void setNgayKy(String ngayKy) {
        NgayKy = ngayKy;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public HopDong(){}

    /*public HopDong(int maHopDong, int khachHang, int duAn, String tenKhachHang, String ngayKy, String trangThai) {
        MaHopDong = maHopDong;
        KhachHang = khachHang;
        MaDuAn = duAn;
        TenKhachHang = tenKhachHang;
        NgayKy = ngayKy;
        TrangThai = trangThai;
    }*/
}
