package me.kartikarora.udacityreviewer.datastructures;

import java.util.ArrayList;

import me.kartikarora.udacityreviewer.models.me.Feedback;


/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.datastructures
 * Project : UdacityReviewer
 * Date : 2/7/17
 */

public class FeedbackList extends ArrayList<Feedback> {
    public Feedback getFeedbackFromId(long submission_id) {
        for (Feedback feedback : this) {
            if (feedback.getSubmissionId() == submission_id)
                return feedback;
        }
        return null;
    }
}
