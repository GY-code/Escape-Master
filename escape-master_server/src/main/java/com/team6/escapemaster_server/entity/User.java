package com.team6.escapemaster_server.entity;

public class User {
    private int id;
    private int phone_number;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", phone_number='" + phone_number + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getphone_number() {
        return phone_number;
    }

    public void setphone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
