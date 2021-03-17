package com.abodsy.abodsy.Main_App;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.abodsy.abodsy.R;
import com.abodsy.abodsy.TabAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import com.google.android.material.chip.Chip;
import com.google.android.material.tabs.TabLayout;

import static com.abodsy.abodsy.LoginActivity.userName;
import static com.abodsy.abodsy.LoginActivity.userPhotoUrl;


/**
 * CONTAINS ALL THE CODE FOR THE HOME SCREEN OF THE APP
 */

public class MainActivity extends AppCompatActivity {


    // view pager that will allow the user to swipe between fragments
    public static ViewPager viewPager;
    // Create an adapter that knows which fragment should be shown on each page
    TabAdapter adapter;
    // TAB LAYOUT VIEW
    TabLayout tabLayout;
    // CHIP FOR USER INFO
    Chip chip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // INITIALIZING VARIABLES OF TAB AND FRAGMENTS
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        adapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_calendar);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_account);
        // Adding user info in chip
        chip = findViewById(R.id.chip);
        chip.setText("Hey, " + userName.substring(0, userName.indexOf(" ")));
        Glide.with(this)
                .asBitmap()
                .load(userPhotoUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable d = new BitmapDrawable(getResources(), resource);
                        chip.setChipIcon(d);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

    }

}



