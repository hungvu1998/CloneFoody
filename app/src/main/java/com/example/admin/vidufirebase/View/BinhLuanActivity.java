package com.example.admin.vidufirebase.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.admin.vidufirebase.Adapter.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.admin.vidufirebase.Controller.BinhLuanController;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity {
    TextView txttenquan,txtdiachi,txtDangBinhLuan;
    Toolbar toolbar;
    ImageButton btnChonHinhBinhLuan,btnChamDiem;
    RecyclerView recyclerViewChonHinhBinhLuan;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;
    final int REQUEST_CHONHINH_BINHLUAN=11;
    final int REQUEST_CHAMDIEM=12;
    String maquan;
    EditText edtTieuDe,edtNoiDung;
    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
    List<String>listHinhDuocChon;
    double diemcham=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        MultiDex.install(this);
        String tenquan =getIntent().getStringExtra("tenquan");
        String diachi =getIntent().getStringExtra("diachi");
         maquan=getIntent().getStringExtra("maquan");
        listHinhDuocChon=new ArrayList<>();

        txttenquan=(TextView)findViewById(R.id.txttenquan);
        txtdiachi=(TextView)findViewById(R.id.txtdiachi);
        toolbar= (Toolbar)findViewById(R.id.toolbar);
        txtDangBinhLuan=(TextView)findViewById(R.id.txtDangBinhLuan);
        btnChonHinhBinhLuan=(ImageButton)findViewById(R.id.btnChonHinhBinhLuan);
        btnChamDiem=(ImageButton)findViewById(R.id.btnChamDiem);
        edtNoiDung=(EditText)findViewById(R.id.edtNoiDung);
        edtTieuDe=(EditText)findViewById(R.id.edtTieuDe);
        recyclerViewChonHinhBinhLuan=(RecyclerView)findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewChonHinhBinhLuan.setLayoutManager(layoutManager);
        binhLuanController=new BinhLuanController(BinhLuanActivity.this);

        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txttenquan.setText(tenquan);
        txtdiachi.setText(diachi);
        btnChonHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BinhLuanActivity.this,ChonHinhBinhLuanActivity.class);


                intent.putStringArrayListExtra("listHinhDuocChon2",(ArrayList<String>)listHinhDuocChon);
                startActivityForResult(intent,REQUEST_CHONHINH_BINHLUAN);
            }
        });
        btnChamDiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BinhLuanActivity.this,ChamDIemActivity.class);

                startActivityForResult(intent,REQUEST_CHAMDIEM);
            }
        });

        txtDangBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diemcham<=0)
                {
                    Toast.makeText(BinhLuanActivity.this, "Vui lòng chấm điểm", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    BinhLuanModel binhLuanModel=new BinhLuanModel();
                    String tieude=edtTieuDe.getText().toString();
                    String noidung= edtNoiDung.getText().toString();
                    binhLuanModel.setTieude(tieude);
                    binhLuanModel.setNoidung(noidung);
                    binhLuanModel.setChamdiem(diemcham);
                    binhLuanModel.setLuotthich(0);
                    String mauser =sharedPreferences.getString("mauser","");
                    binhLuanModel.setUser(mauser);
                    binhLuanController.ThemBinhLuan(maquan,binhLuanModel,listHinhDuocChon);
                    onBackPressed();
                }

            }
        });
    }
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHONHINH_BINHLUAN){
            if(resultCode==RESULT_OK){
                listHinhDuocChon=data.getStringArrayListExtra("listHinhDuocChon");

                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this,R.layout.custom_layout_hienthihinhbinhluanduocchon,listHinhDuocChon);
                recyclerViewChonHinhBinhLuan.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
                adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();

            }
        }
        if(requestCode == REQUEST_CHAMDIEM){
            if(resultCode==RESULT_OK){
                diemcham=data.getDoubleExtra("diemcham",0);

                Drawable drawable = BinhLuanActivity.this.getResources().getDrawable(R.color.colorPrimary);
                btnChamDiem.setBackground(drawable);

            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        finish();
        return true;
    }
}
