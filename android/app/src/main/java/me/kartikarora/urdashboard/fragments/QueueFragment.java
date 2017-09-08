package me.kartikarora.urdashboard.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.adapters.QueueAdapter;
import me.kartikarora.urdashboard.datastructures.QueueList;
import me.kartikarora.urdashboard.models.certifications.Certification;
import me.kartikarora.urdashboard.models.queue.Queue;
import me.kartikarora.urdashboard.models.submissions.SubmissionRequest;
import me.kartikarora.urdashboard.models.waits.Waits;
import me.kartikarora.urdashboard.utils.HelperUtils;
import me.kartikarora.urdashboard.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.fragments
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class QueueFragment extends Fragment {

    private static final String LOG_TAG = QueueFragment.class.getName();
    private QueueList queueList = new QueueList();
    private ArrayMap<String, String> headers;
    private TextView messTextView;
    private RecyclerView queueRecyclerView;
    private UdacityReviewAPIUtils.UdacityReviewService udacityReviewService;
    private SwipeRefreshLayout refreshLayout;

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
        headers = HelperUtils.getInstance().getHeaders(getContext());
        messTextView = (TextView) view.findViewById(R.id.message_text_view);
        udacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();
        queueRecyclerView = (RecyclerView) view.findViewById(R.id.queue_recycle_view);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.accent);
        refreshLayout.setRefreshing(true);
        fetchQueue();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchQueue();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void fetchQueue() {
        queueList.clear();
        refreshLayout.setRefreshing(true);
        udacityReviewService.getSubmissionRequests(headers).enqueue(new Callback<List<SubmissionRequest>>() {
            @Override
            public void onResponse(Call<List<SubmissionRequest>> call, final Response<List<SubmissionRequest>> submissionsResponse) {
                if (submissionsResponse.code() == 401) {
                    if (isAdded()) {
                        messTextView.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        messTextView.setText(R.string.invalid_udacity_token);
                    }
                } else if (submissionsResponse.code() == 200) {
                    if (isAdded()) {
                        refreshLayout.setRefreshing(false);
                        messTextView.setVisibility(View.VISIBLE);
                    }
                    if (submissionsResponse.body().size() == 0) {
                        if (isAdded()) {
                            messTextView.setText(R.string.inactive_submission_requests);
                        }
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
                                            if (isAdded()) {
                                                queueRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                                queueRecyclerView.setAdapter(new QueueAdapter(queueList));
                                                queueRecyclerView.setVisibility(View.VISIBLE);
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<List<Waits>> call, Throwable t) {
                                            t.printStackTrace();
                                            if (isAdded()) {
                                                messTextView.setVisibility(View.VISIBLE);
                                                refreshLayout.setRefreshing(false);
                                                messTextView.setText(R.string.something_wrong);
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Certification>> call, Throwable t) {
                                t.printStackTrace();
                                if (isAdded()) {
                                    messTextView.setVisibility(View.VISIBLE);
                                    refreshLayout.setRefreshing(false);
                                    messTextView.setText(R.string.something_wrong);
                                }
                            }
                        });
                    }
                } else {

                    if (isAdded()) {
                        messTextView.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        messTextView.setText(R.string.something_wrong);
                    }
                    Log.i(LOG_TAG, submissionsResponse.code() + " - " + submissionsResponse.message());
                }
            }

            @Override
            public void onFailure(Call<List<SubmissionRequest>> call, Throwable t) {
                t.printStackTrace();
                if (isAdded()) {
                    messTextView.setVisibility(View.VISIBLE);
                    messTextView.setText(R.string.something_wrong);
                }
            }
        });
    }
}
