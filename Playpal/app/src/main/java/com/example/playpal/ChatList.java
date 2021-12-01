package com.example.playpal;

public class ChatList {

    private String senderId, message, date, time;

    public ChatList(String senderId, String message, String date, String time) {
        this.senderId = senderId;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
