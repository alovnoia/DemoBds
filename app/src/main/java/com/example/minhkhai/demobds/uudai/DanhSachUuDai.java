package com.example.minhkhai.demobds.uudai;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import com.example.minhkhai.demobds.khachhang.KhachHang;
import com.example.minhkhai.demobds.khachhang.KhachHangAdapter;
import com.example.minhkhai.demobds.loaisp.LoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DanhSachUuDai extends Fragment {

    ListView lvUuDai;
    ArrayList<UuDai> arrUuDai;
    ArrayList<UuDai> arrUuDaiGoc = new ArrayList<>();
    FloatingActionButton fabThemUuDai;
    UuDaiAdapter adapter = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_uu_dai,container,false);
        setHasOptionsMenu(true);

        fabThemUuDai = (FloatingActionButton) view.findViewById(R.id.fabThemUuDai);

        if (API.quyen.equals("NVBH")) {
            fabThemUuDai.setVisibility(View.GONE);
        }

        lvUuDai = (ListView) view.findViewById(R.id.lvUuDai);
        arrUuDai = new ArrayList<UuDai>();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoađUuDai().execute("http://"+API.HOST+"/bds_project/public/UuDai");
            }
        });

        fabThemUuDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ThemUuDai.class);
                startActivity(i);
            }
        });

        lvUuDai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CapNhatUuDai.class);
                intent.putExtra("id", arrUuDai.get(position).getMaUuDai());
                startActivity(intent);
            }
        });

        return view;
    }

    private class LoađUuDai extends AsyncTask<String, String, String>{

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

            try {
                JSONArray array = new JSONArray(s);
                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    arrUuDaiGoc.add(new UuDai(
                            object.getInt("MaUuDai"),
                            object.getString("TenUuDai"),
                            object.getString("NgayBatDau"),
                            object.getString("NgayKetThuc")
                    ));
                }
                arrUuDai = new ArrayList<>(arrUuDaiGoc);
                adapter = new UuDaiAdapter(getActivity(), R.layout.item_uudai, arrUuDai);

                lvUuDai.setAdapter(adapter);

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
            arrUuDai.clear();

            List<UuDai> lstUuDaiAll = new ArrayList<UuDai>(arrUuDaiGoc);

            for (UuDai hh : lstUuDaiAll) {
                if ((hh.getTenUuDai().toLowerCase()).contains(newText.toLowerCase())) {
                    arrUuDai.add(hh);
                }
            }
        } else {
            arrUuDai.clear();
            for (UuDai hh : arrUuDaiGoc) {
                arrUuDai.add(hh);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
