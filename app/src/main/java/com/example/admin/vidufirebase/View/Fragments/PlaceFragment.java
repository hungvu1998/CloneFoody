package com.example.admin.vidufirebase.View.Fragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.vidufirebase.Controller.ControllerPlace;
import com.example.admin.vidufirebase.R;
import com.example.admin.vidufirebase.View.DangNhapActivity;
import com.example.admin.vidufirebase.View.ServerActivity;
import com.example.admin.vidufirebase.View.ThanhVienActivity;
import com.example.admin.vidufirebase.View.TrangChuActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PlaceFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    ControllerPlace controllerPlace;
    RecyclerView recyclerPlace;
    ProgressBar PBLoad;
    SharedPreferences sharedPreferences;
    NestedScrollView nestScrollViewPlace;
    Button testqueue;
    FirebaseAuth firebaseAuth;
    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_place,container,false);
        recyclerPlace=(RecyclerView)view.findViewById(R.id.recyclerPlace);
        PBLoad=(ProgressBar)view.findViewById(R.id.PGLoad);
        spinner=(Spinner)view.findViewById(R.id.spinnerNews);
        nestScrollViewPlace=(NestedScrollView) view.findViewById(R.id.nestScrollViewPlace);
        sharedPreferences=getContext().getSharedPreferences("toado",Context.MODE_PRIVATE);
        Location vitrihientai=new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));


        controllerPlace = new ControllerPlace(getContext());


        controllerPlace.getDanhSachQuanAnController(getContext(),nestScrollViewPlace,recyclerPlace,PBLoad,vitrihientai);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(),R.array.spinerUser,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                break;
            case 1:
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                // chức thực firebase
                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                Intent intent2=new Intent(getContext(), DangNhapActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
