package me.kartikarora.urdashboard.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import me.kartikarora.potato.Potato;
import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.utils.HelperUtils;

public class SplashActivity extends AppCompatActivity {

    private Class toClass;
    private Handler splashHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        HelperUtils.getInstance().changeStatusBarColor(SplashActivity.this);

        String udacityToken = Potato.potate(SplashActivity.this).Preferences().getSharedPreferenceString(getString(R.string.pref_udacity_token));
        toClass = udacityToken == null ? IntroActivity.class : DashboardActivity.class;

        splashHandler = new Handler();

    }


    private Runnable startThingyRunnable = new Runnable() {
        @Override
        public void run() {
            startActivity(new Intent(SplashActivity.this, toClass)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        splashHandler.postDelayed(startThingyRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        splashHandler.removeCallbacks(startThingyRunnable);
    }
}
