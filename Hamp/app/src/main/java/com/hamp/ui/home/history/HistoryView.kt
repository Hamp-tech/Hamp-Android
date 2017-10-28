package com.hamp.ui.home.history

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R

class HistoryView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

//    private lateinit var service: Service
//    private var serviceViewQuantityListener: ServiceViewQuantityListener? = null
//    var quantity = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.history_item, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun bind() {

    }
}