package com.hamp.ui.basket

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.Service
import com.hamp.ui.home.service.ServiceViewQuantityListener
import kotlinx.android.synthetic.main.basket_service_item.view.*

class BasketServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var service: Service
    private var serviceViewQuantityListener: ServiceViewQuantityListener? = null
    var quantity = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.basket_service_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

//        plus.onClick { incrementQuantity() }
//        minus.onClick { decrementQuantity() }
    }

    fun bind() {
//        this.service = service
//        this.quantity = quantity
//        this.serviceViewQuantityListener = quantityListener

//        quantityValue.text = quantity.toString()
//        name.text = service.name
    }

//    private fun incrementQuantity() {
//        quantity++
//        serviceViewQuantityListener?.onQuantityChange(ServiceViewQuantityListener.Operation.ADD)
//        quantityValue.text = quantity.toString()
//    }
//
//    private fun decrementQuantity() {
//        quantity--
//        if (quantity < 0) quantity = 0
//        else serviceViewQuantityListener?.onQuantityChange(ServiceViewQuantityListener.Operation.SUBTRACT)
//        quantityValue.text = quantity.toString()
//    }
//
//    fun modifyQuantity(resultQuantity: Int) {
//        quantity = resultQuantity
//        quantityValue.text = quantity.toString()
//    }
}