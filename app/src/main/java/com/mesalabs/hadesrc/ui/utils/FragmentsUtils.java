package com.mesalabs.hadesrc.ui.utils;

import com.mesalabs.hadesrc.ui.tabs.HomeTabPreferenceFragment;
import com.mesalabs.hadesrc.ui.tabs.SettingsTestFragment;

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

public class FragmentsUtils {
    public static String[] getRCFragmentsTitles() {
        return new String[] {
                "Home",
                "Frameworks",};
    }

    public static Class[] getRCFragmentsClasses() {
        return new Class[] {
                HomeTabPreferenceFragment.class,
                SettingsTestFragment.class};
    }

    public static String[] getRCFragmentsTags() {
        return new String[] {
                "ten_fragment_rc_home",
                "ten_fragment_rc_fw"};
    }
}
