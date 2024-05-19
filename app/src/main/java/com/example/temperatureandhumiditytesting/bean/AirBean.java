package com.example.temperatureandhumiditytesting.bean;

import com.google.gson.Gson;

import java.util.List;

public class AirBean {

    /**
     * code : 200
     * updateTime : 2023-03-30T16:58+08:00
     * fxLink : https://www.qweather.com/air/harbin-101050101.html
     * now : {"pubTime":"2023-03-30T16:00+08:00","aqi":"38","level":"1","category":"优","primary":"NA","pm10":"38","pm2p5":"13","no2":"14","so2":"7","co":"0.3","o3":"99"}
     * station : [{"pubTime":"2023-03-30T16:00+08:00","name":"省农科院","id":"CNA1140","aqi":"35","level":"1","category":"优","primary":"NA","pm10":"28","pm2p5":"22","no2":"20","so2":"8","co":"0.1","o3":"110"},{"pubTime":"2023-03-30T16:00+08:00","name":"呼兰师专","id":"CNA1139","aqi":"37","level":"1","category":"优","primary":"NA","pm10":"37","pm2p5":"13","no2":"8","so2":"8","co":"0.6","o3":"100"},{"pubTime":"2023-03-30T16:00+08:00","name":"平房东轻厂","id":"CNA1138","aqi":"37","level":"1","category":"优","primary":"NA","pm10":"37","pm2p5":"16","no2":"10","so2":"8","co":"0.3","o3":"99"},{"pubTime":"2023-03-30T16:00+08:00","name":"道里建国路","id":"CNA1137","aqi":"40","level":"1","category":"优","primary":"NA","pm10":"40","pm2p5":"18","no2":"13","so2":"5","co":"0.3","o3":"94"},{"pubTime":"2023-03-30T16:00+08:00","name":"动力和平路","id":"CNA1136","aqi":"52","level":"2","category":"良","primary":"PM10","pm10":"54","pm2p5":"10","no2":"13","so2":"8","co":"0.5","o3":"97"},{"pubTime":"2023-03-30T16:00+08:00","name":"香坊红旗大街","id":"CNA1135","aqi":"34","level":"1","category":"优","primary":"NA","pm10":"34","pm2p5":"17","no2":"15","so2":"10","co":"0.2","o3":"99"},{"pubTime":"2023-03-30T16:00+08:00","name":"道外承德广场","id":"CNA1134","aqi":"41","level":"1","category":"优","primary":"NA","pm10":"41","pm2p5":"8","no2":"18","so2":"6","co":"0.2","o3":"93"},{"pubTime":"2023-03-30T16:00+08:00","name":"太平宏伟公园","id":"CNA1133","aqi":"33","level":"1","category":"优","primary":"NA","pm10":"33","pm2p5":"7","no2":"14","so2":"5","co":"0.2","o3":"99"},{"pubTime":"2023-03-30T16:00+08:00","name":"南岗学府路","id":"CNA1132","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"32","pm2p5":"3","no2":"10","so2":"5","co":"0.3","o3":"98"},{"pubTime":"2023-03-30T16:00+08:00","name":"松北商大","id":"CNA1130","aqi":"41","level":"1","category":"优","primary":"NA","pm10":"41","pm2p5":"14","no2":"18","so2":"9","co":"0.3","o3":"100"},{"pubTime":"2023-03-30T16:00+08:00","name":"岭北","id":"CNA1129","aqi":"32","level":"1","category":"优","primary":"NA","pm10":"27","pm2p5":"10","no2":"6","so2":"5","co":"0.2","o3":"100"}]
     * refer : {"sources":["QWeather","CNEMC"],"license":["CC BY-SA 4.0"]}
     */

    private String code;
    private String updateTime;
    private String fxLink;
    /**
     * pubTime : 2023-03-30T16:00+08:00
     * aqi : 38
     * level : 1
     * category : 优
     * primary : NA
     * pm10 : 38
     * pm2p5 : 13
     * no2 : 14
     * so2 : 7
     * co : 0.3
     * o3 : 99
     */

    private NowBean now;
    private ReferBean refer;
    /**
     * pubTime : 2023-03-30T16:00+08:00
     * name : 省农科院
     * id : CNA1140
     * aqi : 35
     * level : 1
     * category : 优
     * primary : NA
     * pm10 : 28
     * pm2p5 : 22
     * no2 : 20
     * so2 : 8
     * co : 0.1
     * o3 : 110
     */

    private List<StationBean> station;

    public static AirBean objectFromData(String str) {

        return new Gson().fromJson(str, AirBean.class);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getFxLink() {
        return fxLink;
    }

    public void setFxLink(String fxLink) {
        this.fxLink = fxLink;
    }

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public List<StationBean> getStation() {
        return station;
    }

    public void setStation(List<StationBean> station) {
        this.station = station;
    }

    public static class NowBean {
        private String pubTime;
        private String aqi;
        private String level;
        private String category;
        private String primary;
        private String pm10;
        private String pm2p5;
        private String no2;
        private String so2;
        private String co;
        private String o3;

        public static NowBean objectFromData(String str) {

            return new Gson().fromJson(str, NowBean.class);
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2p5() {
            return pm2p5;
        }

        public void setPm2p5(String pm2p5) {
            this.pm2p5 = pm2p5;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }
    }

    public static class ReferBean {
        private List<String> sources;
        private List<String> license;

        public static ReferBean objectFromData(String str) {

            return new Gson().fromJson(str, ReferBean.class);
        }

        public List<String> getSources() {
            return sources;
        }

        public void setSources(List<String> sources) {
            this.sources = sources;
        }

        public List<String> getLicense() {
            return license;
        }

        public void setLicense(List<String> license) {
            this.license = license;
        }
    }

    public static class StationBean {
        private String pubTime;
        private String name;
        private String id;
        private String aqi;
        private String level;
        private String category;
        private String primary;
        private String pm10;
        private String pm2p5;
        private String no2;
        private String so2;
        private String co;
        private String o3;

        public static StationBean objectFromData(String str) {

            return new Gson().fromJson(str, StationBean.class);
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
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

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrimary() {
            return primary;
        }

        public void setPrimary(String primary) {
            this.primary = primary;
        }

        public String getPm10() {
            return pm10;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public String getPm2p5() {
            return pm2p5;
        }

        public void setPm2p5(String pm2p5) {
            this.pm2p5 = pm2p5;
        }

        public String getNo2() {
            return no2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public String getSo2() {
            return so2;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public String getCo() {
            return co;
        }

        public void setCo(String co) {
            this.co = co;
        }

        public String getO3() {
            return o3;
        }

        public void setO3(String o3) {
            this.o3 = o3;
        }
    }
}
