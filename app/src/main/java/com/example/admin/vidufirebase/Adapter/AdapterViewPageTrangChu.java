package com.example.admin.vidufirebase.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.vidufirebase.View.Fragments.FoodFragment;
import com.example.admin.vidufirebase.View.Fragments.PlaceFragment;

public class    AdapterViewPageTrangChu extends FragmentStatePagerAdapter {
    FoodFragment foodFragment;
    PlaceFragment  placeFragment;
    public AdapterViewPageTrangChu(FragmentManager fm) {
        super(fm);
        foodFragment= new FoodFragment();
        placeFragment= new PlaceFragment();
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                return placeFragment;

            case 1:
                return foodFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
