package com.hamp.mvvm.home.service

interface ServiceViewQuantityListener {
    enum class Operation {
        ADD, SUBTRACT
    }

    fun onQuantityChange(operation: Operation)
}