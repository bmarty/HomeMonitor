package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.BatteryManager
import com.bmarty.homemonitor.data.Message
import com.bmarty.homemonitor.data.typeStatus

fun sendCurrentStatus(context: Context,
                      number: String = getDistantPhoneNumber(context)) {
    // Charge and battery status
    val intent: Intent = context.applicationContext.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    val rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
    val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    var batteryLevel = -1
    if (rawlevel >= 0 && scale > 0) {
        batteryLevel = rawlevel * 100 / scale
    }

    val batteryStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

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

    val latitude = location?.latitude
    val longitude = location?.longitude

    // Other?
    val message = Message(false,
            typeStatus,
            getLastChargerStatus(context),
            batteryStatus,
            batteryLevel,
            latitude,
            longitude)

    sendSms(context, message, number)
}