package me.kartikarora.urdashboard.models.me;

import java.util.HashMap;
import java.util.Map;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.models.me
 * Project : UR Dashboard
 * Date : 2/7/17
 */

public class Me {

    private long id;
    private String email;
    private String name;
    private String role;
    private String githubToken;
    private boolean acceptedTerms;
    private boolean preconditionsCompleted;
    private String udacityKey;
    private Application application;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public boolean getAcceptedTerms() {
        return acceptedTerms;
    }

    public void setAcceptedTerms(boolean acceptedTerms) {
        this.acceptedTerms = acceptedTerms;
    }

    public boolean getPreconditionsCompleted() {
        return preconditionsCompleted;
    }

    public void setPreconditionsCompleted(boolean preconditionsCompleted) {
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