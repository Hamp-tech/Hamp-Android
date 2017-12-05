package com.hamp.domain

data class User(
        val name: String,
        val surname: String,
        val mail: String,
        val phone: String,
        val birthday: String,
        val gender: String,
        val tokenFCM: String,
        val language: String,
        val os: String,
        val signupDate: String = "",
        val stripe: StripeUser = StripeUser(),
        val lastActivityDate: String = "",
        val unsubscribed: Boolean = false,
        val firebaseID: String = ""
)

data class StripeUser(val costumerID: String = "")