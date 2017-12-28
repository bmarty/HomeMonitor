package com.bmarty.homemonitor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import homemonitor.bmarty.com.homemonitor.R

class MainActivity : AppCompatActivity() {

    private var keyPhone: String = "KEY_PHONE"
    private var keyMode: String = "KEY_MODE"

    private var keyModeServer: String = "KEY_MODE_SERVER"
    private var keyModeClient: String = "KEY_MODE_CLIENT"


    @BindView(R.id.main_phone)
    lateinit var mPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()

        mPhone.setText(getPref().getString(keyPhone, "0651547677"))
    }

    // UI Event

    @OnClick(R.id.main_start_client)
    fun startClient() {

    }

    @OnClick(R.id.main_start_client)
    fun startServer() {

    }

    @OnClick(R.id.main_stop)
    fun stop() {
        getPref().edit().remove(keyMode)
    }

    @OnTextChanged(R.id.main_phone)
    fun onPhoneChange() {
        getPref().edit().putString(keyPhone, mPhone.text.toString()).apply()
    }

    // Private

    private fun getPref(): SharedPreferences {
        return getSharedPreferences("default", Context.MODE_PRIVATE)
    }
}