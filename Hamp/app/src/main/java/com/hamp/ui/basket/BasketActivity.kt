package com.hamp.ui.basket

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hamp.R
import com.hamp.common.BaseActivity
import kotlinx.android.synthetic.main.activity_basket.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class BasketActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        basketServices.setHasFixedSize(true)
        basketServices.layoutManager = LinearLayoutManager(this)
//        basketServices.adapter = BasketServiceView()

        back.onClick { onBackPressed() }
    }
}
