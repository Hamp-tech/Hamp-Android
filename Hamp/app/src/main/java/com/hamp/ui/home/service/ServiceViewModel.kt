package com.hamp.ui.home.service

import android.arch.lifecycle.ViewModel
import com.hamp.domain.Service
import com.hamp.repository.ServiceRepository

class ServiceViewModel : ViewModel() {

    private val serviceRepository = ServiceRepository()
    lateinit var servicesList: List<Service>

    fun init() {
        servicesList = serviceRepository.loadServices()
    }
}