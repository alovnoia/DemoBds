package com.example.minhkhai.demobds.no;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.sanpham.DanhSachSanPham;
import com.example.minhkhai.demobds.sanpham.SanPham;
import com.example.minhkhai.demobds.sanpham.SanPhamAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class DanhSachNo extends Fragment {

    ListView lvNo;
    ArrayList<No> arrNo = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_danh_sach_no,container,false);

        lvNo = (ListView) view.findViewById(R.id.lvLichNo);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadNo().execute("http://"+API.HOST+"/bds_project/public/No");
            }
        });

        lvNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietNo.class);
                No no = arrNo.get(position);
                intent.putExtra("MaHopDong", no.getMaHopDong());
                intent.putExtra("TenHopDong", "Hợp đồng " + no.getMaHopDong() + " - " + no.getTenKhachHang());
                startActivity(intent);
            }
        });

        return view;
    }

    private class LoadNo extends AsyncTask<String, String, String> {
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

                    if (object.getString("TrangThai").equals("DaTra"))
                        continue;
                    arrNo.add(new No(
                            object.getInt("MaNo"),
                            object.getInt("MaHopDong"),
                            object.getString("DotTra"),
                            object.getString("NgayTra"),
                            object.getString("TrangThai"),
                            object.getLong("Tien"),
                            object.getString("TenKhachHang")
                    ));
                }

                NoAdapter adapter = new NoAdapter(getActivity(), R.layout.item_no, arrNo);

                lvNo.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
