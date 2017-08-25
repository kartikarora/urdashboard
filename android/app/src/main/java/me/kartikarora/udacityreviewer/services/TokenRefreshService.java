package me.kartikarora.udacityreviewer.services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import chipset.potato.Potato;
import me.kartikarora.udacityreviewer.R;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.services
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class TokenRefreshService extends FirebaseInstanceIdService {

    private static String LOG_TAG = TokenRefreshService.class.getName();

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Potato.potate(getApplicationContext()).Preferences().putSharedPreference(getString(R.string.pref_fcm_token), token);
        super.onTokenRefresh();
    }
}
