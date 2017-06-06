package com.example.minhkhai.demobds.no;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkhai.demobds.MainActivity;
import com.example.minhkhai.demobds.R;
import com.example.minhkhai.demobds.duan.DuAn;
import com.example.minhkhai.demobds.hotro.API;
import com.example.minhkhai.demobds.loaisp.CapNhatLoaiSP;
import com.example.minhkhai.demobds.uudai.CapNhatUuDai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietNo extends AppCompatActivity {

    TextView tvTenHopDong;
    ListView lvChiTietNo;
    int id = 0;
    ArrayList<No> lstChiTietNo = new ArrayList<>();
    ArrayAdapter<No> adapter = null;
    JSONArray jsonNo = new JSONArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_no);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Bundle extras = getIntent().getExtras();
        id = extras.getInt("MaHopDong");

        lvChiTietNo = (ListView) findViewById(R.id.lvChiTietNo);
        lvChiTietNo.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        tvTenHopDong = (TextView) findViewById(R.id.tvTenHopDong);
        tvTenHopDong.setText(extras.getString("TenHopDong"));

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadDotNo().execute("http://"+ API.HOST+"/bds_project/public/ChiTietNo/"+id);
            }
        });

        lvChiTietNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final No no = (No) lvChiTietNo.getItemAtPosition(position);
                final Dialog dialog = new Dialog(ChiTietNo.this);
                dialog.setContentView(R.layout.dialog_duyet_no);
                dialog.setTitle(no.getDotTra());

                TextView tvNgayTraNo = (TextView) dialog.findViewById(R.id.tvNgayTraNo);
                TextView tvSoTienTraNo = (TextView) dialog.findViewById(R.id.tvSoTienTraNo);
                final Button btnDuyet = (Button) dialog.findViewById(R.id.btnDuyet);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);

                tvNgayTraNo.setText(API.convertDate(no.getNgayTra()));
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                tvSoTienTraNo.setText(formatter.format(no.getTien())+ " ₫");

                if (no.getTrangThai().equals("DaTra")){
                    btnDuyet.setVisibility(View.GONE);
                    lvChiTietNo.setItemChecked(position, true);
                    adapter.notifyDataSetChanged();

                } else {
                    btnHuy.setVisibility(View.GONE);
                    lvChiTietNo.setItemChecked(position, false);
                    adapter.notifyDataSetChanged();
                }
                dialog.show();
                btnDuyet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnDuyet.setBackgroundColor(getResources().getColor(R.color.btnDuyetOnclick));
                        lvChiTietNo.setItemChecked(position, true);
                        no.setTrangThai("DaTra");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new DuyetThanhToan().execute("http://"+ API.HOST+"/bds_project/public/duyetThanhToan/"+no.getMaNo());
                            }
                        });
                        API.change = true;
                        Toast.makeText(ChiTietNo.this, "Đã duyệt nợ " + no.getDotTra(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnDuyet.setBackgroundColor(getResources().getColor(R.color.btnDuyetOnclick));
                        lvChiTietNo.setItemChecked(position, false);
                        no.setTrangThai("ChuaTra");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new DuyetThanhToan().execute("http://"+ API.HOST+"/bds_project/public/huyThanhToan/"+no.getMaNo());
                            }
                        });
                        API.change = true;
                        Toast.makeText(ChiTietNo.this, "Đã hủy duyệt nợ " + no.getDotTra(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                adapter.notifyDataSetChanged();
            }
        });


    }

    private class DuyetThanhToan extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return API.GET_URL(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class LoadDotNo extends AsyncTask<String, String, String> {

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
                    lstChiTietNo.add(new No(
                            object.getInt("MaNo"),
                            object.getInt("MaHopDong"),
                            object.getString("NgayTra"),
                            object.getString("TrangThai"),
                            object.getString("DotTra"),
                            object.getLong("Tien")
                    ));
                }
                adapter = new ArrayAdapter<No>(ChiTietNo.this,
                        android.R.layout.simple_list_item_checked, lstChiTietNo);
                lvChiTietNo.setAdapter(adapter);
                for (int i = 0; i < lvChiTietNo.getCount(); i++) {
                    No no = (No) lvChiTietNo.getItemAtPosition(i);
                    if (no.getTrangThai().equals("DaTra")){
                        lvChiTietNo.setItemChecked(i, true);
                    } else {
                        lvChiTietNo.setItemChecked(i, false);
                    }
                    adapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (API.change) {
                Intent i = new Intent(ChiTietNo.this, MainActivity.class);
                i.putExtra("key", "No");
                API.change = false;
                startActivity(i);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
