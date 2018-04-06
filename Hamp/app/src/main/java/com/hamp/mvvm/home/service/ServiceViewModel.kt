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
        val repository: ServiceRepository
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val basket = MutableLiveData<Basket>()
    val totalServices = MutableLiveData<Int>()

    init {
        loadServices()
    }

    private fun loadServices() {
        repository.loadServices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .subscribeBy(
                        onComplete = { getServices() },
                        onError = { }
                )
    }

    private fun getServices() {
        disposables.add(repository.getServices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { loading.value = false }
                .subscribeBy(
                        onSuccess = { basket.value = Basket(it.toMutableList()) },
                        onError = { }
                ))
    }

    fun modifyServiceQuantity(service: Service) {
        val index = basket.value?.services?.indexOf(service) ?: -1
        if (index != -1) basket.value?.services?.set(index, service)

        calculateTotalBasket()
    }

    fun replaceBasket(basket: Basket) {
        this.basket.value = basket
        calculateTotalBasket()
    }

    private fun calculateTotalBasket() {
        totalServices.value = basket.value?.services?.filter { it.quantity != 0 }?.sumBy { it.quantity }
    }
}