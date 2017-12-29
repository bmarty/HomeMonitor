package com.bmarty.homemonitor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) {
            return
        }

        Log.w("TAG", "Sms received: " + intent.action)

        val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val smsMessage = msgs[0]

        if (smsMessage.messageBody.startsWith("{")) {

            EventBus.getDefault().post(SmsEvent(intent.action, smsMessage.messageBody))

            // It's for me

            // Try to parse message
            val message: Message = Gson().fromJson(smsMessage.messageBody, Message::class.java)

            if (amIServer(context) && message.fromClient) {
                // We received a message form the client or any other number. Answer to the caller TODO if on the white list?
                var callerNumber = smsMessage.originatingAddress

                if (message.type == typeGetStatus) {
                    sendCurrentStatus(context, callerNumber)
                } else if (message.type == typeGetCalled) {
                    call(context, callerNumber)
                }
            } else if (amIClient(context) && !message.fromClient) {
                //
            }
        }
    }

}