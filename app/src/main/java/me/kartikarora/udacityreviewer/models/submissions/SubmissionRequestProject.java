package me.kartikarora.udacityreviewer.models.submissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.submissions
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class SubmissionRequestProject {

    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("language")
    @Expose
    private String language;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}