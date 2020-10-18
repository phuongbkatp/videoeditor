package com.video_editor.pro.ActivityVideoJoiner.JoinerModels;

public class VideoPlayerState {
    private int anInt = 0;
    private String string;
    private String string1;
    private int anInt1 = 0;
    private int anInt2 = 0;

    public String getMessageText() {
        return this.string1;
    }

    public void setMessageText(String str) {
        this.string1 = str;
    }

    public String getFilename() {
        return this.string;
    }

    public void setFilename(String str) {
        this.string = str;
    }

    public int getStart() {
        return this.anInt1;
    }

    public void setStart(int i) {
        this.anInt1 = i;
    }

    public int getStop() {
        return this.anInt2;
    }

    public void setStop(int i) {
        this.anInt2 = i;
    }

    public void reset() {
        this.anInt2 = 0;
        this.anInt1 = 0;
    }

    public int getDuration() {
        return this.anInt2 - this.anInt1;
    }

    public int getCurrentTime() {
        return this.anInt;
    }

    public void setCurrentTime(int i) {
        this.anInt = i;
    }

    public boolean isValid() {
        return this.anInt2 > this.anInt1;
    }
}
