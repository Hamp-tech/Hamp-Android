package com.hamp.mvvm.home.service

import com.hamp.domain.Service

interface ServiceViewQuantityListener {
    enum class Operation {
        ADD, SUBTRACT
    }

    fun onQuantityChange(service: Service, operation: Operation)
}