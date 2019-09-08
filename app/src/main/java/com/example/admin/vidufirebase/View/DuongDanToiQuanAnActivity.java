package com.example.admin.vidufirebase.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.admin.vidufirebase.Controller.DanDuongToiQuanAnController;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DuongDanToiQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapFragment mapFragment;
    double latitude;
    double longitude;
    Location vitrihientai;
    SharedPreferences sharedPreferences;
    DanDuongToiQuanAnController danDuongToiQuanAnController;
    String duongdan="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danduong);
        MultiDex.install(this);
        danDuongToiQuanAnController=new DanDuongToiQuanAnController();
      latitude =getIntent().getDoubleExtra("latitude",0);
       longitude=getIntent().getDoubleExtra("longitude",0);

        sharedPreferences=getSharedPreferences("toado",Context.MODE_PRIVATE);
       vitrihientai =new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        duongdan="https://maps.googleapis.com/maps/api/directions/json?origin="+vitrihientai.getLatitude()+","+vitrihientai.getLongitude()+"&destination="+latitude+","+longitude+"&key=AIzaSyCSNQCX6UYnoiq-BSoaHRdQvmPovWRQeSY";

        mapFragment=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;

        googleMap.clear();

        LatLng latLng=new LatLng(vitrihientai.getLatitude(),vitrihientai.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions();
        markerOptions.position(latLng);

        googleMap.addMarker(markerOptions);

        LatLng vitriquanan=new LatLng(latitude,longitude);
        MarkerOptions markerOptionsVitriquanan=new MarkerOptions();
        markerOptionsVitriquanan.position(vitriquanan);

        googleMap.addMarker(markerOptionsVitriquanan);

        CameraUpdate cameraUpdate=CameraUpdateFactory.newLatLngZoom(latLng,14);
        googleMap.moveCamera(cameraUpdate);
        danDuongToiQuanAnController.HienThiToiDanDuongToiQuanAn(googleMap,duongdan);
    }
}
