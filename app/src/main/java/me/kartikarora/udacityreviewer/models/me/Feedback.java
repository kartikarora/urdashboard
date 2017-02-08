package me.kartikarora.udacityreviewer.models.me;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.me
 * Project : UdacityReviewer
 * Date : 2/8/17
 */

public class Feedback {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rubric_id")
    @Expose
    private Integer rubricId;
    @SerializedName("submission_id")
    @Expose
    private Integer submissionId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("grader_id")
    @Expose
    private Integer graderId;
    @SerializedName("read_at")
    @Expose
    private String readAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRubricId() {
        return rubricId;
    }

    public void setRubricId(Integer rubricId) {
        this.rubricId = rubricId;
    }

    public Integer getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Integer submissionId) {
        this.submissionId = submissionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(@Nullable Integer rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(@Nullable String body) {
        this.body = body;
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

    public Integer getGraderId() {
        return graderId;
    }

    public void setGraderId(Integer graderId) {
        this.graderId = graderId;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

}

