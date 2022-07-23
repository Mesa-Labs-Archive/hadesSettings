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

public class LogUtils {
    private static final String LOG_TAG = "hadesRC: ";
    
    // Verbose
    public static void v(String tag, String msg) {
        if (HadesRCApp.isDebugBuild())
            Log.v(LOG_TAG + tag, msg);
    }

    // Debug
    public static void d(String tag, String msg) {
        if (HadesRCApp.isDebugBuild())
            Log.d(LOG_TAG + tag, msg);
    }

    public static void d(String tag, String msg, Exception e) {
        if (HadesRCApp.isDebugBuild())
            Log.d(LOG_TAG + tag, msg, e);
    }

    public static void d(String tag, String msg, Throwable t) {
        if (HadesRCApp.isDebugBuild())
            Log.d(LOG_TAG + tag, msg, t);
    }

    // Info
    public static void i(String tag, String msg) {
        if (HadesRCApp.isDebugBuild())
            Log.i(LOG_TAG + tag, msg);
    }

    public static void i(String tag, String msg, Exception e) {
        if (HadesRCApp.isDebugBuild())
            Log.i(LOG_TAG + tag, msg, e);
    }

    // Warn
    public static void w(String tag, String msg) {
        if (HadesRCApp.isDebugBuild())
            Log.w(LOG_TAG + tag, msg);
    }

    public static void w(String tag, String msg, Exception e) {
        if (HadesRCApp.isDebugBuild())
            Log.w(LOG_TAG + tag, msg, e);
    }

    // Error
    public static void e(String tag, String msg) {
        if (HadesRCApp.isDebugBuild())
            Log.e(LOG_TAG + tag, msg);
    }
}
