package com.mesalabs.hadesrc.ui.tabs;

import android.os.Bundle;
import android.view.View;

import com.mesalabs.hadesrc.R;
import com.mesalabs.hadesrc.rc.utils.SettingsUtils;

import de.dlyt.yanndroid.oneui.layout.PreferenceFragment;
import de.dlyt.yanndroid.oneui.preference.Preference;
import de.dlyt.yanndroid.oneui.preference.SwitchPreference;

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

public class SettingsTestFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle bundle, String str) {
        getPreferenceManager().setSharedPreferencesName("ten_rc_testsettingsfragment");
        addPreferencesFromResource(R.xml.hades_rc_testsettingsfragment);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().seslSetLastRoundedCorner(true);
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        SwitchPreference rc_mass_startingwindow = (SwitchPreference) findPreference("rc_mass_startingwindow");
        rc_mass_startingwindow.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("rc_mass_startingwindow")) {
            SettingsUtils.putSystemBool(getContext().getContentResolver(), "hadespwr_mass_startingwindow", (boolean) newValue);
            return true;
        }

        return false;
    }
}