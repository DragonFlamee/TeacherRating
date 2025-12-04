package com.stu.mapper.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.stu.entity.User;
import com.stu.mapper.UserMapper;

public class UserMapperImpl extends Database implements UserMapper {
    public UserMapperImpl() {
        super();
    }

    public void insert(User user) {
        // 修改SQL语句，不再插入id，让数据库自动生成
        String insertSql = "INSERT INTO userdata (username, password, nickname, phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSql)){
            // 调整参数索引，从1开始，并且不再设置id
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getNickname());
            pstmt.setString(4, user.getPhoneNumber());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        String updateSql = "UPDATE userdata SET username = ?, password = ?, nickname = ?, phone_number = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNickname());
            ps.setString(4, user.getPhoneNumber());
            ps.setLong(5, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getById(Long id) {
        String sql = "SELECT id, username, password, nickname, phone_number FROM userdata WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String nickname = rs.getString("nickname");
                    String phonenumber = rs.getString("phone_number");
                    return new User(id, username, password, nickname, phonenumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByUsername(String username) {
        String sql = "SELECT id, username, password, nickname, phone_number FROM userdata WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Long id = rs.getLong("id");
                    String password = rs.getString("password");
                    String nickname = rs.getString("nickname");
                    String phonenumber = rs.getString("phone_number");
                    return new User(id, username, password, nickname, phonenumber);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getByNickname(String nickname) {
        List<User> ret = new ArrayList<>();
        String sql = "SELECT id, username, password, nickname, phone_number FROM userdata WHERE nickname = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nickname);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String phonenumber = rs.getString("phone_number");
                    User data = new User(id, username, password, nickname, phonenumber);
                    ret.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
}
