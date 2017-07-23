package com.galodb.hamp.domain.response

import com.galodb.hamp.domain.Locker
import com.galodb.hamp.domain.Transaction

/**
 * Created by galo1100 on 29/6/17.
 */
data class BookingResponse(
        val code: Int,
        val message: String = "",
        val data: WashService
)

data class WashService(
        val transaction: Transaction,
        val locker: Locker
)