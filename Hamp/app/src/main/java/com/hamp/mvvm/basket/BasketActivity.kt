package com.hamp.mvvm.basket

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
        basketServices.adapter = BasketAdapter(this, listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13),
                listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13))

//        basketVisaButton.onClick { }
        back.onClick { onBackPressed() }
    }
}
