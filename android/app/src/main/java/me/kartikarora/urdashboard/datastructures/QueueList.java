package me.kartikarora.urdashboard.datastructures;

import java.util.ArrayList;

import me.kartikarora.urdashboard.models.queue.Queue;


/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.datastructures
 * Project : UR Dashboard
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
