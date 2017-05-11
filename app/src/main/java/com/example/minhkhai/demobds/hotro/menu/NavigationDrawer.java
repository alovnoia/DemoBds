package com.example.minhkhai.demobds.hotro.menu;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.loaikhachhang.DanhSachLoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends Fragment {

    String itemDrawer[] = {"Dự án", "Loại Sản phẩm", "Loại khách hàng"};
    int itemHinhAnh[] = {R.drawable.ic_add_black_24dp, R.drawable.ic_add_black_24dp, R.drawable.ic_add_black_24dp};
    List<Drawer> mangDrawer;
    ListView lvMenu;
    String a[] = {"ascasc", "asfcasc"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_navigation_drawer, container, false);
        mangDrawer = new ArrayList<Drawer>();
        for (int i = 0; i < itemDrawer.length; i++){
            Drawer item = new Drawer();

            item.setHinhAnh(itemHinhAnh[i]);
            item.setTenMenu(itemDrawer[i]);

            mangDrawer.add(item);
        }
        lvMenu = (ListView) view.findViewById(R.id.lvMenu);
        DrawerAdapter adapter = new DrawerAdapter(getActivity(), R.layout.listview_drawer, mangDrawer);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, a);
        lvMenu.setAdapter(adapter);
        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(getActivity(), DanhSachDuAn.class);
                        break;
                    case 1:
                        intent = new Intent(getActivity(), DanhSachLoaiSP.class);
                        break;
                    case 2:
                        intent = new Intent(getActivity(), DanhSachLoaiKhachHang.class);
                        break;
                    default:
                        intent = new Intent(getActivity(), NavigationDrawer.class);
                }
                startActivity(intent);
            }
        });
        return view;
    }
}
