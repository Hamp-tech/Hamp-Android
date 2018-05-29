package com.hamp.mvvm.paymentMethod

import android.content.Context
import android.support.v7.widget.AppCompatRadioButton
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import com.hamp.domain.Card
import kotlinx.android.synthetic.main.item_payment_method.view.*

class PaymentMethodView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var card: Card? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.item_payment_method, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun bind(card: Card?) {
        this.card = card

        if (card != null) {
            paymentMethodItemNumber.text = "XXX XXXX XXXX ${card.number}"
        } else {
            paymentMethodItemNumber.text = context.getString(R.string.add_user_card)
            paymentMethodItemRadio.visibility = View.GONE
        }
    }

    fun getRadioButton(): AppCompatRadioButton = paymentMethodItemRadio
}