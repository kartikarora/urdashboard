package me.kartikarora.urdashboard.utils;

import android.support.v4.util.ArrayMap;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;

import me.kartikarora.urdashboard.models.certifications.Certification;
import me.kartikarora.urdashboard.models.me.Assigned;
import me.kartikarora.urdashboard.models.me.Feedback;
import me.kartikarora.urdashboard.models.me.Me;
import me.kartikarora.urdashboard.models.submissions.Completed;
import me.kartikarora.urdashboard.models.submissions.SubmissionRequest;
import me.kartikarora.urdashboard.models.waits.Waits;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.utils
 * Project : UR Dashboard
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

            OkHttpClient client = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
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
        Call<List<Completed>> getSubmissionsCompleted(@HeaderMap ArrayMap<String, String> headers);

        @GET("me")
        Call<Me> getMe(@HeaderMap ArrayMap<String, String> headers);

        @GET("submission_requests/{id}/waits")
        Call<List<Waits>> getWaits(@HeaderMap ArrayMap<String, String> headers, @Path("id") String id);

        @GET("me/student_feedbacks")
        Call<List<Feedback>> getFeedback(@HeaderMap ArrayMap<String, String> headers);

        @GET("me/submissions/assigned")
        Call<List<Assigned>> getCertificationAssigned(@HeaderMap ArrayMap<String, String> headers);

        @GET("me/submissions/completed")
        Call<List<Completed>> getSubmissionsCompletedWithDateRange(@HeaderMap ArrayMap<String, String> headers,
                                                                   @Query("start_date") String startDate, @Query("end_date") String endDate);

    }
}
