package com.example.minhkhai.demobds.sanpham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.DanhSachDuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.khachhang.DanhSachKhachHang;
import com.example.minhkhai.demobds.khachhang.KhachHang;
import com.example.minhkhai.demobds.khachhang.KhachHangAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DanhSachSanPham extends Fragment {

    ListView lvSanPham;
    ArrayList<SanPham> arrSanPham;
    FloatingActionButton fabThemSP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_san_pham,container,false);

        arrSanPham = new ArrayList<>();
        lvSanPham = (ListView) view.findViewById(R.id.lvSanPham);
        fabThemSP = (FloatingActionButton) view.findViewById(R.id.fabThemSP);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSachSP().execute("http://"+API.HOST+"/bds_project/public/SanPham");
            }
        });

        fabThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChonDuAn.class);
                startActivity(intent);
            }
        });

        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CapNhatSanPham.class);
                intent.putExtra("MaSP", arrSanPham.get(position).getMaSP());
                intent.putExtra("MaDuAn", arrSanPham.get(position).getDuAn());
                startActivity(intent);
            }
        });
        return view;
    }

    private class LoadDanhSachSP extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                return API.GET_URL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    arrSanPham.add(new SanPham(
                            object.getInt("MaSP"),
                            //object.getString("TenSP"),
                            "Sản phẩm",
                            object.getString("TenLoaiSP"),
                            object.getInt("GiaBan"),
                            object.getInt("DuAn")
                    ));
                }

                SanPhamAdapter adapter = new SanPhamAdapter(getActivity(), R.layout.item_sp, arrSanPham);

                lvSanPham.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*@Override
    *//*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(DanhSachSanPham.this, AppMenu.class);
        startActivity(intent);
        return true;
    }*/


}
