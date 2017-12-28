package com.bmarty.homemonitor

import android.content.Context
import android.content.SharedPreferences


fun getPref(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", Context.MODE_PRIVATE)
}

fun isServer(context: Context): Boolean {
    return getPref(context).getString(keyMode, keyModeStopped).equals(keyModeServerStarted)
}

fun isClient(context: Context): Boolean {
    return getPref(context).getString(keyMode, keyModeStopped).equals(keyModeClientStarted)
}
