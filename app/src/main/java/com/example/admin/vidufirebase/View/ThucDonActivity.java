package com.example.admin.vidufirebase.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.admin.vidufirebase.Controller.ThucDonController;
import com.example.admin.vidufirebase.R;

public class ThucDonActivity extends AppCompatActivity {
    ThucDonController thucDonController;
    RecyclerView recyclerThucDon;
    String maquaan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thucdon_chitiet);
        MultiDex.install(this);
        AnhXa();
        maquaan=getIntent().getStringExtra("maquanan");
        thucDonController=new ThucDonController();
        thucDonController.getDanhSachThucDonQuanAnTheoMa(this,maquaan,recyclerThucDon);
    }
    void AnhXa(){
        recyclerThucDon=(RecyclerView)findViewById(R.id.recyclerThucDon);
    }
}
