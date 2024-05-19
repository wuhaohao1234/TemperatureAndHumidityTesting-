package com.example.temperatureandhumiditytesting.bean;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

 
public class TopBannerBean extends SimpleBannerInfo {

    private int url;
    private String imgUrl;

    public TopBannerBean(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public TopBannerBean(int url) {
        this.url = url;
    }

    @Override
    public Object getXBannerUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}

