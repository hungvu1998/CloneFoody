package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.vidufirebase.Adapter.AdapterDanhSachWifi;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;

import java.util.ArrayList;
import java.util.List;

public class CapnhatWifiController {
    WifiQuanAnModel wifiQuanAnModel;
    Context context;
    List<WifiQuanAnModel> wifiQuanAnModelList;
    AdapterDanhSachWifi adapterDanhSachWifi;
    Byte data[]= new Byte[1024];
    public CapnhatWifiController(Context context){

        wifiQuanAnModel=new WifiQuanAnModel();
        this.context=context;
    wifiQuanAnModelList =new ArrayList<>();
    }
    public void HienThiDanhSachWifi(String maquaan, final RecyclerView recyclerView, final Context context, final ProgressBar progressBar){

        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        ChiTietQuanAnInterface chiTietQuanAnInterface=new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWiFi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                adapterDanhSachWifi=new AdapterDanhSachWifi(context, R.layout.layout_wifi_chitietquanan,wifiQuanAnModelList);

                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


            }

        };




        wifiQuanAnModel.LayDanhSachWiFiQuanAn(maquaan,chiTietQuanAnInterface,wifiQuanAnModelList);
    }


    public void themWifi(Context context, WifiQuanAnModel wifiQuanAnModel,String maquanan){

        wifiQuanAnModel.ThemWifiQuanAN(context,wifiQuanAnModel,maquanan);


    }
}
