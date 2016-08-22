package com.cepheuen.expensemanager.model;

import java.io.Serializable;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class Expenses implements Serializable{

    private float moneySpent;
    private String title;
    private String notes;
    private long timeStamp;
    public float getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(float moneySpent) {
        this.moneySpent = moneySpent;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
