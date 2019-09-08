package com.example.admin.vidufirebase.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.example.admin.vidufirebase.Adapter.AdapterBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhNoiBat;
import com.example.admin.vidufirebase.Controller.BinhLuanController;
import com.example.admin.vidufirebase.Controller.ChiTietQuanAnController;
import com.example.admin.vidufirebase.Controller.ThucDonController;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.Model.ThucDonModel;
import com.example.admin.vidufirebase.Model.TienIchModel;
import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView txtTenQuanAn, txtDiaChi, txtTieuDeToolBar, txtThoiGianHoatDong, txtTrangThaiHoatDong, txtTongSoHinhAnh, txtTongSoBinhLuan,
            txtTongSoCheckIn, txtTongSoLuuLai, txtChuaCo, txtGioiHanGia, txtTenWifi, txtMatKhauWifi, txtNgayDangWifi, txtDiemTrungBinhQuanAn;
    ImageView imHinhAnhQuanAn, imgPlayTrailer;
    ProgressBar PBLoad;
    VideoView videoview;
    QuanAnModel quanAnModel;
    GoogleMap googleMap;
    Toolbar toolbar;
    RecyclerView recyclerViewBinhLuan, recyclerHinhNoiBat;
    AdapterBinhLuan adapterBinhLuan;
    AdapterRecyclerHinhNoiBat adapterRecyclerHinhNoiBat;
    LinearLayout khungtienich, khungwifi, khungthucdon, khungwifi2;
    NestedScrollView nestScrollViewChiTiet;
    MapFragment mapFragment;
    ChiTietQuanAnController chiTietQuanAnController;
    SharedPreferences sharedPreferences;
    View khungtinhnang;
    Button btnBinhLuan, btnCheckIn,btnXemThemBinhLuan,btnXemChiNhanh;
    long ONE_MEGABYTE = 1024 * 1024;
    String diem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        MultiDex.install(this);
        quanAnModel = getIntent().getParcelableExtra("quanan");


        AnhXa();
        mapFragment.getMapAsync(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedPreferences = this.getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        HienThiChiTiet();
        chiTietQuanAnController.HienThiBinhLuan(quanAnModel.getMaquanan(),txtDiemTrungBinhQuanAn,txtTongSoBinhLuan,recyclerViewBinhLuan);


        nestScrollViewChiTiet.smoothScrollBy(0, 0);

        imgPlayTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoview.start();
                MediaController mediaController = new MediaController(ChiTietQuanAnActivity.this);
                videoview.setMediaController(mediaController);
                imgPlayTrailer.setVisibility(View.GONE);
            }
        });
        khungtinhnang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietQuanAnActivity.this, DuongDanToiQuanAnActivity.class);
                intent.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                intent.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(intent);

            }
        });
        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietQuanAnActivity.this, BinhLuanActivity.class);
                intent.putExtra("tenquan", quanAnModel.getTenquanan());
                intent.putExtra("diachi", quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                intent.putExtra("maquan", quanAnModel.getMaquanan());
                startActivity(intent);
            }
        });
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietQuanAnActivity.this, DuongDanToiQuanAnActivity.class);
                intent.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                intent.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(intent);
            }
        });
        khungwifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietQuanAnActivity.this, CapNhatDanhSachWifiActivity.class);
                intent.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(intent);
            }
        });
        khungthucdon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTietQuanAnActivity.this, ThucDonActivity.class);
                intent.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(intent);
            }
        });
        btnXemChiNhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietQuanAn = new Intent(ChiTietQuanAnActivity.this, ChiNhanhActivity.class);
                iChiTietQuanAn.putExtra("quanan",quanAnModel);
                startActivity(iChiTietQuanAn);
            }
        });
        /*btnXemThemBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ChiTietQuanAnActivity.this,FullBinhLuanQuanAnActivity.class);
                intent.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(intent);
            }
        });*/

        int i = 0;

        while (i < quanAnModel.getTienIchModelList().size()) {
            if (quanAnModel.getTienIchModelList().get(i).getTentienich().equals("Wifi")) {
                khungwifi.setVisibility(View.VISIBLE);
                if (quanAnModel.getWifiQuanAnModelList().size() > 0) {
                    chiTietQuanAnController.HienThiDanhSachWiFiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatKhauWifi, txtNgayDangWifi, txtDiemTrungBinhQuanAn, txtTongSoBinhLuan);
                    khungwifi2.setVisibility(View.VISIBLE);
                    txtChuaCo.setVisibility(View.GONE);

                } else {
                    khungwifi2.setVisibility(View.GONE);
                    txtChuaCo.setVisibility(View.VISIBLE);
                }
                break;
            } else {
                khungwifi.setVisibility(View.GONE);
            }
            i++;
        }


    }

    void HienThiChiTiet() {

        if(quanAnModel.getVideogioithieu()!=null){
            videoview.setVisibility(View.VISIBLE);
            imgPlayTrailer.setVisibility(View.VISIBLE);
            imHinhAnhQuanAn.setVisibility(View.GONE);
            //down video
            StorageReference storageReferenceVideo=FirebaseStorage.getInstance().getReference().child("video").child(quanAnModel.getVideogioithieu());
            storageReferenceVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Picasso.with(context).load(uri).into((Target) videoview);
                    videoview.setVideoURI(uri);
                    videoview.seekTo(1);
                }
            });


        }
        else {
            videoview.setVisibility(View.GONE);
            imgPlayTrailer.setVisibility(View.GONE);
            imHinhAnhQuanAn.setVisibility(View.VISIBLE);
            //Lấy ảnh banner
            StorageReference storageReferenceAnhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
            storageReferenceAnhQuanAn.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(ChiTietQuanAnActivity.this).load(uri).into(imHinhAnhQuanAn);
                }
            });
        }
        //Lấy ảnh banner

        //end lay anh
        txtTenQuanAn.setText(quanAnModel.getTenquanan().toString());

        //Lấy tổng điểm
        double tongdiem = 0;
        //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
        for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()){
            tongdiem += binhLuanModel1.getChamdiem();
        }
        double diemtrungbinh = tongdiem/quanAnModel.getBinhLuanModelList().size();
        txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinh));
        //end

        txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");
        //lay gio dong cua
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();

        try {
            Date hientai = dateFormat.parse(giohientai);
            Date mocua = dateFormat.parse(giomocua);
            Date donngcua = dateFormat.parse(giodongcua);

            if (hientai.after(mocua) && hientai.before(donngcua)) {
                txtTrangThaiHoatDong.setText("Đang mở cửa");
                txtTrangThaiHoatDong.setTextColor(Color.GREEN);
            } else {
                txtTrangThaiHoatDong.setTextColor(Color.RED);
                txtTrangThaiHoatDong.setText("Đã đóng  cửa");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //end
        txtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " + quanAnModel.getGiodongcua());
        txtTongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        /* txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");*/
        txtTieuDeToolBar.setText(quanAnModel.getTenquanan() + "");
        long giatoida = quanAnModel.getGiatoida();
        long giatoithieu = quanAnModel.getGiatoithieu();
        if (giatoida != 0 && giatoithieu != 0) {
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieuu = numberFormat.format(giatoithieu) + " đ";
            String giatoidad = numberFormat.format(giatoida) + " đ";
            txtGioiHanGia.setText(giatoithieuu + " - " + giatoidad);
        } else {
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }

        //lấy hình ảnh tiện ích
        int i = 0;
        while (i < quanAnModel.getTienIchModelList().size()) {
            int vitri = i;
            String x = quanAnModel.getTienIchModelList().get(vitri).getHinhtienich();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(x);

            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    ImageView imgTienIch = new ImageView(ChiTietQuanAnActivity.this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                    imgTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                    layoutParams.setMargins(10, 10, 10, 10);
                    imgTienIch.setLayoutParams(layoutParams);
                    imgTienIch.setPadding(5, 5, 5, 5);
                    Picasso.with(ChiTietQuanAnActivity.this).load(uri).into(imgTienIch);

                    khungtienich.addView(imgTienIch);
                }
            });

            i++;
        }

        //Lấy hình ảnh quán ăn nổi bật
        final List<Uri> uriList = new ArrayList<>();
        for (String linkhinh : quanAnModel.getHinhanhquanan()) {
            StorageReference storageHinhNoiBat = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            storageHinhNoiBat.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    uriList.add(uri);
                    if (uriList.size() == quanAnModel.getHinhanhquanan().size()) {
                        adapterRecyclerHinhNoiBat = new AdapterRecyclerHinhNoiBat(ChiTietQuanAnActivity.this, R.layout.custom_layout_hinhnoibat, uriList, quanAnModel, false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChiTietQuanAnActivity.this, 2);//2 cột
                        recyclerHinhNoiBat.setLayoutManager(layoutManager);
                        recyclerHinhNoiBat.setAdapter(adapterRecyclerHinhNoiBat);
                        adapterRecyclerHinhNoiBat.notifyDataSetChanged();

                    }
                }
            });

        }
        //end

        //Lấy bình luận
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ChiTietQuanAnActivity.this);
        recyclerViewBinhLuan.setLayoutManager(layoutManager);
        if (quanAnModel.getBinhLuanModelList().size() > 0) {
            //Cách 1 : Dùng bubble sort
           /* int n = quanAnModel.getBinhLuanModelList().size();
            for (int ii = 0; ii < n-1; ii++)
                for (int jj =ii+1; jj < n ;jj++)
                    if (quanAnModel.getBinhLuanModelList().get(ii).getLuotthich()< quanAnModel.getBinhLuanModelList().get(jj).getLuotthich())
                    {
                        BinhLuanModel temp = quanAnModel.getBinhLuanModelList().get(ii);
                        quanAnModel.getBinhLuanModelList().set(ii,quanAnModel.getBinhLuanModelList().get(jj));
                        quanAnModel.getBinhLuanModelList().set(jj,temp);
                    }*/
           //Cách 2: dùng implements Comparable trong BinhLuanModel
            Collections.sort(quanAnModel.getBinhLuanModelList());
        }
        adapterBinhLuan = new AdapterBinhLuan(ChiTietQuanAnActivity.this, R.layout.custom_layout_binhluan, quanAnModel.getBinhLuanModelList(), false);
        recyclerViewBinhLuan.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();
        //end

       /* if(quanAnModel.getBinhLuanModelList().size()>3){
            btnXemThemBinhLuan.setVisibility(View.VISIBLE);
        }
        else {
            btnXemThemBinhLuan.setVisibility(View.GONE);
        }*/

        if(quanAnModel.getChiNhanhQuanAnModelList().size()>1){
            btnXemChiNhanh.setVisibility(View.VISIBLE);
            btnXemChiNhanh.setText("Xem "+quanAnModel.getChiNhanhQuanAnModelList().size()+" chi nhánh cửa cửa hàng");
        }
        else {
            btnXemChiNhanh.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    void AnhXa() {
        PBLoad = (ProgressBar) findViewById(R.id.PGLoad);


        recyclerViewBinhLuan = (RecyclerView) findViewById(R.id.RecyclerBinhLuanChiTietQuanAn);
        recyclerHinhNoiBat = (RecyclerView) findViewById(R.id.recyclerHinhNoiBat);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtTieuDeToolBar = (TextView) findViewById(R.id.txtTieuDeToolbar);
        txtGioiHanGia = (TextView) findViewById(R.id.txtGioiHanGia);
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        txtThoiGianHoatDong = (TextView) findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = (TextView) findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoBinhLuan = (TextView) findViewById(R.id.tongSoBinhLuan);
        txtTongSoCheckIn = (TextView) findViewById(R.id.tongSoCheckIn);
        txtTongSoHinhAnh = (TextView) findViewById(R.id.tongSoHinhAnh);
        txtTongSoLuuLai = (TextView) findViewById(R.id.tongSoLuuLai);
        nestScrollViewChiTiet = (NestedScrollView) findViewById(R.id.nestScrollViewChiTiet);
        imHinhAnhQuanAn = (ImageView) findViewById(R.id.imHinhQuanAn);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        khungtienich = (LinearLayout) findViewById(R.id.khungtienich);
        chiTietQuanAnController = new ChiTietQuanAnController(ChiTietQuanAnActivity.this);
        txtTenWifi = (TextView) findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = (TextView) findViewById(R.id.txtMKWifi);
        khungwifi = (LinearLayout) findViewById(R.id.khungWifi);
        txtNgayDangWifi = (TextView) findViewById(R.id.txtNgaydang);
        khungtinhnang = (View) findViewById(R.id.khungtinhnang);
        btnBinhLuan = (Button) findViewById(R.id.btnBinhLuan);
        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        btnXemChiNhanh = (Button) findViewById(R.id.btnXemChiNhanh);
        //btnXemThemBinhLuan = (Button) findViewById(R.id.btnXemThemBinhLuan);
        txtChuaCo = (TextView) findViewById(R.id.txtChuaco);
        khungwifi2 = (LinearLayout) findViewById(R.id.khungwifi2);
        videoview = (VideoView) findViewById(R.id.videoTrailer);

        imgPlayTrailer = (ImageView) findViewById(R.id.playvideo);
        khungthucdon = (LinearLayout) findViewById(R.id.khungthucdon);
        txtDiemTrungBinhQuanAn = (TextView) findViewById(R.id.txtDiemTrungBinhQuanAn);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());
        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }

}
