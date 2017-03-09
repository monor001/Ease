package models;

/**
 * Created by asus on 28.02.2017.
 */

public class Answer {

    int answerID;
    String answer;
    boolean rightAnswer;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public boolean isRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        this.rightAnswer = rightAnswer;
    }
}
