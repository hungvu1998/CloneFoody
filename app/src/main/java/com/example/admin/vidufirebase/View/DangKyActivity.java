package com.example.admin.vidufirebase.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.ControllerDangKy;
import com.example.admin.vidufirebase.Model.ThanhVienModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKyActivity extends AppCompatActivity {
    Button btnDangKy;
    EditText edtTen,edtMK,edtMK2;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    TextView txtDangNhap,txtKhoiPhuc;
    ControllerDangKy controllerDangKy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);
        AnhXa();
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(getString(R.string.dangxuly));
               progressDialog.setIndeterminate(true);

                final String email=edtTen.getText().toString();

               String mk=edtMK.getText().toString();
                String mk2=edtMK2.getText().toString();
                if(email.trim().length()==0 || mk.trim().length() == 0 || mk2.trim().length() == 0 ){
                    Toast.makeText(DangKyActivity.this, "Yêu cầu điền đầy đủ thông tin !!!!" , Toast.LENGTH_SHORT).show();

                } else if(mk2.equals(mk)==false){
                   Toast.makeText(DangKyActivity.this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
              }
                else if(kiemtraEmail(email)==false){
                    Toast.makeText(DangKyActivity.this, "Định dạng Email sai", Toast.LENGTH_SHORT).show();
                }
                else {
                 progressDialog.show();
                   firebaseAuth.createUserWithEmailAndPassword(email,mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                ThanhVienModel thanhVienModel= new ThanhVienModel();
                                thanhVienModel.setHoten(email);
                                thanhVienModel.setHinhanh("user.png");

                                String userID=task.getResult().getUser().getUid();

                                controllerDangKy = new ControllerDangKy();
                                controllerDangKy.ThemthongtinThanhVienController(thanhVienModel,userID);

                                Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }

            }
        });
        txtKhoiPhuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this,KhoiPhucMKActivity.class);
                startActivity(intent);
            }
        });
        txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean kiemtraEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    void AnhXa(){
        btnDangKy=(Button)findViewById(R.id.btnDangKy);
        edtTen=(EditText)findViewById(R.id.edTenDangKy);
        edtMK=(EditText)findViewById(R.id.edMKDangKy);
        edtMK2=(EditText)findViewById(R.id.edMKDangKy2);
        txtDangNhap=(TextView)findViewById(R.id.txtDangNhapDK);
        txtKhoiPhuc=(TextView)findViewById(R.id.txtQuenMatKhauDK);
    }
}
