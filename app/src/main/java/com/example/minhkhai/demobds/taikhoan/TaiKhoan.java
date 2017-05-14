package com.example.minhkhai.demobds.taikhoan;

/**
 * Created by hiep on 05/14/2017.
 */

public class TaiKhoan {
    public Integer maTaiKhoan;
    public String tenTK, matKhau, hoTen, diaChi;
    public String ngaySinh;
    public String sDT, anh, chucVu, thongTinKhac;

    public TaiKhoan(Integer maTaiKhoan, String tenTK, String matKhau, String hoTen,
                    String diaChi, String ngaySinh, String sDT, String anh,
                    String chucVu, String thongTinKhac) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenTK = tenTK;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.sDT = sDT;
        this.anh = anh;
        this.chucVu = chucVu;
        this.thongTinKhac = thongTinKhac;
    }
}
