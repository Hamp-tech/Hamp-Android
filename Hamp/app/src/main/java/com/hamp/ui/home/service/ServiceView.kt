package com.hamp.ui.home.service

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.Service
import com.hamp.extension.loadImg
import kotlinx.android.synthetic.main.service_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class ServiceView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit private var service: Service
    var quantity = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.service_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        plus.onClick { modifyQuantity { quantity++ } }
        minus.onClick { modifyQuantity { quantity-- } }
    }

    fun bind(service: Service) {
        this.service = service

        name.text = service.name
        image.loadImg(service.image)
        quantityValue.text = quantity.toString()
    }

    private fun modifyQuantity(action: () -> Int) {
        action()
        if (quantity < 0) quantity = 0
        quantityValue.text = quantity.toString()
    }
}