package com.example.admin.vidufirebase.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class TienIchModel implements Parcelable {
    String hinhtienich,tentienich;

    public TienIchModel(){}
    public TienIchModel(String hinhtienich, String tentienich) {
        this.hinhtienich = hinhtienich;
        this.tentienich = tentienich;
    }
    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }

    public String getTentienich() {
        return tentienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }

    protected TienIchModel(Parcel in) {
        hinhtienich = in.readString();
        tentienich = in.readString();

    }

    public static final Creator<TienIchModel> CREATOR = new Creator<TienIchModel>() {
        @Override
        public TienIchModel createFromParcel(Parcel in) {
            return new TienIchModel(in);
        }

        @Override
        public TienIchModel[] newArray(int size) {
            return new TienIchModel[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hinhtienich);
        dest.writeString(tentienich);


    }
}
