package com.bmarty.homemonitor.data

import com.bmarty.homemonitor.BuildConfig
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// Type from client
val typeGetStatus = "GET STATUS"
val typeGetCalled = "GET CALLED"

// Type from server
var typeCharger = "CHARGER"
var typeStatus = "STATUS"

class Message(
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
) : RealmObject()
