package me.kartikarora.udacityreviewer.models.submissions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Developer: chipset
 * Package : me.kartikarora.udacityreviewer.models.submissions
 * Project : UdacityReviewer
 * Date : 2/6/17
 */

public class Rubric {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("nomination_eligible")
    @Expose
    private Boolean nominationEligible;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNominationEligible() {
        return nominationEligible;
    }

    public void setNominationEligible(Boolean nominationEligible) {
        this.nominationEligible = nominationEligible;
    }

}
