package com.dubbo.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private String phone;
    private String address;

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.password = "";
        this.phone = "";
        this.address = "";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.phone = "";
        this.address = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
