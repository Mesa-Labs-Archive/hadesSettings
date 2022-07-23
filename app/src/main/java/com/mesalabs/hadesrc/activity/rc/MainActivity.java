package com.mesalabs.hadesrc.activity.rc;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mesalabs.hadesrc.HadesRCApp;
import com.mesalabs.hadesrc.R;
import com.mesalabs.hadesrc.activity.aboutpage.AboutActivity;
import com.mesalabs.hadesrc.ui.utils.FragmentsUtils;
import com.mesalabs.hadesrc.ui.bottomtab.TabManager;

import de.dlyt.yanndroid.oneui.layout.ToolbarLayout;
import de.dlyt.yanndroid.oneui.sesl.support.ViewSupport;
import de.dlyt.yanndroid.oneui.widget.TabLayout;

/*
 * hadesRC
 *
 * Coded by BlackMesa123 @2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 */

public class MainActivity extends AppCompatActivity {
    private static final String SP_DB = HadesRCApp.getAppPackageName() + "_tabs";
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabManager mBottomTabManager;
    private ToolbarLayout mToolbarLayout;
    private Fragment mInflatedFragment;
    private TabLayout mBottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        ViewSupport.semSetRoundedCorners(getWindow().getDecorView(), 0);

        // init TabManager
        mBottomTabManager = new TabManager(SP_DB);
        mBottomTabManager.initTabPosition();

        // init Views
        setContentView(R.layout.hades_rc_activity_main_layout);

        initViews();

        setCurrentBottomTabItem();

        // Get instance of Vibrator from current Context
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 50, 50, 50, 1000, 800};
        int[] amplitudes = {0, 100, 0, 100, 0, 100};
        v.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1));
    }

    private void initViews() {
        mFragmentManager = getSupportFragmentManager();
        mToolbarLayout = findViewById(R.id.hades_mainactivity_toolbarlayout);
        mBottomNavigationView = findViewById(R.id.hades_mainactivity_bottomnavview);

        mToolbarLayout.inflateToolbarMenu(isHkTweaksInstalled() ? R.menu.hades_rc_menu_mainactivity_hk : R.menu.hades_rc_menu_mainactivity);
        mToolbarLayout.setOnToolbarMenuItemClickListener(new ToolbarLayout.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(de.dlyt.yanndroid.oneui.menu.MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.hades_menu_hk:
                        PackageManager pm = mContext.getPackageManager();
                        Intent intent = pm.getLaunchIntentForPackage("com.hades.hKtweaks");
                        if (intent != null) {
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                        break;
                    case R.id.hades_menu_about_app:
                        startActivity(new Intent(mContext, AboutActivity.class));
                        break;
                }
                return true;
            }
        });

        for (String s: FragmentsUtils.getRCFragmentsTitles()) {
            mBottomNavigationView.addTab(mBottomNavigationView.newTab().setText(s));
        }
        mBottomNavigationView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabSelected(TabLayout.Tab tab) {
                int tabPosition = tab.getPosition();
                mBottomTabManager.setTabPosition(tabPosition);
                setCurrentBottomTabItem();
            }

            public void onTabUnselected(TabLayout.Tab tab) { }

            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }

    //
    // BottomTab
    //
    private void setCurrentBottomTabItem() {
        if (mBottomNavigationView.isEnabled()) {
            int tabPosition = mBottomTabManager.getCurrentTab();
            TabLayout.Tab tab = mBottomNavigationView.getTabAt(tabPosition);
            if (tab != null) {
                tab.select();
                setFragment(tabPosition);
            }
        }
    }

    private void setFragment(int tabPosition) {
        String tabTag = FragmentsUtils.getRCFragmentsTags()[tabPosition];
        Class tabClass = FragmentsUtils.getRCFragmentsClasses()[tabPosition];

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(tabTag);
        if (mInflatedFragment != null) {
            transaction.hide(mInflatedFragment);
        }
        if (fragment != null) {
            mInflatedFragment = (Fragment) fragment;
            transaction.show(fragment);
        } else {
            try {
                mInflatedFragment = (Fragment) tabClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            transaction.add(R.id.hades_mainactivity_fragment_container, mInflatedFragment, tabTag);
        }
        transaction.commit();
    }

    //
    // Miscs
    //
    private boolean isHkTweaksInstalled() {
        PackageManager pm = mContext.getPackageManager();
        try {
            pm.getPackageInfo("com.hades.hKtweaks", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}