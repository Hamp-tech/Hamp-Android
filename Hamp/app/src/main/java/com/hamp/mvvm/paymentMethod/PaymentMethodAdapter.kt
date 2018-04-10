package com.hamp.mvvm.paymentMethod

import android.content.Context
import android.support.v7.widget.AppCompatRadioButton
import android.view.View
import android.view.ViewGroup
import com.hamp.common.RecyclerViewAdapterBase
import com.hamp.common.ViewWrapper
import com.hamp.domain.Card
import com.hamp.extensions.notNull
import org.jetbrains.anko.sdk25.coroutines.onClick

class PaymentMethodAdapter(val context: Context, override var items: List<Card?>,
                           private val listener: PaymentMethodListener)
    : RecyclerViewAdapterBase<Card?, PaymentMethodView>(items) {

    private var lastCheckedRB: AppCompatRadioButton? = null

    interface PaymentMethodListener {
        fun creditCardListener(card: Card)
        fun addCardClick()
    }

    override fun onCreateItemView(parent: ViewGroup, viewType: Int) = PaymentMethodView(context)

    override fun onBindViewHolder(holder: ViewWrapper<PaymentMethodView>, position: Int) {
        holder.view.bind(items[position])
        holder.view.getRadioButton().setOnCheckedChangeListener { _, _ ->
            lastCheckedRB?.isChecked = false
            lastCheckedRB = holder.view.getRadioButton()

            items[position].notNull { listener.creditCardListener(it) }
        }

        holder.view.onClick {
            if (holder.view.getRadioButton().visibility == View.VISIBLE) listener.addCardClick()
        }
    }
}