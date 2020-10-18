package com.video_editor.pro.UtilsAndAdapters.CollageStickers;

public class Point {
    float aFloat;
    float aFloat1;

    public Point(float f, float f2) {
        this.aFloat = f;
        this.aFloat1 = f2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("x: ");
        sb.append(this.aFloat);
        sb.append(",y: ");
        sb.append(this.aFloat1);
        return sb.toString();
    }
}
