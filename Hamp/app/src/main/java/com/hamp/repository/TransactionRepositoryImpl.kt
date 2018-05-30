package com.hamp.repository

import com.hamp.data.api.RestApi
import com.hamp.domain.Transaction
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
        val api: RestApi
) : TransactionRepository {

    override fun createTransaction(transaction: Transaction, userId: String) =
            api.createTransaction(transaction, userId)

}