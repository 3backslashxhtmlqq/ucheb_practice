package com.ketaminee.myapplication;

public class Message {

    public String nickname;
    public String content;
    public boolean isFile;

    public Message(String nickname, String content, boolean isFile) {
        this.nickname = nickname;
        this.content = content;
        this.isFile = isFile;
    }
}
