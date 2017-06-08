package com.example.minhkhai.demobds.sanpham;

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

public class DanhSachSanPham extends Fragment {

    ListView lvSanPham;
    ArrayList<SanPham> arrSanPham;
    ArrayList<SanPham> arrSanPhamGoc = new ArrayList<>();
    FloatingActionButton fabThemSP;
    SanPhamAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_san_pham,container,false);
        setHasOptionsMenu(true);

        arrSanPham = new ArrayList<>();
        lvSanPham = (ListView) view.findViewById(R.id.lvSanPham);
        fabThemSP = (FloatingActionButton) view.findViewById(R.id.fabThemSP);

        if (API.quyen.equals("NVBH")) {
            fabThemSP.setVisibility(View.GONE);
        }

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

                    arrSanPhamGoc.add(new SanPham(
                            object.getInt("MaSP"),
                            //object.getString("TenSP"),
                            "Sản phẩm",
                            object.getString("TenLoaiSP"),
                            object.getString("GiaBan"),
                            object.getInt("DuAn"),
                            object.getString("TenDuAn")
                    ));
                }
                arrSanPham = new ArrayList<>(arrSanPhamGoc);
                adapter = new SanPhamAdapter(getActivity(), R.layout.item_sp, arrSanPham);

                lvSanPham.setAdapter(adapter);

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
            arrSanPham.clear();

            List<SanPham> lstSPAll = new ArrayList<SanPham>(arrSanPhamGoc);

            for (SanPham hh : lstSPAll) {
                if ((hh.getTenDuAn().toLowerCase()).contains(newText.toLowerCase()) ||
                        hh.getLoaiSP().toLowerCase().contains(newText.toLowerCase())) {
                    arrSanPham.add(hh);
                }
            }
        } else {
            arrSanPham.clear();
            for (SanPham hh : arrSanPhamGoc) {
                arrSanPham.add(hh);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
