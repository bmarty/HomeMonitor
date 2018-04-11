package com.bmarty.homemonitor.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.data.Message

class HistoryItem(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.item_history_title)
    lateinit var titleText: TextView

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(message: Message) {
        titleText.text = message.type
    }

}
