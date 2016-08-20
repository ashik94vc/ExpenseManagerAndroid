package com.cepheuen.expensemanager.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
       return null;
    }

    @Override
    public int getCount() {
        try {
            return 1;
        }catch (NullPointerException e)
        {
            return 0;
        }
    }
}