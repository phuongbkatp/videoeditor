package com.video_editor.pro.ActivityVideoMusicList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Audio.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.video_editor.pro.UtilsAndAdapters.EditorAudioPlayer;
import com.video_editor.pro.R;
import com.video_editor.pro.AudioTools.EditorCutter.CheapSoundFile;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class FragmentMyMusic extends Fragment {
    private static final String[] STRINGS;
    private static final String[] STRINGS1;
    public static int anInt;
    ArrayList<String> arrayList = new ArrayList<>();
    ListView listView;
    View view;
    String string;
    String[] strings;
    private SimpleCursorAdapter simpleCursorAdapter;
    private boolean aBoolean;
    private AdView adView;
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            FragmentMyMusic.this.a();
        }
    };

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(Media.EXTERNAL_CONTENT_URI);
        sb.append("\"");
        STRINGS = new String[]{"_id", "_data", "title", "artist", "album", "_size", "duration", "is_ringtone", "is_alarm", "is_notification", "is_music", sb.toString()};
        StringBuilder sb2 = new StringBuilder();
        sb2.append("\"");
        sb2.append(Media.INTERNAL_CONTENT_URI);
        sb2.append("\"");
        STRINGS1 = new String[]{"_id", "_data", "title", "artist", "album", "_size", "duration", "is_ringtone", "is_alarm", "is_notification", "is_music", sb2.toString()};
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_select_music, viewGroup, false);
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
                this.listView = this.view.findViewById(R.id.listmusic);
                try {
                    SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.audio_select_row, a(""), new String[]{"artist", "title", "_size", "duration"}, new int[]{R.id.row_artist, R.id.row_title, R.id.row_Size, R.id.row_Duration});
                    this.simpleCursorAdapter = simpleCursorAdapter;
                    this.simpleCursorAdapter.setViewBinder(new ViewBinder() {
                        public boolean setViewValue(View view, Cursor cursor, int i) {
                            if (i != 6) {
                                return false;
                            }
                            if (cursor.getString(i) != null) {
                                try {
                                    FragmentMyMusic.anInt = Integer.parseInt(cursor.getString(i));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                TextView textView = (TextView) view;
                                StringBuilder sb = new StringBuilder();
                                sb.append("Length : ");
                                sb.append(FragmentMyMusic.formatTimeUnit(FragmentMyMusic.anInt));
                                textView.setText(sb.toString());
                            } catch (ParseException e2) {
                                e2.printStackTrace();
                            }
                            return true;
                        }
                    });
                    this.listView.setAdapter(this.simpleCursorAdapter);
                    this.listView.setOnItemClickListener(this.onItemClickListener);
                } catch (IllegalArgumentException | SecurityException unused) {
                }
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        } else {
            try {
                a(getResources().getText(R.string.no_sdcard));
            } catch (NotFoundException e5) {
                e5.printStackTrace();
            }
        }
        this.adView = this.view.findViewById(R.id.banner_AdView);
        this.adView.loadAd(new Builder().build());
        if (isOnline()) {
            this.adView.setVisibility(0);
        } else {
            this.adView.setVisibility(8);
        }
        return this.view;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void a(CharSequence charSequence) {
        new AlertDialog.Builder(getActivity()).setTitle(getResources().getText(R.string.alert_title_failure)).setMessage(charSequence).setPositiveButton(R.string.alert_ok_button, new OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                FragmentMyMusic.this.getActivity().finish();
            }
        }).setCancelable(false).show();
    }


    public void a() {
        Cursor cursor = this.simpleCursorAdapter.getCursor();
        String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
        Intent intent = new Intent(getActivity(), EditorAudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", string);
        bundle.putBoolean("isfrom", true);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private Cursor a(String str, String[] strArr) {
        return getActivity().managedQuery(Media.INTERNAL_CONTENT_URI, STRINGS1, str, strArr, "title_key");
    }

    private Cursor b(String str, String[] strArr) {
        return getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, STRINGS, str, strArr, "title_key");
    }


    public Cursor a(String str) {
        String str2;
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
                String[] supportedExtensions = CheapSoundFile.getSupportedExtensions();
                int length = supportedExtensions.length;
                str3 = str4;
                int i2 = 0;
                while (i2 < length) {
                    try {
                        String str5 = supportedExtensions[i2];
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append("%.");
                            sb.append(str5);
                            arrayList.add(sb.toString());
                            if (str3.length() > 1) {
                                StringBuilder sb2 = new StringBuilder(str3);
                                sb2.append(" OR ");
                                str3 = sb2.toString();
                            }
                            StringBuilder sb3 = new StringBuilder(str3);
                            sb3.append("(_DATA LIKE ?)");
                            str3 = sb3.toString();
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                        i2++;
                    } catch (Exception e5) {
                        e2 = e5;
                        e2.printStackTrace();
                        str2 = str3;
                        try {
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("%");
                            sb4.append(str);
                            sb4.append("%");
                            String sb5 = sb4.toString();
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append("(");
                            sb6.append(str2);
                            sb6.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                            sb6.toString();
                            arrayList.add(sb5);
                            arrayList.add(sb5);
                            arrayList.add(sb5);
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
                        this.string = "_data like?";
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("%");
                        sb7.append(getResources().getString(R.string.MainFolderName));
                        sb7.append("/");
                        sb7.append(getResources().getString(R.string.VideoToMP3));
                        sb7.append("%");
                        this.strings = new String[]{sb7.toString()};
                        MergeCursor mergeCursor = new MergeCursor(new Cursor[]{b(this.string, this.strings), a(this.string, this.strings)});
                        getActivity().startManagingCursor(mergeCursor);
                        return mergeCursor;
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
                } catch (Exception e7) {
                    e2 = e7;
                    str3 = sb10;
                    e2.printStackTrace();
                    str2 = str3;
                    StringBuilder sb42 = new StringBuilder();
                    sb42.append("%");
                    sb42.append(str);
                    sb42.append("%");
                    String sb52 = sb42.toString();
                    StringBuilder sb62 = new StringBuilder();
                    sb62.append("(");
                    sb62.append(str2);
                    sb62.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                    sb62.toString();
                    arrayList.add(sb52);
                    arrayList.add(sb52);
                    arrayList.add(sb52);
                    String[] strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    this.string = "_data like?";
                    StringBuilder sb72 = new StringBuilder();
                    sb72.append("%");
                    sb72.append(getResources().getString(R.string.MainFolderName));
                    sb72.append("/");
                    sb72.append(getResources().getString(R.string.VideoToMP3));
                    sb72.append("%");
                    this.strings = new String[]{sb72.toString()};
                    MergeCursor mergeCursor2 = new MergeCursor(new Cursor[]{b(this.string, this.strings), a(this.string, this.strings)});
                    getActivity().startManagingCursor(mergeCursor2);
                    return mergeCursor2;
                }
            } catch (Exception e8) {
                str3 = str4;
                e2 = e8;
                e2.printStackTrace();
                str2 = str3;
                StringBuilder sb422 = new StringBuilder();
                sb422.append("%");
                sb422.append(str);
                sb422.append("%");
                String sb522 = sb422.toString();
                StringBuilder sb622 = new StringBuilder();
                sb622.append("(");
                sb622.append(str2);
                sb622.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
                sb622.toString();
                arrayList.add(sb522);
                arrayList.add(sb522);
                arrayList.add(sb522);
                String[] strArr22 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                this.string = "_data like?";
                StringBuilder sb722 = new StringBuilder();
                sb722.append("%");
                sb722.append(getResources().getString(R.string.MainFolderName));
                sb722.append("/");
                sb722.append(getResources().getString(R.string.VideoToMP3));
                sb722.append("%");
                this.strings = new String[]{sb722.toString()};
                MergeCursor mergeCursor22 = new MergeCursor(new Cursor[]{b(this.string, this.strings), a(this.string, this.strings)});
                getActivity().startManagingCursor(mergeCursor22);
                return mergeCursor22;
            }
        }
        if (str != null && str.length() > 0) {
            StringBuilder sb4222 = new StringBuilder();
            sb4222.append("%");
            sb4222.append(str);
            sb4222.append("%");
            String sb5222 = sb4222.toString();
            StringBuilder sb6222 = new StringBuilder();
            sb6222.append("(");
            sb6222.append(str2);
            sb6222.append(" AND ((TITLE LIKE ?) OR (ARTIST LIKE ?) OR (ALBUM LIKE ?)))");
            sb6222.toString();
            arrayList.add(sb5222);
            arrayList.add(sb5222);
            arrayList.add(sb5222);
        }
        String[] strArr222 = (String[]) arrayList.toArray(new String[arrayList.size()]);
        this.string = "_data like?";
        StringBuilder sb7222 = new StringBuilder();
        sb7222.append("%");
        sb7222.append(getResources().getString(R.string.MainFolderName));
        sb7222.append("/");
        sb7222.append(getResources().getString(R.string.VideoToMP3));
        sb7222.append("%");
        this.strings = new String[]{sb7222.toString()};
        MergeCursor mergeCursor222 = new MergeCursor(new Cursor[]{b(this.string, this.strings), a(this.string, this.strings)});
        getActivity().startManagingCursor(mergeCursor222);
        return mergeCursor222;
    }
}
