package com.mesalabs.hadesrc.rc.utils;

/*
 * hadesRC
 *
 * Coded by BlackMesa123 @2021
 * Code snippets by MatthewBooth.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 */

import com.mesalabs.hadesrc.utils.SharedPreferencesUtils;

public class PreferencesUtils {
    private static final String KEY_TEMPERATURE_UNIT = "hades_rc_temp_unit";
    private static final String KEY_WIFI_ADB = "hades_rc_wifi_adb";
    private static final String KEY_WIFI_ADB_ALLOWED = "hades_rc_wifi_adb_allow";
    private static SharedPreferencesUtils sp = SharedPreferencesUtils.getInstance();

    public static int getTempUnit() {
        return sp.getInt(KEY_TEMPERATURE_UNIT, 0);
    }

    public static void setTempUnit(int value) {
        sp.put(KEY_TEMPERATURE_UNIT, value);
    }

    public static boolean getIsWifiAdbAllowed() {
        return sp.getBoolean(KEY_WIFI_ADB_ALLOWED, false);
    }

    public static void setIsWifiAdbAllowed(boolean value) {
        sp.put(KEY_WIFI_ADB_ALLOWED, value);
    }

    public static boolean getIsWifiAdbEnabled() {
        return sp.getBoolean(KEY_WIFI_ADB, false);
    }

    public static void setIsWifiAdbEnabled(boolean value) {
        sp.put(KEY_WIFI_ADB, value);
    }
}
