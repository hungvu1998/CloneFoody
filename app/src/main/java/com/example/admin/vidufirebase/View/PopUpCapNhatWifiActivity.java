package com.example.admin.vidufirebase.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.CapnhatWifiController;
import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopUpCapNhatWifiActivity extends AppCompatActivity{
    EditText edtTenWF,edtMatKhauWF;
    Button btnCapNhat;

    CapnhatWifiController capnhatWifiController;
    String maquanan;
    private volatile boolean done;
    private Handler handlerUpdate;
    Runnable runnableUpdate=new Runnable() {
        @Override
        public void run() {
            if(done)
                return;

            handlerUpdate.postDelayed(this,1000);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_capnhatwifi);
        MultiDex.install(this);
        edtMatKhauWF=(EditText)findViewById(R.id.edtMatKhauWifi);
        edtTenWF=(EditText)findViewById(R.id.edtTenWifi);
        btnCapNhat=(Button)findViewById(R.id.btnCapNhat);
        capnhatWifiController=new CapnhatWifiController(this);

        maquanan=getIntent().getStringExtra("maquanan");
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenwf =edtTenWF.getText().toString();
                String matkhau =edtMatKhauWF.getText().toString();
                if(tenwf.trim().length() >8 && matkhau.trim().length()>8){
                    Calendar calendar=Calendar.getInstance();

                    long timeMilli2 = (calendar.getTimeInMillis()-86400000)/1000L;
                    WifiQuanAnModel wifiQuanAnModel=new WifiQuanAnModel();
                    wifiQuanAnModel.setTen(tenwf);
                    wifiQuanAnModel.setMatkhau(matkhau);
                    wifiQuanAnModel.setNgaydang(timeMilli2);
                    capnhatWifiController.themWifi(PopUpCapNhatWifiActivity.this,wifiQuanAnModel,maquanan);
                    Toast.makeText(PopUpCapNhatWifiActivity.this, "Thêm thành công ", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }else {
                    Toast.makeText(PopUpCapNhatWifiActivity.this, "Yêu cầu nhập đủ ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
