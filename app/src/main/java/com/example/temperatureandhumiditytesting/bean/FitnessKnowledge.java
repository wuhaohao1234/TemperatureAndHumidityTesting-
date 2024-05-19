package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;
public class FitnessKnowledge extends BmobObject {
    private String title;
    private String imgs;

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    private User author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
