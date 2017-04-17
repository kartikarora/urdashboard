package me.kartikarora.udacityreviewer.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.adapters.CompletedAdapter;
import me.kartikarora.udacityreviewer.datastructures.CompletedList;
import me.kartikarora.udacityreviewer.datastructures.FeedbackList;
import me.kartikarora.udacityreviewer.models.me.AssignCount;
import me.kartikarora.udacityreviewer.models.me.Feedback;
import me.kartikarora.udacityreviewer.models.submissions.Completed;
import me.kartikarora.udacityreviewer.utils.HelperUtils;
import me.kartikarora.udacityreviewer.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

    private static final String LOG_TAG = StatsFragment.class.getName();
    private int totalRevs = 0;
    private double avgEarned = 0.0;
    private long avgTime = 0;


    private ArrayMap<String, String> headers;
    private RecyclerView recyclerView;
    private UdacityReviewAPIUtils.UdacityReviewService udacityReviewService;
    private SwipeRefreshLayout refreshLayout;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.completed_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        udacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        refreshLayout.setColorSchemeResources(R.color.accent);
        refreshLayout.setRefreshing(true);
        fetchStats(view);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchStats(view);
            }
        });
    }

    private void fetchStats(final View view) {
        refreshLayout.setRefreshing(true);

        udacityReviewService.getSubmissionsCompleted(headers).enqueue(new Callback<CompletedList>() {
            @Override
            public void onResponse(Call<CompletedList> call, final Response<CompletedList> completedResponse) {
                CompletedList completedList = completedResponse.body();
                Collections.sort(completedList);
                recyclerView.setAdapter(new CompletedAdapter(completedList));
                totalRevs = completedList.size();
                for (Completed project : completedList) {
                    avgEarned += Double.parseDouble(project.getPrice());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                    try {
                        Date createdDate = format.parse(project.getCreatedAt());
                        Date completedDate = format.parse(project.getCompletedAt());
                        long diff = completedDate.getTime() - createdDate.getTime();
                        avgTime += diff;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                String totalEarned = HelperUtils.getInstance().priceWithCommas(String.valueOf(avgEarned));
                avgEarned /= totalRevs;
                avgTime /= totalRevs;
                Calendar averageTime = Calendar.getInstance();
                averageTime.setTime(new Date(avgTime));
                averageTime.setTimeZone(TimeZone.getTimeZone("UTC"));

                TextView lineOne = (TextView) view.findViewById(R.id.stats_line_one);
                TextView lineTwo = (TextView) view.findViewById(R.id.stats_line_two);

                if (isAdded()) {
                    lineOne.setText(getString(R.string.line_one, totalRevs, averageTime.get(Calendar.HOUR_OF_DAY),
                            averageTime.get(Calendar.MINUTE), averageTime.get(Calendar.SECOND)));
                    lineTwo.setText(getString(R.string.line_two, totalEarned, avgEarned));
                }
                udacityReviewService.getFeedbacks(headers).enqueue(new Callback<FeedbackList>() {
                    @Override
                    public void onResponse(Call<FeedbackList> call, Response<FeedbackList> response) {
                        FeedbackList list = response.body();
                        Logger.d(list);
                        CompletedList completedList = completedResponse.body();
                        for (Feedback feedback : list) {
                            Completed completed = completedList.getSubmissionFromId(feedback.getSubmissionId());
                            completed.setFeedback(feedback);
                        }

                        if (isAdded()) {
                            view.findViewById(R.id.stats_app_bar).setVisibility(View.VISIBLE);
                            view.findViewById(R.id.reviews_title).setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            refreshLayout.setRefreshing(false);
                        }

                        udacityReviewService.getCertificationAssigned(headers).enqueue(new Callback<AssignCount>() {
                            @Override
                            public void onResponse(Call<AssignCount> call, Response<AssignCount> response) {
                                TextView textView = (TextView) view.findViewById(R.id.assignedReview);
                                textView.setText(getContext().getString(R.string.assigned_review, response.body().getAssignedCount()));
                            }

                            @Override
                            public void onFailure(Call<AssignCount> call, Throwable t) {
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<FeedbackList> call, Throwable t) {
                        t.printStackTrace();
                    }
                });

            }

            @Override
            public void onFailure(Call<CompletedList> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
