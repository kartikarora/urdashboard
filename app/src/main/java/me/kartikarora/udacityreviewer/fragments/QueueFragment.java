package me.kartikarora.udacityreviewer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.adapters.QueueAdapter;
import me.kartikarora.udacityreviewer.datastructures.QueueList;
import me.kartikarora.udacityreviewer.models.certifications.Certification;
import me.kartikarora.udacityreviewer.models.queue.Queue;
import me.kartikarora.udacityreviewer.models.submissions.SubmissionRequest;
import me.kartikarora.udacityreviewer.models.waits.Waits;
import me.kartikarora.udacityreviewer.utils.HelperUtils;
import me.kartikarora.udacityreviewer.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.fragments
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class QueueFragment extends Fragment {

    private static final String LOG_TAG = QueueFragment.class.getName();
    private QueueList queueList = new QueueList();

    public QueueFragment() {
    }

    public static QueueFragment newInstance() {
        QueueFragment fragment = new QueueFragment();
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
        return inflater.inflate(R.layout.fragment_queue, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        final ArrayMap<String, String> headers = HelperUtils.getInstance().getHeaders(getContext());
        final ProgressBar bar = (ProgressBar) view.findViewById(R.id.loading_progress);
        final TextView messTextView = (TextView) view.findViewById(R.id.message_text_view);
        final RecyclerView queueRecyclerView = (RecyclerView) view.findViewById(R.id.queue_recycle_view);
        final UdacityReviewAPIUtils.UdacityReviewService udacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();

        udacityReviewService.getSubmissionRequests(headers).enqueue(new Callback<List<SubmissionRequest>>() {
            @Override
            public void onResponse(Call<List<SubmissionRequest>> call, final Response<List<SubmissionRequest>> submissionsResponse) {
                if (submissionsResponse.code() == 401) {
                    messTextView.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.GONE);
                    messTextView.setText(R.string.invalid_udacity_token);
                } else if (submissionsResponse.code() == 200) {
                    bar.setVisibility(View.GONE);
                    messTextView.setVisibility(View.VISIBLE);
                    if (submissionsResponse.body().size() == 0) {
                        messTextView.setText(R.string.inactive_submission_requests);
                    } else {
                        udacityReviewService.getCertifications(headers).enqueue(new Callback<List<Certification>>() {
                            @Override
                            public void onResponse(Call<List<Certification>> call, Response<List<Certification>> certificationsResponse) {
                                for (Certification certification : certificationsResponse.body()) {
                                    if (certification.getStatus().equals("certified")) {
                                        Queue queue = new Queue();
                                        queue.setProject_id(certification.getProject().getId());
                                        queue.setName(certification.getProject().getName());
                                        queueList.add(queue);
                                    }
                                }
                                for (SubmissionRequest request : submissionsResponse.body()) {
                                    udacityReviewService.getWaits(headers, String.valueOf(request.getId())).enqueue(new Callback<List<Waits>>() {
                                        @Override
                                        public void onResponse(Call<List<Waits>> call, Response<List<Waits>> waitsResponse) {
                                            for (Waits waits : waitsResponse.body()) {
                                                Queue queue = queueList.getQueueItemFromProjectId(waits.getProjectId());
                                                queue.setPosition(waits.getPosition());
                                                queueList.set(queueList.indexOf(queue), queue);
                                            }
                                            queueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                            queueRecyclerView.setAdapter(new QueueAdapter(queueList));
                                            queueRecyclerView.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onFailure(Call<List<Waits>> call, Throwable t) {
                                            t.printStackTrace();
                                            messTextView.setVisibility(View.VISIBLE);
                                            bar.setVisibility(View.GONE);
                                            messTextView.setText(R.string.something_wrong);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Certification>> call, Throwable t) {
                                t.printStackTrace();
                                messTextView.setVisibility(View.VISIBLE);
                                bar.setVisibility(View.GONE);
                                messTextView.setText(R.string.something_wrong);
                            }
                        });

                    }
                } else {
                    messTextView.setVisibility(View.VISIBLE);
                    bar.setVisibility(View.GONE);
                    messTextView.setText(R.string.something_wrong);
                    Log.i(LOG_TAG, submissionsResponse.code() + " - " + submissionsResponse.message());
                }
            }

            @Override
            public void onFailure(Call<List<SubmissionRequest>> call, Throwable t) {
                t.printStackTrace();
                messTextView.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
                messTextView.setText(R.string.something_wrong);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}
