package com.bmarty.homemonitor.ui

import butterknife.OnClick
import com.bmarty.homemonitor.*
import com.bmarty.homemonitor.createClientMessage
import com.bmarty.homemonitor.data.Message.Companion.typeGetCalled
import com.bmarty.homemonitor.data.Message.Companion.typeGetStatus

class ClientActivity : SecondActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_client
    }

    // UI Event

    @OnClick(R.id.client_get_status)
    fun getStatus() {
        sendSms(this, createClientMessage(typeGetStatus))
    }

    @OnClick(R.id.client_get_called)
    fun getCalled() {
        sendSms(this, createClientMessage(typeGetCalled))
    }
}