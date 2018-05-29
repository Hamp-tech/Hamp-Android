package com.hamp.mvvm.home.service

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.Service
import com.hamp.extensions.loadImg
import kotlinx.android.synthetic.main.item_service.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var service: Service
    private var serviceViewQuantityListener: ServiceViewQuantityListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.item_service, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        plus.onClick { incrementQuantity() }
        minus.onClick { decrementQuantity() }
    }

    fun bind(service: Service, quantityListener: ServiceViewQuantityListener) {
        this.service = service
        this.serviceViewQuantityListener = quantityListener

        name.text = service.hampService.name
        image.loadImg(service.hampService.image)
        quantityValue.text = service.amount.toString()
    }

    private fun incrementQuantity() {
        service.amount++
        serviceViewQuantityListener?.onQuantityChange(service)
        quantityValue.text = service.amount.toString()
    }

    private fun decrementQuantity() {
        service.amount--
        if (service.amount < 0) service.amount = 0
        else serviceViewQuantityListener?.onQuantityChange(service)
        quantityValue.text = service.amount.toString()
    }

    fun modifyQuantity(resultQuantity: Int) {
        service.amount = resultQuantity
        quantityValue.text = service.amount.toString()
    }
}