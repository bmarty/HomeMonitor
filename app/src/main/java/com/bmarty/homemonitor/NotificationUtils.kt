package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

fun showChargerEventNotification(context: Context,
                                 info: String) {
    val builder = NotificationCompat.Builder(context)
            .apply {
                setSmallIcon(android.R.drawable.ic_dialog_alert)

                mContentTitle = when (info) {
                    Intent.ACTION_POWER_DISCONNECTED -> "Power failure"
                    Intent.ACTION_POWER_CONNECTED -> "Power is back!"
                    else -> "?"
                }

                mContentText = when (info) {
                    Intent.ACTION_POWER_DISCONNECTED -> "Power is shut down!"
                    Intent.ACTION_POWER_CONNECTED -> "Power is back!"
                    else -> "?"
                }

                priority = NotificationCompat.PRIORITY_MAX
            }

    val notificationManager = NotificationManagerCompat.from(context)

    notificationManager.notify(111, builder.build())
}