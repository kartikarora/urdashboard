package me.kartikarora.udacityreviewer.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import chipset.potato.Potato;
import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.models.me.Me;
import me.kartikarora.udacityreviewer.utils.HelperUtils;
import me.kartikarora.udacityreviewer.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.activities
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class IntroActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        TextView titleText = (TextView) findViewById(R.id.title_text);
        TextView descriptionText = (TextView) findViewById(R.id.description_text);

        titleText.setText("Register Device");
        descriptionText.setText(Html.fromHtml(
                "Device Needs to be registered with the Udacity Review API token in order to receive notifications.\n\n" +
                        "Always run grading-assigner.py with an additional parameter<code> --fcm-token FCM_TOKEN</code>.\n\n" +
                        "This is not a replacement of the Udacity Review API token. Both the tokens are required."));

        findViewById(R.id.copy_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperUtils.getInstance().copyToClipboard(IntroActivity.this,
                        Potato.potate(IntroActivity.this).Preferences()
                                .getSharedPreferenceString(getString(R.string.pref_fcm_token)), "FCM Token");
            }
        });


    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_udacity_token))) {
            final ProgressDialog dialog = new ProgressDialog(IntroActivity.this);
            dialog.setMessage("Completing Registration...");
            dialog.setCancelable(false);
            dialog.show();
            ArrayMap<String, String> headers = HelperUtils.getInstance().getHeaders(IntroActivity.this);
            UdacityReviewAPIUtils.getInstance().getUdacityReviewService().getMe(headers).enqueue(new Callback<Me>() {
                @Override
                public void onResponse(Call<Me> call, Response<Me> response) {
                    if (dialog.isShowing()) {
                        dialog.hide();
                    }
                    String name = response.body().getName();
                    String role = response.body().getRole();
                    Potato.potate(IntroActivity.this).Preferences().putSharedPreference(getString(R.string.pref_name), name);
                    Potato.potate(IntroActivity.this).Preferences().putSharedPreference(getString(R.string.pref_role), role);
                    Toast.makeText(getApplicationContext(), "Registration Complete", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(IntroActivity.this, MainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }

                @Override
                public void onFailure(Call<Me> call, Throwable t) {
                    if (dialog.isShowing()) {
                        dialog.hide();
                    }
                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onResume() {
        getSharedPreferences(getString(R.string.pref_udacity_token), MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        getSharedPreferences(getString(R.string.pref_udacity_token), MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();

    }
}
