package com.bmarty.homemonitor

import butterknife.OnClick

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
        call(this)
    }
}