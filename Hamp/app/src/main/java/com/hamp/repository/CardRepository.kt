package com.hamp.repository

import com.hamp.api.RestApi
import com.hamp.domain.Card
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepository @Inject constructor(
        val api: RestApi
) {

    fun addCard(card: Card, userId: String) = api.addCard(card, userId)

}