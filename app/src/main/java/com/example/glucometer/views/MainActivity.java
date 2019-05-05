package com.example.glucometer.views;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.glucometer.R;
import com.example.glucometer.entities.LogEntry;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements EntryDialogFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private FloatingActionButton fab;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private HomeFragment homeFragment;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    MenuItem prevMenuItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    //HomeFragment.newInstance();
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Title");
        actionBar.setSubtitle("");
        actionBar.hide();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        homeFragment = HomeFragment.newInstance();
        mSectionsPagerAdapter.addFragment(homeFragment);
        mSectionsPagerAdapter.addFragment(TrendsFragment.newInstance());
        mSectionsPagerAdapter.addFragment(DeviceFragment.newInstance());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTextMessage = (TextView) findViewById(R.id.message);
        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //mViewPager.setCurrentItem(0);
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.navigation_dashboard:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.navigation_notifications:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                navigation.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + i);
                navigation.getMenu().getItem(i).setChecked(true);
                prevMenuItem = navigation.getMenu().getItem(i);
                if (i == 0)
                    fab.show();
                else
                    fab.hide();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        final EntryDialogFragment newFragment = EntryDialogFragment.newInstance("hello", "hello2");
        //newFragment.setTargetFragment(this, EntryDialogFragment.REQUEST_CODE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newFragment.show(getSupportFragmentManager(), "dialog");

                Snackbar.make(view, "Nice button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onSubmission(String glucose, Date date, String carbs, String bolus, String basal, String notes) {
       // Date completeDate = new Date(TimeUnit.DAYS.toMillis(day) + TimeUnit.HOURS.toMillis(hour) + TimeUnit.MINUTES.toMillis(minute));
       // Log.d("time",""+TimeUnit.HOURS.toMillis(hour));
       //Log.d("time", ""+TimeUnit.MINUTES.toMillis(minute));
       //Log.d("time", ""+TimeUnit.DAYS.toMillis(day));
        Log.d("time", ""+date.getTime());
        homeFragment.addEntry(new LogEntry(
                Integer.parseInt("0" + glucose),
                date,
                Integer.parseInt("0" + carbs),
                Integer.parseInt("0" + bolus),
                Integer.parseInt("0" + basal),
                notes));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        //public ViewPagerAdapter(FragmentManager manager) {super(manager);}
        public SectionsPagerAdapter(FragmentManager fm) {super(fm);}

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFragment(Fragment fragment) {mFragmentList.add(fragment);}

        @Override
        public int getCount() {return mFragmentList.size();}

    }

}
