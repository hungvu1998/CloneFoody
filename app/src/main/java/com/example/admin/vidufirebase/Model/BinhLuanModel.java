package com.example.admin.vidufirebase.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.Interfaces.BinhLuanInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface2;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;
import com.example.admin.vidufirebase.View.BinhLuanActivity;
import com.example.admin.vidufirebase.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BinhLuanModel implements Parcelable,Comparable<BinhLuanModel> {
    double chamdiem;
    long luotthich;
    ThanhVienModel thanhVienModel;
    String noidung;
    String tieude;
    String manbinhluan;
    List<String> hinhanhBinhLuanList;
    String user;
    List<Uri>uriList;


    private DataSnapshot dataRoot;
    private DatabaseReference nodeRoot ;

    protected BinhLuanModel(Parcel in) {
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        noidung = in.readString();
        tieude = in.readString();
        manbinhluan = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
        user = in.readString();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public String getManbinhluan() {
        return manbinhluan;
    }

    public void setManbinhluan(String manbinhluan) {
        this.manbinhluan = manbinhluan;
    }



    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhList) {
        this.hinhanhBinhLuanList = hinhanhList;
    }
    public List<Uri> getBitmapList() {
        return uriList;
    }

    public void setUriList(List<Uri> uriList) {
        this.uriList = uriList;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }




    public BinhLuanModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public void ThemBinhLuan(String maquanan, BinhLuanModel binhLuanModel, final List<String>listHinh){

        DatabaseReference nodeBinhLuan =FirebaseDatabase.getInstance().getReference().child("binhluantest");
        String mabinhluan =nodeBinhLuan.child(maquanan).push().getKey();
        nodeBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    if(listHinh.size()>0)
                    {
                        for(String value:listHinh){
                            Uri uri =Uri.fromFile(new File(value));
                            StorageReference storageReference=FirebaseStorage.getInstance().getReference().child("hinhanh/"+uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                                }
                            });
                        }
                    }
                }
            }
        });

            //thành công
            if(listHinh.size()>0)
            {
                for(String value:listHinh){
                    Uri uri =Uri.fromFile(new File(value));
                   FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());

                }
            }




    }
    public void layDanhSachBinhLuan(DataSnapshot dataSnapshot, final ChiTietQuanAnInterface2 chiTietQuanAnInterface2,String maquan){
            DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans").child(maquan);
            QuanAnModel quanAnModel = dataSnapshotQuanAn.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(dataSnapshotQuanAn.getKey());
            //Lấy danh sách bình luân của quán ăn
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluantest").child(quanAnModel.getMaquanan());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();
            binhLuanModels.clear();
            for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                binhLuanModel.setManbinhluan(valueBinhLuan.getKey());

                //laythongtinthanhvien
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getUser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuanList = new ArrayList<>();
                DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                    hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);

                binhLuanModels.add(binhLuanModel);
            }
            quanAnModel.setBinhLuanModelList(binhLuanModels);
            //end
            chiTietQuanAnInterface2.HienThiChiTiet(quanAnModel);
        }

    public void getDanhSachBinhLuan(final ChiTietQuanAnInterface2 chiTietQuanAnInterface2, final String maquan){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                layDanhSachBinhLuan(dataSnapshot,chiTietQuanAnInterface2,maquan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            layDanhSachBinhLuan(dataRoot,chiTietQuanAnInterface2,maquan);
        }else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }


    public void layDanhSachBinhLuan2(DataSnapshot dataSnapshot, final BinhLuanInterface binhLuanInterface,String maquan,final int itemtieptheo, final int itemdaco){

        //Lấy danh sách bình luân của quán ăn
        DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluantest").child(maquan);
        List<BinhLuanModel> binhLuanModels = new ArrayList<>();
        int i = 0;
        //binhLuanModels.clear();
        for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()){
            if(i == itemtieptheo){//3
                break;
            }
            if(i < itemdaco){//0
                i++;
                continue;
            }
            i++;
            BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
            binhLuanModel.setManbinhluan(valueBinhLuan.getKey());

            //laythongtinthanhvien
            ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getUser()).getValue(ThanhVienModel.class);
            binhLuanModel.setThanhVienModel(thanhVienModel);

            List<String> hinhanhBinhLuanList = new ArrayList<>();
            DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
            for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()){
                hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
            }
            binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);

            binhLuanModels.add(binhLuanModel);
            binhLuanInterface.HienThiDanhSachBinhLuan(binhLuanModel);
        }

        //end


    }
    public void getDanhSachBinhLuan2(final BinhLuanInterface binhLuanInterface, final String maquan , final int itemtieptheo, final int itemdaco){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                layDanhSachBinhLuan2(dataSnapshot,binhLuanInterface,maquan,itemtieptheo,itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            layDanhSachBinhLuan2(dataRoot,binhLuanInterface,maquan,itemtieptheo,itemdaco);
        }else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(noidung);
        dest.writeString(tieude);
        dest.writeString(manbinhluan);
        dest.writeStringList(hinhanhBinhLuanList);
        dest.writeString(user);
        dest.writeParcelable(thanhVienModel,flags);
    }

    @Override
    public int compareTo(BinhLuanModel o) {
        if (luotthich == o.luotthich)
            return 0;
        else if (luotthich < o.luotthich)
            return 1;
        else
            return -1;
    }
}
