package com.video_editor.pro.ActivityVideoMusicList;

import android.database.Cursor;

public class ListContentUtill {
    public static String getLong(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
        return sb.toString();
    }

    public static String getTime(Cursor cursor, String str) {
        return ListTimeUtils.toFormattedTime(getInt(cursor, str));
    }

    public static int getInt(Cursor cursor, String str) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(str));
    }
}
