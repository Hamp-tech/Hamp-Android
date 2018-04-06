package com.hamp.mvvm.basket

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.Service
import com.hamp.extensions.loadImg
import kotlinx.android.synthetic.main.basket_service_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class BasketServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var service: Service
    private var position: Int = -1

    private var basketListener: BasketListener? = null

    interface BasketListener {
        fun onServiceQuantityChange(service: Service, index: Int)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.basket_service_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        basketPlus.onClick { incrementQuantity() }
        basketMinus.onClick { decrementQuantity() }
    }

    fun bind(service: Service, position: Int, basketListener: BasketListener) {
        this.service = service
        this.position = position
        this.basketListener = basketListener

        basketItemImage.loadImg(service.hampService.image)
        basketItemName.text = service.hampService.name
        basketItemQuantity.text = service.quantity.toString()
    }

    private fun incrementQuantity() {
        service.quantity++
        basketListener?.onServiceQuantityChange(service, position)
//        basketItemQuantity.text = serviceQuantity.quantity.toString()
    }

    private fun decrementQuantity() {
        service.quantity--
        if (service.quantity < 0) service.quantity = 0
        basketListener?.onServiceQuantityChange(service, position)
//        basketItemQuantity.text = serviceQuantity.quantity.toString()
    }
}