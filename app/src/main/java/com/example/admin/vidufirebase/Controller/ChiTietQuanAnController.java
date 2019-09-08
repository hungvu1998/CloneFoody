package com.example.admin.vidufirebase.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.admin.vidufirebase.Adapter.AdapterBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhBinhLuan;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhNoiBat;
import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Controller.Interfaces.BinhLuanInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface;
import com.example.admin.vidufirebase.Controller.Interfaces.ChiTietQuanAnInterface2;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.Model.TienIchModel;
import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChiTietQuanAnController {
    WifiQuanAnModel wifiQuanAnModel;
    List<WifiQuanAnModel>wifiQuanAnModelList;
    QuanAnModel quanAnModel;
    Context context;
    AdapterRecyclerHinhNoiBat adapterRecyclerHinhNoiBat;
    AdapterBinhLuan adapterBinhLuan;
    long ONE_MEGABYTE = 1024 * 1024;
    int binhluandoco =0;
    BinhLuanModel binhLuanModel;
    List<BinhLuanModel> binhLuanModelList;
    public ChiTietQuanAnController(Context context){
        this.context=context;
        wifiQuanAnModel=new WifiQuanAnModel();
        quanAnModel=new QuanAnModel();
        binhLuanModel=new BinhLuanModel();
        wifiQuanAnModelList  =new ArrayList<>();
        binhLuanModelList=new ArrayList<>();
    }
    public void HienThiDanhSachWiFiQuanAn(String maquaan, final TextView txtTenWifi, final TextView txtMatKhauWifi, final TextView txtNgaydang,final TextView txtDiemTrungBinhQuanAn, final TextView txtTongSoBinhLuan){

        ChiTietQuanAnInterface chiTietQuanAnInterface=new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWiFi(WifiQuanAnModel wifiQuanAnModel) {
                List<String>tenwifi=new ArrayList<>();

                wifiQuanAnModelList.add(wifiQuanAnModel);
                txtTenWifi.setText(wifiQuanAnModelList.get(wifiQuanAnModelList.size()-1).getTen());

                tenwifi.add(wifiQuanAnModel.getTen());
                wifiQuanAnModel.setTenwifi(tenwifi);
                txtMatKhauWifi.setText(wifiQuanAnModelList.get(wifiQuanAnModelList.size()-1).getMatkhau());

                long l= Long.valueOf(wifiQuanAnModelList.get(wifiQuanAnModelList.size()-1).getNgaydang());

                Date date = new Date(l*1000L+86400000);//Chuyển sang ml giây 86400000
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm:ss ");//HH:mm:ss
                String Day = simpleDateFormat.format(date);
                txtNgaydang.setText(Day);




            }



        };
        wifiQuanAnModel.LayDanhSachWiFiQuanAn(maquaan,chiTietQuanAnInterface,wifiQuanAnModelList);

    }
    public void HienThiBinhLuan(final String maquanan,final TextView txtDiemTrungBinhQuanAn, final TextView txtTongSoBinhLuan, final RecyclerView recyclerView){


        ChiTietQuanAnInterface2 chiTietQuanAnInterface2=new ChiTietQuanAnInterface2() {
            @Override
            public void HienThiChiTiet(final QuanAnModel quanAnModel) {
                //Lấy tổng điểm
                double tongdiem = 0;
                //Tính tổng điểm trung bình của bình luận và đếm tổng số hình của bình luận
                for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()){
                    tongdiem += binhLuanModel1.getChamdiem();
                }
                double diemtrungbinh = tongdiem/quanAnModel.getBinhLuanModelList().size();
                txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinh));
                //end

                txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size()+"");

                /*int n1 = quanAnModel.getBinhLuanModelList().size();
                for (int ii = 0; ii < n1-1; ii++)
                    for (int jj =ii+1; jj < n1 ;jj++)
                        if (quanAnModel.getBinhLuanModelList().get(ii).getLuotthich()< quanAnModel.getBinhLuanModelList().get(jj).getLuotthich())
                        {
                            BinhLuanModel temp = quanAnModel.getBinhLuanModelList().get(ii);
                            quanAnModel.getBinhLuanModelList().set(ii,quanAnModel.getBinhLuanModelList().get(jj));
                            quanAnModel.getBinhLuanModelList().set(jj,temp);
                        }*/

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(layoutManager);
                adapterBinhLuan = new AdapterBinhLuan(context,R.layout.custom_layout_binhluan,quanAnModel.getBinhLuanModelList(),true);
                recyclerView.setAdapter(adapterBinhLuan);
                adapterBinhLuan.notifyDataSetChanged();


            }
        };

        quanAnModel.getDanhSachChiTiet2(chiTietQuanAnInterface2,maquanan);
    }

}
