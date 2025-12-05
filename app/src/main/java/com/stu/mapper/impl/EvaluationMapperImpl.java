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
        String sql = "SELECT id, student_id, teacher_id, score, context, create_time FROM evaluation WHERE student_id = ? AND teacher_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, studentId);
            ps.setLong(2, teacherId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                long id = rs.getLong("id");
                double score = rs.getDouble("score");
                String context = rs.getString("context");
                Timestamp createTime = rs.getTimestamp("create_time");
                ret = new Evaluation(id, studentId, teacherId, score, context, createTime);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void insert(Evaluation evaluation) {
        String insertSql = "INSERT INTO evaluation (student_id, teacher_id, score, context, create_time) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setLong(1, evaluation.getStudentId());
            pstmt.setLong(2, evaluation.getTeacherId());
            pstmt.setDouble(3, evaluation.getScore());
            pstmt.setString(4, evaluation.getContext());
            pstmt.setTimestamp(5, evaluation.getCreateTime());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Evaluation> getByTeacherId(Long teacherId) {
        List<Evaluation> ret = new ArrayList<>();
        String sql = "SELECT id, student_id, teacher_id, score, context, create_time FROM evaluation WHERE teacher_id = ? ORDER BY create_time DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, teacherId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                long studentId = rs.getLong("student_id");
                double score = rs.getDouble("score");
                String context = rs.getString("context");
                Timestamp createTime = rs.getTimestamp("create_time");
                Evaluation data = new Evaluation(id, studentId, teacherId, score, context, createTime);
                ret.add(data);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public List<Evaluation> getByUserId(Long currentId) {
        List<Evaluation> ret = new ArrayList<>();
        String sql = "SELECT id, student_id, teacher_id, score, context, create_time FROM evaluation WHERE student_id = ?";
         try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, currentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                long teacherId = rs.getLong("teacher_id");
                double score = rs.getDouble("score");
                String context = rs.getString("context");
                Timestamp createTime = rs.getTimestamp("create_time");
                Evaluation data = new Evaluation(id, currentId, teacherId, score, context, createTime);
                ret.add(data);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
