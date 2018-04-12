package com.bmarty.homemonitor

import android.content.Context
import android.content.SharedPreferences

const val keyPhone = "KEY_PHONE"
const val defaultPhone = "0651547677"

const val keyMode = "KEY_MODE"

const val keyModeStopped = "STOPPED"
const val keyModeServerStarted = "SERVER_STARTED"
const val keyModeClientStarted = "CLIENT_STARTED"

const val keyLastChargerStatus = "KEY_LAST_CHARGER_STATUS"

private fun getPref(context: Context): SharedPreferences {
    return context.getSharedPreferences("default", Context.MODE_PRIVATE)
}

fun changeMode(context: Context, newMode: String) {
    getPref(context).edit().putString(keyMode, newMode).apply()
}

fun resetMode(context: Context) {
    getPref(context).edit().remove(keyMode).apply()
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

fun saveLastChargerStatus(context: Context, status: Int) {
    getPref(context).edit().putInt(keyLastChargerStatus, status).apply()
}

fun getLastChargerStatus(context: Context): Int {
    return getPref(context).getInt(keyLastChargerStatus, -1)
}