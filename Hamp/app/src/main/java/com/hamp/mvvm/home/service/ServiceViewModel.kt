package com.hamp.mvvm.home.service

import android.arch.lifecycle.MutableLiveData
import com.hamp.R
import com.hamp.common.BaseViewModel
import com.hamp.common.NetworkViewState
import com.hamp.domain.Basket
import com.hamp.domain.Service
import com.hamp.repository.ServiceRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ServiceViewModel @Inject constructor(
        val repository: ServiceRepositoryImpl
) : BaseViewModel() {

    var basket = Basket()
    val basketStatus = MutableLiveData<NetworkViewState>()
    val totalServices = MutableLiveData<Int>()

    fun loadServices() {
        repository.loadServices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { basketStatus.value = NetworkViewState.Loading() }
                .subscribeBy(
                        onSuccess = {
                            basket = Basket(it.toMutableList())
                            basketStatus.value = NetworkViewState.Success(basket)
                            calculateTotalBasket()
                        },
                        onError = { basketStatus.value = NetworkViewState.Error(R.string.generic_error) }
                )
    }

    fun modifyServiceQuantity(service: Service) {
        val serviceForModify = basket.services.find { it.hampService.id == service.hampService.id }
        val index = basket.services.indexOf(serviceForModify)
        if (index != -1) basket.services[index] = service

        repository.saveServiceQuantity(service)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = { calculateTotalBasket() },
                        onError = { calculateTotalBasket() }
                )
    }

    private fun calculateTotalBasket() {
        totalServices.value = basket.services.filter { it.amount != 0 }.sumBy { it.amount }
    }
}