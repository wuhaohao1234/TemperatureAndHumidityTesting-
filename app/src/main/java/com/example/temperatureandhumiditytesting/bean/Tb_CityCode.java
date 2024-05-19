package com.example.temperatureandhumiditytesting.bean;

public class Tb_CityCode {

    private String cityName;
    private int cityCode;
    public Tb_CityCode(){

    }

    public Tb_CityCode(String cityName, int cityCode) {
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

}
