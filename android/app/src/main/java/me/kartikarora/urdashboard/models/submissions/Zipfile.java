package me.kartikarora.urdashboard.models.submissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.models.submissions
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class Zipfile {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
