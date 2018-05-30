package com.hamp.repository

import com.hamp.domain.Card
import com.hamp.domain.response.CardResponse
import io.reactivex.Single

/**
 * Repository to handle cards
 */
interface CardRepository {

    /**
     * Add card to a user
     *
     * @param card Card with the information required
     * @param userId user Id
     */
    fun addCard(card: Card, userId: String): Single<CardResponse>
}