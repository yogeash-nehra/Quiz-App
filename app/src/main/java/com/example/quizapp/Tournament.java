package com.example.quizapp;

public class Tournament {
    private String id; // New field for the tournament ID
    private String name;
    private String category;
    private String difficulty;
    private String startDate;
    private String endDate;
    private int likes;

    // Required no-argument constructor for Firestore
    public Tournament() {
    }

    // Parameterized constructor
    public Tournament(String name, String category, String difficulty, String startDate, String endDate, int likes) {
        this.name = name;
        this.category = category;
        this.difficulty = difficulty;
        this.startDate = startDate;
        this.endDate = endDate;
        this.likes = likes;
    }

    // Getter and setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Other getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
