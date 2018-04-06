package com.hamp.mvvm.home.service

import com.hamp.domain.Service

interface ServiceViewQuantityListener {
    fun onQuantityChange(service: Service)
}