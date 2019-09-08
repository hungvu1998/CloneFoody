package com.example.admin.vidufirebase.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.ChiNhanhQuanAnModel;
import com.example.admin.vidufirebase.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.squareup.picasso.Picasso;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerPlace extends RecyclerView.Adapter<AdapterRecyclerPlace.ViewHolder> {
    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public AdapterRecyclerPlace(Context context, List<QuanAnModel> quanAnModelList,int resource){
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau,txtTieudebinhluan2,txtTieudebinhluan,txtNodungbinhluan2,txtNodungbinhluan,txtChamDiemBinhLuan,txtChamDiemBinhLuan2,txtTongBinhLuan,txtTongHinhBinhLuan,txtDiemTrungBinhQuanAn,txtDiaChiQuanAnODau,txtKhoanCachQuanAnODau;
        Button btnDatMonOdau;
        ImageView imageHinhQuanAnODau;
        CircleImageView cicleImageUser2,cicleImageUser;
        LinearLayout containerBinhLuan,containerBinhLuan2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenQuanAnOdau = (TextView) itemView.findViewById(R.id.txtTenQuanQuanOdau);
            btnDatMonOdau = (Button) itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhQuanAnODau = (ImageView) itemView.findViewById(R.id.imageHinhQuanAnOdau);
            txtNodungbinhluan = (TextView) itemView.findViewById(R.id.txtNodungbinhluan);
            txtNodungbinhluan2 = (TextView) itemView.findViewById(R.id.txtNodungbinhluan2);
            txtTieudebinhluan = (TextView) itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2 = (TextView) itemView.findViewById(R.id.txtTieudebinhluan2);
            cicleImageUser = (CircleImageView) itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2 = (CircleImageView) itemView.findViewById(R.id.cicleImageUser2);
            containerBinhLuan = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan2);
            txtChamDiemBinhLuan = (TextView) itemView.findViewById(R.id.txtChamDiemBinhLuan);
            txtChamDiemBinhLuan2 = (TextView) itemView.findViewById(R.id.txtChamDiemBinhLuan2);
            txtTongBinhLuan = (TextView) itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhBinhLuan = (TextView) itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiemTrungBinhQuanAn = (TextView) itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtDiaChiQuanAnODau = (TextView) itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtKhoanCachQuanAnODau = (TextView) itemView.findViewById(R.id.txtKhoangCachQuanAnODau);
            cardView = (CardView) itemView.findViewById(R.id.cardViewOdau);
        }
    }

    @Override
    public AdapterRecyclerPlace.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @SuppressLint("NewApi")
    @Override



    public void onBindViewHolder(final AdapterRecyclerPlace.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);


        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());


        if(quanAnModel.isGiaohang()){
            holder.btnDatMonOdau.setEnabled(true);
            Drawable drawable = context.getResources().getDrawable(R.drawable.button_radius_5dp);
            holder.btnDatMonOdau.setBackground(drawable);
        }
        else
        {
            Drawable drawable2 = context.getResources().getDrawable(R.drawable.button_radius_5dp_2);
            holder.btnDatMonOdau.setBackground(drawable2);
            holder.btnDatMonOdau.setEnabled(false);
        }

        if(quanAnModel.getHinhanhquanan().size() > 0){

              /*  StorageReference storageReferenceVideo=FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
                storageReferenceVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(context).load(uri).into(holder.imageHinhQuanAnODau);
                    }
                });*/

            //Picasso.with(context).load(quanAnModel.getUriList().get(0)).into(holder.imageHinhQuanAnODau);
        }
        if(quanAnModel.getBitmapList().size() > 0){

            holder.imageHinhQuanAnODau.setImageBitmap(quanAnModel.getBitmapList().get(0));
        }
        String diemcham = null;
        //Lấy danh sách bình luận của quán ăn
        if(quanAnModel.getBinhLuanModelList().size() >= 1){
            holder.containerBinhLuan.setVisibility(View.VISIBLE);
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
            setHinhAnhBinhLuan(holder.cicleImageUser,binhLuanModel.getThanhVienModel().getHinhanh());
            if(quanAnModel.getBinhLuanModelList().size() >= 2){
                holder.containerBinhLuan2.setVisibility(View.VISIBLE);
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + "");
               setHinhAnhBinhLuan(holder.cicleImageUser2,binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            else {
                holder.containerBinhLuan2.setVisibility(View.GONE);
            }
            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
            int tongsohinhbinhluan = 0;
            double tongdiem = 0;
            //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
            for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()){
                tongsohinhbinhluan += binhLuanModel1.getHinhanhBinhLuanList().size();
                tongdiem += binhLuanModel1.getChamdiem();
            }

            double diemtrungbinh = tongdiem/quanAnModel.getBinhLuanModelList().size();
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinh));
            if(diemtrungbinh>=5.0)
            {
                Drawable drawable = context.getResources().getDrawable(R.drawable.background_cycle);
                holder.txtDiemTrungBinhQuanAn.setBackground(drawable);
            }
            else
            {
                Drawable drawable = context.getResources().getDrawable(R.drawable.background_cycle2);
                holder.txtDiemTrungBinhQuanAn.setBackground(drawable);
            }





            if(tongsohinhbinhluan > 0){
                holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            }

        }
        else{
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
            Drawable drawable = context.getResources().getDrawable(R.drawable.background_cycle2);
            holder.txtDiemTrungBinhQuanAn.setBackground(drawable);

        }



        //Lấy chi nhánh quán ăn và hiển thị địa chỉ và km
       if(quanAnModel.getChiNhanhQuanAnModelList().size() > 0){
           ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()){
                if(chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }

            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoanCachQuanAnODau.setText(String.format("%.1f",chiNhanhQuanAnModelTam.getKhoangcach()) + " km");
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                    iChiTietQuanAn.putExtra("quanan",quanAnModel);
                    context.startActivity(iChiTietQuanAn);
                }
            });



    }
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri).into(circleImageView);
            }
        });
    }




        @Override
        public int getItemCount() {
            return quanAnModelList.size();
        }




}
