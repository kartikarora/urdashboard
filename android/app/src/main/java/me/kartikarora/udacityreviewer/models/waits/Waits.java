package me.kartikarora.udacityreviewer.models.waits;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.waits
 * Project : UdacityReviewer
 * Date : 2/7/17
 */

public class Waits {

    @SerializedName("project_id")
    @Expose
    private long projectId;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("position")
    @Expose
    private long position;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

}
