package com.mesalabs.hadesrc.ui.creditspage;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.mesalabs.hadesrc.R;

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

public class CreditsPageListViewHolder extends RecyclerView.ViewHolder {
    private boolean mIsItem;

    private LinearLayout mParentView;
    private AppCompatImageView mLibIconView;
    private TextView mLibNameTextView;
    private TextView mLibSummaryTextView;

    CreditsPageListViewHolder(View itemView, int viewType) {
        super(itemView);

        mIsItem = viewType == 0;

        if (mIsItem)  {
            mParentView = (LinearLayout) itemView;
            mLibIconView = mParentView.findViewById(android.R.id.icon);
            mLibNameTextView = mParentView.findViewById(android.R.id.title);
            mLibSummaryTextView = mParentView.findViewById(android.R.id.summary);
        }
    }

    public void onBindViewHolder(CreditsListViewModel viewModel) {
        if (mIsItem)  {
            mLibIconView.setClipToOutline(true);
            mLibIconView.setImageDrawable(viewModel.getLibIcon());
            mLibNameTextView.setText(viewModel.getLibName());
            if (!viewModel.getLibDescription().isEmpty())  {
                mLibSummaryTextView.setVisibility(View.VISIBLE);
                mLibSummaryTextView.setText(viewModel.getLibDescription());
            }
        }
    }

    public boolean getIsItem() {
        return mIsItem;
    }

    public void setItemOnClickListener(View.OnClickListener ocl) {
        if (mIsItem)  {
            mParentView.setOnClickListener(ocl);
        }
    }

}
