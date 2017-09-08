package me.kartikarora.urdashboard.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.kartikarora.urdashboard.R;
import me.kartikarora.urdashboard.datastructures.QueueList;
import me.kartikarora.urdashboard.models.queue.Queue;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.adapters
 * Project : UR Dashboard
 * Date : 2/7/17
 */

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.ViewHolder> {

    private QueueList queueList;
    private Context context;

    public QueueAdapter(QueueList queueList) {
        this.queueList = queueList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_queue, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Queue queueItem = getItem(position);
        holder.queueText.setText(context.getString(R.string.queue_text, queueItem.getPosition(), queueItem.getName()));
    }

    @Override
    public int getItemCount() {
        return queueList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView queueText;

        ViewHolder(View itemView) {
            super(itemView);
            queueText = itemView.findViewById(R.id.queue_text);
        }
    }

    private Queue getItem(int position) {
        return queueList.get(position);
    }
}
