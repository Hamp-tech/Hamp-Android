package com.hamp.ui.home.service

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.Service
import com.hamp.extension.loadImg
import com.hamp.ui.home.service.ServiceViewQuantityListener.Operation
import kotlinx.android.synthetic.main.service_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var service: Service
    private var serviceViewQuantityListener: ServiceViewQuantityListener? = null
    var quantity = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.service_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        plus.onClick { incrementQuantity() }
        minus.onClick { decrementQuantity() }
    }

    fun bind(service: Service, quantityListener: ServiceViewQuantityListener) {
        this.service = service
        this.serviceViewQuantityListener = quantityListener

        name.text = service.name
        image.loadImg(service.image)
        quantityValue.text = quantity.toString()
    }

    private fun incrementQuantity() {
        quantity++
        serviceViewQuantityListener?.onQuantityChange(Operation.ADD)
        quantityValue.text = quantity.toString()
    }

    private fun decrementQuantity() {
        quantity--
        if (quantity < 0) quantity = 0
        else serviceViewQuantityListener?.onQuantityChange(Operation.SUBTRACT)
        quantityValue.text = quantity.toString()
    }

    fun modifyQuantity(resultQuantity: Int) {
        quantity = resultQuantity
        quantityValue.text = quantity.toString()
    }
}