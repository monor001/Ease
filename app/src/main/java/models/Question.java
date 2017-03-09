package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Elsayed on 28.02.2017.
 */

public class Question {


    @SerializedName("questionID")
    int questionID;
    @SerializedName("question")
    String question;
    @SerializedName("answerList")
    ArrayList<Answer> answerList = new ArrayList<Answer>();
    @SerializedName("rightAnswer")
    String rightAnswer = "";

    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    @SerializedName("answeredCorrectly")

    boolean answeredCorrectly = false;

    public Question(int questionID, String question, String rightAnswer, ArrayList<Answer> answerList) {
        this.questionID = questionID;
        this.question = question;
        this.answerList = answerList;
        this.rightAnswer = rightAnswer;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public ArrayList<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<Answer> answerList) {
        this.answerList = answerList;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}
