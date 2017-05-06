package com.example.minhkhai.demobds.hotro.menu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer extends AppCompatActivity {

    String itemDrawer[] = {"Dự án", "Loại Sản phẩm"};
    int itemHinhAnh[] = {R.drawable.ic_add_black_24dp, R.drawable.ic_add_black_24dp};
    List<Drawer> mangDrawer;
    ListView lvMenuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        mangDrawer = new ArrayList<Drawer>();
        for (int i = 0; i < itemDrawer.length; i++){
            Drawer item = new Drawer();

            item.setHinhAnh(itemHinhAnh[i]);
            item.setTenMenu(itemDrawer[i]);

            mangDrawer.add(item);
        }
        lvMenuDrawer = (ListView) findViewById(R.id.lvMenuDrawer);
        DrawerAdapter adapter = new DrawerAdapter(this, R.layout.listview_drawer, mangDrawer);
        lvMenuDrawer.setAdapter(adapter);

        lvMenuDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(NavigationDrawer.this, DanhSachDuAn.class);
                        break;
                    case 1:
                        intent = new Intent(NavigationDrawer.this, DanhSachLoaiSP.class);
                        break;
                    default:
                        intent = new Intent(NavigationDrawer.this, NavigationDrawer.class);
                }
                startActivity(intent);
            }
        });
    }
}
