package com.example.xck.bean;

import com.hyphenate.easeui.constants.UserMessage;

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
    private String time;
    private long times;
    private String name;
    private UserMessage userMessage;

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User(int id, String avatar,String name) {
        this.id = id;
        this.avatar = avatar;
        this.name=name;
    }

    public User() {
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
