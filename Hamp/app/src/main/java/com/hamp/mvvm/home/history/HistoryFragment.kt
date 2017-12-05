package com.hamp.mvvm.home.history

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.home.history.detail.HistoryDetailActivity
import kotlinx.android.synthetic.main.fragment_history.*
import org.jetbrains.anko.support.v4.startActivity

class HistoryFragment : BaseFragment(), HistoryAdapter.HistoryListener {
    companion object {
        fun create() = HistoryFragment()
    }

    val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_history, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRecyclerView.setHasFixedSize(true)
        historyRecyclerView.layoutManager = LinearLayoutManager(context)
        historyRecyclerView.adapter = HistoryAdapter(context, listOf(1, 2, 3, 4, 5, 6, 7, 8), this)
    }

    override fun onHistoryClick() = startActivity<HistoryDetailActivity>()
}