package com.hamp.repository

import com.hamp.domain.Transaction
import com.hamp.domain.response.BookingResponse
import io.reactivex.Single

/**
 * Repository to handle transactions
 */
interface TransactionRepository {

    /**
     * Create Transaction
     *
     * @param transaction Transaction with the information required
     * @param userId user Id
     */
    fun createTransaction(transaction: Transaction, userId: String): Single<BookingResponse>
}