package com.bmarty.homemonitor.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.bmarty.homemonitor.*
import com.bmarty.homemonitor.data.*
import com.bmarty.homemonitor.data.Message.Companion.typeCharger
import com.bmarty.homemonitor.data.Message.Companion.typeGetCalled
import com.bmarty.homemonitor.data.Message.Companion.typeGetStatus
import com.bmarty.homemonitor.data.Message.Companion.typeStatus
import com.bmarty.homemonitor.realm.storeMessage
import com.google.gson.Gson
import io.realm.Realm
import org.greenrobot.eventbus.EventBus


class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) {
            return
        }

        val msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val smsMessage = msgs[0]

        Log.w("TAG", "Sms received: " + intent.action)
        Log.w("TAG", "Sms received: " + smsMessage.messageBody)
        Log.w("TAG", "Sms received: " + smsMessage.originatingAddress)

        if (smsMessage.messageBody.startsWith("{")) {

            EventBus.getDefault().post(SmsEvent(intent.action, smsMessage.messageBody))

            // It's for me

            // Try to parse message
            val message: Message = Gson().fromJson(smsMessage.messageBody, Message::class.java)

            // Save message in DB
            val realm = Realm.getDefaultInstance()
            storeMessage(realm, message)

            if (amIServer(context) && message.fromClient) {
                // We received a message form the client or any other number. Answer to the caller TODO if on the white list?
                val callerNumber = smsMessage.originatingAddress

                when (message.type) {
                    typeGetStatus -> sendSms(context, realm, createServerMessage(context, typeStatus), callerNumber)
                    typeGetCalled -> call(context, callerNumber)
                    else -> {
                        // Should not happen
                    }
                }
            } else if (amIClient(context) && !message.fromClient) {
                // We receive a message form the server
                when (message.type) {
                    typeCharger -> showChargerEventNotification(context, message.chargerStatus!!)
                    typeStatus -> {
                        // TODO
                    }
                    else -> {
                        // Should not happen
                    }
                }
            }

            realm.close()
        }
    }

}