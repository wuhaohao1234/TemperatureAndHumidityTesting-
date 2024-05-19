package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;
public class Comment extends BmobObject {
    private String content;
    private String type;
    private User author;
    private FitnessCircle fitnessCircle;
    private Video video;
    private FitnessKnowledge fitnessKnowledge;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FitnessKnowledge getFitnessKnowledge() {
        return fitnessKnowledge;
    }

    public void setFitnessKnowledge(FitnessKnowledge fitnessKnowledge) {
        this.fitnessKnowledge = fitnessKnowledge;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public FitnessCircle getFitnessCircle() {
        return fitnessCircle;
    }

    public void setFitnessCircle(FitnessCircle fitnessCircle) {
        this.fitnessCircle = fitnessCircle;
    }
}
