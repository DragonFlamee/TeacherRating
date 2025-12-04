package com.stu.mapper;

import com.stu.entity.User;

import java.util.List;

public interface UserMapper {
    // 根据用户名获取用户信息
    User getByUsername(String username);

    // 插入新的用户(TODO: 内部没有对数据库内是否已有该评价进行检查)
    void insert(User user);

    // 根据昵称获取用户列表
    List<User> getByNickname(String nickname);

    // 根据用户ID获取用户信息
    User getById(Long id);

}
