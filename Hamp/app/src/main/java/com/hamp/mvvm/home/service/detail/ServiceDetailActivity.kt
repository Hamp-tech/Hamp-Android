package com.hamp.mvvm.home.service.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.db.domain.ServiceQuantity
import com.hamp.extensions.loadImg
import kotlinx.android.synthetic.main.activity_service_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class ServiceDetailActivity : BaseActivity() {

    private lateinit var serviceQuantity: ServiceQuantity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        serviceQuantity = intent.extras.getParcelable("service")

        serviceTitle.text = serviceQuantity.service.name

        image.loadImg(serviceQuantity.service.image)
        description.text = serviceQuantity.service.description
        quantityValue.text = serviceQuantity.quantity.toString()

        plus.onClick { modifyQuantity { serviceQuantity.quantity++ } }
        minus.onClick { modifyQuantity { serviceQuantity.quantity-- } }

        back.onClick { onBackPressed() }
    }

    private fun modifyQuantity(action: () -> Unit) {
        action()
        if (serviceQuantity.quantity < 0) serviceQuantity.quantity = 0
        quantityValue.text = serviceQuantity.quantity.toString()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra("service", serviceQuantity)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}
