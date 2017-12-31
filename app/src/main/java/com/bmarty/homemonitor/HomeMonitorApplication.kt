package com.bmarty.homemonitor

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


class HomeMonitorApplication: Application() {


    override fun onCreate() {
        super.onCreate()


        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("HomeMonitor.realm")
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}