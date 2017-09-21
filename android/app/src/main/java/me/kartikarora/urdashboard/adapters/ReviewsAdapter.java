package me.kartikarora.urdashboard.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
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

import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.models.submissions.Completed;
import me.kartikarora.urdashboard.utils.HelperUtils;

/**
 * Created by kartik on 7/9/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Completed> completed;
    private Context context;

    public ReviewsAdapter(List<Completed> completed) {
        this.completed = completed;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CompletedViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_completed, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        CompletedViewHolder holder = (CompletedViewHolder) viewHolder;
        Completed completed = this.completed.get(position);
        holder.name.setText(completed.getProject().getName());
        holder.setProjectId(completed.getId());
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        try {
            Date createdDate = format.parse(completed.getAssignedAt());
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
    }

    @Override
    public int getItemCount() {
        return completed.size();
    }


    class CompletedViewHolder extends RecyclerView.ViewHolder {
        TextView name, detail;
        long projectId;

        CompletedViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.completed_name);
            detail = itemView.findViewById(R.id.completed_detail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "https://review.udacity.com/#!/reviews/";
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(url + getProjectId()));
                }
            });
        }

        public long getProjectId() {
            return projectId;
        }

        public void setProjectId(long projectId) {
            this.projectId = projectId;
        }
    }
}
