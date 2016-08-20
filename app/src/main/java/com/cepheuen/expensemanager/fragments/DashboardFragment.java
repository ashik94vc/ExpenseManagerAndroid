package com.cepheuen.expensemanager.fragments;

import android.app.Fragment;
import android.os.Bundle;

import com.cepheuen.expensemanager.model.Expenses;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class DashboardFragment extends Fragment {

    private static String TAG = "list_serializable";
    private List<Expenses> expensesList;

    public DashboardFragment newInstance(List<Expenses> expensesList)
    {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putSerializable(TAG, (Serializable) expensesList);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.expensesList = (List<Expenses>) getArguments().getSerializable(TAG);
    }


}
