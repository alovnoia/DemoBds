package com.example.minhkhai.demobds.lo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.appmenu.AppMenu;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DanhSachLo extends Fragment {

    ListView lvLo;
    ArrayList<Lo> mangLo;
    FloatingActionButton fab_Them;
    Spinner spnDuAnOnLo;
    ArrayList<DuAn> mangDuAn = new ArrayList<>();
    DuAn duAn;
    String tenDuAn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_danh_sach_lo, container, false);
        //Test

        mangLo = new ArrayList<Lo>();
        lvLo = (ListView) view.findViewById(R.id.lvDanhSachLo);
        fab_Them = (FloatingActionButton) view.findViewById(R.id.fab_ThemLo);
        spnDuAnOnLo = (Spinner) view.findViewById(R.id.spnDuAnOnDSLo);

        if (API.quyen.equals("NVBH")) {
            fab_Them.setVisibility(View.GONE);
        }

        Bundle extras = getActivity().getIntent().getExtras();
        if (extras!=null)
        {
            tenDuAn = extras.getString("TenDuAn");
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDuAn().execute("http://"+API.HOST+"/bds_project/public/DuAn");
            }
        });

        spnDuAnOnLo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duAn = (DuAn) spnDuAnOnLo.getSelectedItem();
                new LoadDanhSachLo().execute("http://"+ API.HOST+"/bds_project/public/Lo");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        fab_Them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), ThemLo.class);
                i.putExtra("TenDuAn", duAn.getTenDuAn());
                i.putExtra("MaDuAn", duAn.getMaDuAn());
                startActivity(i);
            }
        });

        lvLo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), ChiTietLo.class);
                i.putExtra("id", mangLo.get(position).maLo);
                i.putExtra("TenLo", mangLo.get(position).tenLo);
                i.putExtra("TenDuAn", mangLo.get(position).tenDuAn);
                startActivity(i);
            }
        });

        return view;
    }


    public class LoadDuAn extends AsyncTask<String, Integer, String>{

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
                for (int i=0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    mangDuAn.add(new DuAn(
                            object.getInt("MaDuAn"),
                            object.getString("TenDuAn"),
                            object.getString("DiaChi")
                    ));
                }

                final ArrayAdapter<DuAn> adapter = new ArrayAdapter(getActivity(),
                        android.R.layout.simple_spinner_item, mangDuAn);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnDuAnOnLo.setAdapter(adapter);

                if (tenDuAn!= null){
                    for (int i =0; i< mangDuAn.size(); i++){
                        if (tenDuAn.equals(adapter.getItem(i).getTenDuAn())){
                            spnDuAnOnLo.setSelection(i);
                            break;
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class LoadDanhSachLo extends AsyncTask<String, Integer, String>{

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
                mangLo.clear();
                for (int i = 0; i<array.length(); i++){
                    JSONObject object = array.getJSONObject(i);
                    if (duAn.getTenDuAn().equals(object.getString("TenDuAn")))
                    {
                        mangLo.add(new Lo(
                                object.getInt("MaLo"),
                                object.getString("TenLo"),
                                object.getString("TenDuAn"),
                                object.getInt("DuAn")
                        ));
                    }
                }
                LoAdapter adapter = new LoAdapter(getActivity(), R.layout.item_lo, mangLo);
                lvLo.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
