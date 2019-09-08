package com.example.admin.vidufirebase.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.Adapter.AdapterChiNhanh;
import com.example.admin.vidufirebase.Model.ChiNhanhQuanAnModel;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ChiNhanhActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapFragment mapFragment;
    View khungtinhnang;
    QuanAnModel quanAnModel;
    GoogleMap googleMap;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    RecyclerView recyclerView;
    int i=0;
    ArrayList<ChiNhanhQuanAnModel> mangchinhanh;
    ListView lstChiNhanh;
    TextView  txtTieuDeToolBar;
    AdapterChiNhanh adapterChiNhanh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chinhanh);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        khungtinhnang = (View) findViewById(R.id.khungtinhnang);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        quanAnModel = getIntent().getParcelableExtra("quanan");
        lstChiNhanh=(ListView)findViewById(R.id.lstChiNhanh);
        txtTieuDeToolBar = (TextView) findViewById(R.id.txtTieuDeToolbar);
        mangchinhanh=new ArrayList<ChiNhanhQuanAnModel>();
        adapterChiNhanh=new AdapterChiNhanh(ChiNhanhActivity.this,mangchinhanh);
        lstChiNhanh.setAdapter(adapterChiNhanh);




        mapFragment.getMapAsync(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sharedPreferences = this.getSharedPreferences("toado", Context.MODE_PRIVATE);
        Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude", "0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude", "0")));

        txtTieuDeToolBar.setText(quanAnModel.getTenquanan() + "");
        if(quanAnModel.getChiNhanhQuanAnModelList().size()>1)
        {
            for(int ii=0;ii<quanAnModel.getChiNhanhQuanAnModelList().size();ii++)
            {

                mangchinhanh.add(new ChiNhanhQuanAnModel(quanAnModel.getChiNhanhQuanAnModelList().get(ii).getDiachi(),quanAnModel.getChiNhanhQuanAnModelList().get(ii).getKhoangcach()));
            }

            adapterChiNhanh.notifyDataSetChanged();

        }
        lstChiNhanh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                i=position;
                onMapReady(googleMap);
            }
        });
        khungtinhnang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiNhanhActivity.this, DuongDanToiQuanAnActivity.class);
                intent.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(i).getLatitude());
                intent.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(i).getLongitude());
                startActivity(intent);


            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(i).getLatitude();
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(i).getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());
        googleMap.addMarker(markerOptions);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
