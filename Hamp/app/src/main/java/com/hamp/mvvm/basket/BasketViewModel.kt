package com.hamp.mvvm.basket

import android.arch.lifecycle.MutableLiveData
import com.hamp.common.BaseViewModel
import com.hamp.domain.Basket
import com.hamp.domain.Service
import com.hamp.extensions.round
import com.hamp.repository.ServiceRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BasketViewModel @Inject constructor(
        val repository: ServiceRepositoryImpl
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val basket = MutableLiveData<Basket>()
    val basketValue = MutableLiveData<Double>()

    init {
        getServices()
    }

    private fun getServices() {
        repository.getServices()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.value = true }
                .doAfterTerminate { loading.value = false }
                .subscribeBy(
                        onSuccess = {
                            basket.value = Basket(it.toMutableList())
                            calculateBasketValue()
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
                        onComplete = { calculateBasketValue() },
                        onError = {}
                )
    }

    fun getFilterBasket(): List<Service> {
        return basket.value?.services?.filter { it.amount != 0 } ?: emptyList()
    }

    private fun calculateBasketValue() {
        basketValue.value = basket.value?.services
                ?.filter { it.amount != 0 }
                ?.sumByDouble { (it.hampService.price * it.amount).toDouble() }
                ?.round()
    }
}