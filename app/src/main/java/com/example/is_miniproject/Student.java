package com.example.is_miniproject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Student implements Serializable {
    private String name, password, application, nickname, phone, email, address;
    private int fee,id;
    private Date birthday;

    public Student(){}

    public Student(int id, String name, String password, String application, String nickname, String phone, String email, String address, int fee, String birthday) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.id = id;
        this.name = name;
        this.password = password;
        this.application = application;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.fee = fee;
        try {
            this.birthday = dateFormat.parse(birthday);
        }
        catch (Exception e)
        {
            this.birthday = new Date();
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}