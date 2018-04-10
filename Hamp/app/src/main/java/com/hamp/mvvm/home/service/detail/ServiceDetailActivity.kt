package com.hamp.mvvm.home.service.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.domain.Service
import com.hamp.extensions.loadImg
import com.hamp.extensions.round
import kotlinx.android.synthetic.main.activity_service_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class ServiceDetailActivity : BaseActivity() {

    private lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        service = intent.extras.getParcelable("service")

        serviceTitle.text = service.hampService.name

        image.loadImg(service.hampService.image)
        description.text = service.hampService.description
        quantityValue.text = service.amount.toString()
        price.text = "${service.hampService.price.toDouble().round()}€"

        plus.onClick { modifyQuantity { service.amount++ } }
        minus.onClick { modifyQuantity { service.amount-- } }

        back.onClick { onBackPressed() }
    }

    private fun modifyQuantity(action: () -> Unit) {
        action()
        if (service.amount < 0) service.amount = 0
        quantityValue.text = service.amount.toString()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("service", service)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
