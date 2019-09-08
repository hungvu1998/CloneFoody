package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.ChiNhanhQuanAnModel;
import com.example.admin.vidufirebase.Model.Mess;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerTraLoi extends BaseAdapter {
    int resource;
    List<Mess> messList;
    Context context;
    public AdapterRecyclerTraLoi(int resource,List<Mess>list,Context context){
        this.resource=resource;
        this.messList=list;
        this.context=context;
    }


    @Override
    public int getCount() {
         return  2;//messList.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =inflater.inflate(R.layout.custom_recyclerview_traloicauhoi,null);

        TextView txtNguoiGui,txtVanDe;
        Button btnGui;
        EditText edtCauTraLoi;
        txtNguoiGui=(TextView)convertView.findViewById(R.id.txtNguoiGui);
        txtVanDe=(TextView)convertView.findViewById(R.id.txtVanDe);
        edtCauTraLoi=(EditText)convertView.findViewById(R.id.edtNoidungTraLoi);
        btnGui=(Button)convertView.findViewById(R.id.btnGui);
        final Mess mess = messList.get(position);
        txtNguoiGui.setText(mess.getNguoigui());
        txtVanDe.setText(mess.getNoidung());
        return convertView;
    }



}
