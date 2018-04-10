package com.hamp.domain

data class Transaction(
        val creditCard: Card,
        val userID: String? = null,
        val identifier: String? = null,
        val pickUpDate: String? = null,
        val booking: Book
)
