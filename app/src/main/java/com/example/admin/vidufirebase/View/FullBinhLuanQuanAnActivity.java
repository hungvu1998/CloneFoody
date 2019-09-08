package com.example.admin.vidufirebase.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.content.Context;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.BinhLuanController;
import com.example.admin.vidufirebase.R;

public class FullBinhLuanQuanAnActivity extends AppCompatActivity {
    RecyclerView recyclerViewBinhLuan;
    BinhLuanController binhLuanController;
    ProgressBar PBLoad;
    String maquaan;
    NestedScrollView nestScrollViewChiTiet;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fullbinhluan);
        //maquaan=getIntent().getStringExtra("maquanan");
        maquaan="MAQA1";
        PBLoad = (ProgressBar) findViewById(R.id.PGLoad);
        recyclerViewBinhLuan=(RecyclerView)findViewById(R.id.RecyclerBinhLuanChiTietQuanAn);
        nestScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestScrollViewChiTiet);
        nestScrollViewChiTiet.smoothScrollBy(0, 0);

        binhLuanController=new BinhLuanController(FullBinhLuanQuanAnActivity.this);
        binhLuanController.getDanhSachBinhLuanController2(FullBinhLuanQuanAnActivity.this,maquaan,nestScrollViewChiTiet,recyclerViewBinhLuan,PBLoad);

    }
}
