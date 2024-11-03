package com.example.quizapp;
// Response.java
public class Response {

    private String questionId;
    private String selectedAnswer;
    private String correctAnswer;

    // No-argument constructor
    public Response() {
    }

    // Parameterized constructor
    public Response(String questionId, String selectedAnswer, String correctAnswer) {
        this.questionId = questionId;
        this.selectedAnswer = selectedAnswer;
        this.correctAnswer = correctAnswer;
    }

    // Getters and setters
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}

