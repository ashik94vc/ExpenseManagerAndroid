package com.cepheuen.expensemanager.adapters;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.cepheuen.expensemanager.fragments.DayChartFragment;
import com.cepheuen.expensemanager.fragments.MonthChartFragment;
import com.cepheuen.expensemanager.fragments.WeekChartFragment;
import com.cepheuen.expensemanager.model.Expenses;

import java.util.List;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Expenses> expensesList;
    public ViewPagerAdapter(FragmentManager fm, List<Expenses> expensesList) {
        super(fm);
        this.expensesList = expensesList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0: return DayChartFragment.newInstance(expensesList);

            case 1: return WeekChartFragment.newInstance(expensesList);

            case 2: return MonthChartFragment.newInstance(expensesList);

            default: return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int getItemPosition(Object object)
    {
        return POSITION_NONE;
    }
}