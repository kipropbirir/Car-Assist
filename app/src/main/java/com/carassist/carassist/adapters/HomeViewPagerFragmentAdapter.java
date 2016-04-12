package com.carassist.carassist.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.carassist.carassist.fragments.DriverFragment;
import com.carassist.carassist.fragments.GarageOwnerFragment;
import com.carassist.carassist.fragments.SparesFragment;

/**
 * Created by user on 2/27/2016.
 */
public class HomeViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

    public HomeViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch(position){
            case 0:
                fragment = new DriverFragment();
                break;
            case 1:
                fragment = new GarageOwnerFragment();
                break;
            case 2:
                fragment = new SparesFragment();
                break;
            default:
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = "";
        switch(position){
            case 0:
                pageTitle = "Driver";
                break;
            case 1:
                pageTitle = "Garages";
                break;
            case 2:
                pageTitle = "Spares";
                break;
        }
        return pageTitle;
    }
}
