package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Adapter.AdapterThucDon;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ThucDonInterface;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.Model.ThucDonModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class ThucDonController {
    ThucDonModel thucDonModel;


    AdapterThucDon adapterThucDon;
    public ThucDonController(){

        thucDonModel=new ThucDonModel();
    }

    public void getDanhSachThucDonQuanAnTheoMa(final Context context, String manquanan, final RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDon adapterThucDon = new AdapterThucDon(context,thucDonModelList);
                recyclerView.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDonModel.getDanhSachThucDonTheoMaQuan(manquanan,thucDonInterface);
    }
}
