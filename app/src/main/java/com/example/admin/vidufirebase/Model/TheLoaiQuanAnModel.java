package com.example.admin.vidufirebase.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TheLoaiQuanAnModel implements Parcelable {
    String tentheloai;

    public TheLoaiQuanAnModel(){}
    public TheLoaiQuanAnModel(String tentheloai) {
        this.tentheloai = tentheloai;

    }
    public String getTentheloai() {
        return tentheloai;
    }

    public void setTentheloai(String tentheloai) {
        this.tentheloai = tentheloai;
    }


    protected TheLoaiQuanAnModel(Parcel in) {
        tentheloai = in.readString();

    }

    public static final Creator<TheLoaiQuanAnModel> CREATOR = new Creator<TheLoaiQuanAnModel>() {
        @Override
        public TheLoaiQuanAnModel createFromParcel(Parcel in) {
            return new TheLoaiQuanAnModel(in);
        }

        @Override
        public TheLoaiQuanAnModel[] newArray(int size) {
            return new TheLoaiQuanAnModel[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tentheloai);



    }
}
