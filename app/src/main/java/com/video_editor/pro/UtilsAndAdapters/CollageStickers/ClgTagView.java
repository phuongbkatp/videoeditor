package com.video_editor.pro.UtilsAndAdapters.CollageStickers;

public class ClgTagView {
    int a = -1;
    String b = "";

    public ClgTagView(int i, String str) {
        this.a = i;
        this.b = str;
    }

    public int getPos() {
        return this.a;
    }

    public String getText() {
        return this.b;
    }
}
