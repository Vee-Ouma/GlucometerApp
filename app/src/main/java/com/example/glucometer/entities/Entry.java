package com.example.glucometer.entities;

import java.util.Date;

public class Entry implements Comparable<Entry>{

    protected Date date;
    public Entry(Date date) {
        this.date = date;
    }

    public int getViewType() {return 2;}
    public Date getDate() {return date;}

    @Override
    public boolean equals(Object l) {
        return ((Entry)l).date.compareTo(this.date) == 0;
    }

    public int compareTo(Entry o) {
        return this.date.compareTo(o.date);
    }

}
