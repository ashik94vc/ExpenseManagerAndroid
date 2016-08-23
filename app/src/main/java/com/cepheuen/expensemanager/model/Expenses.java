package com.cepheuen.expensemanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ashik Vetrivelu on 20/08/16.
 */
public class Expenses implements Serializable{

    @SerializedName("moneySpent")
    @Expose
    private float moneySpent;

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("timeStamp")
    @Expose
    private String timeStamp;
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
        return Long.parseLong(timeStamp);
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = String.valueOf(timeStamp);
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }
}
