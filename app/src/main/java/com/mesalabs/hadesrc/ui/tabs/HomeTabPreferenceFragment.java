package com.mesalabs.hadesrc.ui.tabs;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.mesalabs.hadesrc.R;
import com.mesalabs.hadesrc.activity.rc.home.WifiAdbInnerActivity;
import com.mesalabs.hadesrc.rc.utils.SystemInfoUtils;
import com.mesalabs.hadesrc.rc.utils.PreferencesUtils;
import com.mesalabs.hadesrc.rc.utils.WifiAdbUtils;

import de.dlyt.yanndroid.oneui.layout.PreferenceFragment;
import de.dlyt.yanndroid.oneui.preference.LayoutPreference;
import de.dlyt.yanndroid.oneui.preference.Preference;
import de.dlyt.yanndroid.oneui.preference.SwitchPreferenceScreen;
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

public class HomeTabPreferenceFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {
    private static final String KEY_LINKS_CARD = "hades_rc_home_links";
    private static final String KEY_DEVICEINFO_DEVICE_NAME = "hades_rc_home_info_device";
    private static final String KEY_DEVICEINFO_MANUFACTURING_DATE = "hades_rc_home_info_manufacturing";
    private static final String KEY_DEVICEINFO_ROM_VERSION = "hades_rc_home_info_rom";
    private static final String KEY_DEVICEINFO_ANDROID_VERSION = "hades_rc_home_info_android";
    private static final String KEY_DEVICEINFO_ONEUI_VERSION = "hades_rc_home_info_oneui";
    private static final String KEY_DEVICEINFO_KERNEL_BUILD = "hades_rc_home_info_kernel";
    private static final String KEY_DEVICEINFO_BOOTLOADER_BUILD = "hades_rc_home_info_bootloader";
    private static final String KEY_DEVICEINFO_MODEM_BUILD = "hades_rc_home_info_modem";
    private static final String KEY_DEVICEINFO_BUILD_VERSION = "hades_rc_home_info_build";
    private static final String KEY_DEVICEINFO_TEMPERATURE = "hades_rc_home_info_temperature";
    private static final String KEY_DEVICEINFO_UPTIME = "hades_rc_home_info_uptime";
    private static final String KEY_WIFI_ADB = "hades_rc_wifi_adb";

    private Context mContext;
    private final Handler mHandler = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mTemperaturePref.setSummary(SystemInfoUtils.getFormattedHwTemp());
            mUpTimePref.setSummary(SystemInfoUtils.getDeviceUpTime());
            mHandler.postDelayed(this, 1000);
        }
    };

    private Preference mTemperaturePref;
    private Preference mUpTimePref;
    private SwitchPreferenceScreen mWifiAdbPref;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.hades_rc_tab_preference_home);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initPreferences();

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        cm.registerNetworkCallback(builder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build(),
                new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {
                        setWifiAdbAllowed(true);
                    }

                    @Override
                    public void onLost(Network network) {
                        setWifiAdbAllowed(false);
                    }
                }
        );
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mWifiAdbPref != null) {
            if (!WifiAdbUtils.isWifiAdbAllowed()) {
                WifiAdbUtils.setWifiAdb(false);
                mWifiAdbPref.setEnabled(false);
            }
            mWifiAdbPref.setChecked(WifiAdbUtils.isWifiAdbEnabled());
            mWifiAdbPref.setSummary(WifiAdbUtils.getWifiAdbPreferenceSummary(mContext));
            mWifiAdbPref.seslSetSummaryColor(getColoredSummaryColor(WifiAdbUtils.isWifiAdbEnabled()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
        mHandler.removeMessages(2);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(mRunnable);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case KEY_WIFI_ADB:
                WifiAdbUtils.setWifiAdb((Boolean) newValue);
                mWifiAdbPref.setSummary(WifiAdbUtils.getWifiAdbPreferenceSummary(mContext));
                preference.seslSetSummaryColor(getColoredSummaryColor(WifiAdbUtils.isWifiAdbEnabled()));
                return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case KEY_DEVICEINFO_TEMPERATURE:
                if (PreferencesUtils.getTempUnit() == 0) {
                    Toast.makeText(mContext, getString(R.string.hades_rc_home_temperature_f_toast), Toast.LENGTH_SHORT).show();
                    PreferencesUtils.setTempUnit(1);
                } else {
                    Toast.makeText(mContext, getString(R.string.hades_rc_home_temperature_c_toast), Toast.LENGTH_SHORT).show();
                    PreferencesUtils.setTempUnit(0);
                }
                return true;
            case KEY_WIFI_ADB:
                startActivity(new Intent(mContext, WifiAdbInnerActivity.class));
                return true;
        }
        return false;
    }

    private void initPreferences() {
        initLinksCard((LayoutPreference) findPreference(KEY_LINKS_CARD));

        Preference deviceName = findPreference(KEY_DEVICEINFO_DEVICE_NAME);
        deviceName.setSummary(SystemInfoUtils.getDeviceNameFromBootloader());

        Preference manufacturingDate = findPreference(KEY_DEVICEINFO_MANUFACTURING_DATE);
        manufacturingDate.setSummary(SystemInfoUtils.getManufacturingDate());

        mTemperaturePref = findPreference(KEY_DEVICEINFO_TEMPERATURE);
        mTemperaturePref.setOnPreferenceClickListener(this);

        mUpTimePref = findPreference(KEY_DEVICEINFO_UPTIME);

        Preference romVersion = findPreference(KEY_DEVICEINFO_ROM_VERSION);
        romVersion.setSummary(SystemInfoUtils.getROMVersion());

        Preference androidVersion = findPreference(KEY_DEVICEINFO_ANDROID_VERSION);
        androidVersion.setSummary(SystemInfoUtils.getAndroidVersion());

        Preference oneUiVersion = findPreference(KEY_DEVICEINFO_ONEUI_VERSION);
        oneUiVersion.setSummary(SystemInfoUtils.getOneUIVersion());

        Preference kernelBuild = findPreference(KEY_DEVICEINFO_KERNEL_BUILD);
        kernelBuild.setSummary(SystemInfoUtils.getKernelBuild());

        Preference bootloaderBuild = findPreference(KEY_DEVICEINFO_BOOTLOADER_BUILD);
        bootloaderBuild.setSummary(SystemInfoUtils.getBootloaderBuildVersion());

        Preference modemBuild = findPreference(KEY_DEVICEINFO_MODEM_BUILD);
        modemBuild.setSummary(SystemInfoUtils.getModemBuildVersion());

        Preference buildVersion = findPreference(KEY_DEVICEINFO_BUILD_VERSION);
        buildVersion.setSummary(SystemInfoUtils.getBuildNumber());

        mWifiAdbPref = (SwitchPreferenceScreen) findPreference(KEY_WIFI_ADB);
        mWifiAdbPref.setOnPreferenceChangeListener(this);
        mWifiAdbPref.setOnPreferenceClickListener(this);
    }

    private void initLinksCard(LayoutPreference pref) {
        LinearLayout container = pref.findViewById(R.id.hades_home_links_container);

        LinearLayout xdaLink = container.findViewById(R.id.hades_home_link_1);
        ((ImageView) xdaLink.findViewById(R.id.hades_link_item_icon)).setImageResource(R.drawable.hades_xda_logo);
        ((TextView) xdaLink.findViewById(R.id.hades_link_item_text)).setText(getString(R.string.hades_rc_link_xda));
        xdaLink.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                String threadLink =
                        SystemInfoUtils.getBootloaderBuildVersion().startsWith("G95") ?
                                "https://forum.xda-developers.com/t/rom-10-hadesrom-q-v2-0-for-exynos-s8-s8-note8-15-09-2021.4208409/" :
                                "https://forum.xda-developers.com/t/rom-10-hadesrom-q-v2-0-for-exynos-s8-s8-note8-15-09-2021.4208403/";

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(threadLink));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(mContext, "No suitable activity found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout tgSupportLink = container.findViewById(R.id.hades_home_link_2);
        ((ImageView) tgSupportLink.findViewById(R.id.hades_link_item_icon)).setImageResource(R.drawable.hades_telegram_logo);
        ((TextView) tgSupportLink.findViewById(R.id.hades_link_item_text)).setText(getString(R.string.hades_rc_link_tg_support));
        tgSupportLink.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://bit.ly/hadesRom"));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(mContext, "No suitable activity found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout tgChannelLink = container.findViewById(R.id.hades_home_link_3);
        ((ImageView) tgChannelLink.findViewById(R.id.hades_link_item_icon)).setImageResource(R.drawable.hades_telegram_logo);
        ((TextView) tgChannelLink.findViewById(R.id.hades_link_item_text)).setText(getString(R.string.hades_rc_link_tg_channel));
        tgChannelLink.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://t.me/hadesUpdates"));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(mContext, "No suitable activity found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ColorStateList getColoredSummaryColor(boolean enabled) {
        if (enabled) {
            TypedValue textColor = new TypedValue();
            mContext.getTheme().resolveAttribute(enabled ? R.attr.colorPrimaryDark : android.R.attr.textColorSecondary, textColor, true);

            int[][] states = new int[][] {
                    new int[] {android.R.attr.state_enabled},
                    new int[] {-android.R.attr.state_enabled}
            };
            int[] colors = new int[] {
                    Color.argb(0xff, Color.red(textColor.data), Color.green(textColor.data), Color.blue(textColor.data)),
                    Color.argb(0x4d, Color.red(textColor.data), Color.green(textColor.data), Color.blue(textColor.data))
            };
            return new ColorStateList(states, colors);
        } else
            return mContext.getResources().getColorStateList(R.color.sesl_secondary_text, mContext.getTheme());
    }

    private void setWifiAdbAllowed(boolean allowed) {
        getActivity().runOnUiThread(() -> {
            WifiAdbUtils.setWifiAdb(allowed && WifiAdbUtils.isWifiAdbEnabled());
            mWifiAdbPref.setEnabled(allowed);
        });
    }
}
