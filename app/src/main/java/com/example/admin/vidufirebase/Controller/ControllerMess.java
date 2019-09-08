package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.location.Location;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerTraLoi;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.TraLoiInterface;
import com.example.admin.vidufirebase.Model.Mess;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class ControllerMess {
    Context context;
    Mess mess;
    AdapterRecyclerTraLoi adapterRecyclerTraLoi;
    public ControllerMess(Context context){
        this.context = context;
        mess = new Mess();
    }
    public void getDanhSachTraLoi(Context context, ListView lstView){
        final List<Mess> messList = new ArrayList<>();


         adapterRecyclerTraLoi= new AdapterRecyclerTraLoi(R.layout.custom_recyclerview_traloicauhoi,messList,context);
        lstView.setAdapter(adapterRecyclerTraLoi);


        final TraLoiInterface traLoiInterface1=new TraLoiInterface() {
            @Override
            public void getDanhSachTraLoi(final Mess mess) {
                messList.add(mess);
                adapterRecyclerTraLoi.notifyDataSetChanged();

            }
        };
        mess.getDanhSachCauHoi(traLoiInterface1);
    }
}
