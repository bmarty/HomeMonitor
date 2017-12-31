package com.bmarty.homemonitor

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat


fun showChargerEventNotification(context: Context,
                                 chargerStatus: Int) {
    val builder = NotificationCompat.Builder(context)
            .apply {
                setSmallIcon(android.R.drawable.ic_dialog_alert)

                mContentTitle = when (intToChargerIntent(chargerStatus)) {
                    Intent.ACTION_POWER_DISCONNECTED -> "Power"
                    Intent.ACTION_POWER_CONNECTED -> "Power"
                    Intent.ACTION_BATTERY_LOW -> "Battery"
                    Intent.ACTION_BATTERY_OKAY -> "Battery"
                    else -> "?"
                }

                mContentText = when (intToChargerIntent(chargerStatus)) {
                    Intent.ACTION_POWER_DISCONNECTED -> "Power is shut down!"
                    Intent.ACTION_POWER_CONNECTED -> "Power is back!"
                    Intent.ACTION_BATTERY_LOW -> "Battery is low"
                    Intent.ACTION_BATTERY_OKAY -> "Battery is OK"
                    else -> "?"
                }

                priority = NotificationCompat.PRIORITY_MAX

                val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                setSound(alarmSound)

                setLights(Color.RED, 300, 300)
            }

    val notificationManager = NotificationManagerCompat.from(context)

    notificationManager.notify(111, builder.build())
}