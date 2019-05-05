package com.example.glucometer.entities;

import android.util.Log;

import java.util.Date;

public class LogEntry extends Entry{

    private int glucose;
    private int bolus;
    private int basal;
    private String notes;
    private int carbs;


    public LogEntry(int glucose, Date date, int carbs, int bolus, int basal, String notes){
        super(date);
        this.glucose = glucose;
        this.bolus = bolus;
        this.basal = basal;
        this.notes = notes;
        this.carbs = carbs;
    }

    public int getGlucose() {return glucose;}
    public void setGlucose(int glucose) {this.glucose = glucose;}
    public void setDate(Date date) {this.date = date;}

    public int getBasal() {
        return basal;
    }
    public int getBolus() {
        return bolus;
    }
    public String getNotes() {
        return notes;
    }
    public void setBasal(int basal) {
        this.basal = basal;
    }
    public void setBolus(int bolus) {
        this.bolus = bolus;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public int getCarbs() {
        return carbs;
    }
    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }
    public int getViewType() {return 0;}

}
