package me.kartikarora.udacityreviewer.models.submissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class SubmissionRequest {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("submission_id")
    @Expose
    private Object submissionId;
    @SerializedName("closed_at")
    @Expose
    private String closedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("submission_request_projects")
    @Expose
    private List<SubmissionRequestProject> submissionRequestProjects = null;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Object submissionId) {
        this.submissionId = submissionId;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<SubmissionRequestProject> getSubmissionRequestProjects() {
        return submissionRequestProjects;
    }

    public void setSubmissionRequestProjects(List<SubmissionRequestProject> submissionRequestProjects) {
        this.submissionRequestProjects = submissionRequestProjects;
    }

}
