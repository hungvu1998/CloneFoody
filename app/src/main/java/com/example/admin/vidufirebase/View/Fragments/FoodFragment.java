package com.example.admin.vidufirebase.View.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.admin.vidufirebase.Adapter.AdapterRecyclerPlace;
import com.example.admin.vidufirebase.Controller.ControllerFood;
import com.example.admin.vidufirebase.Controller.ControllerPlace;
import com.example.admin.vidufirebase.Model.QuanAnModel;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class FoodFragment extends Fragment {
   ControllerFood controllerFood;
    RecyclerView recyclerFood;
    ProgressBar PBLoad;
    SharedPreferences sharedPreferences;
    NestedScrollView nestScrollViewFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("kiemtra","ffff");
        View view=inflater.inflate(R.layout.layout_fragment_food,container,false);
        recyclerFood=(RecyclerView)view.findViewById(R.id.recycleFood);
        PBLoad=(ProgressBar)view.findViewById(R.id.PGLoad);
        nestScrollViewFood=(NestedScrollView) view.findViewById(R.id.nestScrollViewFood);
        sharedPreferences=getContext().getSharedPreferences("toado",Context.MODE_PRIVATE);
        Location vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        controllerFood = new ControllerFood(getContext());


        controllerFood.getDanhSachQuanAnController(getContext(),nestScrollViewFood,recyclerFood,PBLoad,vitrihientai);
        //recyclerFood.OnItemTouchListener(new RecyclerView.RecyclerListener(),);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
