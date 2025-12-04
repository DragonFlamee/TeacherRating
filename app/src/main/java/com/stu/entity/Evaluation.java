package com.stu.entity;

import java.sql.Timestamp;

public class Evaluation {
    private Long id;

    private Long studentId;

    private Long teacherId;

    private Double score;

    private String context;

    private Timestamp createTime;

    public Evaluation(Long id, Long studentId, Long teacherId, Double score, String context, Timestamp createTime) {
        this.id = id;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.score = score;
        this.context = context;
        this.createTime = createTime;
    }

    public Long getId() { return id; }
    public Long getStudentId() { return studentId; }
    public Long getTeacherId() { return teacherId; }
    public Double getScore() { return score; }
    public String getContext() { return context; }
    public Timestamp getCreateTime() { return createTime; }
}
