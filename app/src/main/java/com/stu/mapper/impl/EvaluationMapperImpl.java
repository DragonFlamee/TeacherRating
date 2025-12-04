package com.stu.mapper.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.stu.entity.Evaluation;
import com.stu.mapper.EvaluationMapper;

public class EvaluationMapperImpl extends Database implements EvaluationMapper {
    public EvaluationMapperImpl() {
        super();
    }

    public Evaluation getByStudentIdAndTeacherId(Long studentId, Long teacherId) {
        Evaluation ret = null;
        try {
            // ===== 查询数据 =====
            String sql = "SELECT id, student_id, teacher_id, score, context, create_time FROM evaluation";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (studentId == rs.getLong("student_id") 
                 && teacherId == rs.getLong("teacher_id")) {
                    long id = rs.getLong("id");
                    double score = rs.getDouble("score");
                    String context = rs.getString("context");
                    Timestamp createTime = rs.getTimestamp("create_time");
                    ret = new Evaluation(id, studentId, teacherId,
                                         score, context, createTime);
                }
            }

            if (ret == null) {
                throw new IllegalArgumentException("No such evaluation");
            }
            rs.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void insert(Evaluation evaluation) {
        try {
            String insertSql = "INSERT INTO evaluation (student_id, teacher_id, score, context) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            pstmt.setLong(1, evaluation.getId());
            pstmt.setLong(2, evaluation.getStudentId());
            pstmt.setLong(3, evaluation.getTeacherId());
            pstmt.setDouble(4, evaluation.getScore());
            pstmt.setString(5, evaluation.getContext());
            pstmt.setTimestamp(6, evaluation.getCreateTime());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Evaluation> getByTeacherId(Long teacherId) {
        List<Evaluation> ret = new ArrayList<>();
        try {
            // ===== 查询数据 =====
            String sql = "SELECT id, student_id, teacher_id, score, context, create_time FROM evaluation";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (teacherId == rs.getLong("teacher_id")) {
                    long id = rs.getLong("id");
                    long studentId = rs.getLong("student_id");
                    double score = rs.getDouble("score");
                    String context = rs.getString("context");
                    Timestamp createTime = rs.getTimestamp("create_time");
                    Evaluation data = new Evaluation(id, studentId, teacherId,
                                                     score, context, createTime);
                    ret.add(data);
                }
            }

            if (ret.isEmpty()) {
                throw new IllegalArgumentException("No such evaluation");
            }
            rs.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Evaluation> getByUserId(Long currentId) {
        List<Evaluation> ret = new ArrayList<>();
        try {
            // ===== 查询数据 =====
            String sql = "SELECT id, student_id, teacher_id, score, context, create_time FROM evaluation";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (currentId == rs.getLong("student_id")) {
                    long id = rs.getLong("id");
                    long teacherId = rs.getLong("teacher_id");
                    double score = rs.getDouble("score");
                    String context = rs.getString("context");
                    Timestamp createTime = rs.getTimestamp("create_time");
                    Evaluation data = new Evaluation(id, currentId, teacherId,
                                                     score, context, createTime);
                    ret.add(data);
                }
            }

            if (ret.isEmpty()) {
                throw new IllegalArgumentException("No such evaluation ");
            }
            rs.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

}
