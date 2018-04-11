package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.BatteryManager
import com.bmarty.homemonitor.data.Message


fun createClientMessage(type: String): Message {
    return Message().apply {
        fromClient = true
        this.type = type
    }
}

fun createServerMessage(context: Context, type: String): Message {
    val message = Message().apply {
        fromClient = false
        this.type = type
    }

    message.chargerStatus = getLastChargerStatus(context)

    // Battery status
    val intent: Intent = context.applicationContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    val rawLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

    if (rawLevel >= 0 && scale > 0) {
        message.batteryLevel = rawLevel * 100 / scale
    }

    message.batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)

    /*
        BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
        BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
        BatteryManager.BATTERY_STATUS_FULL -> "Full"
        BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not charging"
        BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
    */

    // Get latitude and longitude
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    message.latitude = location?.latitude
    message.longitude = location?.longitude

    // Other?

    return message
}
