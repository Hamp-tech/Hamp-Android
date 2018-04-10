package com.hamp.domain.response

import com.hamp.domain.Transaction

data class BookingResponse(
        val code: Int,
        val message: String = "",
        val data: Transaction
)