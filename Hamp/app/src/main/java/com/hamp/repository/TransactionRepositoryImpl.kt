package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.domain.Transaction
import javax.inject.Inject
import javax.inject.Singleton

class TransactionRepositoryImpl @Inject constructor(
        val api: RestApi
) : TransactionRepository {

    override fun createTransaction(transaction: Transaction, userId: String) =
            api.createTransaction(transaction, userId)

}