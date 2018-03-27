package com.hamp.mvvm.home.service

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.di.Injectable
import com.hamp.domain.Service
import com.hamp.extensions.getViewModel
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.home.service.ServiceViewQuantityListener.Operation
import com.hamp.mvvm.home.service.detail.ServiceDetailActivity
import kotlinx.android.synthetic.main.fragment_service.*
import javax.inject.Inject

class ServiceFragment : BaseFragment(), Injectable,
        ServicesAdapter.ClickServiceListener, ServiceViewQuantityListener {

    private val serviceDetailRequest = 1

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var serviceViewModel: ServiceViewModel

    private var currentItemClickPosition = 0

    private var basketCounter = 0

    companion object {
        fun create() = ServiceFragment().apply { }
    }

    private val homeActivity: HomeActivity
        get() = activity as HomeActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_service, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewModel()
        setUpRecyclerServices()
    }

    private fun setUpViewModel() {
        serviceViewModel = getViewModel(this, viewModelFactory)
    }

    private fun setUpRecyclerServices() {
        rvServices.setHasFixedSize(true)
        rvServices.layoutManager = GridLayoutManager(context, 2)
//        rvServices.addItemDecoration(SpaceItemDecoration(4.px))

        rvServices.adapter = ServicesAdapter(context, serviceViewModel.servicesList,
                this, this)
    }

    override fun onServiceClick(service: Service, quantity: Int, position: Int) {
        currentItemClickPosition = position
        val intent = Intent(context, ServiceDetailActivity::class.java)
        intent.putExtra("service", service)
        intent.putExtra("quantity", quantity)
        startActivityForResult(intent, serviceDetailRequest)
    }

    override fun onQuantityChange(operation: Operation) {
        when (operation) {
            Operation.ADD -> modifyBasketCounter { basketCounter++ }
            Operation.SUBTRACT -> modifyBasketCounter { basketCounter-- }
        }
    }

    private fun modifyBasketCounter(action: () -> Unit) {
        action()
        if (basketCounter < 0) basketCounter = 0
        homeActivity.modifyBasketCounter(basketCounter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == serviceDetailRequest) {
            if (resultCode == RESULT_OK && data is Intent) {
                val resultQuantity = data.getIntExtra("quantity", 0)
                val itemView = rvServices.findViewHolderForAdapterPosition(currentItemClickPosition).itemView as ServiceView

                val diffQuantity = resultQuantity - itemView.quantity
                modifyBasketCounter { basketCounter += diffQuantity }

                itemView.modifyQuantity(resultQuantity)
            }
        }
    }
}