package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerFood;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ControllerFood {
    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerFood adapterRecyclerFood;
    int itemdaco = 8;

    public ControllerFood(Context context){
        this.context = context;
        quanAnModel = new QuanAnModel();
    }
    public void getDanhSachQuanAnController(Context context, NestedScrollView nestedScrollView, RecyclerView recyclerFood, final ProgressBar progressBar,final Location vitrihientai ){
        final List<QuanAnModel> quanAnModelList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerFood.setLayoutManager(layoutManager);

        adapterRecyclerFood = new AdapterRecyclerFood(context,quanAnModelList, R.layout.custom_recyclerview_food);
        recyclerFood.setAdapter(adapterRecyclerFood);

        progressBar.setVisibility(View.VISIBLE);

        final PlaceInterface odauInterface = new PlaceInterface() {

            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {
                final List<Uri> bitmaps2=new ArrayList<>();
                //for(String linkhinh:quanAnModel.getHinhanhquanan()){
                StorageReference storageReferenceVideo=FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
                storageReferenceVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        bitmaps2.add(uri);
                        quanAnModel.setUriList(bitmaps2);
                        // if(quanAnModel.getUriList().size()==quanAnModel.getHinhanhquanan().size())
                        {
                            quanAnModelList.add(quanAnModel);
                            adapterRecyclerFood.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });
                //}

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
                        progressBar.setVisibility(View.GONE);
                    }
                }
                else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        quanAnModel.getDanhSachQuanAn(odauInterface,vitrihientai,itemdaco,0);
    }
}

