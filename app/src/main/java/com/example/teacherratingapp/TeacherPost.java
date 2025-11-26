package com.example.teacherratingapp;

public class TeacherPost {
    private String name;
    private String college;
    private float rating;

    public TeacherPost(String name, String college, float rating) {
        this.name = name;
        this.college = college;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getCollege() {
        return college;
    }

    public float getRating() {
        return rating;
    }
}
