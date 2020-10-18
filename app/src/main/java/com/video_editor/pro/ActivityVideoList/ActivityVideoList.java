package com.video_editor.pro.ActivityVideoList;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.ActivityMain.ActivityMain;
import com.video_editor.pro.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityVideoList extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] ints = {R.mipmap.icon_video, R.mipmap.icon_myalbum};

    class a extends FragmentPagerAdapter {
        private final List<Fragment> arrayList = new ArrayList();
        private final List<String> arrayList1 = new ArrayList();

        public a(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            return this.arrayList.get(i);
        }

        public int getCount() {
            return this.arrayList.size();
        }

        public void a(Fragment fragment, String str) {
            this.arrayList.add(fragment);
            this.arrayList1.add(str);
        }

        public CharSequence getPageTitle(int i) {
            return this.arrayList1.get(i);
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_status_list);


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        if (EditorHelper.ModuleId == 1) {
            textView.setText("Video Cutter");
        } else if (EditorHelper.ModuleId == 2) {
            textView.setText("Video Compressor");
        } else if (EditorHelper.ModuleId == 4) {
            textView.setText("Audio Video Mixer");
        } else if (EditorHelper.ModuleId == 5) {
            textView.setText("Video Mute");
        } else if (EditorHelper.ModuleId == 8) {
            textView.setText("Video Converter");
        } else if (EditorHelper.ModuleId == 9) {
            textView.setText("Fast Motion Video");
        } else if (EditorHelper.ModuleId == 10) {
            textView.setText("Slow Motion Video");
        } else if (EditorHelper.ModuleId == 11) {
            textView.setText("Video Croper");
        } else if (EditorHelper.ModuleId == 13) {
            textView.setText("Video Rotate");
        } else if (EditorHelper.ModuleId == 14) {
            textView.setText("Video Mirror");
        } else if (EditorHelper.ModuleId == 15) {
            textView.setText("Video Splitter");
        } else if (EditorHelper.ModuleId == 16) {
            textView.setText("Video Reverse");
        } else if (EditorHelper.ModuleId == 22) {
            textView.setText("Video Watermark");
        }
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.viewPager = findViewById(R.id.viewpager);
            a(this.viewPager);
            this.tabLayout = findViewById(R.id.tabs);

            this.tabLayout.setupWithViewPager(this.viewPager);
            a();
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        this.tabLayout.getTabAt(0).setIcon(this.ints[0]);
        this.tabLayout.getTabAt(1).setIcon(this.ints[1]);
    }

    private void a(ViewPager viewPager) {
        a aVar = new a(getSupportFragmentManager());
        aVar.a(new FragmentSelectVideo(), "VIDEO");
        aVar.a(new FragmentMyVideo(), "MY ALBUM");
        viewPager.setAdapter(aVar);
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (itemId == R.id.shareapp) {
            StringBuilder sb = new StringBuilder();
            sb.append(EditorHelper.share_string);
            sb.append(EditorHelper.package_name);
            String sb2 = sb.toString();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", sb2);
            startActivity(intent);
        } else if (itemId == R.id.moreapp) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(EditorHelper.account_string)));
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        } else if (itemId == R.id.rateus) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(EditorHelper.package_name)));
            } catch (ActivityNotFoundException unused2) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return BOOLEAN;
    }

}
