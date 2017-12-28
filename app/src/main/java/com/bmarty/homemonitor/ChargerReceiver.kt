package com.bmarty.homemonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ChargerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.w("TAG", "Charger info received: " + intent?.action);
    }

}