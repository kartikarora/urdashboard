package me.kartikarora.udacityreviewer.adapters;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.kartikarora.udacityreviewer.R;
import me.kartikarora.udacityreviewer.models.me.Feedback;

/**
 * Created by kartik on 7/9/17.
 */

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private List<Feedback> feedbacks;

    public FeedbackAdapter(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feedback feedback = feedbacks.get(position);
        holder.feedbackRatingBar.setRating(feedback.getRating());
        if (TextUtils.isEmpty(feedback.getBody())) {
            holder.feedbackBodyTextView.setVisibility(View.GONE);
        } else {
            holder.feedbackBodyTextView.setText(feedback.getBody());
        }
        holder.feedbackNameTextView.setText(feedback.getProject().getName());
    }

    @Override
    public int getItemCount() {
        return feedbacks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView feedbackNameTextView, feedbackBodyTextView;
        AppCompatRatingBar feedbackRatingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            feedbackNameTextView = itemView.findViewById(R.id.feedback_name_text_view);
            feedbackBodyTextView = itemView.findViewById(R.id.feedback_body_text_view);
            feedbackRatingBar = itemView.findViewById(R.id.feedback_rating);

        }
    }
}
