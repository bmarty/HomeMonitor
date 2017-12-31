package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import com.bmarty.homemonitor.data.Message
import com.google.gson.Gson


fun call(context: Context,
         number: String = getDistantPhoneNumber(context)) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:" + number)
    context.startActivity(callIntent)
}

fun sendSms(context: Context,
            message: Message,
            number: String = getDistantPhoneNumber(context)) {
    val smsManager: SmsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(number, null, Gson().toJson(message), null, null)
}