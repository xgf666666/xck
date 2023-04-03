package com.example.xck.bean;

/**
 * author ： xiaogf
 * time    ： 2023/4/3
 * describe    ：
 */
public class User {
    private int id;
    private String avatar;
    private String message;
    private int messageNum;

    public User(int id, String avatar) {
        this.id = id;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(int messageNum) {
        this.messageNum = messageNum;
    }
}
