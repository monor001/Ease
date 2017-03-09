package models;

import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Elsayed on 11.02.2017.
 */

public class Course {

    //General attributes
    @SerializedName("courseId")
    int courseId;

    @SerializedName("courseDisplayId")
    int courseDisplayId;

    @SerializedName("courseName")
    String courseName = "";

    @SerializedName("courseDescription")
    String courseDescription = "";

    @SerializedName("courseProgress")
    int courseProgress = 0;

    // Indicators
    @SerializedName("courseActive")
    boolean courseActive = false;

    @SerializedName("displayed_MA")
    boolean displayed_MA = false;

    @SerializedName("flipcards")
    ArrayList<Flipcard> flipcards = new ArrayList<Flipcard>();

    @SerializedName("percentageMax")
    int percentageMax = 0;

    @SerializedName("flipcardsActive")
    int percentageMin = 0;

    @SerializedName("flipcardsActive")
    boolean flipcardsActive = false;

    public boolean isFlipcardsActive() {
        return flipcardsActive;
    }

    public void setFlipcardsActive(boolean flipcardsActive) {
        this.flipcardsActive = flipcardsActive;
    }

    public ArrayList<Flipcard> getFlipcards() {
        return flipcards;
    }

    public void setFlipcards(ArrayList<Flipcard> flipcards) {
        this.flipcards = flipcards;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public int getCourseDisplayId() {
        return courseDisplayId;
    }

    public void setCourseDisplayId(int courseDisplayId) {
        this.courseDisplayId = courseDisplayId;
    }

    Button btn;
    public ProgressBar progressBar_P_A;
    public Handler handler;
    public Runnable runnable;



    // Chapters
    @SerializedName("chapters")
    LinkedHashMap<String, Chapter> chapters = new LinkedHashMap<String, Chapter>();

    public LinkedHashMap<String, Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(LinkedHashMap<String, Chapter> chapters) {
        this.chapters = chapters;
    }




    public boolean isDisplayed_MA() {
        return displayed_MA;
    }

    public void setDisplayed_MA(boolean displayed_MA) {
        this.displayed_MA = displayed_MA;
    }



    public Course() {
    }

    public Course(int courseId, String courseName, boolean courseActive, int courseProgress) {
        this.courseActive = courseActive;
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseProgress = courseProgress;
    }

    public boolean isCourseActive() {
        return courseActive;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ProgressBar getProgressBar_P_A() {
        return progressBar_P_A;
    }

    public void setProgressBar_P_A(ProgressBar progressBar_P_A) {
        this.progressBar_P_A = progressBar_P_A;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setCourseActive(boolean courseActive) {
        this.courseActive = courseActive;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCourseProgress() {
        return courseProgress;
    }

    public void setCourseProgress(int courseProgress) {
        this.courseProgress = courseProgress;
    }
}
