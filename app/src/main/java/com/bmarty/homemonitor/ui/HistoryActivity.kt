package com.bmarty.homemonitor.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import com.bmarty.homemonitor.R
import com.bmarty.homemonitor.realm.clearMessages
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

                            // TODO Do not scroll if user has already scrolled
                            listView.scrollToPosition(it.collection.size - 1)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_clear) {
            clearMessages(realm)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // UI Event
}