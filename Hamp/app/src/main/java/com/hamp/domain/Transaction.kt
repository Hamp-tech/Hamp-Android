package com.hamp.domain

data class Transaction(
        val id: String,
        val userID: String,
        val cardID: String,
        val order: Order
)