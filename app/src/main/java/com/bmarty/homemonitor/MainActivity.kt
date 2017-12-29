package com.bmarty.homemonitor

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : AppCompatActivity() {

    @BindView(R.id.main_phone) lateinit var mPhone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        mPhone.setText(getPhoneNumber(this))

        RxPermissions(this)
                .request(Manifest.permission.SEND_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CALL_PHONE)
                .subscribe({ granted ->
                    if (!granted) {
                        finish()
                    } else {
                        redirect()
                    }
                })
    }

    private fun redirect() {
        if (amIClient(this)) {
            startActivity(Intent(this, ClientActivity::class.java))
            finish()
        } else if (amIServer(this)) {
            startActivity(Intent(this, ServerActivity::class.java))
            finish()
        }

        // Else stay here
    }

    // UI Event

    @OnClick(R.id.main_start_client)
    fun startClient() {
        getPref(this).edit().putString(keyMode, keyModeClientStarted).apply()
        redirect()
    }

    @OnClick(R.id.main_start_server)
    fun startServer() {
        getPref(this).edit().putString(keyMode, keyModeServerStarted).apply()
        redirect()
    }

    @OnTextChanged(R.id.main_phone)
    fun onPhoneChange() {
        getPref(this).edit().putString(keyPhone, mPhone.text.toString()).apply()
    }
}