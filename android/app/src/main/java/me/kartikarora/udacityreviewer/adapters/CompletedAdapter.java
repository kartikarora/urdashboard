package me.kartikarora.udacityreviewer.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.models.me.Feedback;
import me.kartikarora.udacityreviewer.models.submissions.Completed;
import me.kartikarora.udacityreviewer.utils.HelperUtils;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.adapters
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.ViewHolder> {

    private static final String LOG_TAG = CompletedAdapter.class.getName();
    private List<Completed> completed;
    private Context context;

    public CompletedAdapter(List<Completed> completed) {
        this.completed = completed;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.completed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Completed completed = getItem(position);
        holder.name.setText(completed.getProject().getName());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        try {
            Date createdDate = format.parse(completed.getCreatedAt());
            Date completedDate = format.parse(completed.getCompletedAt());
            long diff = completedDate.getTime() - createdDate.getTime();
            Calendar completedDuration = Calendar.getInstance();
            completedDuration.setTime(new Date(diff));
            completedDuration.setTimeZone(TimeZone.getTimeZone("UTC"));
            completedDate = HelperUtils.getInstance().utcToDefault(completedDate, new Date());
            String money = completed.getPrice();
            holder.detail.setText(context.getString(R.string.completed_detail, df.format(completedDate),
                    completedDuration.get(Calendar.HOUR_OF_DAY), completedDuration.get(Calendar.MINUTE),
                    completedDuration.get(Calendar.SECOND), money));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (completed.getFeedback() != null) {
            Feedback feedback = completed.getFeedback();
            if (feedback.getBody() != null) {
                holder.feedback.setText(context.getString(R.string.feedback_detail, feedback.getBody()));
            } else {
                holder.feedback.setVisibility(View.GONE);
            }
            if (feedback.getRating() > 0.0) {
                holder.ratingBar.setRating(feedback.getRating());
            } else {
                holder.ratingBar.setVisibility(View.GONE);
            }
        } else {
            holder.feedback.setVisibility(View.GONE);
            holder.ratingBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return completed.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, detail, feedback;
        AppCompatRatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.completed_name);
            detail = (TextView) itemView.findViewById(R.id.completed_detail);
            feedback = (TextView) itemView.findViewById(R.id.feedback_detail);
            ratingBar = (AppCompatRatingBar) itemView.findViewById(R.id.completed_rating);
        }
    }

    private Completed getItem(int position) {
        return completed.get(position);
    }
}


