package com.hamp.mvp.home.fragments

import android.content.Context
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import com.hamp.domain.Service

class ServicesAdapter(val context: Context, override var items: MutableList<Service>,
                      val clickServiceListener: ClickServiceListener? = null)
    : RecyclerViewAdapterBase<Service, ServiceView>(items) {

    interface ClickServiceListener {
//        fun onServiceClick(planogram: Planogram)
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = ServiceView(context)

    override fun onBindViewHolder(holder: ViewWrapper<ServiceView>?, position: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
