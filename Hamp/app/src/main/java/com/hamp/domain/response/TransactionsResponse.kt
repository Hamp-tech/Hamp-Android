package com.hamp.domain.response

import com.hamp.domain.Transaction

/**
 * Created by galo1100 on 29/6/17.
 */
data class TransactionsResponse(
        val code: Int,
        val data: List<Transaction>
)