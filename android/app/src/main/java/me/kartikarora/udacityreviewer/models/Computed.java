package me.kartikarora.udacityreviewer.models;

import java.util.Calendar;

/**
 * Created by kartik on 8/9/17.
 */

public class Computed {
    private int totalCompleted;
    private Calendar averageTime;
    private String totalEarned;
    private double avgEarned;

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public void setTotalCompleted(int totalCompleted) {
        this.totalCompleted = totalCompleted;
    }

    public Calendar getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Calendar averageTime) {
        this.averageTime = averageTime;
    }

    public String getTotalEarned() {
        return totalEarned;
    }

    public void setTotalEarned(String totalEarned) {
        this.totalEarned = totalEarned;
    }

    public double getAvgEarned() {
        return avgEarned;
    }

    public void setAvgEarned(double avgEarned) {
        this.avgEarned = avgEarned;
    }

    public Computed(int totalCompleted, Calendar averageTime, String totalEarned, double avgEarned) {
        this.totalCompleted = totalCompleted;
        this.averageTime = averageTime;
        this.totalEarned = totalEarned;
        this.avgEarned = avgEarned;
    }
}
