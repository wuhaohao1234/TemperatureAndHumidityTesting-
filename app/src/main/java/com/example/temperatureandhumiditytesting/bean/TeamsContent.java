package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;

public class TeamsContent extends BmobObject {
    private User author;
    private Teams team;
    private String gameID;
    private String remark;
    private String state="0";

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Teams getTeam() {
        return team;
    }

    public void setTeam(Teams team) {
        this.team = team;
    }
}
