package com.example.admin.vidufirebase.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.admin.vidufirebase.Model.ChonHinhBinhLuanModel;
import com.example.admin.vidufirebase.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinh>  {
    Context context;
    int resource;
    List<ChonHinhBinhLuanModel> listDuongDan;

    public  AdapterChonHinhBinhLuan(Context context,int resource,List<ChonHinhBinhLuanModel> listDuongDan){
        this.context = context;
        this.resource = resource;
        this.listDuongDan = listDuongDan;
    }
    @Override
    public ViewHolderChonHinh onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        ViewHolderChonHinh viewHolderChonHinh = new ViewHolderChonHinh(view);
        return viewHolderChonHinh;
    }

    @Override
    public void onBindViewHolder(ViewHolderChonHinh holder, final int position) {

        final ChonHinhBinhLuanModel chonHinhBinhLuanModel=listDuongDan.get(position);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;

        Bitmap bm = BitmapFactory.decodeFile(chonHinhBinhLuanModel.getDuongdan(),options);
        holder.imageView.setImageBitmap(bm);

       /* Uri uri = Uri.parse(chonHinhBinhLuanModel.getDuongdan());
        holder.imageView.setImageURI(uri);*/

        holder.checkBox.setChecked(chonHinhBinhLuanModel.isCheck());

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox=(CheckBox)v;
                listDuongDan.get(position).setCheck(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }

    public class ViewHolderChonHinh extends RecyclerView.ViewHolder {

        ImageView imageView;
        CheckBox checkBox;

        public ViewHolderChonHinh(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);

        }
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
