package com.cepheuen.expensemanager.fragments;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class DashboardFragment extends Fragment {

    public DashboardFragment newInstance()
    {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

}
