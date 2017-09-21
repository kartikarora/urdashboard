package me.kartikarora.urdashboard.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.kartikarora.potato.Potato;
import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.activities.PreferenceActivity;
import me.kartikarora.urdashboard.activities.ReviewsAndFeedbackActivity;
import me.kartikarora.urdashboard.utils.HelperUtils;


public class MeFragment extends Fragment {


    public MeFragment() {
        // Required empty public constructor
    }


    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        String name = Potato.potate(getContext()).Preferences().getSharedPreferenceString(getString(R.string.pref_name));
        String role = Potato.potate(getContext()).Preferences().getSharedPreferenceString(getString(R.string.pref_role));

        AppCompatTextView meTextView = view.findViewById(R.id.me_text_view);
        AppCompatTextView meNameTextView = view.findViewById(R.id.me_name_text_view);
        AppCompatTextView meRoleTextView = view.findViewById(R.id.me_role_text_view);
        AppCompatImageView meImageView = view.findViewById(R.id.me_image_view);

        view.findViewById(R.id.reviews_and_feedback_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ReviewsAndFeedbackActivity.class));
            }
        });

        view.findViewById(R.id.preferences_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PreferenceActivity.class));
            }
        });

        meTextView.setText(HelperUtils.getInstance().capitalize(String.valueOf(name.charAt(0))));
        meNameTextView.setText(name);
        meRoleTextView.setText(HelperUtils.getInstance().capitalize(role));

        super.onViewCreated(view, savedInstanceState);
    }
}
