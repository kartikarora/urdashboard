package me.kartikarora.urdashboard.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import me.kartikarora.urdashboard.BuildConfig;
import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.models.Computed;
import me.kartikarora.urdashboard.models.me.Assigned;
import me.kartikarora.urdashboard.models.submissions.Completed;
import me.kartikarora.urdashboard.utils.HelperUtils;
import me.kartikarora.urdashboard.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsFragment extends Fragment {

    private static final String LOG_TAG = StatsFragment.class.getName();


    private ArrayMap<String, String> headers;
    private UdacityReviewAPIUtils.UdacityReviewService udacityReviewService;

    private Call<List<Completed>> callCompleted;
    private Call<List<Assigned>> callAssigned;

    public StatsFragment() {
        // Required empty public constructor
    }

    public static StatsFragment newInstance() {

        Bundle args = new Bundle();

        StatsFragment fragment = new StatsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headers = HelperUtils.getInstance().getHeaders(getContext());
        udacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();
        setupCalls();
        if (isAdded()) {
            fetchStats(view);
        }
    }

    private void fetchStats(final View view) {
        callCompleted.enqueue(new Callback<List<Completed>>() {
            @Override
            public void onResponse(Call<List<Completed>> call, final Response<List<Completed>> completedResponse) {

                TextView statsLineOne = view.findViewById(R.id.stats_line_one);
                TextView statsLineTwo = view.findViewById(R.id.stats_line_two);
                final ProgressBar statsProgressBar = view.findViewById(R.id.stats_progress_bar);

                final List<Completed> completedList = completedResponse.body();
                Computed computed = HelperUtils.getInstance().computeHeavyStuffFromCompletedList(completedList);
                if (isAdded()) {
                    statsLineOne.setText(getContext().getString(R.string.line_one, computed.getTotalCompleted(),
                            computed.getAverageTime().get(Calendar.HOUR_OF_DAY),
                            computed.getAverageTime().get(Calendar.MINUTE),
                            computed.getAverageTime().get(Calendar.SECOND)));
                    statsLineTwo.setText(getContext().getString(R.string.line_two, computed.getTotalEarned(),
                            computed.getAvgEarned()));

                    statsLineOne.setVisibility(View.VISIBLE);
                    statsLineTwo.setVisibility(View.VISIBLE);
                    statsProgressBar.setVisibility(View.GONE);
                }

                callAssigned.enqueue(new Callback<List<Assigned>>() {
                    @Override
                    public void onResponse(Call<List<Assigned>> call, Response<List<Assigned>> response) {
                        if (isAdded()) {
                            List<Assigned> assignedList = response.body();
                            int assignedCount = assignedList.size();
                            TextView assignedTitle = view.findViewById(R.id.assigned_title);
                            TextView[] assignedLines = new TextView[]{view.findViewById(R.id.assigned_line_one), view.findViewById(R.id.assigned_line_two)};
                            ProgressBar assignedProgressBar = view.findViewById(R.id.assigned_progress_bar);

                            if (isAdded()) {
                                assignedProgressBar.setVisibility(View.GONE);
                                assignedTitle.setText(getContext().getResources().getQuantityString(R.plurals.assigned_review,
                                        assignedCount, assignedCount));
                                for (int i = 0; i < assignedList.size(); i++) {
                                    Assigned assigned = assignedList.get(i);
                                    Calendar remaining = HelperUtils.getInstance().timeRemainingCalculator(assigned.getAssignedAt());
                                    int hr = remaining.get(Calendar.HOUR);
                                    int min = remaining.get(Calendar.MINUTE);
                                    String hours = getResources().getQuantityString(R.plurals.hours, hr, hr);
                                    String minutes = getResources().getQuantityString(R.plurals.minutes, min, min);
                                    assignedLines[i].setVisibility(View.VISIBLE);
                                    assignedLines[i].setText(getString(R.string.assigned_line,
                                            assigned.getProject().getName(),
                                            Double.valueOf(assigned.getPrice()), hours, minutes));

                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Assigned>> call, Throwable t) {
                        t.printStackTrace();
                        if (call.isCanceled() && BuildConfig.DEBUG) {
                            Log.d(LOG_TAG, "Cancelled");
                        }
                    }
                });

            }

            @Override
            public void onFailure(Call<List<Completed>> call, Throwable t) {
                t.printStackTrace();
                if (call.isCanceled() && BuildConfig.DEBUG) {
                    Log.d(LOG_TAG, "Cancelled");
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setupCalls();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (callCompleted != null) {
            callCompleted.cancel();
        }
        if (callAssigned != null) {
            callAssigned.cancel();
        }
    }

    private void setupCalls() {
        if (callCompleted == null) {
            callCompleted = udacityReviewService.getSubmissionsCompleted(headers);
        }
        if (callAssigned == null) {
            callAssigned = udacityReviewService.getCertificationAssigned(headers);
        }
    }
}
