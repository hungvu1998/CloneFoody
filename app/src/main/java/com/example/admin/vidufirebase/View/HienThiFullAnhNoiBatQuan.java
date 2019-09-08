package com.example.admin.vidufirebase.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhNoiBat;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiFullAnhNoiBatQuan extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView txtTieudeBinhLuan,txtNoiDungBinhLuan,txtSoDiem;
    RecyclerView recyclerViewHinhBinhLuan;
    List<Uri> uriList;
    AdapterRecyclerHinhNoiBat adapterRecyclerHinhNoiBat;
    QuanAnModel quanAnModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);
        MultiDex.install(this);
        AnhXa();
        quanAnModel=getIntent().getParcelableExtra("quanAnModel");


        txtTieudeBinhLuan.setText(quanAnModel.getTenquanan());
        txtNoiDungBinhLuan.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());

        double tongdiem = 0;
        //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
        for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()){
            tongdiem += binhLuanModel1.getChamdiem();
        }
        double diemtrungbinh = tongdiem/quanAnModel.getBinhLuanModelList().size();
        txtSoDiem.setText(String.format("%.1f",diemtrungbinh));



        for(String linkhinh:quanAnModel.getHinhanhquanan()){
            StorageReference storageHinhNoiBat = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            storageHinhNoiBat.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    uriList.add(uri);
                    if(uriList.size()==quanAnModel.getHinhanhquanan().size()){
                        adapterRecyclerHinhNoiBat =new AdapterRecyclerHinhNoiBat(HienThiFullAnhNoiBatQuan.this,R.layout.custom_layout_hinhnoibat,uriList,quanAnModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiFullAnhNoiBatQuan.this,2);//2 cột
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhNoiBat);
                        adapterRecyclerHinhNoiBat.notifyDataSetChanged();

                        adapterRecyclerHinhNoiBat.getSoluong();
                    }
                }
            });

        }

        //Log.d("kiemtra",""+x);

    }
    void AnhXa(){
        circleImageView =(CircleImageView)findViewById(R.id.cicleImageUser);
        txtTieudeBinhLuan=(TextView)findViewById(R.id.txtTieudebinhluan);
        txtNoiDungBinhLuan=(TextView)findViewById(R.id.txtNodungbinhluan);
        txtSoDiem=(TextView)findViewById(R.id.txtChamDiemBinhLuan);
        recyclerViewHinhBinhLuan=(RecyclerView)findViewById(R.id.recyclerHinhBinhLuan);
        uriList=new ArrayList<>();
    }

}
