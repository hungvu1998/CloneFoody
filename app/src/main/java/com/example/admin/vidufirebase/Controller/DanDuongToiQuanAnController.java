package com.example.admin.vidufirebase.Controller;

import android.graphics.Color;
import android.util.Log;

import com.example.admin.vidufirebase.Model.DownloadPolyLine;
import com.example.admin.vidufirebase.Model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongToiQuanAnController {
    ParserPolyline parserPolyline;
    DownloadPolyLine downloadPolyLine;
    public DanDuongToiQuanAnController(){


    }
    public void HienThiToiDanDuongToiQuanAn(GoogleMap googleMap,String duongdan){
        parserPolyline=new ParserPolyline();
        downloadPolyLine=new DownloadPolyLine();
        downloadPolyLine.execute(duongdan);
        try {
            String dataJSOn = downloadPolyLine.get();
            List<LatLng> latLngList=  parserPolyline.LayDanhSachToaDo(dataJSOn);
            PolylineOptions polylineOptions= new PolylineOptions();
            polylineOptions.color(Color.RED);
            for(LatLng toado:latLngList){
                polylineOptions.add(toado);
            }

            Polyline polyline = googleMap.addPolyline(polylineOptions);



        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
