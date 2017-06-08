package com.example.minhkhai.demobds.taikhoan;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaisp.LoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DanhSachTaiKhoan extends Fragment {

    ListView lvList;
    ArrayList<TaiKhoan> mangTaiKhoan;
    ArrayList<TaiKhoan> mangTaiKhoanGoc = new ArrayList<>();
    FloatingActionButton fab_Them;
    TaiKhoanAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_tai_khoan,container,false);
        setHasOptionsMenu(true);

        lvList = (ListView) view.findViewById(R.id.lvDanhSachTaiKhoan);
        mangTaiKhoan = new ArrayList<TaiKhoan>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDanhSachTK().execute("http://"+API.HOST+"/bds_project/public/TaiKhoan");
            }
        });

        fab_Them = (FloatingActionButton) view.findViewById(R.id.fab_ThemTaiKhoan);
        fab_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ThemTaiKhoan.class);
                startActivity(intent);
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietTaiKhoan.class);

                intent.putExtra("id", mangTaiKhoan.get(position).maTaiKhoan);

                startActivity(intent);
            }
        });
        return view;
    }

    private class LoadDanhSachTK extends AsyncTask<String, Integer, String> {

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

                for (int i = 0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    mangTaiKhoanGoc.add(new TaiKhoan(
                            object.getInt("MaTaiKhoan"),
                            object.getString("TenTaiKhoan"),
                            object.getString("MatKhau"),
                            object.getString("HoTen"),
                            object.getString("DiaChi"),
                            object.getString("NgaySinh"),
                            object.getString("SoDienThoai"),
                            object.getString("Anh"),
                            object.getString("ChucVu"),
                            object.getString("ThongTinKhac")
                    ));
                }
                mangTaiKhoan = new ArrayList<>(mangTaiKhoanGoc);
                adapter =new TaiKhoanAdapter(getActivity(), R.layout.item_taikhoan, mangTaiKhoan);
                lvList.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.search_action);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                TimKiem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                TimKiem(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void TimKiem (String newText) {
        if (!newText.isEmpty() && newText.length() > 0) {
            mangTaiKhoan.clear();

            List<TaiKhoan> lstTaiKhoanAll = new ArrayList<TaiKhoan>(mangTaiKhoanGoc);

            for (TaiKhoan hh : lstTaiKhoanAll) {
                if ((hh.hoTen.toLowerCase()).contains(newText.toLowerCase()) ||
                        (hh.chucVu.toLowerCase()).contains(newText.toLowerCase()) ||
                        (hh.tenTK.toLowerCase()).contains(newText.toLowerCase())) {
                    mangTaiKhoan.add(hh);
                }
            }
        } else {
            mangTaiKhoan.clear();
            for (TaiKhoan hh : mangTaiKhoanGoc) {
                mangTaiKhoan.add(hh);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
