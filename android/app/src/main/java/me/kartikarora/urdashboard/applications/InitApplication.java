package me.kartikarora.urdashboard.applications;

import android.app.Application;

import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;

import timber.log.Timber;


/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.applications
 * Project : UR Dashboard
 * Date : 5/4/17
 */

public class InitApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(getApplicationContext());
        }
    }
}
