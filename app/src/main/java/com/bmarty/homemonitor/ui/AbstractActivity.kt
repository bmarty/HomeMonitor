package com.bmarty.homemonitor.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import io.realm.Realm

abstract class AbstractActivity : AppCompatActivity() {

    protected lateinit var realm: Realm

    private var unBinder: Unbinder? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutRes())

        unBinder = ButterKnife.bind(this)

        realm = Realm.getDefaultInstance()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onDestroy() {
        super.onDestroy()

        unBinder?.unbind()
        unBinder = null

        realm.close()
    }
}