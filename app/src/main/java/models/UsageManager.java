package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Elsayed on 07.03.2017.
 */

public class UsageManager {

    // Daily
    @SerializedName("daysNumber")
    static int daysNumber = 0;
    @SerializedName("statistics_daily")
    static Statistics statistics_daily = new Statistics();

    // All Times
    static final int ALLTIME_MAX = 7;
    @SerializedName("statistics_All")
    static Statistics statistics_All = new Statistics();

}
