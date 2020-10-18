package com.video_editor.pro.ActivityVideoCollage;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import com.video_editor.pro.UtilsAndAdapters.EditorMusicAdapter;
import com.video_editor.pro.R;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class ActivitySelectMusic extends ListActivity {
    private static final String[] STRINGS;
    private static final String[] STRINGS1;
    EditorMusicAdapter editorMusicAdapter;
    ArrayList<String> arrayList = new ArrayList<>();
    String[] strings;
    Cursor cursor;
    ImageView imageView;
    SimpleCursorAdapter simpleCursorAdapter;
    ArrayList<String> arrayList1 = new ArrayList<>();
    String string;
    private boolean aBoolean;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(Media.EXTERNAL_CONTENT_URI);
        sb.append("\"");
        STRINGS = new String[]{"_id", "_data", "title", "artist", "album", "duration", "_size", "is_ringtone", "is_alarm", "is_notification", "is_music", sb.toString()};
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\"");
        sb2.append(Media.INTERNAL_CONTENT_URI);
        sb2.append("\"");
        STRINGS1 = new String[]{"_id", "_data", "title", "artist", "album", "duration", "_size", "is_ringtone", "is_alarm", "is_notification", "is_music", sb2.toString()};
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.aBoolean = false;
        String externalStorageState = Environment.getExternalStorageState();
        if (externalStorageState.equals("mounted_ro")) {
            try {
                a(getResources().getText(R.string.sdcard_readonly));
            } catch (NotFoundException e2) {
                e2.printStackTrace();
            }
        } else if (externalStorageState.equals("shared")) {
            try {
                a(getResources().getText(R.string.sdcard_shared));
            } catch (NotFoundException e3) {
                e3.printStackTrace();
            }
        } else if (externalStorageState.equals("mounted")) {
            try {
                setContentView(R.layout.select_music);
                this.imageView = findViewById(R.id.imageViewBack);
                this.imageView.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View view) {
                        ActivitySelectMusic.this.finish();
                    }
                });
                try {
                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.select_music_row, a(""), new String[]{"artist", "album", "title"}, new int[]{R.id.row_artist, R.id.row_album, R.id.row_title});
                    this.simpleCursorAdapter = simpleCursorAdapter;
                    this.strings = this.arrayList.toArray(new String[this.arrayList.size()]);
                    this.cursor = new MergeCursor(new Cursor[]{b(this.string, this.strings), a(this.string, this.strings)});
                    startManagingCursor(this.cursor);
                    int[] iArr = {R.id.row_artist, R.id.row_title, R.id.row_Duration};
                    EditorMusicAdapter editorMusicAdapter = new EditorMusicAdapter(this, R.layout.select_music_row, a(""), new String[]{"artist", "album", "title", "duration", "_size"}, iArr);
                    this.editorMusicAdapter = editorMusicAdapter;
                    setListAdapter(this.editorMusicAdapter);
                    getListView().setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
                            ActivitySelectMusic.this.a();
                        }
                    });
                } catch (IllegalArgumentException | SecurityException unused) {
                }
            } catch (OutOfMemoryError e4) {
                e4.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e5) {
                e5.printStackTrace();
            } catch (ActivityNotFoundException e6) {
                e6.printStackTrace();
            } catch (NotFoundException e7) {
                e7.printStackTrace();
            } catch (NullPointerException e8) {
                e8.printStackTrace();
            } catch (StackOverflowError e9) {
                e9.printStackTrace();
            }
        } else {
            a(getResources().getText(R.string.no_sdcard));
        }
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 1) {
            if (i3 != -1) {
                try {
                    setResult(0);
                    finish();
                } catch (Exception e2) {
                    try {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        return;
                    }
                }
            }
            setResult(-1, intent);
            finish();
        }
    }

    private void a(CharSequence charSequence) {
        new Builder(this).setTitle(getResources().getText(R.string.alert_title_failure)).setMessage(charSequence).setPositiveButton(R.string.alert_ok_button, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivitySelectMusic.this.finish();
            }
        }).setCancelable(false).show();
    }

    private Cursor a(String str, String[] strArr) {
        return managedQuery(Media.INTERNAL_CONTENT_URI, STRINGS1, str, strArr, "title_key");
    }

    private Cursor b(String str, String[] strArr) {
        return managedQuery(Media.EXTERNAL_CONTENT_URI, STRINGS, str, strArr, "title_key");
    }


    public Cursor a(String str) {
        String str2;
        String sb;
        String str3;
        Exception e2;
        ArrayList arrayList = new ArrayList();
        if (this.aBoolean) {
            str2 = "(_DATA LIKE ?)";
            try {
                arrayList.add("%");
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            String str4 = "(";
            try {
                String[] supportedExtensions = getSupportedExtensions();
                int length = supportedExtensions.length;
                str3 = str4;
                int i2 = 0;
                while (i2 < length) {
                    try {
                        String str5 = supportedExtensions[i2];
                        try {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("%.");
                            sb2.append(str5);
                            arrayList.add(sb2.toString());
                            if (str3.length() > 1) {
                                try {
                                    StringBuilder sb3 = new StringBuilder(str3);
                                    sb3.append(" OR ");
                                    str3 = sb3.toString();
                                } catch (OutOfMemoryError e4) {
                                    e4.printStackTrace();
                                } catch (ArrayIndexOutOfBoundsException e5) {
                                    e5.printStackTrace();
                                } catch (ActivityNotFoundException e6) {
                                    e6.printStackTrace();
                                } catch (NotFoundException e7) {
                                    e7.printStackTrace();
                                } catch (NullPointerException e8) {
                                    e8.printStackTrace();
                                } catch (StackOverflowError e9) {
                                    e9.printStackTrace();
                                }
                            }
                            StringBuilder sb4 = new StringBuilder(str3);
                            sb4.append("(_DATA LIKE ?)");
                            str3 = sb4.toString();
                        } catch (OutOfMemoryError e10) {
                            e10.printStackTrace();
                        } catch (ArrayIndexOutOfBoundsException e11) {
                            e11.printStackTrace();
                        } catch (ActivityNotFoundException e12) {
                            e12.printStackTrace();
                        } catch (NotFoundException e13) {
                            e13.printStackTrace();
                        } catch (NullPointerException e14) {
                            e14.printStackTrace();
                        } catch (StackOverflowError e15) {
                            e15.printStackTrace();
                        }
                        i2++;
                    } catch (Exception e16) {
                        e2 = e16;
                        e2.printStackTrace();
                        str2 = str3;
                        try {
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append("%");
                            sb5.append(str);
                            sb5.append("%");
                            String sb6 = sb5.toString();
                            StringBuilder sb7 = new StringBuilder();
                            sb7.append("(");
                            sb7.append(str2);
                            sb7.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                            sb = sb7.toString();
                            try {
                                arrayList.add(sb6);
                                arrayList.add(sb6);
                                arrayList.add(sb6);
                                str2 = sb;
                            } catch (Exception e17) {
                                str2 = sb;
                                String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                                this.cursor = new MergeCursor(new Cursor[]{b(str2, strArr), a(str2, strArr)});
                                startManagingCursor(this.cursor);
                                return this.cursor;
                            }
                        } catch (Exception e23) {
                            String[] strArr222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                            this.cursor = new MergeCursor(new Cursor[]{b(str2, strArr222222), a(str2, strArr222222)});
                            startManagingCursor(this.cursor);
                            return this.cursor;
                        }
                        String[] strArr222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                        this.cursor = new MergeCursor(new Cursor[]{b(str2, strArr222222222222), a(str2, strArr222222222222)});
                        startManagingCursor(this.cursor);
                        return this.cursor;
                    }
                }
                StringBuilder sb8 = new StringBuilder();
                sb8.append("(");
                StringBuilder sb9 = new StringBuilder(str3);
                sb9.append(")");
                sb8.append(sb9.toString());
                sb8.append(") AND (_DATA NOT LIKE ?)");
                String sb10 = sb8.toString();
                try {
                    arrayList.add("%espeak-data/scratch%");
                    str2 = sb10;
                } catch (Exception e29) {
                    e2 = e29;
                    str3 = sb10;
                    e2.printStackTrace();
                    str2 = str3;
                    StringBuilder sb52 = new StringBuilder();
                    sb52.append("%");
                    sb52.append(str);
                    sb52.append("%");
                    String sb62 = sb52.toString();
                    StringBuilder sb72 = new StringBuilder();
                    sb72.append("(");
                    sb72.append(str2);
                    sb72.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                    sb = sb72.toString();
                    arrayList.add(sb62);
                    arrayList.add(sb62);
                    arrayList.add(sb62);
                    str2 = sb;
                    String[] strArr2222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    this.cursor = new MergeCursor(new Cursor[]{b(str2, strArr2222222222222), a(str2, strArr2222222222222)});
                    startManagingCursor(this.cursor);
                    return this.cursor;
                }
            } catch (Exception e30) {
                str3 = str4;
                e2 = e30;
                e2.printStackTrace();
                str2 = str3;
                StringBuilder sb522 = new StringBuilder();
                sb522.append("%");
                sb522.append(str);
                sb522.append("%");
                String sb622 = sb522.toString();
                StringBuilder sb722 = new StringBuilder();
                sb722.append("(");
                sb722.append(str2);
                sb722.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                sb = sb722.toString();
                arrayList.add(sb622);
                arrayList.add(sb622);
                arrayList.add(sb622);
                str2 = sb;
                String[] strArr22222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                this.cursor = new MergeCursor(new Cursor[]{b(str2, strArr22222222222222), a(str2, strArr22222222222222)});
                startManagingCursor(this.cursor);
                return this.cursor;
            }
        }
        if (str != null && str.length() > 0) {
            StringBuilder sb5222 = new StringBuilder();
            sb5222.append("%");
            sb5222.append(str);
            sb5222.append("%");
            String sb6222 = sb5222.toString();
            StringBuilder sb7222 = new StringBuilder();
            sb7222.append("(");
            sb7222.append(str2);
            sb7222.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
            sb = sb7222.toString();
            arrayList.add(sb6222);
            arrayList.add(sb6222);
            arrayList.add(sb6222);
            str2 = sb;
        }
        String[] strArr222222222222222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        this.cursor = new MergeCursor(new Cursor[]{b(str2, strArr222222222222222), a(str2, strArr222222222222222)});
        startManagingCursor(this.cursor);
        return this.cursor;
    }

    @Override public void onBackPressed() {
        setResult(0);
        getWindow().clearFlags(128);
        finish();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    public String[] getSupportedExtensions() {
        this.arrayList1 = new ArrayList<>();
        this.arrayList1.add("wav");
        this.arrayList1.add("mp3");
        this.arrayList1.add("m4a");
        this.arrayList1.add("3gpp");
        this.arrayList1.add("3gp");
        this.arrayList1.add("amr");
        return this.arrayList1.toArray(new String[this.arrayList1.size()]);
    }


    public void a() {
        Cursor cursor = this.editorMusicAdapter.getCursor();
        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(string));
            intent.putExtra("audioname", cursor.getString(2));
            setResult(-1, intent);
            finish();
        } catch (Exception unused) {
        }
    }
}
