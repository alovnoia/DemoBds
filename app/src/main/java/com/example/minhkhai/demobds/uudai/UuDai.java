package com.example.minhkhai.demobds.uudai;

import java.util.Date;

/**
 * Created by minhkhai on 29/05/17.
 */

public class UuDai {

    int maUuDai;
    String tenUuDai;
    String batDau, ketThuc;

    public UuDai(int maUuDai, String tenUuDai) {
        this.maUuDai = maUuDai;
        this.tenUuDai = tenUuDai;
    }

    public UuDai(int maDuAn, String tenUuDai, String batDau, String ketThuc) {
        this.maUuDai = maDuAn;
        this.tenUuDai = tenUuDai;
        this.batDau = batDau;
        this.ketThuc = ketThuc;
    }

    public int getMaUuDai() {
        return maUuDai;
    }

    public void setMaUuDai(int maUuDai) {
        this.maUuDai = maUuDai;
    }

    public String getTenUuDai() {
        return tenUuDai;
    }

    public void setTenUuDai(String tenUuDai) {
        this.tenUuDai = tenUuDai;
    }

    public String getBatDau() {
        return batDau;
    }

    public void setBatDau(String batDau) {
        this.batDau = batDau;
    }

    public String getKetThuc() {
        return ketThuc;
    }

    public void setKetThuc(String ketThuc) {
        this.ketThuc = ketThuc;
    }
}
