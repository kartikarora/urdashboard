package me.kartikarora.udacityreviewer.models.me;

import java.util.HashMap;
import java.util.Map;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.me
 * Project : UdacityReviewer
 * Date : 2/7/17
 */

public class Me {

    private Integer id;
    private String email;
    private String name;
    private String role;
    private String githubToken;
    private Boolean acceptedTerms;
    private Boolean preconditionsCompleted;
    private String udacityKey;
    private Application application;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }

    public Boolean getAcceptedTerms() {
        return acceptedTerms;
    }

    public void setAcceptedTerms(Boolean acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
    }

    public Boolean getPreconditionsCompleted() {
        return preconditionsCompleted;
    }

    public void setPreconditionsCompleted(Boolean preconditionsCompleted) {
        this.preconditionsCompleted = preconditionsCompleted;
    }

    public String getUdacityKey() {
        return udacityKey;
    }

    public void setUdacityKey(String udacityKey) {
        this.udacityKey = udacityKey;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}