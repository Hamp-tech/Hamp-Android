package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.domain.Transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
        val api: RestApi
) {

    fun createTransaction(transaction: Transaction, userId: String) = api.createTransaction(transaction, userId)

}