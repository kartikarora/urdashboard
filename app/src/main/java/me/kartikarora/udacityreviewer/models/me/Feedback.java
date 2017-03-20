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
    private long id;
    @SerializedName("rubric_id")
    @Expose
    private long rubricId;
    @SerializedName("submission_id")
    @Expose
    private long submissionId;
    @SerializedName("user_id")
    @Expose
    private long userId;
    @SerializedName("rating")
    @Expose
    private long rating;
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
    private long graderId;
    @SerializedName("read_at")
    @Expose
    private String readAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRubricId() {
        return rubricId;
    }

    public void setRubricId(long rubricId) {
        this.rubricId = rubricId;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(@Nullable long rating) {
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

    public long getGraderId() {
        return graderId;
    }

    public void setGraderId(long graderId) {
        this.graderId = graderId;
    }

    public String getReadAt() {
        return readAt;
    }

    public void setReadAt(String readAt) {
        this.readAt = readAt;
    }

}

