package com.stu.mapper;

import com.stu.entity.Evaluation;

import java.util.List;

public interface EvaluationMapper {
    // 根据学生ID和教师ID获取评价
    Evaluation getByStudentIdAndTeacherId(Long studentId, Long teacherId);

    // 插入新的评价(TODO: 内部没有对数据库内是否已有该评价进行检查)
    void insert(Evaluation evaluation);

    // 根据教师ID获取所有评价
    List<Evaluation> getByTeacherId(Long teacherId);

    // 根据用户ID获取所有评价
    List<Evaluation> getByUserId(Long currentId);

//    void read();
}
