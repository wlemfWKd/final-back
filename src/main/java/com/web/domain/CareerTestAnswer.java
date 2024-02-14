package com.web.domain;

public class CareerTestAnswer {
    private String answerText;
    private int answerScore;

    public CareerTestAnswer(String answerText, int answerScore) {
        this.answerText = answerText;
        this.answerScore = answerScore;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(int answerScore) {
        this.answerScore = answerScore;
    }
}