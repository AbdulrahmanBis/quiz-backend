package com.example.demo.model;

import java.util.List;

public class QuizQuestionsResponse {
    private String quizTitle;
    private List<QuestionWrapper> questions;
    private Integer id;
    public QuizQuestionsResponse(String quizTitle, Integer id, List<QuestionWrapper> questions) {
        this.quizTitle = quizTitle;
        this.questions = questions;
        this.id = id;
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public List<QuestionWrapper> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionWrapper> questions) {
        this.questions = questions;
    }
}