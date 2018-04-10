package com.hamp.domain

import com.hamp.db.domain.ServiceQuantity

data class Book(
        val basket: List<ServiceQuantity>,
        val price: Double,
        val point: Point,
        val pickUpTime: String,
        val pickUpLockers: ArrayList<Locker>? = null
)

data class Point(
        val identifier: String = "1",
        val city: String? = null,
        val cp: String? = null,
        val address: String? = null,
        val location: Location? = null
)

data class Location(
        val name: String,
        val longitude: Int,
        val latitude: Int
)