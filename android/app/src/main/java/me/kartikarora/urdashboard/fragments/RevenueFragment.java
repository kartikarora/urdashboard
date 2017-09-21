package me.kartikarora.urdashboard.fragments;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.models.Computed;
import me.kartikarora.urdashboard.models.submissions.Completed;
import me.kartikarora.urdashboard.utils.HelperUtils;
import me.kartikarora.urdashboard.utils.UdacityReviewAPIUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevenueFragment extends Fragment {
    private ArrayMap<String, String> headers;
    private Calendar calendarToday;
    private UdacityReviewAPIUtils.UdacityReviewService mUdacityReviewService;
    private Call<List<Completed>> callRevenueToday, callRevenueThisMonth, callRevenueCustom;
    private static final SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");


    public RevenueFragment() {
        sd.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendarToday = Calendar.getInstance();
        mUdacityReviewService = UdacityReviewAPIUtils.getInstance().getUdacityReviewService();
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
        setupCalls();
        if (isAdded()) {
            fetchRevenue(view);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (callRevenueToday != null) {
            callRevenueToday.cancel();
        }
        if (callRevenueThisMonth != null) {
            callRevenueThisMonth.cancel();
        }
        if (callRevenueCustom != null) {
            callRevenueCustom.cancel();
        }
    }

    private void fetchRevenue(final View view) {
        callRevenueToday.enqueue(new Callback<List<Completed>>() {
            @Override
            public void onResponse(Call<List<Completed>> call, Response<List<Completed>> response) {
                List<Completed> completedList = response.body();
                Collections.sort(completedList);
                Computed computed = HelperUtils.getInstance().computeHeavyStuffFromCompletedList(completedList);

                ProgressBar todayProgressBar = view.findViewById(R.id.today_progress_bar);
                AppCompatTextView todayLineOneTextView = view.findViewById(R.id.today_stats_line_one);
                AppCompatTextView todayLineTwoTextView = view.findViewById(R.id.today_stats_line_two);

                if (isAdded()) {
                    todayLineOneTextView.setText(getString(R.string.line_one, computed.getTotalCompleted(),
                            computed.getAverageTime().get(Calendar.HOUR_OF_DAY),
                            computed.getAverageTime().get(Calendar.MINUTE),
                            computed.getAverageTime().get(Calendar.SECOND)));
                    todayLineTwoTextView.setText(getString(R.string.line_two, computed.getTotalEarned(),
                            computed.getAvgEarned()));

                    todayProgressBar.setVisibility(View.GONE);
                    todayLineOneTextView.setVisibility(View.VISIBLE);
                    todayLineTwoTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Completed>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        callRevenueThisMonth.enqueue(new Callback<List<Completed>>() {
            @Override
            public void onResponse(Call<List<Completed>> call, Response<List<Completed>> response) {
                List<Completed> completedList = response.body();
                Collections.sort(completedList);
                Computed computed = HelperUtils.getInstance().computeHeavyStuffFromCompletedList(completedList);

                ProgressBar todayProgressBar = view.findViewById(R.id.month_progress_bar);
                AppCompatTextView monthLineOneTextView = view.findViewById(R.id.month_stats_line_one);
                AppCompatTextView monthLineTwoTextView = view.findViewById(R.id.month_stats_line_two);

                if (isAdded()) {
                    monthLineOneTextView.setText(getString(R.string.line_one, computed.getTotalCompleted(),
                            computed.getAverageTime().get(Calendar.HOUR_OF_DAY),
                            computed.getAverageTime().get(Calendar.MINUTE),
                            computed.getAverageTime().get(Calendar.SECOND)));
                    monthLineTwoTextView.setText(getString(R.string.line_two, computed.getTotalEarned(),
                            computed.getAvgEarned()));

                    todayProgressBar.setVisibility(View.GONE);
                    monthLineOneTextView.setVisibility(View.VISIBLE);
                    monthLineTwoTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Completed>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        final TextInputLayout startDateTextInputLayout = view.findViewById(R.id.start_date_text_input_layout);
        final TextInputLayout endDateTextInputLayout = view.findViewById(R.id.end_date_text_input_layout);
        final AppCompatButton calculateRevenueButton = view.findViewById(R.id.calculate_revenue_button);
        final ProgressBar customProgressBar = view.findViewById(R.id.custom_progress_bar);
        final AppCompatTextView customLineOneTextView = view.findViewById(R.id.custom_stats_line_one);
        final AppCompatTextView customLineTwoTextView = view.findViewById(R.id.custom_stats_line_two);
        final String[] dates = new String[2];

        final DatePickerDialog.OnDateSetListener endDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        endDateTextInputLayout.getEditText().setText(String.valueOf(day) + "/" +
                                (month + 1) + "/" + year);
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, 23, 59, 59);
                        Date date = cal.getTime();
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                        sd.setTimeZone(TimeZone.getTimeZone("UTC"));
                        dates[1] = sd.format(date);
                        calculateRevenueButton.setEnabled(true);
                    }
                };


        final DatePickerDialog.OnDateSetListener startDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        startDateTextInputLayout.getEditText().setText(String.valueOf(day) + "/" +
                                (month + 1) + "/" + year);
                        final Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, 0, 0, 0);
                        Date date = cal.getTime();
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
                        sd.setTimeZone(TimeZone.getTimeZone("UTC"));
                        dates[0] = sd.format(date);

                        endDateTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatePickerDialog dialog =
                                        new DatePickerDialog(getActivity(), endDateListener,
                                                calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH),
                                                calendarToday.get(Calendar.DAY_OF_MONTH));
                                dialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                                dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                                dialog.show();
                            }
                        });
                        endDateTextInputLayout.setVisibility(View.VISIBLE);
                    }
                };

        startDateTextInputLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog =
                        new DatePickerDialog(getActivity(), startDateListener,
                                calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH),
                                calendarToday.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(calendarToday.getTimeInMillis());
                dialog.show();
            }
        });

        calculateRevenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                customProgressBar.setVisibility(View.VISIBLE);
                customLineOneTextView.setVisibility(View.GONE);
                customLineTwoTextView.setVisibility(View.GONE);
                callRevenueCustom = mUdacityReviewService.getSubmissionsCompletedWithDateRange(headers,
                        dates[0], dates[1]);
                callRevenueCustom.enqueue(new Callback<List<Completed>>() {
                    @Override
                    public void onResponse(Call<List<Completed>> call, Response<List<Completed>> response) {
                        List<Completed> completedList = response.body();
                        Collections.sort(completedList);
                        Computed computed = HelperUtils.getInstance().computeHeavyStuffFromCompletedList(completedList);


                        if (isAdded()) {
                            customLineOneTextView.setText(getString(R.string.line_one, computed.getTotalCompleted(),
                                    computed.getAverageTime().get(Calendar.HOUR_OF_DAY),
                                    computed.getAverageTime().get(Calendar.MINUTE),
                                    computed.getAverageTime().get(Calendar.SECOND)));
                            customLineTwoTextView.setText(getString(R.string.line_two, computed.getTotalEarned(),
                                    computed.getAvgEarned()));

                            customProgressBar.setVisibility(View.GONE);
                            customLineOneTextView.setVisibility(View.VISIBLE);
                            customLineTwoTextView.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Completed>> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

    }


    private void setupCalls() {
        if (callRevenueToday == null) {
            Calendar startToday = Calendar.getInstance();
            startToday.set(Calendar.HOUR_OF_DAY, 0);
            startToday.set(Calendar.MINUTE, 0);
            startToday.set(Calendar.SECOND, 0);
            startToday.set(Calendar.MILLISECOND, 0);
            Calendar endToday = Calendar.getInstance();
            endToday.set(Calendar.HOUR_OF_DAY, 23);
            endToday.set(Calendar.MINUTE, 59);
            endToday.set(Calendar.SECOND, 59);
            endToday.set(Calendar.MILLISECOND, 999);
            callRevenueToday = mUdacityReviewService.getSubmissionsCompletedWithDateRange(headers,
                    sd.format(startToday.getTime()), sd.format(endToday.getTime()));
        }
        if (callRevenueThisMonth == null) {
            callRevenueThisMonth = mUdacityReviewService.getSubmissionsCompletedWithDateRange(headers,
                    sd.format(getFirstDateOfCurrentMonth().getTime()), sd.format(getLastDayOfCurrentMonth().getTime()));
        }
    }

    private Calendar getFirstDateOfCurrentMonth() {
        Calendar firstDay = Calendar.getInstance();
        firstDay.set(Calendar.HOUR_OF_DAY, 0);
        firstDay.set(Calendar.MINUTE, 0);
        firstDay.set(Calendar.SECOND, 0);
        firstDay.set(Calendar.MILLISECOND, 0);
        firstDay.set(Calendar.DAY_OF_MONTH, firstDay.getActualMinimum(Calendar.DAY_OF_MONTH));
        return firstDay;
    }

    private Calendar getLastDayOfCurrentMonth() {
        Calendar lastDay = Calendar.getInstance();
        calendarToday.set(Calendar.HOUR_OF_DAY, 23);
        calendarToday.set(Calendar.MINUTE, 59);
        calendarToday.set(Calendar.SECOND, 59);
        calendarToday.set(Calendar.MILLISECOND, 999);
        lastDay.set(Calendar.DAY_OF_MONTH, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        return lastDay;
    }
}