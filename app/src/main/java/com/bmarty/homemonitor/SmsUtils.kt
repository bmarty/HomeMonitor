package com.bmarty.homemonitor

import android.content.Context
import android.telephony.SmsManager
import com.bmarty.homemonitor.data.Message
import com.google.gson.Gson

fun sendSms(context: Context,
            message: Message,
            number: String = getDistantPhoneNumber(context)) {
    val smsManager: SmsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(number, null, Gson().toJson(message), null, null)
}

