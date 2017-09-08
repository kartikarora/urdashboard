package me.kartikarora.urdashboard.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.fragments.MeFragment;
import me.kartikarora.urdashboard.fragments.QueueFragment;
import me.kartikarora.urdashboard.fragments.RevenueFragment;
import me.kartikarora.urdashboard.fragments.StatsFragment;
import me.kartikarora.urdashboard.utils.HelperUtils;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.activities
 * Project : UR Dashboard
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

        int selectedItem = savedInstanceState == null ? R.id.nav_stats : savedInstanceState.getInt("selected_item", R.id.nav_stats);
        mNavigationView.setSelectedItemId(selectedItem);
        switchFragment(selectedItem);

        mNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int selectedItem = mNavigationView.getSelectedItemId();
        outState.putInt("selected_item", selectedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        int selectedItem = savedInstanceState.getInt("selected_item", R.id.nav_stats);
        mNavigationView.setSelectedItemId(selectedItem);
        switchFragment(selectedItem);
        super.onRestoreInstanceState(savedInstanceState);
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
