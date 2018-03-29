package com.hamp.mvvm.home.service

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.ServiceQuantity
import com.hamp.extensions.loadImg
import com.hamp.mvvm.home.service.ServiceViewQuantityListener.Operation
import kotlinx.android.synthetic.main.service_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var serviceQuantity: ServiceQuantity
    private var serviceViewQuantityListener: ServiceViewQuantityListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.service_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        plus.onClick { incrementQuantity() }
        minus.onClick { decrementQuantity() }
    }

    fun bind(serviceQuantity: ServiceQuantity, quantityListener: ServiceViewQuantityListener) {
        this.serviceQuantity = serviceQuantity
        this.serviceViewQuantityListener = quantityListener

        name.text = serviceQuantity.service.name
        image.loadImg(serviceQuantity.service.image)
        quantityValue.text = serviceQuantity.quantity.toString()
    }

    private fun incrementQuantity() {
        serviceQuantity.quantity++
        serviceViewQuantityListener?.onQuantityChange(serviceQuantity.service, Operation.ADD)
        quantityValue.text = serviceQuantity.quantity.toString()
    }

    private fun decrementQuantity() {
        serviceQuantity.quantity--
        if (serviceQuantity.quantity < 0) serviceQuantity.quantity = 0
        else serviceViewQuantityListener?.onQuantityChange(serviceQuantity.service, Operation.SUBTRACT)
        quantityValue.text = serviceQuantity.quantity.toString()
    }

    fun getServiceId() = serviceQuantity.service.id

    fun modifyQuantity(resultQuantity: Int) {
        serviceQuantity.quantity = resultQuantity
        quantityValue.text = serviceQuantity.quantity.toString()
    }
}