package com.example.minhkhai.demobds.sanpham;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minhkhai.demobds.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by minhkhai on 14/05/17.
 */

public class SanPhamAdapter extends BaseAdapter{

    Context myContext;
    int myLayout;
    List<SanPham> arrSanPham;

    public SanPhamAdapter(Context myContext, int myLayout, List<SanPham> arrSanPham) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrSanPham = arrSanPham;
    }

    @Override
    public int getCount() {
        return arrSanPham.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(myLayout, null);

        ImageView ivAnh = (ImageView) convertView.findViewById(R.id.ivDanhSachSPAnh);
        TextView tvTenSP = (TextView) convertView.findViewById(R.id.tvTenSP);
        TextView tvLoaiSP = (TextView) convertView.findViewById(R.id.tvSPLoaiSP);
        TextView tvGiaSP = (TextView) convertView.findViewById(R.id.tvDanhSachSPGia);

        //Bo sung anh sau
        /*try {
            URL urlAnh = new URL("http://xemanh.net/wp-content/uploads/2015/03/14548862185_6b22d16a64_b.jpg");
            Bitmap bmp;
            HttpURLConnection conn = (HttpURLConnection) urlAnh.openConnection();
            conn.setDoInput(true);
            bmp = BitmapFactory.decodeStream(conn.getInputStream());
            ivAnh.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        tvTenSP.setText(arrSanPham.get(position).getTenDuAn() + " - Mã " +
                String.valueOf(arrSanPham.get(position).getMaSP()));
        tvLoaiSP.setText(arrSanPham.get(position).getLoaiSP());
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        System.out.println(formatter.format(1000000)+" VNĐ");
        tvGiaSP.setText(String.valueOf(formatter.format(arrSanPham.get(position).getGiaSP()))+" VNĐ");

        return convertView;
    }
}
