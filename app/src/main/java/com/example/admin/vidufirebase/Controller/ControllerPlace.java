package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.HienThiChiTietActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ControllerPlace {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerPlace adapterRecyclerOdau;
    int itemdaco = 4;

    public ControllerPlace(Context context){
        this.context = context;
        quanAnModel = new QuanAnModel();
    }


    public void getDanhSachQuanAnController(final Context context, NestedScrollView nestedScrollView, RecyclerView recyclerOdau, final ProgressBar progressBar, final Location vitrihientai){
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);

        recyclerOdau.setLayoutManager(layoutManager);

        adapterRecyclerOdau = new AdapterRecyclerPlace(context,quanAnModelList, R.layout.custom_recyclerview_place);
        recyclerOdau.setAdapter(adapterRecyclerOdau);

        progressBar.setVisibility(View.VISIBLE);

        final PlaceInterface odauInterface = new PlaceInterface() {

            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {

                final List<Bitmap> bitmaps = new ArrayList<>();

                for(String linkhinh : quanAnModel.getHinhanhquanan()){

                    StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = decodeSampledBitmapFromResource(bytes, 100, 100);
                            bitmaps.add(bitmap);
                            quanAnModel.setBitmapList(bitmaps);

                            if(quanAnModel.getBitmapList().size() == quanAnModel.getHinhanhquanan().size()){
                                quanAnModelList.add(quanAnModel);
                                adapterRecyclerOdau.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
                }


            }
        };
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(v.getChildAt(v.getChildCount() - 1) !=null){//kiểm tra xem còn thằng con không
                    if(scrollY >= (v.getChildAt(v.getChildCount() - 1)).getMeasuredHeight() - v.getMeasuredHeight()){
                        progressBar.setVisibility(View.VISIBLE);
                        itemdaco +=2 ;
                        quanAnModel.getDanhSachQuanAn(odauInterface,vitrihientai,itemdaco,itemdaco-2);


                    }
                    else {
                        //progressBar.setVisibility(View.GONE);
                    }

                }

            }
        });

        quanAnModel.getDanhSachQuanAn(odauInterface,vitrihientai,itemdaco,0);
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
