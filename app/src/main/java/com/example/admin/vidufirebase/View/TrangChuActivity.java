package com.example.admin.vidufirebase.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.vidufirebase.Adapter.AdapterViewPageTrangChu;
import com.example.admin.vidufirebase.Controller.Interfaces.ThucDonInterface;
import com.example.admin.vidufirebase.Model.MonAnModel;
import com.example.admin.vidufirebase.Model.ThanhVienModel;
import com.example.admin.vidufirebase.Model.ThucDonModel;
import com.example.admin.vidufirebase.R;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrangChuActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener ,AdapterView.OnItemSelectedListener {
    RadioButton rdMoiNhat,rdKhuVuc,rdFood;
    RadioButton rbFood,rbPlace;
    ViewPager viewPagerTrangChu;
    RadioGroup radioGroupPAF;
    Spinner spinnerUser;
    FirebaseAuth firebaseAuth;
    CircleImageView buttonUser;
    ThanhVienModel thanhVienModel;
    LoginManager loginManager;
    private static final String ttt="ffff";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        MultiDex.install(this);
        AnhXa();
        ArrayList<Float>x;
        loginManager=LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        getThongTinThanhvien(firebaseAuth.getCurrentUser().getUid());
        AdapterViewPageTrangChu adapterViewPageTrangChu= new AdapterViewPageTrangChu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPageTrangChu);
        viewPagerTrangChu.addOnPageChangeListener(this);
        radioGroupPAF.setOnCheckedChangeListener(this);
        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this,R.array.spinerUser,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUser.setAdapter(adapter);
        spinnerUser.setOnItemSelectedListener(this);

    }
    void AnhXa(){
        rdMoiNhat=(RadioButton) findViewById(R.id.rbMoiNhat);
        rdKhuVuc=(RadioButton) findViewById(R.id.rbKhuVuc);
        rdFood=(RadioButton) findViewById(R.id.rbFood);
        viewPagerTrangChu=(ViewPager)findViewById(R.id.viewpagetrangchu);
        rbFood=(RadioButton) findViewById(R.id.RBPFood);
        rbPlace= (RadioButton) findViewById(R.id.RBPlace);
        radioGroupPAF=(RadioGroup) findViewById(R.id.radioGroupPAF);
        spinnerUser=(Spinner)findViewById(R.id.spinnerUser);
        buttonUser=(CircleImageView)findViewById(R.id.buttonUser);

    }



    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                rbPlace.setChecked(true);
                break;
            case 1:
                rbFood.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.RBPlace:
                viewPagerTrangChu.setCurrentItem(0);
                break;
            case R.id.RBPFood:
                viewPagerTrangChu.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position){
            case 0:
                break;
            case 1:
                Intent intent=new Intent(TrangChuActivity.this,ThanhVienActivity.class);
                intent.putExtra("mauser",thanhVienModel.getMathanhvien());
                intent.putExtra("hoten",thanhVienModel.getHoten());
                intent.putExtra("linkhinh",thanhVienModel.getHinhanh());
                startActivity(intent);
                break;
            case 2:
               // chức thực firebase
                firebaseAuth.signOut();
                    LoginManager.getInstance().logOut();
                Intent intent2=new Intent(TrangChuActivity.this,DangNhapActivity.class);
                startActivity(intent2);
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void getThongTinThanhvien(final String mauser){
        DatabaseReference nodeThanhVien=FirebaseDatabase.getInstance().getReference().child("thanhviens").child(mauser);

        nodeThanhVien.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                thanhVienModel =dataSnapshot.getValue(ThanhVienModel.class);
                thanhVienModel.setMathanhvien(mauser);
                setHinhAnhUser(buttonUser,thanhVienModel.getHinhanh());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void setHinhAnhUser(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}
