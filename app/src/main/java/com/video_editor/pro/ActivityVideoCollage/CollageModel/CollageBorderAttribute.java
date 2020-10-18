package com.video_editor.pro.ActivityVideoCollage.CollageModel;

public class CollageBorderAttribute {
    private boolean aBoolean = false;
    private boolean aBoolean1 = false;
    private boolean aBoolean2 = false;
    private boolean aBoolean3 = false;

    public void setBorderLeft(boolean z) {
        this.aBoolean1 = z;
    }

    public boolean getBorderLeft() {
        return this.aBoolean1;
    }

    public void setBorderRight(boolean z) {
        this.aBoolean2 = z;
    }

    public boolean getBorderRight() {
        return this.aBoolean2;
    }

    public void setBorderTop(boolean z) {
        this.aBoolean3 = z;
    }

    public boolean getBorderTop() {
        return this.aBoolean3;
    }

    public void setBorderBottom(boolean z) {
        this.aBoolean = z;
    }

    public boolean getBorderBottom() {
        return this.aBoolean;
    }
}
