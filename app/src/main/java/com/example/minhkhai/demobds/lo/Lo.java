package com.example.minhkhai.demobds.lo;

/**
 * Created by hiep on 05/21/2017.
 */

public class Lo {
    public int maLo;
    public String tenLo;
    public String tenDuAn;
    public int maDuAn;

    public Lo(int maLo, String tenLo, String tenDuAn, int maDuAn) {
        this.maLo = maLo;
        this.tenLo = tenLo;
        this.tenDuAn = tenDuAn;
        this.maDuAn = maDuAn;
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
