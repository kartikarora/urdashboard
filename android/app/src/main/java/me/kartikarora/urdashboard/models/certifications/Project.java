package me.kartikarora.urdashboard.models.certifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.models.certifications
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class Project {

    @SerializedName("languages_to_recruit")
    @Expose
    private List<String> languagesToRecruit = null;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("udacity_key")
    @Expose
    private String udacityKey;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("required_skills")
    @Expose
    private String requiredSkills;
    @SerializedName("visible")
    @Expose
    private boolean visible;
    @SerializedName("awaiting_review_count")
    @Expose
    private long awaitingReviewCount;
    @SerializedName("waitlist")
    @Expose
    private boolean waitlist;
    @SerializedName("nanodegree_key")
    @Expose
    private String nanodegreeKey;
    @SerializedName("audit_project_id")
    @Expose
    private long auditProjectId;
    @SerializedName("hashtag")
    @Expose
    private String hashtag;
    @SerializedName("audit_rubric_id")
    @Expose
    private long auditRubricId;
    @SerializedName("is_cert_project")
    @Expose
    private boolean isCertProject;
    @SerializedName("audit_price")
    @Expose
    private String auditPrice;
    @SerializedName("awaiting_audit_count")
    @Expose
    private long awaitingAuditCount;

    public List<String> getLanguagesToRecruit() {
        return languagesToRecruit;
    }

    public void setLanguagesToRecruit(List<String> languagesToRecruit) {
        this.languagesToRecruit = languagesToRecruit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUdacityKey() {
        return udacityKey;
    }

    public void setUdacityKey(String udacityKey) {
        this.udacityKey = udacityKey;
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

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public long getAwaitingReviewCount() {
        return awaitingReviewCount;
    }

    public void setAwaitingReviewCount(long awaitingReviewCount) {
        this.awaitingReviewCount = awaitingReviewCount;
    }

    public boolean getWaitlist() {
        return waitlist;
    }

    public void setWaitlist(boolean waitlist) {
        this.waitlist = waitlist;
    }

    public String getNanodegreeKey() {
        return nanodegreeKey;
    }

    public void setNanodegreeKey(String nanodegreeKey) {
        this.nanodegreeKey = nanodegreeKey;
    }

    public long getAuditProjectId() {
        return auditProjectId;
    }

    public void setAuditProjectId(long auditProjectId) {
        this.auditProjectId = auditProjectId;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public long getAuditRubricId() {
        return auditRubricId;
    }

    public void setAuditRubricId(long auditRubricId) {
        this.auditRubricId = auditRubricId;
    }

    public boolean getIsCertProject() {
        return isCertProject;
    }

    public void setIsCertProject(boolean isCertProject) {
        this.isCertProject = isCertProject;
    }

    public String getAuditPrice() {
        return auditPrice;
    }

    public void setAuditPrice(String auditPrice) {
        this.auditPrice = auditPrice;
    }

    public long getAwaitingAuditCount() {
        return awaitingAuditCount;
    }

    public void setAwaitingAuditCount(long awaitingAuditCount) {
        this.awaitingAuditCount = awaitingAuditCount;
    }
}