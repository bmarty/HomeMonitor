package com.bmarty.homemonitor.ui

import butterknife.OnClick
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.sendCurrentStatus

class ServerActivity : SecondActivity() {

    override fun getLayoutRes(): Int {
        return R.layout.activity_server
    }

    // UI Event

    @OnClick(R.id.server_send_status)
    fun sendStatus() {
        sendCurrentStatus(this)
    }

    @OnClick(R.id.server_call)
    fun call() {
        com.bmarty.homemonitor.call(this)
    }
}