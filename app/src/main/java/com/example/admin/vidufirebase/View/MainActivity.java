package com.example.admin.vidufirebase.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.example.admin.vidufirebase.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity  {

    TextView txtPhienban;
    public static final int REQUEST_CODE = 1000;


    public static final int REQUEST_PERMISSION_LOCATION = 1;
    SharedPreferences sharedPreferences;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);
        txtPhienban = (TextView) findViewById(R.id.txtPhienBan);
        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        int checkPermissionCoarseLocaltion = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION);
        int checkPermissionFineLocation = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(checkPermissionCoarseLocaltion != PackageManager.PERMISSION_GRANTED && checkPermissionFineLocation != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
        }else{
            layvitri();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_PERMISSION_LOCATION:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    layvitri();
                }
                break;
        }
    }

    @SuppressLint("MissingPermission")
    void layvitri(){
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("latitude", String.valueOf(location.getLatitude()));
                    editor.putString("longitude", String.valueOf(location.getLongitude()));
                    editor.commit();
                    try {
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
                        txtPhienban.setText(getString(R.string.phienban) + " " + packageInfo.versionName);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent iDangNhap = new Intent(MainActivity.this,DangNhapActivity.class);
                                startActivity(iDangNhap);
                                finish();
                            }
                        },2000);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_PERMISSION_LOCATION);
                    Toast.makeText(MainActivity.this, "Bật GPS lên", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}
