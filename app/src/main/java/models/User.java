package models;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

/**
 * Created by Elsayed on 12.02.2017.
 */

public class User {

    @SerializedName("id")
    int userId;

    @SerializedName("userName")
    String userName = "";

    @SerializedName("email")
    String Email = "" ;

    @SerializedName("PW")
    String PW = "";

    @SerializedName("facebook")
    Boolean facebook = false;

    @SerializedName("gmail")
    Boolean gmail = false;

    @SerializedName("userCourseslist")
    LinkedHashMap<String, Course> userCourseslist = new LinkedHashMap<String, Course>();

    @SerializedName("No_Notification_Day")
    int No_Notifications_Day = 0;

    @SerializedName("statistics")
    Statistics statistics = new Statistics();


    @SerializedName("finishedChapter")
    int finishedChapters = 0;

    @SerializedName("finishedCourses")
    int finishedCourses = 0;


   /* @SerializedName("finishedCourses")
    History user_history = new History();*/

    public LinkedHashMap<String, Course> getUserCourseslist() {
        return userCourseslist;
    }

    public void setUserCourseslist(LinkedHashMap<String, Course> userCourseslist) {
        this.userCourseslist = userCourseslist;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getFacebook() {
        return facebook;
    }

    public void setFacebook(Boolean facebook) {
        this.facebook = facebook;
    }

    public int getFinishedChapters() {
        return finishedChapters;
    }

    public void setFinishedChapters(int finishedChapters) {
        this.finishedChapters = finishedChapters;
    }

    public int getFinishedCourses() {
        return finishedCourses;
    }

    public void setFinishedCourses(int finishedCourses) {
        this.finishedCourses = finishedCourses;
    }


    public Boolean getGmail() {
        return gmail;
    }

    public void setGmail(Boolean gmail) {
        this.gmail = gmail;
    }

    public int getNo_Notifications_Day() {
        return No_Notifications_Day;
    }

    public void setNo_Notifications_Day(int no_Notifications_Day) {
        No_Notifications_Day = no_Notifications_Day;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String PW) {
        this.PW = PW;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
