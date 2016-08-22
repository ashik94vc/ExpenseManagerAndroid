package com.cepheuen.expensemanager.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.expensemanager.R;
import com.cepheuen.expensemanager.model.Expenses;
import com.cepheuen.expensemanager.utils.TimeUtils;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import im.dacer.androidcharts.LineView;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class WeekChartFragment extends Fragment {

    private static String TAG = "list_serializable";
    private List<Expenses> expensesList;

    public static WeekChartFragment newInstance(List<Expenses> expensesList)
    {
        WeekChartFragment fragment = new WeekChartFragment();
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
        View view = inflater.inflate(R.layout.chart_week,container,false);

        LineView lineView = (LineView) view.findViewById(R.id.line_view);

        Map<String,Integer> chartMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String date1, String date2) {
                try {
                    return TimeUtils.getTimestamp(date1).compareTo(TimeUtils.getTimestamp(date2));
                }
                catch (ParseException e)
                {
                    return 0;
                }
            }
        });
        for(Expenses expenses:expensesList)
        {
            String date = TimeUtils.getDate(expenses.getTimeStamp());
            if(chartMap.get(date) == null)
            {
                long diff = Math.abs(expenses.getTimeStamp() - Calendar.getInstance().getTimeInMillis());
                long diffDays = diff / (24 * 60 * 60 * 1000);
                if(diffDays <= 7)
                    chartMap.put(date,Math.round(expenses.getMoneySpent()));
            }
            else
            {
                Integer money = chartMap.get(date);
                money = money + Math.round(expenses.getMoneySpent());
                chartMap.put(date,money);
            }
        }
        chartMap = setVoidDates(chartMap);
        ArrayList<Integer> cumValues = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>(chartMap.keySet());
        ArrayList<Integer> values = new ArrayList<>(chartMap.values());
        Integer numCumValue = 0;
        for(Integer value : values)
        {
            numCumValue += value;
            cumValues.add(numCumValue);
        }
        ArrayList<ArrayList<Integer>> finalValues = new ArrayList<>();
        finalValues.add(values);
        lineView.setBottomTextList(labels);
        lineView.setDataList(finalValues);
        return view;
    }
    private Map<String,Integer> setVoidDates(Map<String,Integer> map)
    {
        List<String> dates = TimeUtils.getDatesInRange();
        for(String date : dates)
        {
            if(map.get(date) == null)
                map.put(date,0);
        }
        return map;
    }
}
