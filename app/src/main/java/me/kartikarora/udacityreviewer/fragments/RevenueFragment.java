package me.kartikarora.udacityreviewer.fragments;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.datastructures.CompletedList;
import me.kartikarora.udacityreviewer.models.submissions.Completed;
import me.kartikarora.udacityreviewer.utils.HelperUtils;
import me.kartikarora.udacityreviewer.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends Fragment {
    private DatePicker mStartDatePicker;
    private DatePicker mEndDatePicker;
    private TextInputLayout startDateTextInputLayout;
    private TextInputLayout endDateTextInputLayout;
    private ArrayMap<String, String> headers;
    private String startDate;
    private String endDate;
    private SwipeRefreshLayout refreshLayout;
    private Calendar calendar;

    public RevenueFragment() {

    }

    public static RevenueFragment newInstance() {
        return new RevenueFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_revenue, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headers = HelperUtils.getInstance().getHeaders(getContext());
        calendar = Calendar.getInstance();

        startDateTextInputLayout = (TextInputLayout) view.findViewById(R.id.start_date_text_input_layout);
        endDateTextInputLayout = (TextInputLayout) view.findViewById(R.id.end_date_text_input_layout);
        AppCompatButton getRevenueButton = (AppCompatButton) view.findViewById(R.id.get_revenue_button);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        final TextView lineOneTextView = (TextView) view.findViewById(R.id.revenue_line_one);
        final TextView lineTwoTextView = (TextView) view.findViewById(R.id.revenue_line_two);

        startDateTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog =
                        new DatePickerDialog(getActivity(), R.style.AppTheme, startDateListener,
                                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dialog.show();
            }
        });

        getRevenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setMessage("Please wait...");
                dialog.show();
                UdacityReviewAPIUtils.getInstance().getUdacityReviewService()
                        .getSubmissionsCompletedWithDateRange(headers, startDate, endDate).enqueue(new Callback<CompletedList>() {
                    @Override
                    public void onResponse(Call<CompletedList> call, Response<CompletedList> response) {
                        dialog.hide();
                        CompletedList completedList = response.body();
                        Collections.sort(completedList);
                        int totalRevs = 0;
                        double avgEarned = 0.0;
                        long avgTime = 0;
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
                        if (totalRevs != 0) {
                            avgEarned /= totalRevs;
                            avgTime /= totalRevs;
                        }
                        Calendar averageTime = Calendar.getInstance();
                        averageTime.setTime(new Date(avgTime));
                        averageTime.setTimeZone(TimeZone.getTimeZone("UTC"));
                        lineOneTextView.setText(getString(R.string.line_one, totalRevs, averageTime.get(Calendar.HOUR_OF_DAY),
                                averageTime.get(Calendar.MINUTE), averageTime.get(Calendar.SECOND)));
                        lineTwoTextView.setText(getString(R.string.line_two, totalEarned, avgEarned));
                        view.findViewById(R.id.revenue_title).setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<CompletedList> call, Throwable t) {
                        dialog.hide();
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private DatePickerDialog.OnDateSetListener startDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    startDateTextInputLayout.getEditText().setText(String.valueOf(day) + "/" +
                            (month + 1) + "/" + year);
                    startDate = String.valueOf(year) + "-" +
                            (month + 1) + "-" + day;

                    endDateTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DatePickerDialog dialog =
                                    new DatePickerDialog(getActivity(), R.style.AppTheme, endDateListener,
                                            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                            calendar.get(Calendar.DAY_OF_MONTH));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                                dialog.getDatePicker().setMinDate(sdf.parse(startDate).getTime());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            dialog.show();
                        }
                    });
                    endDateTextInputLayout.setVisibility(View.VISIBLE);
                }
            };

    private DatePickerDialog.OnDateSetListener endDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int year, int month, int day) {
                    endDateTextInputLayout.getEditText().setText(String.valueOf(day) + "/" +
                            (month + 1) + "/" + year);
                    endDate = String.valueOf(year) + "-" +
                            (month + 1) + "-" + day;

                }
            };

}
