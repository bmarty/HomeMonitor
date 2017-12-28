package com.bmarty.homemonitor

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.tbruyelle.rxpermissions2.RxPermissions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    @BindView(R.id.main_mode) lateinit var mMode: TextView
    @BindView(R.id.main_event) lateinit var mEvent: TextView
    @BindView(R.id.main_phone) lateinit var mPhone: EditText

    private lateinit var mRxPermissions: RxPermissions

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

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        mRxPermissions = RxPermissions(this)

        mRxPermissions
                .request(Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe({ granted ->
                    if (!granted) {
                        finish()
                    }
                })
    }

    override fun onResume() {
        super.onResume()

        updateUi()
    }

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

    @OnClick(R.id.main_start_client)
    fun startClient() {
        getPref(this).edit().putString(keyMode, keyModeClientStarted).apply()
        updateUi()
    }

    @OnClick(R.id.main_start_server)
    fun startServer() {
        getPref(this).edit().putString(keyMode, keyModeServerStarted).apply()
        updateUi()
    }

    @OnClick(R.id.main_stop)
    fun stop() {
        getPref(this).edit().remove(keyMode).apply()
        updateUi()
    }

    @OnClick(R.id.main_get_status)
    fun getSatus() {
        sendClientSms(this, smsGetStatus)
    }

    @OnTextChanged(R.id.main_phone)
    fun onPhoneChange() {
        getPref(this).edit().putString(keyPhone, mPhone.text.toString()).apply()
    }

    // Private

    private fun updateUi() {
        mMode.text = getPref(this).getString(keyMode, keyModeStopped)
        mPhone.setText(getPhoneNumber(this))
    }
}