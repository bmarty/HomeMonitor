package com.bmarty.homemonitor.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import io.realm.Realm

abstract class AbstractActivity : AppCompatActivity() {

    private lateinit var realm: Realm

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()
    }

    override fun onDestroy() {
        super.onDestroy()

        realm.close()
    }
}