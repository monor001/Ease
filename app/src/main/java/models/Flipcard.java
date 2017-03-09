package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Elsayed on 13.02.2017.
 */

public class Flipcard {

    @SerializedName("flipcardID")
    int flipcardID;
    @SerializedName("question")
    String question;
    @SerializedName("answer")
    String answer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getFlipcardID() {
        return flipcardID;
    }

    public void setFlipcardID(int flipcardID) {
        this.flipcardID = flipcardID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Flipcard(int flipcardID, String question, String answer) {
        this.answer = answer;
        this.flipcardID = flipcardID;
        this.question = question;
    }
}
