package com.example.temperatureandhumiditytesting.bean;

import com.google.gson.Gson;

import java.util.List;

public class LifeZhiShuBean {

    /**
     * code : 200
     * updateTime : 2023-03-30T15:36+08:00
     * fxLink : https://www.qweather.com/indices/beijing-101010100.html
     * daily : [{"date":"2023-03-30","type":"1","name":"运动指数","level":"3","category":"较不宜","text":"有扬沙或浮尘，建议适当停止户外运动，选择在室内进行运动，以避免吸入更多沙尘，有损健康。"},{"date":"2023-03-30","type":"2","name":"洗车指数","level":"2","category":"较适宜","text":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。"},{"date":"2023-03-30","type":"3","name":"穿衣指数","level":"5","category":"舒适","text":"建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"},{"date":"2023-03-30","type":"4","name":"钓鱼指数","level":"1","category":"适宜","text":"白天风和日丽，适宜垂钓，渺渺蓝天，悠悠白云将陪伴你度过愉快的垂钓时光。"},{"date":"2023-03-30","type":"5","name":"紫外线指数","level":"3","category":"中等","text":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。"},{"date":"2023-03-30","type":"6","name":"旅游指数","level":"1","category":"适宜","text":"天气较好，温度适宜，是个好天气哦。这样的天气适宜旅游，您可以尽情地享受大自然的风光。"},{"date":"2023-03-30","type":"7","name":"过敏指数","level":"4","category":"易发","text":"天气条件易诱发过敏，易过敏人群应减少外出，外出宜穿长衣长裤并佩戴好眼镜和口罩，外出归来时及时清洁手和口鼻。"},{"date":"2023-03-30","type":"8","name":"舒适度指数","level":"1","category":"舒适","text":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},{"date":"2023-03-30","type":"9","name":"感冒指数","level":"1","category":"少发","text":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。"},{"date":"2023-03-30","type":"10","name":"空气污染扩散条件指数","level":"2","category":"良","text":"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。"}]
     * refer : {"sources":["QWeather"],"license":["CC BY-SA 4.0"]}
     */

    private String code;
    private String updateTime;
    private String fxLink;
    private ReferBean refer;
    /**
     * date : 2023-03-30
     * type : 1
     * name : 运动指数
     * level : 3
     * category : 较不宜
     * text : 有扬沙或浮尘，建议适当停止户外运动，选择在室内进行运动，以避免吸入更多沙尘，有损健康。
     */

    private List<DailyBean> daily;

    public static LifeZhiShuBean objectFromData(String str) {

        return new Gson().fromJson(str, LifeZhiShuBean.class);
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

    public ReferBean getRefer() {
        return refer;
    }

    public void setRefer(ReferBean refer) {
        this.refer = refer;
    }

    public List<DailyBean> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyBean> daily) {
        this.daily = daily;
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

    public static class DailyBean {
        private String date;
        private String type;
        private String name;
        private String level;
        private String category;
        private String text;

        public static DailyBean objectFromData(String str) {

            return new Gson().fromJson(str, DailyBean.class);
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
