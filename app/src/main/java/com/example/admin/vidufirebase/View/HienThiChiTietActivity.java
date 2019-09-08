package com.example.admin.vidufirebase.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerHinhBinhLuan;
import com.example.admin.vidufirebase.Model.BinhLuanModel;
import com.example.admin.vidufirebase.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiChiTietActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView txtTieudeBinhLuan,txtNoiDungBinhLuan,txtSoDiem;
    RecyclerView recyclerViewHinhBinhLuan;
    List<Bitmap> bitmapList;
    List<Uri> bitmapList2;
    BinhLuanModel binhLuanModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);
        MultiDex.install(this);
        AnhXa();
        binhLuanModel=getIntent().getParcelableExtra("binhluanmodel");

        txtTieudeBinhLuan.setText(binhLuanModel.getTieude());
        txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        txtSoDiem.setText(binhLuanModel.getChamdiem()+ "");
        setHinhAnhBinhLuan(circleImageView,binhLuanModel.getThanhVienModel().getHinhanh());

        /*for(String linkhinh:binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    bitmapList.add(bitmap);
                    if(bitmapList.size()==binhLuanModel.getHinhanhBinhLuanList().size()){
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan=new AdapterRecyclerHinhBinhLuan(HienThiChiTietActivity.this,R.layout.custom_layout_hinhbinhluan,bitmapList,binhLuanModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietActivity.this,2);//2 cột
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }*/
        for(String linkhinh:binhLuanModel.getHinhanhBinhLuanList()){
            StorageReference storageReferenceVideo=FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            storageReferenceVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    bitmapList2.add(uri);
                    if(bitmapList2.size()==binhLuanModel.getHinhanhBinhLuanList().size())
                    {
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(HienThiChiTietActivity.this,R.layout.custom_layout_hinhbinhluan,bitmapList2,binhLuanModel,true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietActivity.this,2);
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }
    }
    void AnhXa(){
        circleImageView =(CircleImageView)findViewById(R.id.cicleImageUser);
        txtTieudeBinhLuan=(TextView)findViewById(R.id.txtTieudebinhluan);
        txtNoiDungBinhLuan=(TextView)findViewById(R.id.txtNodungbinhluan);
        txtSoDiem=(TextView)findViewById(R.id.txtChamDiemBinhLuan);
        recyclerViewHinhBinhLuan=(RecyclerView)findViewById(R.id.recyclerHinhBinhLuan);
        bitmapList=new ArrayList<>();
        bitmapList2=new ArrayList<>();
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
