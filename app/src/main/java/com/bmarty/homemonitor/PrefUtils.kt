package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences


fun getPref(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", Context.MODE_PRIVATE)
}

fun amIServer(context: Context): Boolean {
    return getPref(context).getString(keyMode, keyModeStopped).equals(keyModeServerStarted)
}

fun amIClient(context: Context): Boolean {
    return getPref(context).getString(keyMode, keyModeStopped).equals(keyModeClientStarted)
}

fun getDistantPhoneNumber(context: Context): String {
    return getPref(context).getString(keyPhone, defaultPhone)
}

fun saveLastChargerStatus(context: Context, status: String) {
    getPref(context).edit().putString(keyLastChargerStatus, status)
}

fun getLastChargerStatus(context: Context): String {
    return getPref(context).getString(keyLastChargerStatus, Intent.ACTION_POWER_DISCONNECTED)
}