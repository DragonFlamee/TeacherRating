package com.stu.mapper.impl;

import java.sql.*;

public class Database {
    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    private final String DB_URL = "jdbc:mysql://www.lhh-redbean.cn:3306/midterm-proj?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private final String USER = "midterm-proj";
    private final String PASS = "YRtJPfwYGT37MtXR";

    Connection conn = null;
    Statement stmt = null;

    Database() {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        try { if (stmt != null) stmt.close(); } catch (SQLException ignore) {}
        try { if (conn != null) conn.close(); } catch (SQLException ignore) {}
    }
}
