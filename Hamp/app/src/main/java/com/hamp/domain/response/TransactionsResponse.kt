package com.hamp.domain.response

import com.hamp.domain.Transaction

data class TransactionsResponse(
        val code: Int,
        val data: List<Transaction>
)