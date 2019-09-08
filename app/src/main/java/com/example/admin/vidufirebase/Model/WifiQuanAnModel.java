package com.example.admin.vidufirebase.Model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class   WifiQuanAnModel implements Parcelable {
    String ten,matkhau;
    long ngaydang;

    List<WifiQuanAnModel>wifiQuanAnModelList;
    List<String>tenwifi;
    public WifiQuanAnModel(){

    }


    public WifiQuanAnModel(String ten, String matkhau, long ngaydang) {
        this.ten = ten;
        this.matkhau = matkhau;
        this.ngaydang = ngaydang;
    }
    public List<WifiQuanAnModel> getWifiQuanAnModelList() {
        return wifiQuanAnModelList;
    }

    public void setWifiQuanAnModelList(List<WifiQuanAnModel> wifiQuanAnModelList) {
        this.wifiQuanAnModelList = wifiQuanAnModelList;
    }
    public List<String> getTenwifi() {
        return tenwifi;
    }

    public void setTenwifi(List<String> tenwifi) {
        this.tenwifi = tenwifi;
    }




    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }

    public long getNgaydang() {
        return ngaydang;
    }

    public void setNgaydang(long ngaydang) {
        this.ngaydang = ngaydang;
    }

    private DatabaseReference nodeWifiQuanAn;

    public void LayDanhSachWiFiQuanAn(final String maquan, final ChiTietQuanAnInterface chiTietQuanAnInterface, final List<WifiQuanAnModel>wifiQuanAnModelList){
        nodeWifiQuanAn=FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquan);

        nodeWifiQuanAn.orderByChild("ngaydang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wifiQuanAnModelList.clear();

                for(DataSnapshot valueWifi: dataSnapshot.getChildren()){
                    WifiQuanAnModel wifiQuanAnModel=valueWifi.getValue(WifiQuanAnModel.class);//Down thành công
                    wifiQuanAnModel.setWifiQuanAnModelList(wifiQuanAnModelList);
                    chiTietQuanAnInterface.HienThiDanhSachWiFi(wifiQuanAnModel);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }




    public void ThemWifiQuanAN(final Context context, WifiQuanAnModel wifiQuanAnModel,String maquanan){
        DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference().child("wifiquanans").child(maquanan);
        databaseReference.push().setValue(wifiQuanAnModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected WifiQuanAnModel(Parcel in) {
        ten = in.readString();
        matkhau = in.readString();
        ngaydang=in.readLong();

    }
    public static final Creator<WifiQuanAnModel> CREATOR = new Creator<WifiQuanAnModel>() {
        @Override
        public WifiQuanAnModel createFromParcel(Parcel in) {
            return new WifiQuanAnModel(in);
        }

        @Override
        public WifiQuanAnModel[] newArray(int size) {
            return new WifiQuanAnModel[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ten);
        dest.writeString(matkhau);
        dest.writeLong(ngaydang);

    }
}
