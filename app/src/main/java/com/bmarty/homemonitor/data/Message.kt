package com.bmarty.homemonitor.data

import com.bmarty.homemonitor.BuildConfig
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Message : RealmObject() {

    @PrimaryKey
    @SerializedName("id")
    var id: Long? = null

    @SerializedName("fc")
    var fromClient: Boolean = false

    @SerializedName("t")
    var type: String = ""

    // For type charger
    /**
     * One off
     * 0: disconnected
     * 1: connected
     * 2: Battery low
     * 3: Battery ok
     * -1: Unknown
     */
    @SerializedName("cs")
    var chargerStatus: Int? = null

    // For type Status
    /**
     * One off
     * BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
     * BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
     * BatteryManager.BATTERY_STATUS_FULL -> "Full"
     * BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not charging"
     * BatteryManager.BATTERY_STATUS_UNKNOWN -> "Unknown"
     */
    @SerializedName("bs")
    var batteryStatus: Int? = null
    /**
     * From 0 to 100
     */
    @SerializedName("bl")
    var batteryLevel: Int? = null

    @SerializedName("lt")
    var latitude: Double? = null
    @SerializedName("lg")
    var longitude: Double? = null

    // Version code
    @SerializedName("v")
    var versionCode = BuildConfig.VERSION_CODE

    companion object {
        // Type from client
        const val typeGetStatus = "GET STATUS"
        const val typeGetCalled = "GET CALLED"

        // Type from server
        const val typeCharger = "CHARGER"
        const val typeStatus = "STATUS"
    }
}