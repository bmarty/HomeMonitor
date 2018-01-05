package com.bmarty.homemonitor.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


class SmsAckReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) {
            return
        }

        Log.w("TAG", "Sms ack received: " + intent.action)

        // Get Extra
        val smsId = intent.getIntExtra("SMS_ID", -1)
        Log.w("TAG", "Sms ack received. SMSId: " + smsId)

        // TODO Update database
    }

}