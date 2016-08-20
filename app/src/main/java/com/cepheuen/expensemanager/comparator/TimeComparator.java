package com.cepheuen.expensemanager.comparator;

import com.cepheuen.expensemanager.model.Expenses;

import java.util.Comparator;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class TimeComparator implements Comparator<Expenses> {
    @Override
    public int compare(Expenses expense1, Expenses expense2) {
        if(expense1.getTimeStamp() < expense2.getTimeStamp())
        {
            return 1;
        }
        return -1;
    }
}
