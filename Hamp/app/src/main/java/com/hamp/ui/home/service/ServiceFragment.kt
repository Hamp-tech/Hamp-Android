package com.hamp.ui.home.service

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.domain.Service
import com.hamp.ui.home.HomeActivity
import com.hamp.ui.home.service.detail.ServiceDetailActivity
import kotlinx.android.synthetic.main.fragment_service.*


class ServiceFragment : BaseFragment(), ServicesAdapter.ClickServiceListener {

    private val SERVICE_DETAIL_REQUEST = 1

    lateinit private var viewModel: ServiceViewModel

    companion object {
        fun create() = ServiceFragment().apply { }
    }

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_service, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ServiceViewModel::class.java)
        viewModel.init()

        rvServices.layoutManager = GridLayoutManager(context, 2)
//        rvServices.addItemDecoration(SpaceItemDecoration(4.px))

        rvServices.adapter = ServicesAdapter(context, viewModel.servicesList, this)
    }

    override fun onServiceClick(service: Service, quantity: Int) {
        val intent = Intent(context, ServiceDetailActivity::class.java)
        intent.putExtra("service", service)
        intent.putExtra("quantity", quantity)
        startActivityForResult(intent, SERVICE_DETAIL_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SERVICE_DETAIL_REQUEST) {
            if (resultCode == RESULT_OK) {
                
            }
        }
    }
}