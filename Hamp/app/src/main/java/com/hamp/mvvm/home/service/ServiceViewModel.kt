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

    fun loadServices() {
        repository.loadServices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate { loading.value = false }
                .subscribeBy(
                        onSuccess = {
                            basket.value = Basket(it.toMutableList())
                            calculateTotalBasket()
                        },
                        onError = { }
                )
    }

    fun modifyServiceQuantity(service: Service) {
        val serviceForModify = basket.value?.services?.find { it.hampService.id == service.hampService.id }
        val index = basket.value?.services?.indexOf(serviceForModify) ?: -1
        if (index != -1) basket.value?.services?.set(index, service)

        repository.saveServiceQuantity(service)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = { calculateTotalBasket() },
                        onError = {}
                )
    }

    private fun calculateTotalBasket() {
        totalServices.value = basket.value?.services?.filter { it.amount != 0 }?.sumBy { it.amount }
    }
}