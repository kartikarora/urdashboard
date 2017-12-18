package me.kartikarora.urdashboard.fragments;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import me.kartikarora.potato.Potato;
import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.utils.HelperUtils;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.fragments
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class PreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = PreferenceFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        Preference udacityPreference = findPreference(getString(R.string.pref_udacity_token));
        udacityPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String token = Potato.potate(getContext()).Preferences().getSharedPreferenceString(getString(R.string.pref_udacity_token));
                showDialog(token, "Udacity Reviews API Token");
                return true;
            }
        });
    }

    public static PreferenceFragment newInstance() {

        Bundle args = new Bundle();

        PreferenceFragment fragment = new PreferenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
    }


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);

    }

    private void showDialog(final String message, final String what) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("Copy to clipboard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HelperUtils.getInstance().copyToClipboard(PreferenceFragment.this.getActivity().getApplicationContext(), message, what);
                    }
                }).create().show();
    }
}
