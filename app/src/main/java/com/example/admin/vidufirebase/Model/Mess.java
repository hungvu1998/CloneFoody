package com.example.admin.vidufirebase.Model;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.TraLoiInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public  class Mess implements Parcelable {
    String nguoigui;
    String noidung;
    String ngaydang;
    String id;
    protected Mess(Parcel in) {

        nguoigui = in.readString();
        ngaydang = in.readString();
        noidung = in.readString();
        id = in.readString();

    }
    public static final Creator<Mess> CREATOR = new Creator<Mess>() {
        @Override
        public Mess createFromParcel(Parcel in) {
            return new Mess(in);
        }

        @Override
        public Mess[] newArray(int size) {
            return new Mess[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNgaydang() {
        return ngaydang;
    }


    public void setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
    }



    public String getNguoigui() {
        return nguoigui;
    }

    public void setNguoigui(String nguoigui) {
        this.nguoigui = nguoigui;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public Mess(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }
    public Mess(String nguoigui,String noidung,String ngaydang){
        this.nguoigui=nguoigui;
        this.noidung=noidung;
        this.ngaydang=ngaydang;
    }
    private DataSnapshot dataRoot;
    private DatabaseReference nodeRoot ;

    public void getDanhSachCauHoi(final TraLoiInterface traLoiInterface){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachTraLoi(dataSnapshot,traLoiInterface);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            LayDanhSachTraLoi(dataRoot,traLoiInterface);
        }else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }
    private void LayDanhSachTraLoi(DataSnapshot dataSnapshot,TraLoiInterface traLoiInterface){
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("Chats");
        //Lấy danh sách quán ăn
        int i = 0;
        for (DataSnapshot valuetraloi : dataSnapshotQuanAn.getChildren()){



            Mess mess = valuetraloi.getValue(Mess.class);
            mess.setId(valuetraloi.getKey());







            traLoiInterface.getDanhSachTraLoi(mess);
        }
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(ngaydang);
        dest.writeString(nguoigui);
        dest.writeString(noidung);

    }


}