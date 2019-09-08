package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.vidufirebase.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Share;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener,FirebaseAuth.AuthStateListener {
    EditText edtTenDN,edtMatKhau;
    Button btnDangNhapGG,btnDangNhap,btnDangNhapFB;
    TextView txtDangKy,txtQuenMatKhau;
    GoogleApiClient apiClient;
    public static  int REQUESTCODE_DANGNHAP_GOOOGLE=99;
    public static int KIEMTRA_PROVIDER_DANGNHAP=0;
    FirebaseAuth firebaseAuth;

    CallbackManager mCallbackFB;
    LoginManager loginManager;
    List<String>permissionFB= Arrays.asList("email","public_profile");
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.layout_dangnhap);
        MultiDex.install(this);
        mCallbackFB=CallbackManager.Factory.create();
        loginManager=LoginManager.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();// chức thực firebase
       /* firebaseAuth.signOut();
        LoginManager.getInstance().logOut();*/
        AnhXa();


        btnDangNhapGG.setOnClickListener(this);
        btnDangNhapFB.setOnClickListener(this);

        txtDangKy.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        txtQuenMatKhau.setOnClickListener(this);
        sharedPreferences=getSharedPreferences("luudangnhap",MODE_PRIVATE);//chỉ có ứng dụng mình đọc dc
        TaoClientDangNhapGG();

    }
    private void DangNhapFB(){
        loginManager.logInWithReadPermissions(this,permissionFB);

        loginManager.registerCallback(mCallbackFB, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                KIEMTRA_PROVIDER_DANGNHAP=2;
                String tokenID=loginResult.getAccessToken().getToken();
                ChungThucDanhNhapFirebase(tokenID);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    //Khởi tạo Client cho Đăng nhập GG
    private void TaoClientDangNhapGG(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        apiClient =new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
    }
    //end khởi tạo client
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    //Mở form Đăng nhập bằng GG
    private void DangNhapGG(GoogleApiClient apiClient){
        KIEMTRA_PROVIDER_DANGNHAP=1;
        Intent intentGG =Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(intentGG,REQUESTCODE_DANGNHAP_GOOOGLE);
    }

    //Lấy tokenid đã đăng nhập bằng GG để đăng nhập trên firebase
    private void ChungThucDanhNhapFirebase(String tokenid){

        if(KIEMTRA_PROVIDER_DANGNHAP==1)
        {
            AuthCredential authCredential=GoogleAuthProvider.getCredential(tokenid,null);
            firebaseAuth.signInWithCredential(authCredential);
        }
        else if (KIEMTRA_PROVIDER_DANGNHAP ==2){
            AuthCredential authCredential=FacebookAuthProvider.getCredential(tokenid);
            firebaseAuth.signInWithCredential(authCredential);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTCODE_DANGNHAP_GOOOGLE){
            if(resultCode==RESULT_OK){
                GoogleSignInResult signInResult=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount googleSignInAccount=signInResult.getSignInAccount();
                String tokenID =googleSignInAccount.getIdToken();
                ChungThucDanhNhapFirebase(tokenID);
            }
        }
        else {
            mCallbackFB.onActivityResult(requestCode,resultCode,data);
        }
    }

    void AnhXa(){
        edtTenDN =(EditText)findViewById(R.id.edTenDangNhap);
        edtMatKhau =(EditText)findViewById(R.id.edMKDangNhap);
        btnDangNhap =(Button)findViewById(R.id.btnDangNhap);
        btnDangNhapGG =(Button)findViewById(R.id.btnDangNhapGG);
        btnDangNhapFB =(Button) findViewById(R.id.btnDangNhapFB);
        txtDangKy=(TextView)findViewById(R.id.txtDangKyDN);
        txtQuenMatKhau=(TextView)findViewById(R.id.txtQuenMatKhauDN);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onClick(View v) {
        int id =v.getId();
        switch (id){
            case R.id.btnDangNhapGG:
                DangNhapGG(apiClient);
                break;
            case  R.id.btnDangNhapFB:
                DangNhapFB();
                break;
            case  R.id.txtDangKyDN:
                Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);
                break;
            case  R.id.btnDangNhap:
                DangNhap();
                break;
            case  R.id.txtQuenMatKhauDN:
                Intent intent2 = new Intent(DangNhapActivity.this,KhoiPhucMKActivity.class);
                startActivity(intent2);
                break;
        }
    }
    void DangNhap(){
        String email = edtTenDN.getText().toString();
        String matkhau=edtMatKhau.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                }
                else {
                    Toast.makeText(DangNhapActivity.this, "Tài khoản không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Kiểm tra xem người dùng đã đăng nhập thành công hay đã logout
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user!=null){

            SharedPreferences.Editor editor =sharedPreferences.edit();
            editor.putString("mauser",user.getUid());

            editor.commit();
            Intent intent =new Intent(this,TrangChuActivity.class);

            startActivity(intent);
        }
        else {

        }
    }
}
