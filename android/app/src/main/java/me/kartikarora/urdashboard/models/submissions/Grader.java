package me.kartikarora.urdashboard.models.submissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.urdashboard.models.submissions
 * Project : UR Dashboard
 * Date : 2/6/17
 */

public class Grader{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("udacity_key")
    @Expose
    private String udacityKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUdacityKey() {
        return udacityKey;
    }

    public void setUdacityKey(String udacityKey) {
        this.udacityKey = udacityKey;
    }

}
