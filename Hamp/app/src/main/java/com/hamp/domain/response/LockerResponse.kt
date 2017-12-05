package com.hamp.domain.response

import com.hamp.domain.Locker

data class LockerResponse(
        val code: Int,
        val message: String = "",
        val data: Locker
)