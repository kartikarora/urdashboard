
package me.kartikarora.urdashboard.models.me;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rubric {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("nomination_eligible")
    @Expose
    private boolean nominationEligible;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNominationEligible() {
        return nominationEligible;
    }

    public void setNominationEligible(boolean nominationEligible) {
        this.nominationEligible = nominationEligible;
    }

}
