package com.example.quizapp;
// User.java
public class User {

    private String userId;
    private String email;
    private String userType;

    // No-argument constructor required for Firestore
    public User() {
    }

    // Parameterized constructor
    public User(String userId, String email, String userType) {
        this.userId = userId;
        this.email = email;
        this.userType = userType;
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

