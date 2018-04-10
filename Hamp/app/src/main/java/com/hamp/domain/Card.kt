package com.hamp.domain

data class Card(
        val id: String? = null,
        val number: String?,
        val exp_month: Int?,
        val exp_year: Int?,
        val cvc: String?
)