package com.galodb.hamp.domain.response

import com.galodb.hamp.domain.Locker

/**
 * Created by galo1100 on 29/6/17.
 */
data class LockerResponse(
        val code: Int,
        val message: String = "",
        val data: Locker
)