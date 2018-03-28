package com.hamp.mvvm.basket

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.domain.Basket
import kotlinx.android.synthetic.main.activity_basket.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class BasketActivity : BaseActivity() {

    private lateinit var basket: Basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basket = intent.extras.getParcelable("basket")

        basketServices.setHasFixedSize(true)
        basketServices.layoutManager = LinearLayoutManager(this)
        basketServices.adapter = BasketAdapter(this, basket.services)

//        basketVisaButton.onClick { }
        back.onClick { onBackPressed() }
    }
}
