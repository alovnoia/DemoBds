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
