package com.cepheuen.expensemanager.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cepheuen.expensemanager.R;
import com.cepheuen.expensemanager.model.Expenses;
import com.cepheuen.expensemanager.utils.TimeUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import im.dacer.androidcharts.LineView;

/**
 * Created by Ashik Vetrivelu on 22/08/16.
 */
public class DayChartFragment extends Fragment {

    private static String TAG = "list_serializable";
    private List<Expenses> expensesList;

    public static DayChartFragment newInstance(List<Expenses> expensesList)
    {
        DayChartFragment fragment = new DayChartFragment();
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
        View view = inflater.inflate(R.layout.chart_day,container,false);

        LineView lineView = (LineView) view.findViewById(R.id.line_view);
        Integer numCumValue = 0;
        ArrayList<String> labels = new ArrayList<>(Arrays.asList("12:00 AM","04:00 AM","08:00 AM","12:00 PM","04:00 PM","08:00 PM"));
        ArrayList<Integer> values = new ArrayList<>(Collections.nCopies(6,0));
        Map<String,Integer> chartMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                try {
                    Date startTime = dateFormat.parse(s);
                    Date endTime = dateFormat.parse(t1);
                    return startTime.compareTo(endTime);
                }catch (ParseException e)
                {
                    return 0;
                }
            }
        });
        for(int i=0;i<labels.size();i++)
            chartMap.put(labels.get(i),values.get(i));
        for(Expenses expenses:expensesList)
        {

            String time = TimeUtils.getTime(expenses.getTimeStamp());
            long diff = Math.abs(expenses.getTimeStamp() - Calendar.getInstance().getTimeInMillis());
            long diffDays = diff / (24 * 60 * 60 * 1000);
            Log.d("ExpenseDayView",diffDays+ " for time:"+time+" on "+TimeUtils.getDate(expenses.getTimeStamp()));
            if(diffDays == 0) {

                numCumValue += Math.round(expenses.getMoneySpent());
                chartMap.put(time,numCumValue);
            }
        }
        List<String> keyList = new ArrayList<>(chartMap.keySet());
        for(int j=0;j<keyList.size()-1;j++)
        {
            if(labels.contains(keyList.get(j)))
            {
                chartMap.put(keyList.get(j),chartMap.get(keyList.get(j+1)));
            }
            else {
                chartMap.remove(keyList.get(j));
            }
        }
        numCumValue = 0;
        for(Map.Entry<String,Integer> entry : chartMap.entrySet())
        {
            numCumValue += entry.getValue();
            entry.setValue(numCumValue);
        }
        labels = new ArrayList<>(chartMap.keySet());
        values = new ArrayList<>(chartMap.values());
        ArrayList<ArrayList<Integer>> finalValues = new ArrayList<>();
        finalValues.add(values);
        lineView.setBottomTextList(labels);
        lineView.setDataList(finalValues);
        return view;
    }
}
