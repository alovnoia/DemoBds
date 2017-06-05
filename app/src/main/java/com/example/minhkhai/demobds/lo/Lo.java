package com.example.minhkhai.demobds.lo;

/**
 * Created by hiep on 05/21/2017.
 */

public class Lo {
    public int maLo;
    public String tenLo;
    public String tenDuAn;
    public int maDuAn;

    public int getMaLo() {
        return maLo;
    }

    public void setMaLo(int maLo) {
        this.maLo = maLo;
    }

    public String getTenLo() {
        return tenLo;
    }

    public void setTenLo(String tenLo) {
        this.tenLo = tenLo;
    }

    public String getTenDuAn() {
        return tenDuAn;
    }

    public void setTenDuAn(String tenDuAn) {
        this.tenDuAn = tenDuAn;
    }

    public int getMaDuAn() {
        return maDuAn;
    }

    public void setMaDuAn(int maDuAn) {
        this.maDuAn = maDuAn;
    }

    public Lo(int maLo, String tenLo, String tenDuAn, int maDuAn) {
        this.maLo = maLo;
        this.tenLo = tenLo;
        this.tenDuAn = tenDuAn;
        this.maDuAn = maDuAn;
    }
    public Lo(int ma, String ten){
        this.maLo = ma;
        this.tenLo = ten;
    }

    public Lo(int maLo, String tenLo, String tenDuAn) {
        this.maLo = maLo;
        this.tenLo = tenLo;
        this.tenDuAn = tenDuAn;
    }

    public Lo(int maLo, String tenLo, int maDuAn) {
        this.maLo = maLo;
        this.tenLo = tenLo;
        this.maDuAn = maDuAn;
    }

    public int getMaLo() {
        return maLo;
    }

    public void setMaLo(int maLo) {
        this.maLo = maLo;
    }

    public String toString() {
        return this.tenLo;            // What to display in the Spinner list.
    }
}
