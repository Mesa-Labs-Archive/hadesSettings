package com.mesalabs.hadesrc.fragment.rc.home;

import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mesalabs.hadesrc.R;
import com.mesalabs.hadesrc.activity.rc.home.WifiAdbInnerActivity;
import com.mesalabs.hadesrc.rc.utils.WifiAdbUtils;

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

public class WifiAdbInnerFragment extends Fragment {
    private View mRootView;
    private TextView mSubheaderTextView;
    private String mSubheaderText;
    private SpannableStringBuilder mEnabledSubheaderText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cmd = "adb connect " + WifiAdbUtils.getWifiIpAddress() + ":5555";
        int colorPrimaryDark = getResources().getColor(R.color.hades_rc_colorprimarydark, null);

        mSubheaderText = getString(R.string.hades_rc_wifi_adb_subheader);
        mEnabledSubheaderText = new SpannableStringBuilder();
        mEnabledSubheaderText.append(getString(R.string.hades_rc_wifi_adb_subheader_enabled));
        mEnabledSubheaderText.append(System.getProperty("line.separator"));
        mEnabledSubheaderText.append(cmd);
        mEnabledSubheaderText.setSpan(new ForegroundColorSpan(colorPrimaryDark), mEnabledSubheaderText.length() - cmd.length(), mEnabledSubheaderText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.hades_rc_layout_inner_wifiadbactivity, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSubheaderTextView = mRootView.findViewById(R.id.hades_wifiadb_subheader);
        mSubheaderTextView.setText(WifiAdbUtils.isWifiAdbEnabled() ? mEnabledSubheaderText : mSubheaderText);
    }

    public void onSwitchBarChanged(boolean checked) {
        mSubheaderTextView.setText(checked ? mEnabledSubheaderText : mSubheaderText);
    }
}
