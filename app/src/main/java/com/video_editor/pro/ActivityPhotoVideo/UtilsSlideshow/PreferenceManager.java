package com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.core.view.ViewCompat;

import java.io.File;

public class PreferenceManager extends Application {
    static Editor editor;
    static SharedPreferences sharedPreferences;
    private static PreferenceManager preferenceManager;

    @Override public void onCreate() {
        super.onCreate();
        preferenceManager = this;
        sharedPreferences = getSharedPreferences("videomaker", 0);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    @Override public void onTerminate() {
        super.onTerminate();
        trimCache(getApplicationContext());
    }

    public static void setBackgroundColor(int i) {
        editor.putInt("backgrondcolor", i);
        editor.commit();
    }

    public static void setLibraryFlag(boolean z) {
        editor.putBoolean("libFlag", z);
        editor.commit();
    }

    public static void setExceptionFlag(boolean z) {
        editor.putBoolean("exception", z);
        editor.commit();
    }

    public static int getBackColor() {
        return sharedPreferences.getInt("backgrondcolor", ViewCompat.MEASURED_STATE_MASK);
    }

    public static boolean getLibStatus() {
        return sharedPreferences.getBoolean("libFlag", true);
    }

    public static boolean getExceptionFlag() {
        return sharedPreferences.getBoolean("exception", false);
    }

    public static void setMusicExtension(String str) {
        editor.putString("musicextension", str);
        editor.commit();
    }

    public static String getMusicExtension() {
        return sharedPreferences.getString("musicextension", "");
    }

    public static boolean getShowcaseFlag() {
        return sharedPreferences.getBoolean("showcase", false);
    }

    public static void setisMusic(Boolean bool) {
        editor.putBoolean("ismusic", bool.booleanValue());
        editor.commit();
    }

    public static void setShowcaseFlag(Boolean bool) {
        editor.putBoolean("showcase", bool.booleanValue());
        editor.commit();
    }

    public static Boolean getisMusic() {
        return Boolean.valueOf(sharedPreferences.getBoolean("ismusic", false));
    }

    public static void setCounter(int i) {
        editor.putInt("counter", i);
        editor.commit();
    }

    public static int getCounter() {
        return sharedPreferences.getInt("counter", 0);
    }

    public static void setCropIndex(int i) {
        editor.putInt("cropindex", i);
        editor.commit();
    }

    public static int getCropIndex() {
        return sharedPreferences.getInt("cropindex", 0);
    }

    public static void setIndexId(int i) {
        editor.putInt("indexid", i);
        editor.commit();
    }

    public static void setRegistered(boolean z) {
        editor.putBoolean("isRegistered", z);
        editor.commit();
    }

    public static int getIndexId() {
        return sharedPreferences.getInt("indexid", 0);
    }

    public static boolean getRegisteredStatus() {
        return sharedPreferences.getBoolean("isRegistered", false);
    }

    public static void trimCache(Context context) {
        try {
            File cacheDir = context.getCacheDir();
            if (cacheDir != null && cacheDir.isDirectory()) {
                deleteDir(cacheDir);
            }
        } catch (Exception unused) {
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            for (String file2 : file.list()) {
                if (!deleteDir(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static synchronized PreferenceManager getInstance() {
        PreferenceManager preferenceManager;
        synchronized (PreferenceManager.class) {
            synchronized (PreferenceManager.class) {
                preferenceManager = PreferenceManager.preferenceManager;
            }
        }
        return preferenceManager;
    }
}
