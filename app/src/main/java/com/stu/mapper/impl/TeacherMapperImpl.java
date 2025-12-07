package com.stu.mapper.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.stu.entity.Teacher;
import com.stu.mapper.TeacherMapper;

public class TeacherMapperImpl extends Database implements TeacherMapper {
    public TeacherMapperImpl() {
        super();
    }

    public void insert(Teacher teacher) {
        String insertSql = "INSERT INTO teacher (name, title, department, research_area) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
            pstmt.setString(1, teacher.getName());
            pstmt.setString(2, teacher.getTitle());
            pstmt.setString(3, teacher.getDepartment());
            pstmt.setString(4, teacher.getResearchArea());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Teacher teacher) {
        String updateSql = "UPDATE teacher SET title = ?, department = ?, research_area = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setString(1, teacher.getTitle());
            ps.setString(2, teacher.getDepartment());
            ps.setString(3, teacher.getResearchArea());
            ps.setLong(4, teacher.getId());

            int rows = ps.executeUpdate(); // 返回受影响的行数
            if (rows <= 0) {
                throw new IllegalArgumentException("No such teacher");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Teacher getByTeacherId(Long id) {
        Teacher ret = null;
        try {
            // ===== 查询数据 =====
            String sql = "SELECT id, name, title, department, research_area FROM teacher";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                if (id == rs.getLong("id")) {
                    String name = rs.getString("name");
                    String title = rs.getString("title");
                    String department = rs.getString("department");
                    String research_area = rs.getString("research_area");
                    ret = new Teacher(id, name, title,
                                      department, research_area);
                    break;
                }
            }

            if (ret == null) {
                throw new IllegalArgumentException("No such teacher");
            }
            rs.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public Teacher getByName(String name) {
        String sql = "SELECT id, name, title, department, research_area FROM teacher WHERE name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    String title = rs.getString("title");
                    String department = rs.getString("department");
                    String research_area = rs.getString("research_area");
                    return new Teacher(id, name, title, department, research_area);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            String sql = "SELECT id, name, title, department, research_area FROM teacher";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String title = rs.getString("title");
                String department = rs.getString("department");
                String research_area = rs.getString("research_area");
                teachers.add(new Teacher(id, name, title, department, research_area));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }
}
