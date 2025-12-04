package com.stu.entity;

public class User {
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String phoneNumber;

    public User(Long id, String username, String password, String nickname, String phoneNumber) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public String getPhoneNumber() { return phoneNumber; }
}
