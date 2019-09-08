package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.vidufirebase.Model.MonAnModel;
import com.example.admin.vidufirebase.R;

import java.util.List;

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.HolderMonAn> {
    Context context;
    List<MonAnModel>monAnModelList;
    public AdapterMonAn(Context context, List<MonAnModel>monAnModelList){
        this.context=context;
        this.monAnModelList=monAnModelList;
    }
    @NonNull
    @Override
    public HolderMonAn onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.custom_layout_monan,viewGroup,false);

        return new HolderMonAn(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMonAn holderMonAn, int i) {
        MonAnModel monAnModel=monAnModelList.get(i);

        holderMonAn.txtTenMonAn.setText(monAnModel.getTenmon());
    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }

    public class HolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn;
        public HolderMonAn(@NonNull View itemView) {
            super(itemView);
            txtTenMonAn=itemView.findViewById(R.id.txtTennMonAn);
        }
    }
}
