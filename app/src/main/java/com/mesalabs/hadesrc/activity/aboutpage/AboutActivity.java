package com.mesalabs.hadesrc.activity.aboutpage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.mesalabs.hadesrc.HadesRCApp;
import com.mesalabs.hadesrc.R;
import com.mesalabs.hadesrc.utils.LogUtils;

import de.dlyt.yanndroid.oneui.layout.ToolbarLayout;
import de.dlyt.yanndroid.oneui.utils.OnSingleClickListener;

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

public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "AboutActivity";

    private Context mContext;
    private ToolbarLayout mToolbarLayout;
    private LinearLayout mMainLayout;
    private LinearLayout mAppInfoLayout;
    private LinearLayout mAppInfoContainer;
    private View mTopSpacing;
    private TextView mAppNameTextView;
    private TextView mAppVersionTextView;
    private TextView mOptionalTextView;
    private View mMiddleSpacing;
    private LinearLayout mButtonsContainer;
    private AppCompatButton mCreditsButton;
    private AppCompatButton mOpenSourceButton;
    private View mBottomSpacing;
    private LinearLayout mButtonsContainerLand;
    private AppCompatButton mCreditsButtonLand;
    private AppCompatButton mOpenSourceButtonLand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hades_rc_layout_aboutactivity);

        mContext = this;

        mToolbarLayout = findViewById(R.id.hades_aboutactivity_toolbarlayout);
        mToolbarLayout.findViewById(R.id.toolbar_layout_app_bar).setBackgroundColor(getResources().getColor(R.color.splash_background, getTheme()));
        mToolbarLayout.findViewById(R.id.toolbar_layout_collapsing_toolbar_layout).setBackgroundColor(getResources().getColor(R.color.splash_background, getTheme()));
        mToolbarLayout.setNavigationButtonOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                onBackPressed();
            }
        });
        mToolbarLayout.setNavigationButtonTooltip(getString(R.string.sesl_navigate_up));
        mToolbarLayout.inflateToolbarMenu(R.menu.hades_menu_aboutactivity);
        mToolbarLayout.setOnToolbarMenuItemClickListener(new ToolbarLayout.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(de.dlyt.yanndroid.oneui.menu.MenuItem item) {
                if (item.getItemId() == R.id.hades_menu_app_info) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", HadesRCApp.getAppPackageName(), null));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        mMainLayout = findViewById(R.id.hades_aboutactivity_main_layout);
        mAppInfoLayout = findViewById(R.id.hades_aboutactivity_app_info_layout);
        mAppInfoContainer = findViewById(R.id.hades_aboutactivity_app_info_container);

        mTopSpacing = findViewById(R.id.hades_aboutactivity_top_spacing);
        mMiddleSpacing = findViewById(R.id.hades_aboutactivity_middle_spacing);
        mBottomSpacing = findViewById(R.id.hades_aboutactivity_bottom_spacing);

        mAppNameTextView = findViewById(R.id.hades_aboutactivity_app_name_text);
        mAppVersionTextView = findViewById(R.id.hades_aboutactivity_app_version_text);
        mOptionalTextView = findViewById(R.id.hades_aboutactivity_optional_text);
        mAppNameTextView.setText(getText(R.string.hadesrc));
        mAppVersionTextView.setText(getString(R.string.sesl_version) + " " + HadesRCApp.getAppVersionString());
        mOptionalTextView.setText(getString(R.string.hades_about_page_optional_text));

        mButtonsContainer = findViewById(R.id.hades_aboutactivity_buttons_container);
        mCreditsButton = findViewById(R.id.hades_aboutactivity_creditsbtn);
        mOpenSourceButton = findViewById(R.id.hades_aboutactivity_opensourcebtn);
        mButtonsContainerLand = findViewById(R.id.hades_aboutactivity_buttons_container_land);
        mCreditsButtonLand = findViewById(R.id.hades_aboutactivity_creditsbtn_land);
        mOpenSourceButtonLand = findViewById(R.id.hades_aboutactivity_opensourcebtn_land);
        setOpenSourceButtonWidth(mCreditsButton);
        setOpenSourceButtonWidth(mOpenSourceButton);
        setOpenSourceButtonWidth(mCreditsButtonLand);
        setOpenSourceButtonWidth(mOpenSourceButtonLand);
        setOnClickListeners();

        setLayoutOrientation();
        setButtonsTextSize();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setOpenSourceButtonWidth(mCreditsButton);
        setOpenSourceButtonWidth(mOpenSourceButton);
        setOpenSourceButtonWidth(mCreditsButtonLand);
        setOpenSourceButtonWidth(mOpenSourceButtonLand);
        setLayoutOrientation();
    }


    private void setOnClickListeners() {
        mCreditsButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                startActivity(new Intent(mContext, CreditsActivity.class));
            }
        });
        mOpenSourceButton.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                startActivity(new Intent(mContext, OpenSourceLicenseActivity.class));
            }
        });
        mCreditsButtonLand.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                startActivity(new Intent(mContext, CreditsActivity.class));
            }
        });
        mOpenSourceButtonLand.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                startActivity(new Intent(mContext, OpenSourceLicenseActivity.class));
            }
        });
    }

    private void setLayoutOrientation() {
        LinearLayout.LayoutParams appInfoViewLp = (LinearLayout.LayoutParams) mAppInfoLayout.getLayoutParams();
        LinearLayout.LayoutParams webLinkViewLp = (LinearLayout.LayoutParams) mButtonsContainerLand.getLayoutParams();
        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        double heightPixels = getResources().getDisplayMetrics().heightPixels;

        double d = isPortrait ? 0.05d : 0.036d;
        double d2 = isPortrait ? 0.086d : 0.036d;
        int i = (int) (d2 * heightPixels);

        mTopSpacing.getLayoutParams().height = i;
        mMiddleSpacing.getLayoutParams().height = i;
        mBottomSpacing.getLayoutParams().height = (int) (heightPixels * d);

        if (isPortrait) {
            mMainLayout.setOrientation(LinearLayout.VERTICAL);
            mAppInfoLayout.setGravity(49);
            mButtonsContainer.setVisibility(View.VISIBLE);
            mButtonsContainerLand.setVisibility(View.GONE);
        } else {
            mMainLayout.setOrientation(LinearLayout.HORIZONTAL);
            appInfoViewLp.weight = 5.0f;
            webLinkViewLp.weight = 5.0f;
            mAppInfoLayout.setGravity(17);
            mAppInfoContainer.setGravity(17);
            mButtonsContainer.setVisibility(View.GONE);
            mButtonsContainerLand.setVisibility(View.VISIBLE);
            mButtonsContainerLand.setGravity(17);
        }
    }

    private void setOpenSourceButtonWidth(AppCompatButton button) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            widthPixels /= 2;
        }
        button.setMaxWidth((int) (0.75d * widthPixels));
        button.setMinWidth((int) (widthPixels * 0.61d));
    }

    private final void setButtonsTextSize() {
        setLargeTextSize(mAppNameTextView, (float) getResources().getDimensionPixelSize(R.dimen.hades_about_page_app_name_text_size));

        float dimensionPixelSize = (float) getResources().getDimensionPixelSize(R.dimen.hades_about_page_secondary_text_size);
        setLargeTextSize(mAppVersionTextView, dimensionPixelSize);

        dimensionPixelSize = (float) getResources().getDimensionPixelSize(R.dimen.hades_about_page_optional_text_size);
        setLargeTextSize(mOptionalTextView, dimensionPixelSize);

        dimensionPixelSize = (float) getResources().getDimensionPixelSize(R.dimen.hades_about_page_button_text_size);
        setLargeTextSize(mCreditsButton, dimensionPixelSize);
        setLargeTextSize(mOpenSourceButton, dimensionPixelSize);
        setLargeTextSize(mCreditsButtonLand, dimensionPixelSize);
        setLargeTextSize(mOpenSourceButtonLand, dimensionPixelSize);
    }

    private void setLargeTextSize(TextView textView, float size) {
        if (textView != null) {
            float fontScale = mContext.getResources().getConfiguration().fontScale;
            float fixSize = size / fontScale;
            LogUtils.d(TAG, "setLargeTextSize fontScale : " + fontScale + ", " + size + ", " + fixSize);
            if (fontScale > 1.3f) {
                fontScale = 1.3f;
            }

            textView.setTextSize(0, (float) Math.ceil(fixSize * fontScale));
        }
    }
}