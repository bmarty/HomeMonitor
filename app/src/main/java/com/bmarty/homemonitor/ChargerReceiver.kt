package com.bmarty.homemonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.greenrobot.eventbus.EventBus

class ChargerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) {
            return
        }

        saveLastChargerStatus(context, chargerIntentToInt(intent.action))

        if (!amIServer(context)) {
            return
        }

        Log.w("TAG", "Charger info received: " + intent.action)

        EventBus.getDefault().post(ChargerEvent(intent.action))

        // Send SMS to the configured client
        sendSms(context, createServerMessage(context, typeCharger))
    }
}

fun chargerIntentToInt(intent: String): Int {
    return when (intent) {
        Intent.ACTION_POWER_DISCONNECTED -> 0
        Intent.ACTION_POWER_CONNECTED -> 1
        Intent.ACTION_BATTERY_LOW -> 2
        Intent.ACTION_BATTERY_OKAY -> 3
        else -> -1
    }
}

fun intToChargerIntent(chargerStatus: Int): String {
    return when (chargerStatus) {
        0 -> Intent.ACTION_POWER_DISCONNECTED
        1 -> Intent.ACTION_POWER_CONNECTED
        2 -> Intent.ACTION_BATTERY_LOW
        3 -> Intent.ACTION_BATTERY_OKAY
        else -> ""
    }
}