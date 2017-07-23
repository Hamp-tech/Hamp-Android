package com.galodb.hamp.domain.response

import com.galodb.hamp.domain.Order

/**
 * Created by galo1100 on 29/6/17.
 */
data class BookingRequest(
        val cardID: String,
        val order: Order
)