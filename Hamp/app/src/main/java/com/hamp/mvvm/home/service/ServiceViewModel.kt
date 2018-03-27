package com.hamp.mvvm.home.service

import com.hamp.common.BaseViewModel
import com.hamp.domain.Service
import com.hamp.repository.ServiceRepository
import javax.inject.Inject

class ServiceViewModel @Inject constructor(
        repository: ServiceRepository
) : BaseViewModel() {

    var servicesList: List<Service> = repository.loadServices()

}