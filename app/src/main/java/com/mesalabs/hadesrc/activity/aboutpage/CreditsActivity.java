package com.mesalabs.hadesrc.activity.aboutpage;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mesalabs.hadesrc.R;
import com.mesalabs.hadesrc.ui.creditspage.CreditsListViewModel;
import com.mesalabs.hadesrc.ui.creditspage.CreditsPageListAdapter;
import com.mesalabs.hadesrc.ui.creditspage.CreditsPageListItemDecoration;

import java.util.ArrayList;
import java.util.List;

import de.dlyt.yanndroid.oneui.layout.ToolbarLayout;
import de.dlyt.yanndroid.oneui.sesl.recyclerview.LinearLayoutManager;
import de.dlyt.yanndroid.oneui.utils.OnSingleClickListener;
import de.dlyt.yanndroid.oneui.view.RecyclerView;

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

public class CreditsActivity extends AppCompatActivity {
    private Context mContext;
    private ToolbarLayout mToolbarLayout;
    private TextView mSubheaderText;
    private RecyclerView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hades_rc_layout_creditsactivity);

        mContext = this;

        mToolbarLayout = findViewById(R.id.hades_creditsactivity_toolbarlayout);
        mToolbarLayout.setNavigationButtonOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                onBackPressed();
            }
        });
        mToolbarLayout.setNavigationButtonTooltip(getString(R.string.sesl_navigate_up));

        mSubheaderText = findViewById(R.id.hades_creditsactivity_subheader_text);
        mSubheaderText.setPadding(mSubheaderText.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.sesl_list_divider_inset) - mToolbarLayout.getAppBarLayout().getPaddingBottom(), mSubheaderText.getPaddingRight(), mSubheaderText.getPaddingBottom());

        initListView();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSubheaderText.setPadding(mSubheaderText.getPaddingLeft(), getResources().getDimensionPixelSize(R.dimen.sesl_list_divider_inset) - mToolbarLayout.getAppBarLayout().getPaddingBottom(), mSubheaderText.getPaddingRight(), mSubheaderText.getPaddingBottom());
    }

    private void initListView() {
        mListView = findViewById(R.id.hades_creditsactivity_listview);

        RecyclerView.Adapter adapter = new CreditsPageListAdapter(this, getCreditsList());
        CreditsPageListItemDecoration decoration = new CreditsPageListItemDecoration(this);
        TypedValue divider = new TypedValue();
        mContext.getTheme().resolveAttribute(android.R.attr.listDivider, divider, true);

        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        mListView.setAdapter(adapter);

        mListView.addItemDecoration(decoration);
        decoration.setDivider(mContext.getDrawable(divider.resourceId));
        mListView.setItemAnimator(null);
        mListView.seslSetFillBottomEnabled(true);
        mListView.seslSetLastRoundedCorner(false);
    }

    private List<CreditsListViewModel> getCreditsList() {
        List<CreditsListViewModel> modelList = new ArrayList<>();

        int[] libIcons =
                {R.drawable.hades_credits_androidx_icon,
                        R.drawable.hades_credits_gmaterial_icon,
                        R.drawable.hades_credits_oneui_icon};
        int [] libNames =
                {R.string.hades_credits_page_lib1_name,
                        R.string.hades_credits_page_lib2_name,
                        R.string.hades_credits_page_lib3_name};
        int [] libDescs =
                {R.string.hades_credits_page_lib1_desc,
                        R.string.hades_credits_page_lib2_desc,
                        R.string.hades_credits_page_lib3_desc};
        String [] libWebLinks =
                {"",
                        "",
                        "https://github.com/Yanndroid/OneUI-Design-Library"};

        for (int i = 0; i < libNames.length; i++) {
            modelList.add(new CreditsListViewModel(getResources().getDrawable(libIcons[i], getTheme()),
                    getString(libNames[i]),
                    getString(libDescs[i]),
                    libWebLinks[i]));
        }
        modelList.add(new CreditsListViewModel());

        return modelList;
    }

    public Context getContext() {
        return mContext;
    }

    public RecyclerView getListView() {
        return mListView;
    }

}
