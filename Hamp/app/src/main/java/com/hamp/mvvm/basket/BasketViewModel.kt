package com.hamp.mvvm.basket

import android.arch.lifecycle.MutableLiveData
import com.hamp.common.BaseViewModel
import com.hamp.domain.Basket
import com.hamp.domain.Service
import com.hamp.repository.ServiceRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BasketViewModel @Inject constructor(
        val repository: ServiceRepository
) : BaseViewModel() {

    val loading = MutableLiveData<Boolean>()
    val basket = MutableLiveData<Basket>()

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
                        onSuccess = { basket.value = Basket(it.toMutableList()) },
                        onError = { }
                )
    }

    fun modifyServiceQuantity(service: Service) {
        val index = basket.value?.services?.indexOf(service) ?: -1
        if (index != -1) basket.value?.services?.set(index, service)

        repository.saveServiceQuantity(service)
    }

    fun getFilterBasket(): List<Service> {
        return basket.value?.services?.filter { it.quantity != 0 } ?: emptyList()
    }
}