package com.hamp.ui.home.history

import android.content.Context
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import org.jetbrains.anko.sdk25.coroutines.onClick

class HistoryAdapter(val context: Context, override var items: List<Int>,
                     private val historyListener: HistoryListener)
    : RecyclerViewAdapterBase<Int, HistoryView>(items) {

    interface HistoryListener {
        fun onHistoryClick()
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = HistoryView(context)

    override fun onBindViewHolder(holder: ViewWrapper<HistoryView>?, position: Int) {
        holder?.view?.bind()
        holder?.view?.onClick { historyListener.onHistoryClick() }
    }

//    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = ServiceView(context)
//
//    override fun onBindViewHolder(holder: ViewWrapper<ServiceView>?, position: Int) {
//        holder?.view?.bind(items[position], quantityListener)
//        holder?.view?.onClick {
//            clickServiceListener.onServiceClick(items[position], holder.view.quantity, position)
//        }
//    }
}