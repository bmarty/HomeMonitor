package com.bmarty.homemonitor

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class SecondActivity : AppCompatActivity() {

    @BindView(R.id.second_event) lateinit var mEvent: TextView
    @BindView(R.id.second_phone) lateinit var mPhone: TextView

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutRes())

        ButterKnife.bind(this)

        mPhone.setText(getDistantPhoneNumber(this))
    }

    @LayoutRes abstract fun getLayoutRes(): Int

    // Bus event

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: ChargerEvent) {
        mEvent.text = "Event: " + event.intentAction
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: SmsEvent) {
        mEvent.text = "Sms: " + event.intentAction + " " + event.smsContent
    }

    // UI Event

    @OnClick(R.id.second_stop)
    fun stop() {
        getPref(this).edit().remove(keyMode).apply()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}