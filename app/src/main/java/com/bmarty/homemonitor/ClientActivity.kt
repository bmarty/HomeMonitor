package com.bmarty.homemonitor

import butterknife.OnClick

class ClientActivity : SecondActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_client
    }

    // UI Event

    @OnClick(R.id.client_get_status)
    fun getStatus() {
        sendClientSms(this, smsGetStatus)
    }

    @OnClick(R.id.client_get_called)
    fun getCalled() {
        sendClientSms(this, smsGetCalled)
    }
}