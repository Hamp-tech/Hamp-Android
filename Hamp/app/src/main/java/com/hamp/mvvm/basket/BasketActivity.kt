package com.hamp.mvvm.basket

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.di.Injectable
import com.hamp.domain.Service
import com.hamp.extensions.getViewModel
import com.hamp.extensions.notNull
import com.hamp.extensions.observe
import com.hamp.mvvm.paymentMethod.PaymentMethodActivity
import kotlinx.android.synthetic.main.activity_basket.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import javax.inject.Inject

@BaseActivity.Animation(BaseActivity.PUSH)
class BasketActivity : BaseActivity(), Injectable, BasketServiceView.BasketListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var basketViewModel: BasketViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        setUpViewModel()
        setUpRecyclerServices()

        basketVisaButton.onClick { goToPaymentMethod() }
        back.onClick { onBackPressed() }
    }

    private fun setUpViewModel() {
        basketViewModel = getViewModel(viewModelFactory)
        basketViewModel.loading.observe(this, { it.notNull { showLoading(it) } })
        basketViewModel.basket.observe(this, { it.notNull { refreshBasket() } })
        basketViewModel.basketValue.observe(this, { it.notNull { refreshBasketValue(it) } })
    }

    private fun setUpRecyclerServices() {
        basketServices.setHasFixedSize(true)
        basketServices.layoutManager = LinearLayoutManager(this)
    }

    private fun refreshBasket() {
        val services = basketViewModel.getFilterBasket()
        basketServices.adapter = BasketAdapter(this, services, this)
    }

    private fun refreshBasketValue(total: Double) {
        basketValue.text = "€$total"
    }

    private fun goToPaymentMethod() {
        startActivity<PaymentMethodActivity>()
    }

    override fun onServiceQuantityChange(service: Service, index: Int) {
        basketViewModel.modifyServiceQuantity(service)
    }

    private fun showLoading(show: Boolean) {
        if (show) loadingView.visibility = View.VISIBLE
        else loadingView.visibility = View.GONE
    }
}
