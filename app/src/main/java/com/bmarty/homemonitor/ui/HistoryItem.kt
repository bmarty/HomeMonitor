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

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(message: Message) {
        message.let {
            titleText.text = it.type

            (container.layoutParams as ViewGroup.MarginLayoutParams).apply {
                if (it.fromClient) {
                    marginStart = marginSmall
                    marginEnd = marginBig
                } else {
                    marginStart = marginBig
                    marginEnd = marginStart
                }
            }
        }


    }

}
