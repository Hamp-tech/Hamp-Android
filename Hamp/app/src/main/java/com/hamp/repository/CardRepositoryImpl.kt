package com.hamp.repository

import com.hamp.data.api.RestApi
import com.hamp.domain.Card
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
        val api: RestApi
) : CardRepository {

    override fun addCard(card: Card, userId: String) = api.addCard(card, userId)

}