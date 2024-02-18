package com.web.entity;

import java.util.List;

public class CareerTestQuestion {
    private String question;
    private List<CareerTestAnswer> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<CareerTestAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<CareerTestAnswer> answers) {
        this.answers = answers;
    }
}
