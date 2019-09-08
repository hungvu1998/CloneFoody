package com.example.admin.vidufirebase.View;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.example.admin.vidufirebase.R;
import com.squareup.picasso.Picasso;

public class PopUpFullAnhActivity extends AppCompatActivity {
    ImageView imgAnh;
    Uri uri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_fullimage);
        imgAnh=(ImageView)findViewById(R.id.imgAnh);
        uri=getIntent().getData();
        Picasso.with(PopUpFullAnhActivity.this).load(uri).into(imgAnh);


    }
}
