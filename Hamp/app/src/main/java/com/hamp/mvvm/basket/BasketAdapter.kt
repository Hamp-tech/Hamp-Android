package com.hamp.mvvm.basket

import android.content.Context
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import com.hamp.domain.ServiceQuantity

class BasketAdapter(val context: Context, override var items: List<ServiceQuantity>,
                    private val basketListener: BasketServiceView.BasketListener)
    : RecyclerViewAdapterBase<ServiceQuantity, BasketServiceView>(items) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = BasketServiceView(context)

    override fun onBindViewHolder(holder: ViewWrapper<BasketServiceView>?, position: Int) {
        holder?.view?.bind(items[position], position, basketListener)
    }
}