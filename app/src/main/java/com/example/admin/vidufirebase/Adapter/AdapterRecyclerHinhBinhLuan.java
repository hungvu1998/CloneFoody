package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.CapNhatDanhSachWifiActivity;
import com.example.admin.vidufirebase.View.HienThiChiTietActivity;
import com.example.admin.vidufirebase.View.PopUpCapNhatWifiActivity;
import com.example.admin.vidufirebase.View.PopUpFullAnhActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolderHinhBinhLuan> {
    Context context;
    int resource;

    List<Uri>listHinh2;
    BinhLuanModel binhLuanModel;
    boolean iChiTietBinhluan;
    public AdapterRecyclerHinhBinhLuan(Context context, int resource, List<Uri>listHinh2, BinhLuanModel binhLuanModel, boolean iChiTietBinhluan){
        this.context=context;
        this.iChiTietBinhluan=iChiTietBinhluan;
        this.resource=resource;
        this.listHinh2=listHinh2;
        this.binhLuanModel=binhLuanModel;




    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imgHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout khunghinhbinhluan;
        public ViewHolderHinhBinhLuan(@NonNull View itemView) {
            super(itemView);
            imgHinhBinhLuan=(ImageView)itemView.findViewById(R.id.imgBinhLuan);
            txtSoHinhBinhLuan=(TextView) itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khunghinhbinhluan=(FrameLayout)itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }
    @NonNull
    @Override
    public ViewHolderHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan= new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderHinhBinhLuan viewHolder, final int i) {


        Picasso.with(context).load(listHinh2.get(i)).into(viewHolder.imgHinhBinhLuan,new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
        //Tự làm clik full hình ảnh

        if(iChiTietBinhluan!=true){
            if(i==3){
                int sohinhconlai=listHinh2.size()-4;
                viewHolder.khunghinhbinhluan.setVisibility(View.VISIBLE);
                viewHolder.txtSoHinhBinhLuan.setText("+" +sohinhconlai);
                viewHolder.imgHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iChiTietBinhLuan = new Intent(context,HienThiChiTietActivity.class);
                        iChiTietBinhLuan.putExtra("binhluanmodel",binhLuanModel);
                        context.startActivity(iChiTietBinhLuan);
                    }
                });
            }
        }
        if(i!=3 || iChiTietBinhluan==true){
            viewHolder.imgHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PopUpFullAnhActivity.class);

                    intent.setData(listHinh2.get(i));
                    context.startActivity(intent);

                }
            });

        }

    }

    @Override
    public int getItemCount() {

        if(iChiTietBinhluan==false)
            if(listHinh2.size()<4)
                return listHinh2.size();
            else
                return 4;
        else
            return listHinh2.size();

    }


}
