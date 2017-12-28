package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager


fun sendCurrentStatus(context: Context) {
    // Charge and battery status
    val intent: Intent = context.applicationContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    val rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    var level = -1
    if (rawlevel >= 0 && scale > 0) {
        level = rawlevel * 100 / scale
    }

    val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
    val plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

    val statusStr = "Battery status: " +
            when (status) {
                BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
                BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
                BatteryManager.BATTERY_STATUS_FULL -> "Full"
                BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not charging"
                BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
                else -> "Other: " + status.toString()
            }

    val pluggedStr = "Battery plugged: " +
            when (plugged) {
                BatteryManager.BATTERY_PLUGGED_AC -> "AC"
                BatteryManager.BATTERY_PLUGGED_USB -> "USB"
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> "Wireless"
                else -> "Other: " + plugged.toString()
            }

    sendServerSms(context, "$statusStr. $pluggedStr. Battery level: $level%")

    // Other?
}