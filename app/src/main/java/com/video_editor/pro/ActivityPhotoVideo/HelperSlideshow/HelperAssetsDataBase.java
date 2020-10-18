package com.video_editor.pro.ActivityPhotoVideo.HelperSlideshow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class HelperAssetsDataBase extends SQLiteOpenHelper {
    private static String string = "imgdata.sqlite";
    private static String imgInfo = "imgInfo";
    private String string1 = "";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {

    }

    public HelperAssetsDataBase(Context context) throws IOException {
        super(context, string, null, 1);
        this.context = context;
        StringBuilder sb = new StringBuilder();
        sb.append("/data/data/");
        sb.append(this.context.getApplicationContext().getPackageName());
        sb.append("/databases/");
        this.string1 = sb.toString();
        if (a()) {
            OpenData();
        } else {
            CreateData();
        }
    }

    public void CreateData() throws IOException {
        if (!a()) {
            getReadableDatabase();
            try {
                b();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean a() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(this.string1);
            sb.append(string);
            return new File(sb.toString()).exists();
        } catch (SQLiteException unused) {
            return false;
        }
    }

    private void b() throws IOException {
        InputStream open = this.context.getAssets().open(string);
        StringBuilder sb = new StringBuilder();
        sb.append(this.string1);
        sb.append(string);
        FileOutputStream fileOutputStream = new FileOutputStream(sb.toString());
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read <= 0) {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
            fileOutputStream.write(bArr, 0, read);
        }
    }

    public void OpenData()  {
        StringBuilder sb = new StringBuilder();
        sb.append(this.string1);
        sb.append(string);
        this.sqLiteDatabase = SQLiteDatabase.openDatabase(sb.toString(), null, 0);
    }

    @Override
    public synchronized void close() {
        if (this.sqLiteDatabase != null) {
            this.sqLiteDatabase.close();
        }
        super.close();
    }

    @Override public void onCreate(SQLiteDatabase sQLiteDatabase) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append(imgInfo);
        sb.append("(Name VARCHAR)");
        sQLiteDatabase.execSQL(sb.toString());
    }

    public void addImageDetails(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", str);
        writableDatabase.insert(imgInfo, null, contentValues);
        writableDatabase.close();
    }

    public void getAllImageDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  * FROM ");
        sb.append(imgInfo);
        String sb2 = sb.toString();
        SQLiteDatabase writableDatabase = getWritableDatabase();
        Cursor rawQuery = writableDatabase.rawQuery(sb2, null);
        if (rawQuery.moveToFirst()) {
            do {
                Utils.createImageList.add(rawQuery.getString(0));
            } while (rawQuery.moveToNext());
        }
        writableDatabase.close();
    }

    public void deleteAll() {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(imgInfo, null, null);
        writableDatabase.close();
    }
}
