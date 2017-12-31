package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.BatteryManager
import com.google.gson.annotations.SerializedName

// Type from client
val typeGetStatus = "GET STATUS"
val typeGetCalled = "GET CALLED"

// Type from server
var typeCharger = "CHARGER"
var typeStatus = "STATUS"

data class Message(
        @SerializedName("fc") val fromClient: Boolean,
        @SerializedName("t") val type: String,

        // For type charger
        /**
         * One off
         * 0: disconnected
         * 1: connected
         * 2: Battery low
         * 3: Battery ok
         * -1: Unknown
         */
        @SerializedName("cs") val chargerStatus: Int?,

        // For type Status
        /**
         * One off
         * BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
         * BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
         * BatteryManager.BATTERY_STATUS_FULL -> "Full"
         * BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not charging"
         * BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
         */
        @SerializedName("bs") var batteryStatus: Int?,
        /**
         * From 0 to 100
         */
        @SerializedName("bl") var batteryLevel: Int?,

        @SerializedName("lt") var latitude: Double?,
        @SerializedName("lg") var longitude: Double?,

        // Version code
        @SerializedName("v") val versionCode: Int = BuildConfig.VERSION_CODE
)

fun createClientMessage(type: String): Message {
    return Message(true,
            type,
            null,
            null,
            null,
            null,
            null)
}

fun createServerMessage(context: Context, type: String): Message {
    val message = Message(false,
            type,
            getLastChargerStatus(context),
            null,
            -1,
            null,
            null)


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