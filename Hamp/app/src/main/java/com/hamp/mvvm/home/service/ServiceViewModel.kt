package com.hamp.mvvm.home.service

import android.arch.lifecycle.MutableLiveData
import com.hamp.common.BaseViewModel
import com.hamp.domain.Basket
import com.hamp.domain.Service
import com.hamp.domain.ServiceQuantity
import com.hamp.extensions.notNull
import com.hamp.repository.ServiceRepository
import javax.inject.Inject

class ServiceViewModel @Inject constructor(
        repository: ServiceRepository
) : BaseViewModel() {

    var servicesList: List<Service> = repository.loadServices()
    var basket = Basket()
    var totalServices = MutableLiveData<Int>()

    fun addServiceToBasket(service: Service) {
        val basketService = basket.services.find { it.service.id == service.id }
        val index = basket.services.indexOf(basketService)

        if (index != -1) basket.services[index].quantity++
        else basket.services.add(ServiceQuantity(service, 1))

        totalServices.value = basket.services.sumBy { it.quantity }
    }

    fun subtractServiceToBasket(service: Service) {
        val basketService = basket.services.find { it.service.id == service.id }
        val index = basket.services.indexOf(basketService)

        if (index != -1) {
            basket.services[index].quantity--
            if (basket.services[index].quantity <= 0) basket.services.removeAt(index)
        } else basket.services.add(ServiceQuantity(service, 1))

        totalServices.value = basket.services.sumBy { it.quantity }
    }

    fun modifyServiceQuantity(service: Service?, resultQuantity: Int) {
        val basketService = basket.services.find { it.service.id == service?.id }
        val index = basket.services.indexOf(basketService)

        if (index != -1) {
            if (resultQuantity <= 0) basket.services.removeAt(index)
            else basket.services[index].quantity = resultQuantity
        } else service.notNull { basket.services.add(ServiceQuantity(it, resultQuantity)) }

        totalServices.value = basket.services.sumBy { it.quantity }
    }
}