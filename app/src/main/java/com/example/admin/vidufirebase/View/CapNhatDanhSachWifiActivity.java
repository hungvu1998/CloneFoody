package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.admin.vidufirebase.Controller.CapnhatWifiController;
import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class CapNhatDanhSachWifiActivity extends AppCompatActivity {
    Button btnCapNhat;
    RecyclerView recyclerViewDanhsachWifi;
    CapnhatWifiController capnhatWifiController;
    String maquaan;

    ProgressBar progressBar;
    List<WifiQuanAnModel> wifiQuanAnModelList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);
        MultiDex.install(this);
        btnCapNhat=(Button)findViewById(R.id.btnCapNhatWifi);
        recyclerViewDanhsachWifi=(RecyclerView)findViewById(R.id.recyclerDanhSachWifi);
        progressBar=(ProgressBar)findViewById(R.id.PGLoad);
        wifiQuanAnModelList=new ArrayList<>();

        maquaan=getIntent().getStringExtra("maquanan");

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerViewDanhsachWifi.setLayoutManager(layoutManager);


        capnhatWifiController =new CapnhatWifiController(this);
        capnhatWifiController.HienThiDanhSachWifi(maquaan,recyclerViewDanhsachWifi,CapNhatDanhSachWifiActivity.this,progressBar);

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(CapNhatDanhSachWifiActivity.this,PopUpCapNhatWifiActivity.class);
               intent.putExtra("maquanan",maquaan);
               startActivity(intent);
            }
        });
    }
}
