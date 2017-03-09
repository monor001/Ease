package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Elsayed on 11.02.2017.
 */

public class Chapter {
    @SerializedName("chapterId")
    int chapterId;

    @SerializedName("chapterName")
    String chapterName = "";

    @SerializedName("chapter_progress")
    int chapter_progress = 0;

    @SerializedName("chapterActivate")
    boolean chapterActivate = false;

    @SerializedName("questionList")
    ArrayList<Question> questionList = new ArrayList<Question>();

    @SerializedName("numQuestion")
    int numQuestion = 0;

    public int getNumQuestion() {
        return numQuestion;
    }

    public void setNumQuestion(int numQuestion) {
        this.numQuestion = numQuestion;
    }

    @SerializedName("numberofCorrectAnswers")
    int numberofCorrectAnswers = 0;

    @SerializedName("courseSuccessPercentage")
    int courseSuccessPercentage = 0;

    public int getNumberofCorrectAnswers() {
        return numberofCorrectAnswers;
    }

    public void setNumberofCorrectAnswers(int numberofCorrectAnswers) {
        this.numberofCorrectAnswers = numberofCorrectAnswers;
    }

    public int getCourseSuccessPercentage() {
        return courseSuccessPercentage;
    }

    public void setCourseSuccessPercentage(int courseSuccessPercentage) {
        this.courseSuccessPercentage = courseSuccessPercentage;
    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public Chapter(int chapterId, String chapterName, int chapter_progress, boolean chapterActivate) {
        this.chapter_progress = chapter_progress;
        this.chapterId = chapterId;
        this.chapterActivate = chapterActivate;
        this.chapterName = chapterName;
    }

    public int getChapter_progress() {
        return chapter_progress;
    }

    public void setChapter_progress(int chapter_progress) {
        this.chapter_progress = chapter_progress;
    }

    public boolean isChapterActivate() {
        return chapterActivate;
    }

    public void setChapterActivate(boolean chapterActivate) {
        this.chapterActivate = chapterActivate;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
