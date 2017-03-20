package me.kartikarora.udacityreviewer.datastructures;

import java.util.ArrayList;

import me.kartikarora.udacityreviewer.models.queue.Queue;


/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.datastructures
 * Project : UdacityReviewer
 * Date : 2/7/17
 */

public class QueueList extends ArrayList<Queue> {
    public Queue getQueueItemFromProjectId(long project_id) {
        for (Queue queue : this) {
            if (queue.getProject_id() == project_id)
                return queue;
        }
        return null;
    }
}
