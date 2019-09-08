package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.ChiNhanhQuanAnModel;
import com.example.admin.vidufirebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterChiNhanh extends BaseAdapter {
    Context context;
    ArrayList<ChiNhanhQuanAnModel> arrayList;
    LayoutInflater inflater;
    public AdapterChiNhanh(Context context, ArrayList<ChiNhanhQuanAnModel> arrayList) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.arrayList = arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflater.inflate(R.layout.custom_dong_chinhanh,null);
        TextView txtDiaChiQuanAnODau = (TextView) view.findViewById(R.id.txtDiaChiQuanAnODau);
        TextView txtKhoanCachQuanAnODau = (TextView) view.findViewById(R.id.txtKhoangCachQuanAnODau);

        ChiNhanhQuanAnModel chiNhanhQuanAnModel = arrayList.get(position);

        txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModel.getDiachi());

        txtKhoanCachQuanAnODau.setText(String.format("%.1f",chiNhanhQuanAnModel.getKhoangcach()) + " km");
        return view;
    }
}
