package com.bmarty.homemonitor

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import com.bmarty.homemonitor.data.Message
import com.bmarty.homemonitor.realm.storeMessage
import com.google.gson.Gson
import io.realm.Realm


fun call(context: Context,
         number: String = getDistantPhoneNumber(context)) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:" + number)
    context.startActivity(callIntent)
}

fun sendSms(context: Context,
            realm: Realm,
            message: Message,
            number: String = getDistantPhoneNumber(context)) {
    // Store in Realm
    storeMessage(realm, message)

    val sentIntent = Intent("action.sms.sent")
    sentIntent.putExtra("SMS_ID", 1)
    var sentPendingIntent = PendingIntent.getBroadcast(context, 0, sentIntent, 0)

    val deliveryIntent = Intent("action.sms.delivery")
    deliveryIntent.putExtra("SMS_ID", 1)
    var deliveryPendingIntent = PendingIntent.getBroadcast(context, 0, deliveryIntent, 0)

    val smsManager: SmsManager = SmsManager.getDefault()
    smsManager.sendTextMessage(number,
            null,
            Gson().toJson(message),
            sentPendingIntent,
            deliveryPendingIntent)
}