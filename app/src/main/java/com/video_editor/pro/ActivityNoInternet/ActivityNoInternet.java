package com.video_editor.pro.ActivityNoInternet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.video_editor.pro.R;

public class ActivityNoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
    }
    public void noInternet(View view) {
        finishAffinity();
    }

}