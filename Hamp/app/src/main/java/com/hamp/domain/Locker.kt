package com.hamp.domain

/**
 * Created by galo1100 on 29/6/17.
 */
data class Locker(
        val lockerID: String,
        val secretKey: String,
        val booked: String,
        val firebaseID: String = ""
)