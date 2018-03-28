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
    private var quantity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        service = intent.extras.getParcelable("service")
        quantity = intent.extras.getInt("quantity")

        serviceTitle.text = service.name

        image.loadImg(service.image)
        description.text = service.description
        quantityValue.text = quantity.toString()

        plus.onClick { modifyQuantity { quantity++ } }
        minus.onClick { modifyQuantity { quantity-- } }

        back.onClick { onBackPressed() }
    }

    private fun modifyQuantity(action: () -> Unit) {
        action()
        if (quantity < 0) quantity = 0
        quantityValue.text = quantity.toString()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("quantity", quantity)
        returnIntent.putExtra("service", service)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
