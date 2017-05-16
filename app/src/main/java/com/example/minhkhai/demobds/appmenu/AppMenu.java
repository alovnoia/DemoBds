package com.example.minhkhai.demobds.appmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.dangnhap.DangNhap;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.hotro.menu.NavigationDrawer;
import com.example.minhkhai.demobds.khachhang.DanhSachKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.DanhSachLoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;
import com.example.minhkhai.demobds.sanpham.DanhSachSanPham;
import com.example.minhkhai.demobds.taikhoan.DanhSachTaiKhoan;
import com.example.minhkhai.demobds.thongtincanhan.ThongTinCaNhan;


import java.util.ArrayList;
import java.util.List;

public class AppMenu extends AppCompatActivity {

    String itemTenNVQL[] = {"Tài khoản", "Dự án", "Loại sản phẩm", "Sản phẩm", "Loại khách hàng", "Khách hàng",
            "Ưu đãi", "Hợp đồng", "Nợ", "Thông tin cá nhân", "Đăng xuất"};
    int itemAnhNVQL[] = {R.drawable.ic_add_black_24dp, R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp, R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp};
    String itemTenNVBH[] = {"Dự án", "Loại sản phẩm", "Sản phẩm", "Loại khách hàng", "Khách hàng",
            "Ưu đãi", "Hợp đồng", "Nợ", "Thông tin cá nhân", "Đăng xuất"};
    int itemAnhNVBH[] = {R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp, R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp,R.drawable.ic_add_black_24dp,
            R.drawable.ic_add_black_24dp};
    String[] itemTen;
    int[] itemAnh;
    List<MenuItem> arrItem;
    ListView lvAppMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_menu);

        arrItem = new ArrayList<>();
        if (API.quyen.equals("NVQL")) {
            itemTen = itemTenNVQL;
            itemAnh = itemAnhNVQL;
        } else {
            itemTen = itemTenNVBH;
            itemAnh = itemAnhNVBH;
        }
        for (int i = 0; i < itemTen.length; i++){
            MenuItem item = new MenuItem();

            item.setAnh(itemAnh[i]);
            item.setTenChucNang(itemTen[i]);

            arrItem.add(item);
        }
        lvAppMenu = (ListView) findViewById(R.id.lvAppMenu);
        AppMenuAdapter adapter = new AppMenuAdapter(AppMenu.this, R.layout.item_app_menu, arrItem);
        lvAppMenu.setAdapter(adapter);
        lvAppMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (API.quyen.equals("NVQL")) {
                    switch (position){
                        case 0:
                            intent = new Intent(AppMenu.this, DanhSachTaiKhoan.class);
                            break;
                        case 1:
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                            break;
                        case 2:
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                            break;
                        case 3:
                            intent = new Intent(AppMenu.this, DanhSachSanPham.class);
                            break;
                        case 4:
                            intent = new Intent(AppMenu.this, DanhSachLoaiKhachHang.class);
                            break;
                        case 5:
                            intent = new Intent(AppMenu.this, DanhSachKhachHang.class);
                            break;
                        case 6:
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                            break;
                        case 7:
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                            break;
                        case 8:
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                            break;
                        case 9:
                            intent = new Intent(AppMenu.this, ThongTinCaNhan.class);
                            break;
                        case 10:
                            API.idUser = null;
                            API.quyen = null;
                            intent = new Intent(AppMenu.this, DangNhap.class);
                            break;
                        default:
                            intent = new Intent(AppMenu.this, NavigationDrawer.class);
                            break;
                    }
                } else {
                    switch (position){
                        case 0:
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                            break;
                        case 1:
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                            break;
                        case 2:
                            intent = new Intent(AppMenu.this, DanhSachSanPham.class);
                            break;
                        case 3:
                            intent = new Intent(AppMenu.this, DanhSachLoaiKhachHang.class);
                            break;
                        case 4:
                            intent = new Intent(AppMenu.this, DanhSachKhachHang.class);
                            break;
                        case 5:
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                            break;
                        case 6:
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                            break;
                        case 7:
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                            break;
                        case 8:
                            intent = new Intent(AppMenu.this, ThongTinCaNhan.class);
                            break;
                        case 9:
                            API.idUser = null;
                            API.quyen = null;
                            intent = new Intent(AppMenu.this, DangNhap.class);
                            break;
                        default:
                            intent = new Intent(AppMenu.this, NavigationDrawer.class);
                            break;
                    }
                }

                startActivity(intent);
            }
        });
    }

}
