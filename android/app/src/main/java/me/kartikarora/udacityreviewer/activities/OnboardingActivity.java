package me.kartikarora.udacityreviewer.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fujiyuu75.sequent.Sequent;

import me.kartikarora.potato.Potato;
import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.models.me.Me;
import me.kartikarora.udacityreviewer.utils.HelperUtils;
import me.kartikarora.udacityreviewer.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        HelperUtils.getInstance().changeStatusBarColor(OnboardingActivity.this);

        final String apikey = getIntent().getStringExtra(getString(R.string.pref_udacity_token));
        if (TextUtils.isEmpty(apikey)) {
            Toast.makeText(OnboardingActivity.this, "Invalid Udacity Review API token", Toast.LENGTH_LONG).show();
            finish();
        } else {
            ArrayMap<String, String> headers = HelperUtils.getInstance().getHeaders(apikey);
            UdacityReviewAPIUtils.getInstance().getUdacityReviewService().getMe(headers)
                    .enqueue(new Callback<Me>() {
                        @Override
                        public void onResponse(Call<Me> call, Response<Me> response) {
                            if (response.isSuccessful()) {
                                final String name = response.body().getName();
                                final String role = response.body().getRole();
                                TextView nameTextView = findViewById(R.id.name_text_view);
                                nameTextView.setText(getString(R.string.onboarding_text_name, name));
                                LinearLayout layout = findViewById(R.id.text_linear_layout);
                                layout.setVisibility(View.VISIBLE);
                                ProgressBar bar = findViewById(R.id.progress_bar);
                                YoYo.with(Techniques.FadeOut)
                                        .duration(1000)
                                        .onEnd(new YoYo.AnimatorCallback() {
                                            @Override
                                            public void call(Animator animator) {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        AppCompatButton gojiraButton = findViewById(R.id.gojira_button);
                                                        YoYo.with(Techniques.FadeIn)
                                                                .duration(1000)
                                                                .playOn(gojiraButton);
                                                        gojiraButton.setVisibility(View.VISIBLE);
                                                        ((TextView) findViewById(R.id.information_text_view)).setText(getString(R.string.onboarding_text_complete));
                                                        gojiraButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                Potato.potate(OnboardingActivity.this).Preferences()
                                                                        .putSharedPreference(getString(R.string.pref_name), name);
                                                                Potato.potate(OnboardingActivity.this).Preferences()
                                                                        .putSharedPreference(getString(R.string.pref_role), role);
                                                                Potato.potate(OnboardingActivity.this).Preferences()
                                                                        .putSharedPreference(getString(R.string.pref_udacity_token), apikey);
                                                                startActivity(new Intent(OnboardingActivity.this, DashboardActivity.class)
                                                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                                            }
                                                        });
                                                    }
                                                }, 5000);
                                            }
                                        })
                                        .playOn(bar);
                                bar.setVisibility(View.GONE);
                                Sequent.origin(layout).anim(OnboardingActivity.this, com.fujiyuu75.sequent.Animation.FADE_IN_UP)
                                        .duration(1000).start();

                            }
                        }

                        @Override
                        public void onFailure(Call<Me> call, Throwable t) {
                            t.printStackTrace();
                            Toast.makeText(OnboardingActivity.this,
                                    "Error with the Udacity Review API, please try again in some time",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        }
    }
}
