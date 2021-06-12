package com.lzy.miaosha.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

public class LyhUser {
    private Long id;
    private String phoneNumber;
    private String nickname;
    private String password;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Date registerDate;
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
