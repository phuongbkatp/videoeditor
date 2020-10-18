package com.video_editor.pro.ActivityVideoToAudio;

import android.content.Context;
import android.os.Environment;

import com.video_editor.pro.ActivityVideoConverter.ActivityVideoConverter;
import com.video_editor.pro.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static int Bitrate;

    static class a implements FilenameFilter {
        private final String string;

        a(String str) {
            this.string = str;
        }

        public boolean accept(File file, String str) {
            if (str != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(ActivityVideoConverter.outputformat);
                stringBuilder.append("-");
                if (str.startsWith(stringBuilder.toString())) {
                    StringBuilder sb2 = new StringBuilder(this.string.substring(0, this.string.lastIndexOf(".")));
                    sb2.append(".");
                    sb2.append(ActivityVideoConverter.outputformat);
                    return str.endsWith(sb2.toString());
                }
            }
            return false;
        }
    }

    public static String getFileName(Context context, String str) {
        int i;
        Exception exception;
        String name = new File(str).getAbsoluteFile().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        stringBuilder.append("/");
        stringBuilder.append(context.getResources().getString(R.string.MainFolderName));
        stringBuilder.append("/");
        stringBuilder.append(context.getResources().getString(R.string.VideoToMP3));
        List asList = Arrays.asList(new File(stringBuilder.toString()).getAbsoluteFile().list(new a(name)));
        int i2 = 0;
        while (true) {
            try {
                StringBuilder sb2 = new StringBuilder("mp3-");
                int i3 = i2 + 1;
                sb2.append(String.format("%03d", Integer.valueOf(i2)));
                sb2.append("-");
                sb2.append(name);
                String sb3 = sb2.toString();
                i = i3 - 1;
                try {
                    StringBuilder sb4 = new StringBuilder("mp3-");
                    int i4 = i + 1;
                    sb4.append(String.format("%03d", Integer.valueOf(i)));
                    sb4.append("-");
                    sb4.append(name.substring(0, name.lastIndexOf(".")));
                    sb4.append(".mp3");
                    if (!asList.contains(sb4.toString())) {
                        try {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                            sb5.append("/");
                            sb5.append(context.getResources().getString(R.string.MainFolderName));
                            sb5.append("/");
                            sb5.append(context.getResources().getString(R.string.VideoToMP3));
                            return new File(sb5.toString(), sb3).getPath();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                    i2 = i4;
                } catch (Exception e3) {
                    exception = e3;
                    exception.printStackTrace();
                    i2 = i;
                }
            } catch (Exception e4) {
                i = i2;
                exception = e4;
                exception.printStackTrace();
                i2 = i;
            }
        }
    }
}
