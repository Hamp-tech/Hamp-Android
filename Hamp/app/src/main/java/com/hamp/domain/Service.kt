package com.hamp.domain

class Service(
        val name: String,
        val image: Int,
        val description: String,
        val price: Float,
        var quantity: Int = 0
)