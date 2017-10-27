package me.kartikarora.urdashboard.models.submissions;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kartik on 14/10/17.
 */

public class Unassign {
    @SerializedName("id")
    private int id;
    @SerializedName("repo_url")
    private String repoUrl;
    @SerializedName("commit_sha")
    private String commitSha;
    @SerializedName("hidden")
    private boolean hidden;
    @SerializedName("previous_submission_id")
    private int previousSubmissionId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("assigned_at")
    private String assignedAt;
    @SerializedName("completed_at")
    private String completedAt;
    @SerializedName("grader_id")
    private int graderId;
    @SerializedName("status")
    private String status;
    @SerializedName("status_reason")
    private String statusReason;
    @SerializedName("udacity_key")
    private String udacityKey;
    @SerializedName("training_id")
    private int trainingId;
    @SerializedName("general_comment")
    private String generalComment;
    @SerializedName("notes")
    private String notes;
    @SerializedName("user")
    private User user;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("grader")
    private Grader grader;
    @SerializedName("rubric_id")
    private int rubricId;
    @SerializedName("rubric")
    private Rubric rubric;
    @SerializedName("project")
    private Project project;
    @SerializedName("price")
    private String price;
    @SerializedName("result")
    private String result;
    @SerializedName("result_reason")
    private String resultReason;
    @SerializedName("nomination")
    private String nomination;
    @SerializedName("zipfile")
    private String zipfile;
    @SerializedName("files")
    private String files;
    @SerializedName("archive_url")
    private String archiveUrl;
    @SerializedName("url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(String repoUrl) {
        this.repoUrl = repoUrl;
    }

    public String getCommitSha() {
        return commitSha;
    }

    public void setCommitSha(String commitSha) {
        this.commitSha = commitSha;
    }

    public boolean getHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public int getPreviousSubmissionId() {
        return previousSubmissionId;
    }

    public void setPreviousSubmissionId(int previousSubmissionId) {
        this.previousSubmissionId = previousSubmissionId;
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

    public String getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(String assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getGraderId() {
        return graderId;
    }

    public void setGraderId(int graderId) {
        this.graderId = graderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getUdacityKey() {
        return udacityKey;
    }

    public void setUdacityKey(String udacityKey) {
        this.udacityKey = udacityKey;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Grader getGrader() {
        return grader;
    }

    public void setGrader(Grader grader) {
        this.grader = grader;
    }

    public int getRubricId() {
        return rubricId;
    }

    public void setRubricId(int rubricId) {
        this.rubricId = rubricId;
    }

    public Rubric getRubric() {
        return rubric;
    }

    public void setRubric(Rubric rubric) {
        this.rubric = rubric;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultReason() {
        return resultReason;
    }

    public void setResultReason(String resultReason) {
        this.resultReason = resultReason;
    }

    public String getNomination() {
        return nomination;
    }

    public void setNomination(String nomination) {
        this.nomination = nomination;
    }

    public String getZipfile() {
        return zipfile;
    }

    public void setZipfile(String zipfile) {
        this.zipfile = zipfile;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getArchiveUrl() {
        return archiveUrl;
    }

    public void setArchiveUrl(String archiveUrl) {
        this.archiveUrl = archiveUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class User {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Grader {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Project {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("required_skills")
        private String requiredSkills;
        @SerializedName("awaiting_review_count")
        private int awaitingReviewCount;
        @SerializedName("hashtag")
        private String hashtag;
        @SerializedName("visible")
        private boolean visible;
        @SerializedName("audit_rubric_id")
        private int auditRubricId;
        @SerializedName("price")
        private String price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRequiredSkills() {
            return requiredSkills;
        }

        public void setRequiredSkills(String requiredSkills) {
            this.requiredSkills = requiredSkills;
        }

        public int getAwaitingReviewCount() {
            return awaitingReviewCount;
        }

        public void setAwaitingReviewCount(int awaitingReviewCount) {
            this.awaitingReviewCount = awaitingReviewCount;
        }

        public String getHashtag() {
            return hashtag;
        }

        public void setHashtag(String hashtag) {
            this.hashtag = hashtag;
        }

        public boolean getVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }

        public int getAuditRubricId() {
            return auditRubricId;
        }

        public void setAuditRubricId(int auditRubricId) {
            this.auditRubricId = auditRubricId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class Rubric {
        @SerializedName("id")
        private int id;
        @SerializedName("project_id")
        private int projectId;
        @SerializedName("project")
        private Project project;
        @SerializedName("description")
        private String description;
        @SerializedName("upload_types")

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProjectId() {
            return projectId;
        }

        public void setProjectId(int projectId) {
            this.projectId = projectId;
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
