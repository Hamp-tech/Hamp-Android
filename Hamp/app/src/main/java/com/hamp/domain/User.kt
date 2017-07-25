package com.hamp.domain

/**
 * Created by galo1100 on 29/6/17.
 */
class User(
        val name: String,
        val surname: String,
        val mail: String,
        val phone: String,
        val birthday: String,
        val gender: String,
        val tokenFCM: String,
        val language: String,
        val os: String,
        val signupDate: String,
        val lastActivityDate: String,
        val unsubscribed: Boolean,
        val firebaseID: String = ""
)