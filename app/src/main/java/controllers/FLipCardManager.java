package controllers;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Elsayed on 14.02.2017.
 */

public class FLipCardManager {

    @SerializedName("id")
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
