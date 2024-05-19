package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;

 
public class Video extends BmobObject {
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String img_url;
    private String video_url;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    private User send_user;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getSend_user() {
        return send_user;
    }

    public void setSend_user(User send_user) {
        this.send_user = send_user;
    }
}
