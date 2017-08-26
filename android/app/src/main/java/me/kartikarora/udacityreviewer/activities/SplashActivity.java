package me.kartikarora.udacityreviewer.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.kartikarora.potato.Potato;
import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.utils.HelperUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        HelperUtils.getInstance().changeStatusBarColor(SplashActivity.this);

        String udacityToken = Potato.potate(SplashActivity.this).Preferences().getSharedPreferenceString(getString(R.string.pref_udacity_token));
        final Class toClass = udacityToken == null ? IntroActivity.class : DashboardActivity.class;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, toClass)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        }, 3000);
    }
}
