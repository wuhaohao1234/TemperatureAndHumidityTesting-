package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;


public class FeedBack extends BmobObject {

    /**
     * 发布者
     */
    private User author;
    private  String content;
    private String phone;
    private String imgs;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
