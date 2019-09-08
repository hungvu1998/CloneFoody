package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.ChiNhanhQuanAnModel;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.ChiTietQuanAnActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerFood extends RecyclerView.Adapter<AdapterRecyclerFood.ViewHolder> {
    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;
    public AdapterRecyclerFood(Context context, List<QuanAnModel> quanAnModelList,int resource){
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAn,txtDiemTrungBinhQuanAn,txtTongBinhLuan,txtTongHinhBinhLuan,txtDiaChiQuanAn,txtTheLoai;
        ImageView imageHinhQuanAn;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAn = (TextView) itemView.findViewById(R.id.txtTenQuanAn);
            imageHinhQuanAn = (ImageView) itemView.findViewById(R.id.imageHinhQuanAnFood);
            txtTongBinhLuan = (TextView) itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhBinhLuan = (TextView) itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiemTrungBinhQuanAn = (TextView) itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtDiaChiQuanAn = (TextView) itemView.findViewById(R.id.txtDiaChiQuanAn);
            txtTheLoai=(TextView) itemView.findViewById(R.id.txtTheLoai);

            cardView = (CardView) itemView.findViewById(R.id.cardViewFood);
        }
    }

    @Override
    public AdapterRecyclerFood.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        AdapterRecyclerFood.ViewHolder viewHolder = new AdapterRecyclerFood.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerFood.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);


        holder.txtTenQuanAn.setText(quanAnModel.getTenquanan());
        holder.txtTheLoai.setText(quanAnModel.getTheLoaiQuanAnModelList().get(0).getTentheloai());



        if(quanAnModel.getHinhanhquanan().size() > 0){

            Picasso.with(context).load(quanAnModel.getUriList().get(0)).into(holder.imageHinhQuanAn);
        }
        String diemcham = null;
        if(quanAnModel.getBinhLuanModelList().size() >= 1){

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

            if(tongsohinhbinhluan > 0){
                holder.txtTongHinhBinhLuan.setText(tongsohinhbinhluan + "");
            }

        }
        else{


        }



        //Lấy chi nhánh quán ăn và hiển thị địa chỉ và km
        if(quanAnModel.getChiNhanhQuanAnModelList().size() > 0){
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()){
                if(chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()){
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }

            holder.txtDiaChiQuanAn.setText(chiNhanhQuanAnModelTam.getDiachi());

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

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }


}
