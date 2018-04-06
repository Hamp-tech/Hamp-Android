package com.hamp.mvvm.home.service.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.domain.Service
import com.hamp.extensions.loadImg
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
        quantityValue.text = service.quantity.toString()

        plus.onClick { modifyQuantity { service.quantity++ } }
        minus.onClick { modifyQuantity { service.quantity-- } }

        back.onClick { onBackPressed() }
    }

    private fun modifyQuantity(action: () -> Unit) {
        action()
        if (service.quantity < 0) service.quantity = 0
        quantityValue.text = service.quantity.toString()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("service", service)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
