package com.example.admin.vidufirebase.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.admin.vidufirebase.Adapter.AdapterChonHinhBinhLuan;
import com.example.admin.vidufirebase.Model.ChonHinhBinhLuanModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhBinhLuanActivity extends AppCompatActivity {
    List<ChonHinhBinhLuanModel> listDuongDan;
    List<String> listHinhDuocChon;

    RecyclerView recyclerChonHinhBinhLuan;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;
    ChonHinhBinhLuanModel chonHinhBinhLuanModel;
    TextView txtXong;
    ImageView imgCamera;
    List<Boolean>check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinhbinhluan);
        MultiDex.install(this);

        recyclerChonHinhBinhLuan=(RecyclerView)findViewById(R.id.recyclerChonHinhBinhLuan);
        txtXong=(TextView)findViewById(R.id.txtXong);
        imgCamera=(ImageView) findViewById(R.id.imgCamera);
        listDuongDan=new ArrayList<>();
        listHinhDuocChon=new ArrayList<>();

        recyclerChonHinhBinhLuan = (RecyclerView) findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(this,R.layout.custom_layout_chonhinhbinhluan,listDuongDan);
        recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
        recyclerChonHinhBinhLuan.setAdapter(adapterChonHinhBinhLuan);



        int checkReadExStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(checkReadExStorage != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
           getTatCaHinhAnhTrongTheNho();


        }

        txtXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ChonHinhBinhLuanModel value:listDuongDan){
                    if(value.isCheck()){
                        listHinhDuocChon.add(value.getDuongdan());
                    }
                }
                Intent data= new Intent();
                data.putStringArrayListExtra("listHinhDuocChon",(ArrayList<String>)listHinhDuocChon);
                setResult(RESULT_OK,data);

                finish();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getTatCaHinhAnhTrongTheNho();
            }
        }
    }
    public void getTatCaHinhAnhTrongTheNho(){
        String [] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
        cursor.moveToFirst();
        List<String>listHinhDuocChon2=getIntent().getStringArrayListExtra("listHinhDuocChon2");
        if(listHinhDuocChon2.size()<=0)
        {
            while (!cursor.isAfterLast()){
                String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan,false);//mặc định khi vừa tạo ra k có cho check
                listDuongDan.add(chonHinhBinhLuanModel);
                adapterChonHinhBinhLuan.notifyDataSetChanged();
                cursor.moveToNext();
            }
        }
        else {
            while (!cursor.isAfterLast()){
                String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                for(int i=0;i<listHinhDuocChon2.size();++i)
                {
                    if(duongdan.equals(listHinhDuocChon2.get(i)))
                    {
                        chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan,true);//mặc định khi vừa tạo ra k có cho check
                        break;
                    }
                    else {
                        chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan,false);//mặc định khi vừa tạo ra k có cho check
                    }
                }

                listDuongDan.add(chonHinhBinhLuanModel);
                adapterChonHinhBinhLuan.notifyDataSetChanged();
                cursor.moveToNext();
            }
        }


    }










}
