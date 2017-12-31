package com.bmarty.homemonitor.ui

import butterknife.OnClick
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.createServerMessage
import com.bmarty.homemonitor.data.typeStatus
import com.bmarty.homemonitor.call
import com.bmarty.homemonitor.sendSms

class ServerActivity : SecondActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_server
    }

    // UI Event

    @OnClick(R.id.server_send_status)
    fun sendStatus() {
        sendSms(this, createServerMessage(this, typeStatus))
    }

    @OnClick(R.id.server_call)
    fun callClient() {
        call(this)
    }
}