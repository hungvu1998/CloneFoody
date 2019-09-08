package com.example.admin.vidufirebase.Model;

import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    String mathucdon,tenthucdon;

    public ThucDonModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    List<MonAnModel> monAnModelList;

    private DatabaseReference nodeRoot ;
    private DataSnapshot dataRoot;
    public void getDanhSachThucDonTheoMaQuan(final String maquan, final ThucDonInterface thucDonInterface){
        DatabaseReference nodeThucDonQuanAn=FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquan);

        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final List<ThucDonModel>thucDonModels=new ArrayList<>();
                for(DataSnapshot valueThucDon:dataSnapshot.getChildren()){
                    final ThucDonModel thucDonModel= new ThucDonModel();
                    DatabaseReference nodeThucDon=FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotThucDon) {
                            String mathucdon=dataSnapshotThucDon.getKey();
                            thucDonModel.setMathucdon(dataSnapshotThucDon.getKey());
                            thucDonModel.setTenthucdon(dataSnapshotThucDon.getValue(String.class));
                            List<MonAnModel>monAnModels=new ArrayList<>();
                            for(DataSnapshot valueMonAn :dataSnapshot.child(mathucdon).getChildren()){
                                MonAnModel monAnModel=valueMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamon(valueMonAn.getKey());
                                monAnModels.add(monAnModel);
                            }
                            thucDonModel.setMonAnModelList(monAnModels);
                            thucDonModels.add(thucDonModel);
                            thucDonInterface.getThucDonThanhCong(thucDonModels);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }






}
