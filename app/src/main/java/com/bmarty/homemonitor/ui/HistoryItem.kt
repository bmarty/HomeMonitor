package com.bmarty.homemonitor.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindDimen
import butterknife.BindView
import butterknife.ButterKnife
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.data.Message
import com.bmarty.homemonitor.data.Message.Companion.typeCharger
import com.bmarty.homemonitor.data.Message.Companion.typeGetCalled
import com.bmarty.homemonitor.data.Message.Companion.typeGetStatus
import com.bmarty.homemonitor.data.Message.Companion.typeStatus

class HistoryItem(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    @JvmField
    @BindDimen(R.dimen.history_margin_small)
    var marginSmall: Int = 0

    @JvmField
    @BindDimen(R.dimen.history_margin_big)
    var marginBig: Int = 0

    @BindView(R.id.item_history_container)
    lateinit var container: View

    @BindView(R.id.item_history_title)
    lateinit var titleText: TextView

    private val containerLayoutParams: ViewGroup.MarginLayoutParams

    init {
        ButterKnife.bind(this, itemView)

        containerLayoutParams = container.layoutParams as ViewGroup.MarginLayoutParams
    }

    fun bind(message: Message) {
        message.let {
            if (it.fromClient) {
                container.setBackgroundResource(R.drawable.item_history_background_client)

                containerLayoutParams.marginStart = marginSmall
                containerLayoutParams.marginEnd = marginBig

                // Type from client
                titleText.text = when (it.type) {
                    typeGetStatus -> "Give me your status please."
                    typeGetCalled -> "Call me back please."
                    else -> "?"
                }

            } else {
                container.setBackgroundResource(R.drawable.item_history_background_server)

                containerLayoutParams.marginStart = marginBig
                containerLayoutParams.marginEnd = marginSmall

                // Type from server
                titleText.text = when (it.type) {
                    typeCharger -> "CHARGER"
                    typeStatus -> "STATUS"
                    else -> "?"
                }
            }
        }
    }

}
