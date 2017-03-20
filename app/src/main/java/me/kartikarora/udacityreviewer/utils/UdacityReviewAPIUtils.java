package me.kartikarora.udacityreviewer.utils;

import android.support.v4.util.ArrayMap;

import java.util.List;

import me.kartikarora.udacityreviewer.datastructures.CompletedList;
import me.kartikarora.udacityreviewer.datastructures.FeedbackList;
import me.kartikarora.udacityreviewer.models.certifications.Certification;
import me.kartikarora.udacityreviewer.models.me.Me;
import me.kartikarora.udacityreviewer.models.submissions.SubmissionRequest;
import me.kartikarora.udacityreviewer.models.waits.Waits;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.utils
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class UdacityReviewAPIUtils {
    private static UdacityReviewAPIUtils udacityReviewAPIUtils = null;
    private static UdacityReviewService udacityReviewService = null;

    private static String BASE_URL = "https://review-api.udacity.com/api/v1/";

    public static UdacityReviewAPIUtils getInstance() {
        if (udacityReviewAPIUtils == null) {
            udacityReviewAPIUtils = new UdacityReviewAPIUtils();
        }
        return udacityReviewAPIUtils;
    }

    private UdacityReviewAPIUtils() {
    }

    public UdacityReviewService getUdacityReviewService() {
        if (udacityReviewService == null) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            udacityReviewService = retrofit.create(UdacityReviewService.class);
        }
        return udacityReviewService;
    }

    public interface UdacityReviewService {
        @GET("me/submission_requests")
        Call<List<SubmissionRequest>> getSubmissionRequests(@HeaderMap ArrayMap<String, String> headers);

        @GET("me/certifications")
        Call<List<Certification>> getCertifications(@HeaderMap ArrayMap<String, String> headers);

        @GET("me/submissions/completed")
        Call<CompletedList> getSubmissionsCompleted(@HeaderMap ArrayMap<String, String> headers);

        @GET("me")
        Call<Me> getMe(@HeaderMap ArrayMap<String, String> headers);

        @GET("submission_requests/{id}/waits")
        Call<List<Waits>> getWaits(@HeaderMap ArrayMap<String, String> headers, @Path("id") String id);

        @GET("me/student_feedbacks")
        Call<FeedbackList> getFeedbacks(@HeaderMap ArrayMap<String, String> headers);
    }
}
