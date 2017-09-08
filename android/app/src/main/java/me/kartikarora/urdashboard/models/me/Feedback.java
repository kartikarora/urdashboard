package me.kartikarora.urdashboard.models.me;

import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.models.me
 * Project : UR Dashboard
 * Date : 2/8/17
 */

public class Feedback {
    @SerializedName("id")
    private int id;
    @SerializedName("rubric_id")
    private int rubric_id;
    @SerializedName("submission_id")
    private int submission_id;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("rating")
    private int rating;
    @SerializedName("body")
    private String body;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("grader_id")
    private int grader_id;
    @SerializedName("read_at")
    private String read_at;
    @SerializedName("project")
    private Project project;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRubric_id() {
        return rubric_id;
    }

    public void setRubric_id(int rubric_id) {
        this.rubric_id = rubric_id;
    }

    public int getSubmission_id() {
        return submission_id;
    }

    public void setSubmission_id(int submission_id) {
        this.submission_id = submission_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getGrader_id() {
        return grader_id;
    }

    public void setGrader_id(int grader_id) {
        this.grader_id = grader_id;
    }

    public String getRead_at() {
        return read_at;
    }

    public void setRead_at(String read_at) {
        this.read_at = read_at;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public static class Project {
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

