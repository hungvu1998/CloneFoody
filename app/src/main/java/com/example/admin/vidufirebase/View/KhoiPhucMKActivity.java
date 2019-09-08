package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class KhoiPhucMKActivity extends AppCompatActivity {
    EditText edtEmailKhoiPhuc;
    Button btnKhoiPhuc;
    TextView txtDangKy,txtDangNhap;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);
        MultiDex.install(this);
        AnhXa();
        btnKhoiPhuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmailKhoiPhuc.getText().toString().trim().length()==0){
                    Toast.makeText(KhoiPhucMKActivity.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
               else if(kiemtraEmail(edtEmailKhoiPhuc.getText().toString())==false){
                    Toast.makeText(KhoiPhucMKActivity.this, "Định dạng Email sai", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.sendPasswordResetEmail(edtEmailKhoiPhuc.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(KhoiPhucMKActivity.this, "Gửi thành công", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoiPhucMKActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });
        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KhoiPhucMKActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });

    }
    private boolean kiemtraEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    void AnhXa(){
        btnKhoiPhuc=(Button) findViewById(R.id.btnKhoiPhucMK);
        edtEmailKhoiPhuc=(EditText)findViewById(R.id.edtEmailKhoiPhuc);
        txtDangKy=(TextView)findViewById(R.id.txtDangKyKP);
        txtDangNhap=(TextView)findViewById(R.id.txtDangNhapKP);
        firebaseAuth=FirebaseAuth.getInstance();
    }
}
