package com.hamp.mvvm.basket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.domain.Basket
import com.hamp.db.domain.ServiceQuantity
import com.hamp.domain.Service
import kotlinx.android.synthetic.main.activity_basket.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class BasketActivity : BaseActivity(), BasketServiceView.BasketListener {

    private lateinit var basket: Basket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basket = intent.extras.getParcelable("basket")

        basketServices.setHasFixedSize(true)
        basketServices.layoutManager = LinearLayoutManager(this)
        basketServices.adapter = BasketAdapter(this, basket.services, this)

//        basketVisaButton.onClick { }
        back.onClick { onBackPressed() }
    }

    override fun onServiceQuantityChange(service: Service, index: Int) {
        basket.services[index] = service
        basketServices.adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("basket", basket)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
