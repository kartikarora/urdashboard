package me.kartikarora.urdashboard.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import me.kartikarora.urdashboard.BuildConfig;
import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.adapters.FeedbackAdapter;
import me.kartikarora.urdashboard.adapters.ReviewsAdapter;
import me.kartikarora.urdashboard.models.me.Feedback;
import me.kartikarora.urdashboard.models.submissions.Completed;
import me.kartikarora.urdashboard.utils.HelperUtils;
import me.kartikarora.urdashboard.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewsAndFeedbackActivity extends AppCompatActivity {

    private static final String LOG_TAG = ReviewsAndFeedbackActivity.class.getName();
    private ArrayMap<String, String> headers;
    private Call<List<Completed>> callCompleted;
    private Call<List<Feedback>> callFeedback;
    private UdacityReviewAPIUtils.UdacityReviewService udacityReviewService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_and_feedback);

        HelperUtils.getInstance().changeStatusBarColor(ReviewsAndFeedbackActivity.this);
        headers = HelperUtils.getInstance().getHeaders(ReviewsAndFeedbackActivity.this);
        udacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();

        final RecyclerView reviewsRecyclerView = findViewById(R.id.reviews_recycle_view);
        final RecyclerView feedbackRecyclerView = findViewById(R.id.feedback_recycler_view);

        setupCalls();

        callCompleted.enqueue(new Callback<List<Completed>>() {
            @Override
            public void onResponse(Call<List<Completed>> call, final Response<List<Completed>> response) {
                reviewsRecyclerView.setAdapter(new ReviewsAdapter(response.body()));
                reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                reviewsRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Completed>> call, Throwable t) {
                t.printStackTrace();
                if (call.isCanceled() && BuildConfig.DEBUG) {
                    Log.d(LOG_TAG, "Cancelled");
                }
            }
        });

        callFeedback.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                feedbackRecyclerView.setAdapter(new FeedbackAdapter(response.body()));
                feedbackRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                feedbackRecyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                t.printStackTrace();
                if (call.isCanceled() && BuildConfig.DEBUG) {
                    Log.d(LOG_TAG, "Cancelled");
                }
            }
        });


        ConstraintLayout bottomSheetLayout = findViewById(R.id.feedback_bottom_sheet);
        final AppCompatTextView feedbacksTextView = findViewById(R.id.feedback_text_view);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetLayout.setVisibility(View.VISIBLE);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    feedbacksTextView.setText(getString(R.string.feedback));
                    HelperUtils.getInstance().changeStatusBarColor(ReviewsAndFeedbackActivity.this, R.color.primary);
                    HelperUtils.getInstance().clearLightStatusBar(ReviewsAndFeedbackActivity.this);
                } else {
                    feedbacksTextView.setText(R.string.swipe_up_feedback);
                    HelperUtils.getInstance().changeStatusBarColor(ReviewsAndFeedbackActivity.this);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (callCompleted != null) {
            callCompleted.cancel();
        }
        if (callFeedback != null) {
            callFeedback.cancel();
        }
    }

    private void setupCalls() {
        if (callCompleted == null) {
            callCompleted = udacityReviewService.getSubmissionsCompleted(headers);
        }
        if (callFeedback == null) {
            callFeedback = udacityReviewService.getFeedback(headers);
        }
    }
}
