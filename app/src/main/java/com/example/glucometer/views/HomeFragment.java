package com.example.glucometer.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.glucometer.R;
import com.example.glucometer.entities.Entry;
import com.example.glucometer.entities.LogEntry;
import com.example.glucometer.entities.LogEntryHeader;
import com.example.glucometer.viewmodels.HomeViewModel;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private ArrayList<Entry> list;
    private ArrayList<LogEntryHeader> headers;
    private LogEntryAdapter logEntryAdapter;
    private LogEntryHeader today;
    private Calendar cal;


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //super(view, savedInstanceState);
        cal = Calendar.getInstance();
        super.onViewCreated(view, savedInstanceState);
        RecyclerView logView = view.findViewById(R.id.logView);
        logView.setLayoutManager(new LinearLayoutManager(getActivity()));
        logView.setHasFixedSize(true);
        list = new ArrayList<>();
        headers = new ArrayList<>();

        //list.add(new LogEntryHeader(new Date(29,2,5,23,59)));
       // list.add(new LogEntryHeader(new Date(new Long("1551848399000"))));

        //today = new LogEntryHeader(getEndOfDay(new Date(), Calendar.getInstance()));
        //headers.add(today);
        //list.add(0, today);


        list.add(new Entry(new Date(Long.MAX_VALUE)));


        logEntryAdapter = new LogEntryAdapter(list);
        logView.setAdapter(logEntryAdapter);
        logView.addItemDecoration(new HeaderItemDecoration(logView, (HeaderItemDecoration.StickyHeaderInterface) logEntryAdapter));


        for (int i = 0; i < 10; i++) {

            addEntry(new LogEntry(99, new Date(29,3, 3,10,30), 40,10,0, "hello"));
            addEntry(new LogEntry(450, new Date(), 0,7,0, ""));

            addEntry(new LogEntry(0, new Date(29,2,5,4,28), 4,1,0, ""));
            addEntry(new LogEntry(45, new Date(), 0,0, 0, "cool note"));
            addEntry(new LogEntry(89, new Date(), 36,9,25, ""));
        }
    }

    public void addEntry(LogEntry entry) {
        list.add(0, entry);
        //Log.d("add", entry.getDate().toString());
        LogEntryHeader header = new LogEntryHeader(getEndOfDay(entry.getDate(), Calendar.getInstance()));
        if (!headers.contains(header)) {
            headers.add(header);
            list.add(header);
        }
        //today.setDate(new Date());
        list.sort(new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o2.compareTo(o1);
            }
        });
        logEntryAdapter.notifyDataSetChanged();
    }


    private static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }
}
