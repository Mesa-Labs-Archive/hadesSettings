package com.mesalabs.hadesrc.utils;

import android.util.Log;

import com.mesalabs.hadesrc.HadesRCApp;

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

public class PropUtils {
    private static final Class<?> SP = getSystemProperties();

    public static String get(String key, String def) {
        try {
            return (String) SP.getMethod("get", String.class, String.class).invoke(null, key, def);
        } catch (Exception e) {
            return def;
        }
    }

    public static boolean getBoolean(String key, boolean def) {
        try {
            return (boolean) SP.getMethod("getBoolean", String.class, boolean.class).invoke(null, key, def);
        } catch (Exception e) {
            return def;
        }
    }

    public static int getInt(String key, int def) {
        try {
            return (Integer) SP.getMethod("getInt", String.class, int.class).invoke(null, key, def);
        } catch (Exception e) {
            return def;
        }
    }

    private static Class<?> getSystemProperties() {
        try {
            return Class.forName("android.os.SystemProperties");
        } catch (ClassNotFoundException e) {
            Log.e(HadesRCApp.getAppName(), "WTF? No SystemProperties class found? Oof.");
            return null;
        }
    }

}
