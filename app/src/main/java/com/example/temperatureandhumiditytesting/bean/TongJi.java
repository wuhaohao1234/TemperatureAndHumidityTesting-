package com.example.temperatureandhumiditytesting.bean;

import cn.bmob.v3.BmobObject;

  
public class TongJi extends BmobObject {
    private String type;
    private User user;
    private String shuzhi;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getShuzhi() {
        return shuzhi;
    }

    public void setShuzhi(String shuzhi) {
        this.shuzhi = shuzhi;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
