package com.hamp.mvvm.home.service

import android.arch.lifecycle.MutableLiveData
import com.hamp.common.BaseViewModel
import com.hamp.domain.Basket
import com.hamp.domain.Service
import com.hamp.repository.ServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ServiceViewModel @Inject constructor(
        repository: ServiceRepository
) : BaseViewModel() {

    var basket = MutableLiveData<Basket>()
    var totalServices = MutableLiveData<Int>()

    init {
        repository.loadServices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = { basket.value = Basket(it.toMutableList()) },
                        onError = { }
                )
    }

    fun addServiceToBasket(service: Service) {
        val basketService = basket.services.find { it.hampService.id == service.hampService.id }
        val index = basket.services.indexOf(basketService)

        if (index != -1) basket.services[index] = service

        calculateTotalBasket()
    }

    fun subtractServiceToBasket(service: Service) {
        val basketService = basket.services.find { it.hampService.id == service.hampService.id }
        val index = basket.services.indexOf(basketService)

        if (index != -1) basket.services[index] = service

        calculateTotalBasket()
    }

    fun modifyServiceQuantity(service: Service) {
        val basketService = basket.services.find { it.hampService.id == service.hampService.id }
        val index = basket.services.indexOf(basketService)

        if (index != -1) basket.services[index] = service
        calculateTotalBasket()
    }

    fun replaceBasket(basket: Basket) {
        this.basket = basket
        calculateTotalBasket()
    }

    private fun calculateTotalBasket() {
        totalServices.value = basket.services.filter { it.quantity != 0 }.sumBy { it.quantity }
    }
}