package com.example.minhkhai.demobds.loaisp;

/**
 * Created by minhkhai on 02/05/17.
 */

public class LoaiSP {

    private int MaLoaiSP;
    private String tenLoaiSP;
    private String moTaLoaiSP;

    public LoaiSP(int maLoaiSP, String tenLoaiSP, String moTaLoaiSP) {
        MaLoaiSP = maLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
        this.moTaLoaiSP = moTaLoaiSP;
    }

    public LoaiSP(int maLoaiSP, String tenLoaiSP) {
        MaLoaiSP = maLoaiSP;
        this.tenLoaiSP = tenLoaiSP;
    }

    public int getMaLoaiSP() {
        return MaLoaiSP;
    }

    public void setMaLoaiSP(int idLoaiSP) {
        this.MaLoaiSP = idLoaiSP;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getMoTaLoaiSP() {
        return moTaLoaiSP;
    }

    public void setMoTaLoaiSP(String moTaLoaiSP) {
        this.moTaLoaiSP = moTaLoaiSP;
    }

    public String toString() {
        return this.tenLoaiSP;            // What to display in the Spinner list.
    }

}
