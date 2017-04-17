package me.kartikarora.udacityreviewer.models.me;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.me
 * Project : udacity-reviewer-android
 * Date : 4/17/17
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignCount {

    @SerializedName("assigned_count")
    @Expose
    private int assignedCount;

    public int getAssignedCount() {
        return assignedCount;
    }

    public void setAssignedCount(int assignedCount) {
        this.assignedCount = assignedCount;
    }

}