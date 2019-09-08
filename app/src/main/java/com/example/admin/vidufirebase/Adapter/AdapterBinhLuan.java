package com.example.admin.vidufirebase.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.vidufirebase.Controller.BinhLuanController;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<BinhLuanModel>binhLuanModelList;
    boolean iTrangThai;



    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel>binhLuanModelList,boolean iTrangThai){
        this.context=context;
        this.layout=layout;
        this.iTrangThai=iTrangThai;
        this.binhLuanModelList=binhLuanModelList;


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieudeBinhLuan,txtNoiDungBinhLuan,txtSoDiem;
        RecyclerView recyclerViewHinhBinhLuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView =(CircleImageView)itemView.findViewById(R.id.cicleImageUser);
            txtTieudeBinhLuan=(TextView)itemView.findViewById(R.id.txtTieudebinhluan);
            txtNoiDungBinhLuan=(TextView)itemView.findViewById(R.id.txtNodungbinhluan);
            txtSoDiem=(TextView)itemView.findViewById(R.id.txtChamDiemBinhLuan);
            recyclerViewHinhBinhLuan=(RecyclerView)itemView.findViewById(R.id.recyclerHinhBinhLuan);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(i);
        viewHolder.txtTieudeBinhLuan.setText(binhLuanModel.getTieude());
        viewHolder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        viewHolder.txtSoDiem.setText(binhLuanModel.getChamdiem()+ "");

       setHinhAnhBinhLuan(viewHolder.circleImageView,binhLuanModel.getThanhVienModel().getHinhanh());

       if(binhLuanModel.getHinhanhBinhLuanList().size()>0)
       {
           final List<Uri> uris=new ArrayList<>();
           for(String linkhinh:binhLuanModel.getHinhanhBinhLuanList()){
               StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);

               storageHinhAnh.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                   @Override
                   public void onSuccess(Uri uri) {
                       uris.add(uri);
                       binhLuanModel.setUriList(uris);
                       if(uris.size()==binhLuanModel.getHinhanhBinhLuanList().size())
                       {
                           AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(context,R.layout.custom_layout_hinhbinhluan,uris,binhLuanModel,false);
                           RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
                           viewHolder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                           viewHolder.recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                           adapterRecyclerHinhBinhLuan.notifyDataSetChanged();

                       }
                   }
               });

           }


       }






    }
    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkhinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = decodeSampledBitmapFromResource(bytes, 100, 100);

                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(iTrangThai==false)
            if(binhLuanModelList.size()<3)
                return binhLuanModelList.size();
            else
                return 3;
        else
            return binhLuanModelList.size();


    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(byte[] bytes, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(bytes,0, bytes.length, options);
    }
}
