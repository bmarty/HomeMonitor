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

        if (!amIServer(context)) {
            return
        }

        Log.w("TAG", "Charger info received: " + intent.action)

        EventBus.getDefault().post(ChargerEvent(intent.action))

        // Send SMS to the configured client
        sendSms(context, Message(false, typeCharger, intent.action))
    }

}
