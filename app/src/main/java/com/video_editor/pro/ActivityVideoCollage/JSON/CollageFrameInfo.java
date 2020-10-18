package com.video_editor.pro.ActivityVideoCollage.JSON;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollageFrameInfo {
    @SerializedName("id")
    public String frameId;
    @SerializedName("type")
    public String frameType;
    @SerializedName("noofrc")
    public int noofRC;
    @SerializedName("values")
    public List<CollageFrameRCInfo> rcValues;
}
