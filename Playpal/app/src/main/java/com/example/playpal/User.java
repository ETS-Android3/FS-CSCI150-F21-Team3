package com.example.playpal;

public class User {

    String name, lastMessage, lastMsgTime, phoneNo, city;
    int imageId;

    public User(String name, String lastMessage, String lastMsgTime, String phoneNo, String city, int imageId) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.lastMsgTime = lastMsgTime;
        this.phoneNo = phoneNo;
        this.city = city;
        this.imageId = imageId;
    }
}