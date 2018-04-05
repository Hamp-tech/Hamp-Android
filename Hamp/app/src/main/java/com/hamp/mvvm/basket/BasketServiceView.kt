package com.hamp.mvvm.basket

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.db.domain.ServiceQuantity
import com.hamp.extensions.loadImg
import kotlinx.android.synthetic.main.basket_service_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class BasketServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var serviceQuantity: ServiceQuantity
    private var position: Int = -1

    private var basketListener: BasketListener? = null

    interface BasketListener {
        fun onServiceQuantityChange(service: ServiceQuantity, index: Int)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.basket_service_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        basketPlus.onClick { incrementQuantity() }
        basketMinus.onClick { decrementQuantity() }
    }

    fun bind(serviceQuantity: ServiceQuantity, position: Int, basketListener: BasketListener) {
        this.serviceQuantity = serviceQuantity
        this.position = position
        this.basketListener = basketListener

        basketItemImage.loadImg(serviceQuantity.service.image)
        basketItemName.text = serviceQuantity.service.name
        basketItemQuantity.text = serviceQuantity.quantity.toString()
    }

    private fun incrementQuantity() {
        serviceQuantity.quantity++
        basketListener?.onServiceQuantityChange(serviceQuantity, position)
//        basketItemQuantity.text = serviceQuantity.quantity.toString()
    }

    private fun decrementQuantity() {
        serviceQuantity.quantity--
        if (serviceQuantity.quantity < 0) serviceQuantity.quantity = 0
        basketListener?.onServiceQuantityChange(serviceQuantity, position)
//        basketItemQuantity.text = serviceQuantity.quantity.toString()
    }
}