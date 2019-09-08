package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThanhVienActivity extends AppCompatActivity {
    Toolbar toolbar;
    String mauser,hoten,linkhinh;
    TextView txtThanhVien;
    CircleImageView cicleImageUser;
    Button btnUpload;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhvien);
        AnhXa();
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mauser=getIntent().getStringExtra("mauser");
        hoten=getIntent().getStringExtra("hoten");
        linkhinh=getIntent().getStringExtra("linkhinh");
        txtThanhVien.setText(hoten);
        setHinhAnhUser(cicleImageUser,linkhinh);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(ThanhVienActivity.this,ChonHinhUserActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    void AnhXa(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        txtThanhVien=(TextView)findViewById(R.id.txtThanhVien);
        cicleImageUser=(CircleImageView)findViewById(R.id.cicleImageUser);
        btnUpload=(Button)findViewById(R.id.btnUpload);
    }
    private void setHinhAnhUser(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
