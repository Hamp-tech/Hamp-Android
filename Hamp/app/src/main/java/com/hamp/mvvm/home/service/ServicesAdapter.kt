package com.hamp.mvvm.home.service

import android.content.Context
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import com.hamp.domain.Service
import org.jetbrains.anko.sdk25.coroutines.onClick

class ServicesAdapter(val context: Context, override var items: List<Service>,
                      private val clickServiceListener: ClickServiceListener,
                      private val quantityListener: ServiceViewQuantityListener)
    : RecyclerViewAdapterBase<Service, ServiceView>(items) {

    interface ClickServiceListener {
        fun onServiceClick(service: Service, position: Int)
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = ServiceView(context)

    override fun onBindViewHolder(holder: ViewWrapper<ServiceView>?, position: Int) {
        holder?.view?.bind(items[position], quantityListener)
        holder?.view?.onClick {
            clickServiceListener.onServiceClick(items[position], position)
        }
    }
}
