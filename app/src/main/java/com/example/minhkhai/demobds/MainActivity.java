package com.example.minhkhai.demobds;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.dangnhap.DangNhap;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hopdong.DanhSachHopDong;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.DanhSachKhachHang;
import com.example.minhkhai.demobds.lo.DanhSachLo;
import com.example.minhkhai.demobds.loaikhachhang.DanhSachLoaiKhachHang;
import com.example.minhkhai.demobds.loaisp.DanhSachLoaiSP;
import com.example.minhkhai.demobds.sanpham.DanhSachSanPham;
import com.example.minhkhai.demobds.taikhoan.ChiTietTaiKhoan;
import com.example.minhkhai.demobds.taikhoan.DanhSachTaiKhoan;
import com.example.minhkhai.demobds.thongtincanhan.ThongTinCaNhan;
import com.example.minhkhai.demobds.uudai.DanhSachUuDai;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    TextView tvTenThat;
    private GoogleApiClient client;
    FragmentManager fragmentManager = getFragmentManager();
    ActionBarDrawerToggle toggle;
    String loadInterface ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*tvTenThat = (TextView) findViewById(R.id.tvTenThat);
        tvTenThat.setText("cs");*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        try {
            loadInterface = extras.getString("key");
        }catch(Exception e){

        }

        Fragment fragment;


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        switch (loadInterface){
            case "LoaiKhachHang":
                fragment = new DanhSachLoaiKhachHang();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "KhachHang":
                fragment = new DanhSachKhachHang();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "DuAn":
                fragment = new DanhSachDuAn();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "TaiKhoan":
                fragment = new DanhSachTaiKhoan();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "LoaiSP":
                fragment = new DanhSachLoaiSP();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "SanPham":
                fragment = new DanhSachSanPham();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "Lo":
                fragment = new DanhSachLo();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "uuDai":
                fragment = new DanhSachUuDai();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            case "HopDong":
                fragment = new DanhSachHopDong();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
            default:
                fragment = new DanhSachDuAn();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();
                toggle.setDrawerIndicatorEnabled(true);
                break;
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.khachHang) {
            Fragment fragment = new DanhSachKhachHang();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);
        } else if (id == R.id.loaiKhachHang) {

            Fragment fragment = new DanhSachLoaiKhachHang();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);
        } else if (id == R.id.duAn) {
            Fragment fragment = new DanhSachDuAn();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);
        } else if (id == R.id.loaiSP) {
            Fragment fragment = new DanhSachLoaiSP();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);

        } else if (id == R.id.lo) {
            Fragment fragment = new DanhSachLo();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);

        } else if (id == R.id.sanPham) {
            Fragment fragment = new DanhSachSanPham();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);

        } else if (id == R.id.danhSachTaiKhoan) {
            if (API.quyen.equals("NVBH")){
                toastWarning();
            } else {
                Fragment fragment = new DanhSachTaiKhoan();
                fragmentManager.beginTransaction()
                        .replace(R.id.framelayout, fragment).commit();

                toggle.setDrawerIndicatorEnabled(true);
            }

        }  else if (id == R.id.uuDai) {
            Fragment fragment = new DanhSachUuDai();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);

        }else if (id == R.id.hopDong) {

            Fragment fragment = new DanhSachHopDong();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);
        } else if (id == R.id.tkCaNhan) {
            Fragment fragment = new ThongTinCaNhan();
            fragmentManager.beginTransaction()
                    .replace(R.id.framelayout, fragment).commit();

            toggle.setDrawerIndicatorEnabled(true);

        } else if (id == R.id.dangXuat) {
            API.quyen = null;
            API.idUser = null;
            Intent i = new Intent(MainActivity.this, DangNhap.class);
            startActivity(i);

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void toastWarning(){
        Toast.makeText(this, "Bạn không có quyền sử dụng chức năng này", Toast.LENGTH_SHORT).show();
    }
}
