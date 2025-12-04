package com.stu.entity;

public class Teacher {
    private Long id;

    private String name;

    private String title;

    private String department;

    private String researchArea;

    public Teacher(Long id, String name, String title, String department, String researchArea) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.department = department;
        this.researchArea = researchArea;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getTitle() { return title; }
    public String getDepartment() { return department; }
    public String getResearchArea() { return researchArea; }
}
