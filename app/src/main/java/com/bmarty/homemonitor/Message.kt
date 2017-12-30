package com.bmarty.homemonitor

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
        @SerializedName("cs") val chargerStatus: String?,

        // For type Status
        /**
         * One off
         * BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
         * BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
         * BatteryManager.BATTERY_STATUS_FULL -> "Full"
         * BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not charging"
         * BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
         */
        @SerializedName("bs") val batteryStatus: Int?,
        /**
         * From 0 to 100
         */
        @SerializedName("bl") val batteryLevel: Int?,
        @SerializedName("lt") val latitude: Double?,
        @SerializedName("lg") val longitude: Double?,

        // Version code
        @SerializedName("v") val versionCode: Int = BuildConfig.VERSION_CODE
)

fun createClientMessage(type: String): Message {
    return Message(true, type, null, null, null, null, null)
}

