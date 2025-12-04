package com.stu.mapper;

import com.stu.entity.Teacher;

public interface TeacherMapper {
    // 插入新的老师数据(TODO: 内部没有对数据库内是否已有该评价进行检查)
    void insert(Teacher teacher);

    // 更新老师数据
    void update(Teacher teacher);

    // 根据教师ID获取教师信息
    Teacher getByTeacherId(Long id);

    // 根据教师姓名获取教师信息
    Teacher getByName(String name);
}
