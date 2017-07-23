package com.galodb.hamp.domain

/**
 * Created by galo1100 on 29/6/17.
 */
data class Transaction(
        val id: String,
        val userID: String,
        val cardID: String,
        val order: Order
)