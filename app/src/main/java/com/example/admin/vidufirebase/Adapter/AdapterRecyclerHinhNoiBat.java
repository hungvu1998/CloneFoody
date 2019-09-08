package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.HienThiChiTietActivity;
import com.example.admin.vidufirebase.View.HienThiFullAnhNoiBatQuan;
import com.example.admin.vidufirebase.View.PopUpFullAnhActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRecyclerHinhNoiBat extends RecyclerView.Adapter<AdapterRecyclerHinhNoiBat.ViewHolderHinhNoiBat> {
    Context context;
    int resource;
    List<Uri> uriList;
    QuanAnModel quanAnModel;
    boolean iChiTiet;
    String tenquan,diachi;
    int soluong=0;

    public AdapterRecyclerHinhNoiBat(Context context, int resource, List<Uri>uriList, QuanAnModel quanAnModel,boolean iChiTiet){
        this.context=context;
        this.resource=resource;
        this.uriList=uriList;
        this.quanAnModel=quanAnModel;
        this.iChiTiet=iChiTiet;

    }
    public class ViewHolderHinhNoiBat extends RecyclerView.ViewHolder {
        ImageView imgHinhNoiBat;
        TextView txtSoHinhNoiBat;
        FrameLayout khunghinhnoibat;
        public ViewHolderHinhNoiBat(@NonNull View itemView) {
            super(itemView);
            imgHinhNoiBat=(ImageView)itemView.findViewById(R.id.imgHinhNoiBat);
            txtSoHinhNoiBat=(TextView) itemView.findViewById(R.id.txtSoHinhNoiBat);
            khunghinhnoibat=(FrameLayout)itemView.findViewById(R.id.khungSoHinhNoiBat);
        }
    }
    @NonNull
    @Override
    public ViewHolderHinhNoiBat onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource,viewGroup,false);
        AdapterRecyclerHinhNoiBat.ViewHolderHinhNoiBat viewHolderHinhNoiBat=new AdapterRecyclerHinhNoiBat.ViewHolderHinhNoiBat(view);
        return viewHolderHinhNoiBat;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHinhNoiBat viewHolder, final int i) {

        Picasso.with(context).load(uriList.get(i)).into(viewHolder.imgHinhNoiBat,new com.squareup.picasso.Callback(){

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Toast.makeText(context, "not", Toast.LENGTH_SHORT).show();
            }
        });
        if(iChiTiet!=true){
            if(i==3){
                int sohinhconlai=uriList.size()-4;
                viewHolder.khunghinhnoibat.setVisibility(View.VISIBLE);
                viewHolder.txtSoHinhNoiBat.setText("+" +sohinhconlai);
                viewHolder.imgHinhNoiBat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iChiTietAnhNoiBat = new Intent(context,HienThiFullAnhNoiBatQuan.class);
                        iChiTietAnhNoiBat.putExtra("quanAnModel",quanAnModel);
                        context.startActivity(iChiTietAnhNoiBat);
                    }
                });
            }
        }
        if(i!=3 || iChiTiet==true){
            viewHolder.imgHinhNoiBat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PopUpFullAnhActivity.class);

                    intent.setData(uriList.get(i));
                    context.startActivity(intent);

                }
            });

        }
    }
    public void getSoluong(){
       // return this.soluong;
        Log.d("kiemtra",""+this.soluong);
    }
    @Override
    public int getItemCount() {
        if(iChiTiet==false)
            if(uriList.size()<4)
                return uriList.size();
            else
                return 4;
        else
            return uriList.size();
    }


}
