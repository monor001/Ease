package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Elsayed on 13.02.2017.
 */

public class Statistics {


    @SerializedName("coursesNum")
    int coursesNum = 0;
    @SerializedName("coursesMax")
    int coursesMax = 0;
    @SerializedName("chaptersNum")
    int chaptersNum = 0;
    @SerializedName("chaptersMax")
    int chaptersMax = 0;
    @SerializedName("questionsNum")
    int questionsNum = 0;
    @SerializedName("questionsMax")
    int questionsMax = 0;
    @SerializedName("cardsNum")
    int cardsNum = 0;
    @SerializedName("cardsMax")
    int cardsMax = 0;



    public int getCardsMax() {
        return cardsMax;
    }

    public void setCardsMax(int cardsMax) {
        this.cardsMax = cardsMax;
    }

    public int getChaptersMax() {
        return chaptersMax;
    }

    public void setChaptersMax(int chaptersMax) {
        this.chaptersMax = chaptersMax;
    }

    public int getCoursesMax() {
        return coursesMax;
    }

    public void setCoursesMax(int coursesMax) {
        this.coursesMax = coursesMax;
    }

    public int getQuestionsMax() {
        return questionsMax;
    }

    public void setQuestionsMax(int questionsMax) {
        this.questionsMax = questionsMax;
    }

    public int getCardsNum() {
        return cardsNum;
    }

    public void setCardsNum(int cardsNum) {
        this.cardsNum = cardsNum;
    }

    public int getChaptersNum() {
        return chaptersNum;
    }

    public void setChaptersNum(int chaptersNum) {
        this.chaptersNum = chaptersNum;
    }

    public int getCoursesNum() {
        return coursesNum;
    }

    public void setCoursesNum(int coursesNum) {
        this.coursesNum = coursesNum;
    }

    public int getQuestionsNum() {
        return questionsNum;
    }

    public void setQuestionsNum(int questionsNum) {
        this.questionsNum = questionsNum;
    }
}
