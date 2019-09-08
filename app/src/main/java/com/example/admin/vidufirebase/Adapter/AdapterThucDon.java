package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.ThucDonModel;
import com.example.admin.vidufirebase.R;

import java.util.List;

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.HolderThucDon> {
    Context context;
    List<ThucDonModel>thucDonModels;
    
    public AdapterThucDon(Context context, List<ThucDonModel>thucDonModels){
        this.context=context;
        this.thucDonModels=thucDonModels;


    }
    @NonNull
    @Override
    public HolderThucDon onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.custom_layout_thucdon,viewGroup,false);

        return new HolderThucDon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderThucDon holderThucDon, int i) {
        ThucDonModel thucDonModel=thucDonModels.get(i);
        holderThucDon.txtThucDon.setText(thucDonModel.getTenthucdon());

        holderThucDon.recyclerViewMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn = new AdapterMonAn(context,thucDonModel.getMonAnModelList());
        holderThucDon.recyclerViewMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return thucDonModels.size();
    }

    public class HolderThucDon extends RecyclerView.ViewHolder {
        TextView txtThucDon;
        RecyclerView recyclerViewMonAn;

        public HolderThucDon(@NonNull View itemView) {
            super(itemView);
            txtThucDon=itemView.findViewById(R.id.txtTenThucDon);
            recyclerViewMonAn=itemView.findViewById(R.id.recyclerMonAn);

        }
    }
}
