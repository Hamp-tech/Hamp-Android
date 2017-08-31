package com.hamp.ui.home.service.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.domain.Service
import com.hamp.extension.loadImg
import com.hamp.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_detail_service.*

class ServiceDetailFragment : BaseFragment() {

    companion object {
        fun create(service: Service) = ServiceDetailFragment().apply {
            this.service = service
        }
    }

    lateinit var service: Service

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_detail_service, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image.loadImg(service.image)
        description.text = service.description
        quantity.text = service.quantity.toString()
    }
}