package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.admin.vidufirebase.Adapter.AdapterBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Controller.Interfaces.BinhLuanInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface2;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanController {
    BinhLuanModel binhLuanModel;
    Context context;
    AdapterBinhLuan adapterBinhLuan;
    QuanAnModel quanAnModel;
    int itemdaco = 6;
    public BinhLuanController(Context context){
        this.context=context;
        quanAnModel=new QuanAnModel();
        binhLuanModel=new BinhLuanModel();

    }
    public void ThemBinhLuan(String maquanan, BinhLuanModel binhLuanModel, final List<String> listHinh){
        binhLuanModel.ThemBinhLuan(maquanan,binhLuanModel,listHinh);
    }
    public void getDanhSachBinhLuanController( final RecyclerView recyclerBinhLuan, final ProgressBar progressBar, final String maquan){
        ChiTietQuanAnInterface2 chiTietQuanAnInterface2=new ChiTietQuanAnInterface2() {
            @Override
            public void HienThiChiTiet(final QuanAnModel quanAnModel) {
                //Lấy tổng điểm
                /*double tongdiem = 0;
                //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
                for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()){
                    tongdiem += binhLuanModel1.getChamdiem();
                }
                double diemtrungbinh = tongdiem/quanAnModel.getBinhLuanModelList().size();
                txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinh));
                //end

                txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");*/



                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerBinhLuan.setLayoutManager(layoutManager);
                adapterBinhLuan = new AdapterBinhLuan(context,R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModelList(),true);
                recyclerBinhLuan.setAdapter(adapterBinhLuan);
                adapterBinhLuan.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);


            }
        };

        quanAnModel.getDanhSachChiTiet2(chiTietQuanAnInterface2,maquan);

    }

    public void getDanhSachBinhLuanController2(final Context context, final String maquan, NestedScrollView nestedScrollView, RecyclerView recyclerBinhLuan, final ProgressBar progressBar){
        final List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerBinhLuan.setLayoutManager(layoutManager);
        adapterBinhLuan = new AdapterBinhLuan(context,R.layout.custom_layout_binhluan,binhLuanModelList,true);
        recyclerBinhLuan.setAdapter(adapterBinhLuan);

        progressBar.setVisibility(View.VISIBLE);
        final BinhLuanInterface binhLuanInterface = new BinhLuanInterface() {
            @Override
            public void HienThiDanhSachBinhLuan(BinhLuanModel binhLuanModel) {

                binhLuanModelList.add(binhLuanModel);
                adapterBinhLuan.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }
        };
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) !=null){//kiểm tra xem còn thằng con không
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        progressBar.setVisibility(View.VISIBLE);
                        itemdaco +=2 ;
                        binhLuanModel.getDanhSachBinhLuan2(binhLuanInterface,maquan,itemdaco,itemdaco-2);

                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                    }

                }

            }
        });
        binhLuanModel.getDanhSachBinhLuan2(binhLuanInterface,maquan,itemdaco,0);
    }
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(byte[] bytes, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length, options);
    }
}
