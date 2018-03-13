package com.hamp.domain.response

import com.hamp.domain.Card

data class CardResponse(
        val code: Int,
        val message: String = "",
        val data: Card
)