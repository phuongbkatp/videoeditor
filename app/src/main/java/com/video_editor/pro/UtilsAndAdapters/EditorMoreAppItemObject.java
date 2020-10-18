package com.video_editor.pro.UtilsAndAdapters;

public class EditorMoreAppItemObject {
    private int anInt;
    private String string;
    private String string1;

    public EditorMoreAppItemObject(int i, String str, String str2) {
        this.string = str;
        this.anInt = i;
        this.string1 = str2;
    }

    public int getPhoto() {
        return this.anInt;
    }

    public void setPhoto(int i) {
        this.anInt = i;
    }

    public String getName() {
        return this.string;
    }

    public void setName(String str) {
        this.string = str;
    }

    public String getLink() {
        return this.string1;
    }

    public void setLink(String str) {
        this.string1 = str;
    }
}
