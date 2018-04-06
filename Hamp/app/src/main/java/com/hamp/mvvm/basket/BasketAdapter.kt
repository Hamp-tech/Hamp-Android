package com.hamp.mvvm.basket

import android.content.Context
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import com.hamp.domain.Service

class BasketAdapter(val context: Context, override var items: List<Service>,
                    private val basketListener: BasketServiceView.BasketListener)
    : RecyclerViewAdapterBase<Service, BasketServiceView>(items) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = BasketServiceView(context)

    override fun onBindViewHolder(holder: ViewWrapper<BasketServiceView>?, position: Int) {
        holder?.view?.bind(items[position], position, basketListener)
    }
}