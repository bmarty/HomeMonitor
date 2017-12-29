package com.bmarty.homemonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import org.greenrobot.eventbus.EventBus


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) {
            return
        }

        Log.w("TAG", "Sms received: " + intent.action)

        val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val smsMessage = msgs[0]

        EventBus.getDefault().post(SmsEvent(intent.action, smsMessage.messageBody))

        if (smsMessage.messageBody.startsWith(smsTag)) {
            // It's for me

            if (amIServer(context)) {
                if (smsMessage.messageBody.endsWith(smsGetStatus)) {
                    sendCurrentStatus(context)
                } else if (smsMessage.messageBody.endsWith(smsGetCalled)) {
                    call(context)
                }
            }
        }
    }

}