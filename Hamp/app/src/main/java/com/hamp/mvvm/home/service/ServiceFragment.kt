package com.hamp.mvvm.home.service

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.di.Injectable
import com.hamp.domain.Basket
import com.hamp.domain.Service
import com.hamp.extensions.getViewModel
import com.hamp.extensions.observe
import com.hamp.mvvm.basket.BasketActivity
import com.hamp.mvvm.home.HomeActivity
import com.hamp.mvvm.home.service.detail.ServiceDetailActivity
import kotlinx.android.synthetic.main.fragment_service.*
import org.jetbrains.anko.support.v4.startActivityForResult
import javax.inject.Inject

class ServiceFragment : BaseFragment(), Injectable,
        ServicesAdapter.ClickServiceListener, ServiceViewQuantityListener {

    private val serviceDetailRequest = 1
    private val basketRequest = 2

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var serviceViewModel: ServiceViewModel

    private var currentItemClickPosition = 0

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
        serviceViewModel.loading.observe(this, { it?.let { showLoading(it) } })
        serviceViewModel.basket.observe(this, { it?.let { refreshServicesList(it) } })
        serviceViewModel.totalServices.observe(this, { it?.let { refreshBasketCounter(it) } })
    }

    private fun setUpRecyclerServices() {
        rvServices.setHasFixedSize(true)
        rvServices.layoutManager = GridLayoutManager(context, 2)
        rvServices.adapter = ServicesAdapter(context, emptyList(), this, this)
    }

    private fun refreshServicesList(basket: Basket) {
        rvServices.adapter = ServicesAdapter(context, basket.services,
                this, this)
    }

    override fun onServiceClick(service: Service, position: Int) {
        currentItemClickPosition = position
        val intent = Intent(context, ServiceDetailActivity::class.java)
        intent.putExtra("service", service)
        homeActivity.startActivityForResult(intent, serviceDetailRequest)
    }

    override fun onQuantityChange(service: Service) {
        serviceViewModel.modifyServiceQuantity(service)
    }

    private fun refreshBasketCounter(totalServices: Int) {
        homeActivity.refreshBasketCounter(totalServices)
    }

    fun goToBasket() {
        startActivityForResult<BasketActivity>(basketRequest,
                "basket" to serviceViewModel.basket)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == basketRequest) {
            if (resultCode == AppCompatActivity.RESULT_OK && data is Intent) {
                val basket = data.getParcelableExtra<Basket>("basket")
                replaceBasket(basket)
            }
        } else if (requestCode == serviceDetailRequest) {
            if (resultCode == Activity.RESULT_OK && data is Intent) {
                val service = data.getParcelableExtra<Service>("service")
                modifyItemView(service)
            }
        }
    }

    private fun modifyItemView(service: Service) {
        val itemView = rvServices.findViewHolderForAdapterPosition(currentItemClickPosition)
                .itemView as ServiceView

        serviceViewModel.modifyServiceQuantity(service)

        itemView.modifyQuantity(service.quantity)
    }

    private fun replaceBasket(basket: Basket) = serviceViewModel.replaceBasket(basket)

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }
}