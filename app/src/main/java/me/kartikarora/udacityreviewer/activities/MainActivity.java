package me.kartikarora.udacityreviewer.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.appinvite.AppInviteInvitation;

import chipset.potato.Potato;
import me.kartikarora.udacityreviewer.BuildConfig;
import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.fragments.QueueFragment;
import me.kartikarora.udacityreviewer.fragments.StatsFragment;
import me.kartikarora.udacityreviewer.utils.HelperUtils;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.activities
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getName();
    private int checkedItem;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String udacityToken = Potato.potate(MainActivity.this).Preferences().getSharedPreferenceString(getString(R.string.pref_udacity_token));

        if (udacityToken.equals("null")) {
            startActivity(new Intent(MainActivity.this, IntroActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        } else {

            getWindow().setStatusBarColor(Color.TRANSPARENT);

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

            setSupportActionBar(toolbar);

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };

            mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    checkedItem = id;
                    item.setChecked(true);
                    switch (id) {
                        case R.id.nav_stats: {
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, StatsFragment.newInstance()).commit();
                            break;
                        }
                        case R.id.nav_queue: {
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, QueueFragment.newInstance()).commit();
                            break;
                        }
                    }
                    mDrawerLayout.closeDrawers();
                    return true;
                }
            });

            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, StatsFragment.newInstance()).commit();
            String name = Potato.potate(MainActivity.this).Preferences().getSharedPreferenceString(getString(R.string.pref_name));
            String role = Potato.potate(MainActivity.this).Preferences().getSharedPreferenceString(getString(R.string.pref_role));
            View header = mNavigationView.getHeaderView(0);
            TextView nameView = (TextView) header.findViewById(R.id.name);
            TextView roleView = (TextView) header.findViewById(R.id.role);
            TextView nameInitialView = (TextView) header.findViewById(R.id.name_initial);
            nameView.setText(HelperUtils.getInstance().capitalize(name));
            roleView.setText(HelperUtils.getInstance().capitalize(role));
            nameInitialView.setText(HelperUtils.getInstance().capitalize(String.valueOf(name.charAt(0))));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preference:
                startActivity(new Intent(MainActivity.this, PreferenceActivity.class));
                break;
            case R.id.action_share:
                Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                        .setMessage(getString(R.string.invitation_message))
                        .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                        .setCallToActionText(getString(R.string.invitation_cta))
                        .build();
                startActivityForResult(intent, BuildConfig.VERSION_CODE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("checkedItem", checkedItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        checkedItem = savedInstanceState.getInt("checkedItem", R.id.nav_queue);
        mNavigationView.setCheckedItem(checkedItem);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
