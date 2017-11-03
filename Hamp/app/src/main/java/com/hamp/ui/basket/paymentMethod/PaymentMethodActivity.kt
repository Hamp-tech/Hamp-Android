package com.hamp.ui.basket.paymentMethod

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity

@BaseActivity.Animation(BaseActivity.PUSH)
class PaymentMethodActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_method)
    }
}
