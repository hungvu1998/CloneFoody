package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.WifiQuanAnModel;
import com.example.admin.vidufirebase.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterDanhSachWifi extends RecyclerView.Adapter<AdapterDanhSachWifi.ViewHolderWifi> {
    Context context;
    int resource;

    public List<WifiQuanAnModel> getWifiQuanAnModelList() {
        return wifiQuanAnModelList;
    }

    public void setWifiQuanAnModelList(List<WifiQuanAnModel> wifiQuanAnModelList) {
        this.wifiQuanAnModelList = wifiQuanAnModelList;
    }

    List<WifiQuanAnModel>wifiQuanAnModelList;
    public AdapterDanhSachWifi(Context context,int resource,List<WifiQuanAnModel>wifiQuanAnModelList){
        this.context=context;
        this.resource=resource;

        this.wifiQuanAnModelList=wifiQuanAnModelList;

    }
    public class ViewHolderWifi extends RecyclerView.ViewHolder {
        TextView txtTenWifi,txtMatkhauWifi,txtNgaydang;
        public ViewHolderWifi(@NonNull View itemView) {
            super(itemView);
            txtTenWifi=(TextView)itemView.findViewById(R.id.txtTenWifi);
            txtMatkhauWifi=(TextView)itemView.findViewById(R.id.txtMKWifi);
            txtNgaydang=(TextView)itemView.findViewById(R.id.txtNgaydang);
        }
    }

    @NonNull
    @Override
    public AdapterDanhSachWifi.ViewHolderWifi onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(resource,viewGroup,false);
        ViewHolderWifi viewHolderWifi=new ViewHolderWifi(view);
        return viewHolderWifi;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDanhSachWifi.ViewHolderWifi viewHolderWifi, int i) {

        WifiQuanAnModel wifiQuanAnModel=wifiQuanAnModelList.get(i);
        viewHolderWifi.txtTenWifi.setText(wifiQuanAnModel.getTen());
        viewHolderWifi.txtMatkhauWifi.setText(wifiQuanAnModel.getMatkhau());


        long l= Long.valueOf(wifiQuanAnModel.getNgaydang());

        Date date = new Date(l*1000L+86400000);//Chuyển sang ml giây 86400000
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy HH:mm:ss ");//HH:mm:ss
        String Day = simpleDateFormat.format(date);
        viewHolderWifi.txtNgaydang.setText(Day);
    }

    @Override
    public int getItemCount() {
        return wifiQuanAnModelList.size();
    }


}
