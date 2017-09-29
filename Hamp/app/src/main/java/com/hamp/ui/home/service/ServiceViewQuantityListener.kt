package com.hamp.ui.home.service

interface ServiceViewQuantityListener {
    enum class Operation {
        ADD, SUBTRACT
    }

    fun onQuantityChange(operation: Operation)
}