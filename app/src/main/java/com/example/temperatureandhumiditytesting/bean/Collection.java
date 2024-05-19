package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;

 
public class Collection extends BmobObject {

    /**
     * 发布者
     */
    private User author;
    private FitnessKnowledge likes;
    private FitnessCircle likestiezi;
    private String name;
    private String id;

    public FitnessCircle getLikestiezi() {
        return likestiezi;
    }

    public void setLikestiezi(FitnessCircle likestiezi) {
        this.likestiezi = likestiezi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public FitnessKnowledge getLikes() {
        return likes;
    }

    public void setLikes(FitnessKnowledge likes) {
        this.likes = likes;
    }
}
