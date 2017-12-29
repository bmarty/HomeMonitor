package com.bmarty.homemonitor

import butterknife.OnClick

class ClientActivity : SecondActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_client
    }

    // UI Event

    @OnClick(R.id.client_get_status)
    fun getStatus() {
        sendSms(this, Message(true, typeGetStatus, ""))
    }

    @OnClick(R.id.client_get_called)
    fun getCalled() {
        sendSms(this, Message(true, typeGetCalled, ""))
    }
}