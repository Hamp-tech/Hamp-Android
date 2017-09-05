package com.hamp.ui.home.service.detail

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.domain.Service
import com.hamp.extension.loadImg
import kotlinx.android.synthetic.main.activity_service_detail.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@BaseActivity.Animation(BaseActivity.PUSH)
class ServiceDetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_detail)

        val service = intent.extras.getParcelable<Service>("service")
        val quantity = intent.extras.getInt("quantity")

        serviceTitle.text = service.name

        image.loadImg(service.image)
        description.text = service.description
        quantityValue.text = quantity.toString()

        back.onClick { onBackPressed() }
    }
}
