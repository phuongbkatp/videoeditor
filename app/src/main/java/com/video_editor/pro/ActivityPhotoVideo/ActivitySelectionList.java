package com.video_editor.pro.ActivityPhotoVideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.video_editor.pro.ActivityMain.ActivityMain;
import com.video_editor.pro.ActivityPhotoVideo.AdapterSlideshow.AdapterSelectionList;
import com.video_editor.pro.ActivityPhotoVideo.HelperSlideshow.HelperAssetsDataBase;
import com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow.HomeTab;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.video_editor.pro.R;

import org.askerov.dynamicgrid.DynamicGridView;
import org.askerov.dynamicgrid.DynamicGridView.OnDragListener;
import org.askerov.dynamicgrid.DynamicGridView.OnDropListener;

import java.io.File;
import java.io.IOException;

@SuppressLint({"WrongConstant"})
public class ActivitySelectionList extends AppCompatActivity {
    protected static final String TAG = "main";
    public static Activity activity = null;
    static final boolean BOOLEAN = true;
    AdapterSelectionList adapterSelectionList;
    HelperAssetsDataBase helperAssetsDataBase = null;
    boolean aBoolean = false;
    TextView textView;
    ImageLoader imageLoader;
    boolean aBoolean1 = false;
    boolean aBoolean2 = BOOLEAN;
    boolean aBoolean3;
    TextView textView1;
    ProgressDialog progressDialog = null;

    public DynamicGridView dynamicGridView;
    private AdView adView;

    class a extends AsyncTask<Void, Void, Boolean> {
        a() {
        }


        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(ActivitySelectionList.BOOLEAN);
        }


        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (ActivitySelectionList.this.progressDialog != null && ActivitySelectionList.this.progressDialog.isShowing()) {
                ActivitySelectionList.this.progressDialog.dismiss();
            }
            ActivitySelectionList.this.startActivity(new Intent(ActivitySelectionList.activity, ActivityMovieMaker.class));
        }
    }

    class b extends AsyncTask<Void, Void, Boolean> {
        b() {
        }


        public Boolean doInBackground(Void... voidArr) {
            try {
                ActivitySelectionList.this.helperAssetsDataBase.getAllImageDetails();
                return Boolean.valueOf(ActivitySelectionList.BOOLEAN);
            } catch (Exception unused) {
                return Boolean.valueOf(false);
            }
        }


        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (bool.booleanValue()) {
                ActivitySelectionList.this.adapterSelectionList = new AdapterSelectionList(ActivitySelectionList.this, Utils.createImageList, 2);
                ActivitySelectionList.this.dynamicGridView.setAdapter(ActivitySelectionList.this.adapterSelectionList);
                return;
            }
            Intent intent = new Intent(ActivitySelectionList.this, ActivityMain.class);
            intent.addFlags(335544320);
            ActivitySelectionList.this.startActivity(intent);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.slideshow_select_image_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        StringBuilder sb = new StringBuilder();
        sb.append("Arrange Photos(");
        sb.append(Utils.myUri.size());
        sb.append(")");
        textView.setText(sb.toString());
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            activity = this;
            this.textView = findViewById(R.id.fit_tv);
            this.textView1 = findViewById(R.id.original_tv);
            this.textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivitySelectionList.this.b();
                }
            });
            this.textView1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivitySelectionList.this.a();
                }
            });
            c();
            this.dynamicGridView = findViewById(R.id.dynamic_grid);
            this.adapterSelectionList = new AdapterSelectionList(this, Utils.createImageList, 2);
            this.dynamicGridView.setAdapter(this.adapterSelectionList);
            this.dynamicGridView.setOnDragListener(new OnDragListener() {
                public void onDragStarted(int i) {
                    String str = ActivitySelectionList.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("drag started at position ");
                    sb.append(i);
                    Log.d(str, sb.toString());
                }

                public void onDragPositionsChanged(int i, int i2) {
                    Log.d(ActivitySelectionList.TAG, String.format("drag item position changed from %d to %d", Integer.valueOf(i), Integer.valueOf(i2)));
                    String str = Utils.createImageList.get(i2);
                    Utils.createImageList.set(i2, Utils.createImageList.get(i));
                    Utils.createImageList.set(i, str);
                    String str2 = Utils.myUri.get(i2);
                    Utils.myUri.set(i2, Utils.myUri.get(i));
                    Utils.myUri.set(i, str2);
                    ActivitySelectionList.this.adapterSelectionList.notifyDataSetChanged();
                }
            });
            this.dynamicGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                    ActivitySelectionList.this.dynamicGridView.startEditMode(i);
                    return ActivitySelectionList.BOOLEAN;
                }
            });
            this.dynamicGridView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    Toast.makeText(ActivitySelectionList.this, "Long press to change position up/down", 0).show();
                }
            });
            this.dynamicGridView.setOnDropListener(new OnDropListener() {
                public void onActionDrop() {
                    ActivitySelectionList.this.dynamicGridView.stopEditMode();
                }
            });
            this.aBoolean3 = BOOLEAN;
            ((ViewStub) findViewById(R.id.stub_import)).inflate();
            findViewById(R.id.panel_import).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivitySelectionList.this.aBoolean3 = false;
                    view.setVisibility(8);
                    ActivitySelectionList.this.findViewById(R.id.btnOK).setVisibility(8);
                }
            });
            final ImageView imageView = findViewById(R.id.ivUp);
            final ImageView imageView2 = findViewById(R.id.ivDown);
            final ImageView imageView3 = findViewById(R.id.ivImage);
            findViewById(R.id.ivHand).startAnimation(AnimationUtils.loadAnimation(activity, R.anim.hand_come));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    imageView.startAnimation(AnimationUtils.loadAnimation(ActivitySelectionList.activity, R.anim.arrow_fadein_out));
                    imageView2.startAnimation(AnimationUtils.loadAnimation(ActivitySelectionList.activity, R.anim.arrow_fadein_out));
                    imageView3.startAnimation(AnimationUtils.loadAnimation(ActivitySelectionList.activity, R.anim.image_vibrate));
                    if (ActivitySelectionList.this.aBoolean3) {
                        ActivitySelectionList.this.findViewById(R.id.btnOK).setVisibility(0);
                    }
                    ActivitySelectionList.this.findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivitySelectionList.this.findViewById(R.id.panel_import).setVisibility(8);
                            view.setVisibility(8);
                            ActivitySelectionList.this.aBoolean3 = false;
                        }
                    });
                }
            }, 1500);
            this.adView = findViewById(R.id.banner_AdView);
            this.adView.loadAd(new Builder().build());
            if (isOnline()) {
                this.adView.setVisibility(0);
            } else {
                this.adView.setVisibility(8);
            }
        } else {
            throw new AssertionError();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return BOOLEAN;
    }


    @SuppressLint({"ResourceAsColor"})
    public void a() {
        if (!this.aBoolean2) {
            this.textView1.setTextColor(Color.parseColor("#000000"));
            this.textView1.setTextColor(Color.parseColor("#ff000000"));
            this.textView.setTextColor(Color.parseColor("#FFFFFF"));
            this.adapterSelectionList.setScaleType(ScaleType.CENTER_INSIDE);
            this.adapterSelectionList.notifyDataSetInvalidated();
            this.aBoolean2 = BOOLEAN;
            this.aBoolean = false;
            this.adapterSelectionList.notifyDataSetChanged();
        }
    }


    @SuppressLint({"ResourceAsColor"})
    public void b() {
        if (!this.aBoolean) {
            this.textView1.setTextColor(Color.parseColor("#FFFFFF"));
            this.textView.setTextColor(Color.parseColor("#ff000000"));
            this.adapterSelectionList.setScaleType(ScaleType.CENTER_CROP);
            this.adapterSelectionList.notifyDataSetInvalidated();
            this.aBoolean = BOOLEAN;
            this.aBoolean2 = false;
            this.adapterSelectionList.notifyDataSetChanged();
        }
    }


    @Override
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1 && i2 == 99) {
            this.adapterSelectionList.refreshList();
            Toast.makeText(activity, "Edit Image Successfully", 0).show();
            Utils.pos = -1;
        }
        if (i2 == 98 && this.adapterSelectionList != null) {
            this.adapterSelectionList.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        this.aBoolean1 = false;
        if (this.aBoolean) {
            this.aBoolean = false;
            this.aBoolean2 = BOOLEAN;
        } else {
            this.aBoolean = false;
            this.aBoolean2 = BOOLEAN;
        }
        try {
            if (this.helperAssetsDataBase == null) {
                this.helperAssetsDataBase = new HelperAssetsDataBase(getApplicationContext());
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        if (Utils.createImageList.size() <= 0) {
            new b().execute();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Utils.bitmap != null) {
            Utils.bitmap.recycle();
        }
    }

    private void c() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getApplicationContext()).diskCacheExtraOptions(ModuleDescriptor.MODULE_VERSION, 480, null).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisk(BOOLEAN).considerExifParams(BOOLEAN).bitmapConfig(Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).build()).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(build);
    }

    @Override
    public void onBackPressed() {
        if (this.aBoolean3) {
            findViewById(R.id.panel_import).setVisibility(8);
            findViewById(R.id.btnOK).setVisibility(8);
            this.aBoolean3 = false;
            return;
        }
        super.onBackPressed();
        d();
        Intent intent = new Intent(getApplicationContext(), HomeTab.class);
        intent.addFlags(67108864);
        startActivity(intent);
    }

    private void d() {
        StringBuilder sb = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath()));
        sb.append("/");
        sb.append(getResources().getString(R.string.app_name));
        File file = new File(sb.toString());
        if (file.exists()) {
            String str = ".jpg";
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(str)) {
                        file2.delete();
                    }
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb3 = new StringBuilder(sb2.toString());
        sb3.append("/");
        sb3.append("/temp");
        File file3 = new File(sb3.toString());
        if (file3.exists()) {
            String str2 = ".jpg";
            File[] listFiles2 = file3.listFiles();
            if (listFiles2 != null) {
                for (File file4 : listFiles2) {
                    if (file4.getName().endsWith(str2)) {
                        file4.delete();
                    }
                }
            }
        }
        if (file3.exists()) {
            file3.delete();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Intent intent = new Intent(this, ActivityMain.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done && !this.aBoolean1) {
            this.aBoolean1 = BOOLEAN;
            if (Utils.myUri.size() > 0) {
                this.progressDialog = new ProgressDialog(activity);
                this.progressDialog.setMessage("Processing images...");
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
                if (this.aBoolean) {
                    new SaveImageAsync(this, BOOLEAN).execute();
                } else {
                    new SaveImageAsync(this, false).execute();
                }
                new a().execute();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
