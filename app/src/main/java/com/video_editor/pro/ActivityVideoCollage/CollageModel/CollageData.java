package com.video_editor.pro.ActivityVideoCollage.CollageModel;

public class CollageData {
    private int anInt;
    private int anInt1;
    private int anInt2;
    private int anInt3;
    private int anInt4;
    private int anInt5;
    private int anInt6;
    private Boolean aBoolean;
    private String string;
    private String string1;
    private int anInt7;
    private int anInt8;

    public void setVideoUrl(String str) {
        this.string1 = str;
    }

    public String getVideoUrl() {
        return this.string1;
    }

    public void setStartTime(String str) {
        this.string = str;
    }

    public String getStartTime() {
        return this.string;
    }

    public void setDurationTime(int i2) {
        this.anInt6 = i2;
    }

    public int getDurationTime() {
        return this.anInt6;
    }

    public void setWidth(int i2) {
        this.anInt1 = i2;
    }

    public int getWidth() {
        return this.anInt1;
    }

    public void setHeight(int i2) {
        this.anInt = i2;
    }

    public int getHeight() {
        return this.anInt;
    }

    public void setXPoint(int i2) {
        this.anInt7 = i2;
    }

    public int getXPoint() {
        return this.anInt7;
    }

    public void setYPoint(int i2) {
        this.anInt8 = i2;
    }

    public int getYPoint() {
        return this.anInt8;
    }

    public void setIsImage(Boolean bool) {
        this.aBoolean = bool;
    }

    public Boolean getIsImage() {
        return this.aBoolean;
    }

    public void setCrop_width(int i2) {
        this.anInt3 = i2;
    }

    public int getCrop_width() {
        return this.anInt3;
    }

    public void setCrop_height(int i2) {
        this.anInt2 = i2;
    }

    public int getCrop_height() {
        return this.anInt2;
    }

    public void setCrop_X(int i2) {
        this.anInt4 = i2;
    }

    public int getCrop_X() {
        return this.anInt4;
    }

    public void setCrop_Y(int i2) {
        this.anInt5 = i2;
    }

    public int getCrop_Y() {
        return this.anInt5;
    }
}
