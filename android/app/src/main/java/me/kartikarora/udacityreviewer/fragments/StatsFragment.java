package me.kartikarora.udacityreviewer.fragments;


import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.fujiyuu75.sequent.Sequent;

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
import me.kartikarora.udacityreviewer.models.me.AssignCount;
import me.kartikarora.udacityreviewer.models.submissions.Completed;
import me.kartikarora.udacityreviewer.utils.HelperUtils;
import me.kartikarora.udacityreviewer.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatsFragment extends Fragment {

    private static final String LOG_TAG = StatsFragment.class.getName();
    private int totalRevs = 0;
    private double avgEarned = 0.0;
    private long avgTime = 0;


    private ArrayMap<String, String> headers;
    private RecyclerView recyclerView;
    private UdacityReviewAPIUtils.UdacityReviewService udacityReviewService;
    private ProgressBar progressBar;

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
        recyclerView = view.findViewById(R.id.completed_recycle_view);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        udacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();

        if (isAdded()) {
            fetchStats(view);
        }
    }

    private void fetchStats(final View view) {
        udacityReviewService.getSubmissionsCompleted(headers).enqueue(new Callback<CompletedList>() {
            @Override
            public void onResponse(Call<CompletedList> call, final Response<CompletedList> completedResponse) {

                udacityReviewService.getCertificationAssigned(headers).enqueue(new Callback<AssignCount>() {
                    @Override
                    public void onResponse(Call<AssignCount> call, Response<AssignCount> response) {

                        TextView lineOne = view.findViewById(R.id.stats_line_one);
                        TextView lineTwo = view.findViewById(R.id.stats_line_two);
                        TextView lineThree = view.findViewById(R.id.stats_line_three);
                        TextView reviewsTitle = view.findViewById(R.id.reviews_title);
                        ConstraintLayout layout = view.findViewById(R.id.layout);

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
                        if (isAdded()) {
                            lineOne.setText(getContext().getString(R.string.line_one, totalRevs, averageTime.get(Calendar.HOUR_OF_DAY),
                                    averageTime.get(Calendar.MINUTE), averageTime.get(Calendar.SECOND)));
                            lineTwo.setText(getContext().getString(R.string.line_two, totalEarned, avgEarned));
                            lineThree.setText(getContext().getString(R.string.assigned_review, response.body().getAssignedCount()));

                            Sequent.origin(layout).anim(getContext(), com.fujiyuu75.sequent.Animation.FADE_IN_UP)
                                    .duration(1000).start();
                            Sequent.origin(recyclerView).anim(getContext(), com.fujiyuu75.sequent.Animation.FADE_IN_UP)
                                    .duration(1000).start();

                            lineOne.setVisibility(View.VISIBLE);
                            lineTwo.setVisibility(View.VISIBLE);
                            lineThree.setVisibility(View.VISIBLE);
                            reviewsTitle.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                            YoYo.with(Techniques.FadeOut)
                                    .duration(1000)
                                    .onEnd(new YoYo.AnimatorCallback() {
                                        @Override
                                        public void call(Animator animator) {
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    })
                                    .playOn(progressBar);
                        }
                    }

                    @Override
                    public void onFailure(Call<AssignCount> call, Throwable t) {
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
