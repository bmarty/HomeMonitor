package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.net.Uri


fun call(context: Context) {
    val callIntent = Intent(Intent.ACTION_CALL)
    callIntent.data = Uri.parse("tel:" + getPhoneNumber(context))
    context.startActivity(callIntent)
}