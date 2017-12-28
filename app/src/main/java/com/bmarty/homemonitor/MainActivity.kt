package com.bmarty.homemonitor

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.telephony.SmsManager
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.tbruyelle.rxpermissions2.RxPermissions
import homemonitor.bmarty.com.homemonitor.R


class MainActivity : AppCompatActivity() {

    private var keyPhone: String = "KEY_PHONE"
    private var keyMode: String = "KEY_MODE"

    private var keyModeStopped: String = "STOPPED"
    private var keyModeServerStarted: String = "SERVER_STARTED"
    private var keyModeClientStarted: String = "CLIENT_STARTED"


    @BindView(R.id.main_mode) lateinit var mMode: TextView
    @BindView(R.id.main_phone) lateinit var mPhone: EditText

    private lateinit var mRxPermissions: RxPermissions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        mRxPermissions = RxPermissions(this)

        mRxPermissions
                .request(Manifest.permission.SEND_SMS)
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


    // UI Event

    @OnClick(R.id.main_start_client)
    fun startClient() {
        getPref().edit().putString(keyMode, keyModeClientStarted).apply()
        updateUi()
    }

    @OnClick(R.id.main_start_server)
    fun startServer() {
        getPref().edit().putString(keyMode, keyModeServerStarted).apply()
        updateUi()
    }

    @OnClick(R.id.main_stop)
    fun stop() {
        getPref().edit().remove(keyMode).apply()
        updateUi()
    }

    @OnClick(R.id.main_test_sms)
    fun testSms() {
        val smsManager: SmsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(mPhone.text.toString(), null, "test", null, null)
    }

    @OnTextChanged(R.id.main_phone)
    fun onPhoneChange() {
        getPref().edit().putString(keyPhone, mPhone.text.toString()).apply()
    }

    // Private

    private fun getPref(): SharedPreferences {
        return getSharedPreferences("default", Context.MODE_PRIVATE)
    }

    private fun updateUi() {
        mMode.text = getPref().getString(keyMode, keyModeStopped)
        mPhone.setText(getPref().getString(keyPhone, "0651547677"))
    }
}