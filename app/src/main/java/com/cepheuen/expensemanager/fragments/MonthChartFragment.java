package com.cepheuen.expensemanager.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.expensemanager.R;
import com.cepheuen.expensemanager.model.Expenses;
import com.cepheuen.expensemanager.utils.TimeUtils;
import com.cepheuen.expensemanager.views.PagerScrollView;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import im.dacer.androidcharts.BarView;
import im.dacer.androidcharts.LineView;

/**
 * Created by Ashik Vetrivelu on 22/08/16.
 */
public class MonthChartFragment extends Fragment{
    private static String TAG = "list_serializable";
    private List<Expenses> expensesList;
    private Integer maxValue = 0;

    public static MonthChartFragment newInstance(List<Expenses> expensesList)
    {
        MonthChartFragment fragment = new MonthChartFragment();
        Bundle args = new Bundle();
        args.putSerializable(TAG, (Serializable) expensesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.expensesList = (List<Expenses>) getArguments().getSerializable(TAG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.chart_month,container,false);

        BarView barView = (BarView) view.findViewById(R.id.bar_view);
        final PagerScrollView pagerScrollView = (PagerScrollView) view.findViewById(R.id.scroll_view);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pagerScrollView.scrollTo(400,0);
            }
        },2000);
        ArrayList<String> labels = new ArrayList<>(Arrays.asList("JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"));
        ArrayList<Integer> values = new ArrayList<>(Collections.nCopies(12,0));
        for(Expenses expenses:expensesList)
        {
            Date date = new Date(expenses.getTimeStamp());
            int month = date.getMonth();
            if(values.get(month) == null)
            {
                if(maxValue < expenses.getMoneySpent())
                {
                    maxValue = Math.round(expenses.getMoneySpent());
                }
                values.add(month,Math.round(expenses.getMoneySpent()));
            }
            else {
                Integer moneySpent = values.get(month);
                moneySpent += Math.round(expenses.getMoneySpent());
                if(moneySpent > maxValue)
                    maxValue = moneySpent;
                values.set(month,moneySpent);
            }
        }
        barView.setBottomTextList(labels);
        barView.setDataList(values,maxValue);
        return view;
    }
}
