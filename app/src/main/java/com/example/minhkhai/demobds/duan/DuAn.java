package com.example.minhkhai.demobds.duan;

import java.util.Date;

/**
 * Created by minhkhai on 02/05/17.
 */

public class DuAn {
    private int maDuAn, soLuongSanPham;
    private float tongDienTich;
    private Date ngayCap;
    private String tenDuAn, diaChi, giayPhep, moTa;

    public DuAn(int maDuAn, String tenDuAn, String diaChi) {
        this.maDuAn = maDuAn;
        this.tenDuAn = tenDuAn;
        this.diaChi = diaChi;
    }

    public int getMaDuAn() {
        return maDuAn;
    }

    public void setMaDuAn(int maDuAn) {
        this.maDuAn = maDuAn;
    }

    public int getSoLuongSanPham() {
        return soLuongSanPham;
    }

    public void setSoLuongSanPham(int soLuongSanPham) {
        this.soLuongSanPham = soLuongSanPham;
    }

    public float getTongDienTich() {
        return tongDienTich;
    }

    public void setTongDienTich(float tongDienTich) {
        this.tongDienTich = tongDienTich;
    }

    public Date getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(Date ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGiayPhep() {
        return giayPhep;
    }

    public void setGiayPhep(String giayPhep) {
        this.giayPhep = giayPhep;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String toString() {
        return this.tenDuAn;            // What to display in the Spinner list.
    }
}
