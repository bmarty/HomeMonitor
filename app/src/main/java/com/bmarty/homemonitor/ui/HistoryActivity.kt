package com.bmarty.homemonitor.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import butterknife.BindView
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.realm.getMessages
import io.reactivex.disposables.Disposable

class HistoryActivity : AbstractActivity() {

    @BindView(R.id.history_list)
    lateinit var listView: RecyclerView

    private lateinit var historyAdapter: HistoryAdapter

    override fun getLayoutRes() = R.layout.activity_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        historyAdapter = HistoryAdapter(this)

        listView.let {
            it.layoutManager = LinearLayoutManager(this).apply {
                orientation = LinearLayoutManager.VERTICAL

                it.adapter = historyAdapter
            }
        }
    }

    private var disposable: Disposable? = null

    override fun onResume() {
        super.onResume()

        disposable = getMessages(realm)
                .subscribe(
                        {
                            historyAdapter.setData(it.collection)
                        },
                        {
                            Log.e("TAG", "Error: ", it)
                        })

    }

    override fun onPause() {
        super.onPause()

        disposable?.dispose()
        disposable = null
    }

    // UI Event
}