package com.example.temperatureandhumiditytesting.bean;

import java.io.Serializable;

  
public class NumberBean implements Serializable {
   private int fanyi = 0;
  private  int naoling= 0;
  private int beiwanglu= 0;
  private  int luntan= 0;
  private  int zudui= 0;
  private int video= 0;

  public NumberBean() {

  }

  public NumberBean(int fanyi, int naoling, int beiwanglu, int luntan, int zudui, int video) {
    this.fanyi = fanyi;
    this.naoling = naoling;
    this.beiwanglu = beiwanglu;
    this.luntan = luntan;
    this.zudui = zudui;
    this.video = video;
  }

  public int getFanyi() {
    return fanyi;
  }

  public void setFanyi(int fanyi) {
    this.fanyi = fanyi;
  }

  public int getNaoling() {
    return naoling;
  }

  public void setNaoling(int naoling) {
    this.naoling = naoling;
  }

  public int getBeiwanglu() {
    return beiwanglu;
  }

  public void setBeiwanglu(int beiwanglu) {
    this.beiwanglu = beiwanglu;
  }

  public int getLuntan() {
    return luntan;
  }

  public void setLuntan(int luntan) {
    this.luntan = luntan;
  }

  public int getZudui() {
    return zudui;
  }

  public void setZudui(int zudui) {
    this.zudui = zudui;
  }

  public int getVideo() {
    return video;
  }

  public void setVideo(int video) {
    this.video = video;
  }
}
