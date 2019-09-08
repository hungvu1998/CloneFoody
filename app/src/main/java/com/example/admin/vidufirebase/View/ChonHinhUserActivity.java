package com.example.admin.vidufirebase.View;

import android.Manifest;
import android.content.Intent;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vidufirebase.Adapter.AdapterChonHinhBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterChonHinhUser;
import com.example.admin.vidufirebase.Model.ChonHinhBinhLuanModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhUserActivity extends AppCompatActivity {
    List<String>listDuongDan;
    RecyclerView recyclerChonHinhBinhLuan;
    AdapterChonHinhUser adapterChonHinhUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinhuser);
        listDuongDan=new ArrayList<>();
        recyclerChonHinhBinhLuan=(RecyclerView)findViewById(R.id.recyclerChonHinhUser);
        int checkReadExStorage = ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE);
        if(checkReadExStorage != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            getTatCaHinhAnhTrongTheNho();


        }
        /*RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        adapterChonHinhUser = new AdapterChonHinhUser(this,R.layout.custom_layout_chonhinhbinhluan,listDuongDan);
        recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
        recyclerChonHinhBinhLuan.setAdapter(adapterChonHinhUser);*/
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
            while (!cursor.isAfterLast()){
                String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                listDuongDan.add(duongdan);
                //adapterChonHinhBinhLuan.notifyDataSetChanged();
                cursor.moveToNext();
            }
    }

}
