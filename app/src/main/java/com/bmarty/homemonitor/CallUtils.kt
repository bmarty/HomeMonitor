package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.net.Uri


fun call(context: Context,
         number: String = getDistantPhoneNumber(context)) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:" + number)
    context.startActivity(callIntent)
}