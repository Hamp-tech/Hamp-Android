package com.hamp.ui.basket

import android.content.Context
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import com.hamp.domain.Service

class BasketAdapter(val context: Context, override var items: List<Service>,
                    private var quantities: List<Int>)
    : RecyclerViewAdapterBase<Service, BasketServiceView>(items) {

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = BasketServiceView(context)

    override fun onBindViewHolder(holder: ViewWrapper<BasketServiceView>?, position: Int) {
        holder?.view?.bind(items[position], quantities[position])
    }

//    interface ClickServiceListener {
//        fun onServiceClick(service: Service, quantity: Int, position: Int)
//    }
//
//    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = ServiceView(context)
//
//    override fun onBindViewHolder(holder: ViewWrapper<ServiceView>?, position: Int) {
//        holder?.view?.bind(items[position], quantityListener)
//        holder?.view?.onClick {
//            clickServiceListener.onServiceClick(items[position], holder.view.quantity, position)
//        }
//    }
}