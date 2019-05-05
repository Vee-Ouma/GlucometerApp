package com.example.glucometer.entities;

import java.util.Date;

public class LogEntryHeader extends Entry {

    public LogEntryHeader(Date date) {
        super(date);
    }

    @Override
    public int getViewType() {return 1;}
}
