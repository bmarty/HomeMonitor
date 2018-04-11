package com.bmarty.homemonitor.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.data.Message

class HistoryAdapter(context: Context) :
        RecyclerView.Adapter<HistoryItem>() {

    private var listMessage: List<Message> = arrayListOf()

    private val layoutInflater = LayoutInflater.from(context)

    fun setData(list: List<Message>) {
        listMessage = list

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HistoryItem {
        val itemView = layoutInflater.inflate(R.layout.item_history, parent, false)

        return HistoryItem(itemView)
    }

    override fun getItemCount() = listMessage.size

    override fun onBindViewHolder(holder: HistoryItem, position: Int) {
        holder.bind(listMessage[position])
    }
}
