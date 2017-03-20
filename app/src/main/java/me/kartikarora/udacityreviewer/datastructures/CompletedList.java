package me.kartikarora.udacityreviewer.datastructures;

import java.util.ArrayList;
import java.util.Comparator;

import me.kartikarora.udacityreviewer.models.submissions.Completed;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.datastructures
 * Project : UdacityReviewer
 * Date : 3/20/17
 */

public class CompletedList extends ArrayList<Completed> implements Comparator<Completed> {
    public Completed getSubmissionFromId(long submission_id) {
        for (Completed completed : this) {
            if (completed.getId() == submission_id) {
                return completed;
            }
        }
        return null;
    }

    @Override
    public int compare(Completed o1, Completed o2) {
        return o1.compareTo(o2);
    }
}
