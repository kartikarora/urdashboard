package me.kartikarora.udacityreviewer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.fragments.PreferenceFragment;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.activities
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preference);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, PreferenceFragment.newInstance())
                .commit();

    }

}
