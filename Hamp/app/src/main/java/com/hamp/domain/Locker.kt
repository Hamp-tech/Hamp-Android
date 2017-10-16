package com.hamp.domain

data class Locker(
        val lockerID: String,
        val secretKey: String,
        val booked: String,
        val firebaseID: String = ""
)