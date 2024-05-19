package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;

public class Teams extends BmobObject {
    /**
     * 发布者
     */
    private User author;
    private String name;
    private String content;
    private String state;
    private String gametime;
    private String gamename;
    private String logo;
    private String diuzhangid;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDiuzhangid() {
        return diuzhangid;
    }

    public void setDiuzhangid(String diuzhangid) {
        this.diuzhangid = diuzhangid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGametime() {
        return gametime;
    }

    public void setGametime(String gametime) {
        this.gametime = gametime;
    }

    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
