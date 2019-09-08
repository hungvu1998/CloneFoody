package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.CapnhatWifiController;
import com.example.admin.vidufirebase.Model.ChonHinhBinhLuanModel;
import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.InputFilter.InputFilterMinMax;

import java.util.ArrayList;
import java.util.Calendar;

public class ChamDIemActivity extends AppCompatActivity  {
    EditText edtViTri,edtGiaCa,edtChatLuong,edtDichVu,edtKhongGian;
    Button btnCapNhat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chamdiem);
        MultiDex.install(this);
        edtViTri=(EditText)findViewById(R.id.edtViTri);
        edtGiaCa=(EditText)findViewById(R.id.edtGiaCa);
        edtChatLuong=(EditText)findViewById(R.id.edtChatLuong);
        edtDichVu=(EditText)findViewById(R.id.edtDichVu);
        edtKhongGian=(EditText)findViewById(R.id.edtKhongGian);

        edtViTri.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        edtGiaCa.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        edtChatLuong.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        edtDichVu.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});
        edtKhongGian.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "10")});

        btnCapNhat=(Button)findViewById(R.id.btnChamDiem);



        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtViTri.getText().toString().trim().length()>0 && edtChatLuong.getText().toString().trim().length()>0 && edtDichVu.getText().toString().trim().length()>0 && edtGiaCa.getText().toString().trim().length()>0 && edtKhongGian.getText().toString().trim().length()>0 )
                {
                    double tinhdiem= (Double.parseDouble(edtViTri.getText().toString().trim())+Double.parseDouble(edtChatLuong.getText().toString().trim())+Double.parseDouble(edtDichVu.getText().toString().trim())+Double.parseDouble(edtGiaCa.getText().toString().trim())+Double.parseDouble(edtKhongGian.getText().toString().trim()))/5;
                    Intent data= new Intent();
                    data.putExtra("diemcham",tinhdiem);
                    //data.putExtra("diemcham",String.format("%.1f",tinhdiem));


                    setResult(RESULT_OK,data);
                    finish();
                }
                else
                {
                    Toast.makeText(ChamDIemActivity.this, "Vui lòng nhập đủ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
