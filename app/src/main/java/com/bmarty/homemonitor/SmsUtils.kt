package com.bmarty.homemonitor

import android.content.Context
import android.telephony.SmsManager

fun sendServerSms(context: Context,
                  text: String) {
    if (amIServer(context)) {
        sendSms(context, text)
    }
}

fun sendClientSms(context: Context,
                  text: String) {
    if (amIClient(context)) {
        sendSms(context, text)
    }
}

private fun sendSms(context: Context,
            text: String) {
    val smsManager: SmsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(getPhoneNumber(context), null, smsTag + text, null, null)
}

fun getPhoneNumber(context: Context): String {
    return getPref(context).getString(keyPhone, defaultPhone)
}
