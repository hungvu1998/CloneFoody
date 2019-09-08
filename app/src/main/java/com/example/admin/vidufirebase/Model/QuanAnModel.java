package com.example.admin.vidufirebase.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface2;
import com.example.admin.vidufirebase.Controller.Interfaces.PlaceInterface;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuanAnModel implements Parcelable {
    boolean giaohang;
    String giodongcua,giomocua,tenquanan,videogioithieu,maquanan;
    List<String> tienich;
    List<String> theloai;
    List<String> hinhanhquanan;
    List<BinhLuanModel> binhLuanModelList;
    List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList;
    List<Bitmap> bitmapList;
    List<Uri>uriList;
    List<ThucDonModel> thucDons;
    List<TienIchModel> tienIchModelList;
    List<WifiQuanAnModel>wifiQuanAnModelList;
    List<TheLoaiQuanAnModel>theLoaiQuanAnModelList;
    long giatoida;
    long giatoithieu;
    long luotthich;

    public List<ThucDonModel> getThucDons() {
        return thucDons;
    }

    public void setThucDons(List<ThucDonModel> thucDons) {
        this.thucDons = thucDons;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }



    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        giodongcua = in.readString();
        giomocua = in.readString();
        tenquanan = in.readString();

        videogioithieu = in.readString();
        maquanan = in.readString();
        tienich = in.createStringArrayList();
        theloai = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        chiNhanhQuanAnModelList = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chiNhanhQuanAnModelList,ChiNhanhQuanAnModel.CREATOR);
        binhLuanModelList = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhLuanModelList,BinhLuanModel.CREATOR);
        tienIchModelList = new ArrayList<TienIchModel>();
        in.readTypedList(tienIchModelList,TienIchModel.CREATOR);
        wifiQuanAnModelList = new ArrayList<WifiQuanAnModel>();
        in.readTypedList(wifiQuanAnModelList,WifiQuanAnModel.CREATOR);
        theLoaiQuanAnModelList = new ArrayList<TheLoaiQuanAnModel>();
        in.readTypedList(theLoaiQuanAnModelList,TheLoaiQuanAnModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public List<Uri> getUriList() {
        return uriList;
    }

    public void setUriList(List<Uri> uriList) {
        this.uriList = uriList;
    }



    public List<ChiNhanhQuanAnModel> getChiNhanhQuanAnModelList() {
        return chiNhanhQuanAnModelList;
    }

    public void setChiNhanhQuanAnModelList(List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList) {
        this.chiNhanhQuanAnModelList = chiNhanhQuanAnModelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }


    private DatabaseReference nodeRoot ;

    public List<TienIchModel> getTienIchModelList() {
        return tienIchModelList;
    }

    public void setTienIchModelList(List<TienIchModel> tienIchModelList) {
        this.tienIchModelList = tienIchModelList;
    }

    public List<TheLoaiQuanAnModel> getTheLoaiQuanAnModelList() {
        return theLoaiQuanAnModelList;
    }

    public void setTheLoaiQuanAnModelList(List<TheLoaiQuanAnModel> theLoaiQuanAnModelList) {
        this.theLoaiQuanAnModelList = theLoaiQuanAnModelList;
    }


    public List<WifiQuanAnModel> getWifiQuanAnModelList() {
        return wifiQuanAnModelList;
    }

    public void setWifiQuanAnModelList(List<WifiQuanAnModel> wifiQuanAnModelList) {
        this.wifiQuanAnModelList = wifiQuanAnModelList;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }


    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public QuanAnModel(){
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }



    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public List<String> getTheloai() {
        return theloai;
    }

    public void setTheloai(List<String> theloai) {
        this.theloai = theloai;
    }

    private DataSnapshot dataRoot;

    public void getDanhSachQuanAn(final PlaceInterface odauInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                LayDanhSachQuanAn(dataSnapshot,odauInterface,vitrihientai,itemtieptheo,itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            LayDanhSachQuanAn(dataRoot,odauInterface,vitrihientai,itemtieptheo,itemdaco);
        }else{
            nodeRoot.addListenerForSingleValueEvent(valueEventListener);
        }


    }
    private void LayDanhSachQuanAn(DataSnapshot dataSnapshot,PlaceInterface odauInterface,Location vitrihientai,int itemtieptheo,int itemdaco){
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");
        //Lấy danh sách quán ăn
        int i = 0;
        for (DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()){

            if(i == itemtieptheo){//3
                break;
            }
            if(i < itemdaco){//0
                i++;
                continue;
            }
            i++;
            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(valueQuanAn.getKey());

            //Lấy danh sách hình ảnh của quán ăn theo mã
            DataSnapshot dataSnapshotHinhQuanAn = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
            List<String> hinhanhlist = new ArrayList<>();
            for (DataSnapshot valueHinhQuanAn : dataSnapshotHinhQuanAn.getChildren()){
                hinhanhlist.add(valueHinhQuanAn.getValue(String.class));

            }
            quanAnModel.setHinhanhquanan(hinhanhlist);
            //End

            //Lấy danh sách bình luân của quán ăn
            DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluantest").child(quanAnModel.getMaquanan());
            List<BinhLuanModel> binhLuanModels = new ArrayList<>();
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

            //lấy danh sách tiện ích
            List<TienIchModel> tienIchModels = new ArrayList<>();
            if(quanAnModel.getTienich()!=null)
            {
                for(String matienich : quanAnModel.getTienich())
                {

                    DataSnapshot dataSnapshotTienIch = dataSnapshot.child("quanlytienichs").child(matienich);
                    tienIchModels.add(dataSnapshotTienIch.getValue(TienIchModel.class));
                }
                quanAnModel.setTienIchModelList(tienIchModels);
            }


            //end
            //Lấy danh sách thể loại

            List<TheLoaiQuanAnModel> theLoaiQuanAnModels = new ArrayList<>();
            for(String matheloai : quanAnModel.getTheloai())
            {

                DataSnapshot dataSnapshotTheLoai = dataSnapshot.child("theloais").child(matheloai);
                theLoaiQuanAnModels.add(dataSnapshotTheLoai.getValue(TheLoaiQuanAnModel.class));
            }
            quanAnModel.setTheLoaiQuanAnModelList(theLoaiQuanAnModels);
            //end
            List<WifiQuanAnModel>wifiQuanAnModels=new ArrayList<>();

            DataSnapshot dataSnapshotWifiQuanAn = dataSnapshot.child("wifiquanans").child(quanAnModel.getMaquanan());
            for(DataSnapshot value :dataSnapshotWifiQuanAn.getChildren()){
                WifiQuanAnModel wifiQuanAnModel = value.getValue(WifiQuanAnModel.class);
               wifiQuanAnModels.add(wifiQuanAnModel);
            }
            quanAnModel.setWifiQuanAnModelList(wifiQuanAnModels);
            //Lấy chi nhánh quán ăn

            DataSnapshot dataSnapshotChiNhanhQuanan = dataSnapshot.child("chinhanhquanans").child(quanAnModel.getMaquanan());
            List<ChiNhanhQuanAnModel>chiNhanhQuanAnModels=new ArrayList<>();
            for(DataSnapshot valueChiNhanhQuanAn :dataSnapshotChiNhanhQuanan.getChildren()){
                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);
                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());

                double khoangcach = vitrihientai.distanceTo(vitriquanan)/1000;
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);

                chiNhanhQuanAnModels.add(chiNhanhQuanAnModel);
            }
            quanAnModel.setChiNhanhQuanAnModelList(chiNhanhQuanAnModels);

            odauInterface.getDanhSachQuanAnModel(quanAnModel);
        }
    }

    private void LayDanhSachChiTiet2(DataSnapshot dataSnapshot,final ChiTietQuanAnInterface2 chiTietQuanAnInterface2,final String maquan ){

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
    public void getDanhSachChiTiet2(final ChiTietQuanAnInterface2 chiTietQuanAnInterface2, final String maquan){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot= dataSnapshot;
                LayDanhSachChiTiet2(dataSnapshot,chiTietQuanAnInterface2,maquan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            LayDanhSachChiTiet2(dataRoot,chiTietQuanAnInterface2,maquan);
        }else{
            nodeRoot.addValueEventListener(valueEventListener);
        }

    }


@Override
public int describeContents() {
    return 0;
}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (giaohang ? 1 : 0));
        dest.writeString(giodongcua);
        dest.writeString(giomocua);
        dest.writeString(tenquanan);

        dest.writeString(videogioithieu);
        dest.writeString(maquanan);
        dest.writeStringList(tienich);
        dest.writeStringList(theloai);
        dest.writeStringList(hinhanhquanan);
        dest.writeLong(luotthich);
        dest.writeLong(giatoida);
        dest.writeLong(giatoithieu);
        dest.writeTypedList(chiNhanhQuanAnModelList);
        dest.writeTypedList(binhLuanModelList);
        dest.writeTypedList(tienIchModelList);
        dest.writeTypedList(wifiQuanAnModelList);
        dest.writeTypedList(theLoaiQuanAnModelList);

    }
}