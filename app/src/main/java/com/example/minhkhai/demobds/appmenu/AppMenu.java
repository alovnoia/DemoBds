package com.example.minhkhai.demobds.appmenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.dangnhap.DangNhap;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.hotro.menu.NavigationDrawer;
import com.example.minhkhai.demobds.khachhang.DanhSachKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.DanhSachLoaiKhachHang;
import com.example.minhkhai.demobds.loaikhachhang.LoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;
import com.example.minhkhai.demobds.sanpham.DanhSachSanPham;
import com.example.minhkhai.demobds.taikhoan.DanhSachTaiKhoan;
import com.example.minhkhai.demobds.thongtincanhan.ThongTinCaNhan;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppMenu extends AppCompatActivity {
    private static final String TAG = "AppMenu";
    private ExpandableListView eplMenu;
    private HashMap<String, List<MenuItem>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_app_menu);
        setContentView(R.layout.expandable_listview);

        eplMenu = (ExpandableListView) findViewById(R.id.eplMenu);

        final List<String> listHeader = new ArrayList<>();
        listHeader.add("Khách hàng");
        listHeader.add("Sản phẩm");
        listHeader.add("Hợp đồng");
        listHeader.add("Tài khoản");

        mData = new HashMap<>();
        List<MenuItem> listKhachHang = new ArrayList<>();
        listKhachHang.add(new MenuItem("Loại khách hàng"));
        listKhachHang.add(new MenuItem("Khách hàng"));

        List<MenuItem> listSanPham = new ArrayList<>();
        listSanPham.add(new MenuItem("Dự án"));
        listSanPham.add(new MenuItem("Lô"));
        listSanPham.add(new MenuItem("Loại sản phẩm"));
        listSanPham.add(new MenuItem("Sản phâm"));

        List<MenuItem> listHopDong = new ArrayList<>();
        listHopDong.add(new MenuItem("Ưu đãi"));
        listHopDong.add(new MenuItem("Hợp đồng"));
        listHopDong.add(new MenuItem("Nợ"));

        List<MenuItem> listTaiKhoan = new ArrayList<>();
        if (API.quyen.equals("NVQL")) {
            listTaiKhoan.add(new MenuItem("Quản lý tài khoản"));
        }
        listTaiKhoan.add(new MenuItem("Tài khoản cá nhân"));
        listTaiKhoan.add(new MenuItem("Đăng xuất"));

        mData.put(listHeader.get(0), listKhachHang);
        mData.put(listHeader.get(1), listSanPham);
        mData.put(listHeader.get(2), listHopDong);
        mData.put(listHeader.get(3), listTaiKhoan);

        AppMenuAdapter adapter = new AppMenuAdapter(this, listHeader, mData);
        eplMenu.setAdapter(adapter);

        eplMenu.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = null;
                switch (groupPosition) {
                    case 0:
                        if (childPosition == 0) {
                            intent = new Intent(AppMenu.this, DanhSachLoaiKhachHang.class);
                        } else {
                            intent = new Intent(AppMenu.this, DanhSachKhachHang.class);
                        }
                        break;
                    case 1:
                        if (childPosition == 0) {
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                        } else if (childPosition == 1) {
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                        } else if (childPosition == 2) {
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                        } else if (childPosition == 3) {
                            intent = new Intent(AppMenu.this, DanhSachSanPham.class);
                        }
                        break;
                    case 2:
                        if (childPosition == 0) {
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                        } else if (childPosition == 1) {
                            intent = new Intent(AppMenu.this, DanhSachDuAn.class);
                        } else if (childPosition == 2) {
                            intent = new Intent(AppMenu.this, DanhSachLoaiSP.class);
                        }
                        break;
                    case 3:
                        if (API.quyen.equals("NVQL")) {
                            if (childPosition == 0) {
                                intent = new Intent(AppMenu.this, DanhSachTaiKhoan.class);
                            } else if (childPosition == 1){
                                intent = new Intent(AppMenu.this, ThongTinCaNhan.class);
                            } else {
                                intent = new Intent(AppMenu.this, DangNhap.class);
                                API.idUser = null;
                                API.quyen = null;
                            }
                        } else {
                            if (childPosition == 0) {
                                intent = new Intent(AppMenu.this, ThongTinCaNhan.class);
                            } else {
                                intent = new Intent(AppMenu.this, DangNhap.class);
                                API.idUser = null;
                                API.quyen = null;
                            }
                        }
                        break;
                    default:
                        intent = new Intent(AppMenu.this, AppMenu.class);
                        break;
                }
                startActivity(intent);
                return false;
            }
        });

        eplMenu.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        for(int i=0; i < adapter.getGroupCount(); i++){
            eplMenu.expandGroup(i);
        }
    }

}

/*String itemTenNVQL[] = {"Tài khoản", "Dự án", "Loại sản phẩm", "Sản phẩm", "Loại khách hàng", "Khách hàng",
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
    ListView lvAppMenu;*/


/*arrItem = new ArrayList<>();
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
        });*/
