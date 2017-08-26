package me.kartikarora.udacityreviewer.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.fragments.MeFragment;
import me.kartikarora.udacityreviewer.fragments.QueueFragment;
import me.kartikarora.udacityreviewer.fragments.RevenueFragment;
import me.kartikarora.udacityreviewer.fragments.StatsFragment;
import me.kartikarora.udacityreviewer.utils.HelperUtils;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.activities
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class DashboardActivity extends AppCompatActivity {

    private final static String LOG_TAG = DashboardActivity.class.getName();
    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        HelperUtils.getInstance().changeStatusBarColor(DashboardActivity.this);

        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationView = findViewById(R.id.bottomNavigationView);
        mNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchFragment(item.getItemId());
                return true;
            }
        });


        mNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        int selectedItem = mNavigationView.getSelectedItemId();
        outState.putInt("selected_item", selectedItem);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int selectedItem = savedInstanceState.getInt("selected_item", R.id.nav_stats);
        mNavigationView.setSelectedItemId(selectedItem);
        switchFragment(selectedItem);
    }

    private void switchFragment(int id) {
        Fragment fragment;
        switch (id) {
            case R.id.nav_revenue:
                fragment = RevenueFragment.newInstance();
                break;
            case R.id.nav_queue:
                fragment = QueueFragment.newInstance();
                break;
            case R.id.nav_me:
                fragment = MeFragment.newInstance();
                break;
            case R.id.nav_stats:
            default:
                fragment = StatsFragment.newInstance();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
