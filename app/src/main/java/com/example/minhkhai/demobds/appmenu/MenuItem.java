package com.example.minhkhai.demobds.appmenu;

/**
 * Created by minhkhai on 13/05/17.
 */

public class MenuItem {

    String tenChucNang;
    int anh;

    public MenuItem(String tenChucNang, int anh) {
        this.anh = anh;
        this.tenChucNang = tenChucNang;
    }

    public MenuItem(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }
}
