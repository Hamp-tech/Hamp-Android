package com.galodb.hamp.domain.response

import com.galodb.hamp.domain.User

/**
 * Created by galo1100 on 29/6/17.
 */
data class UserResponse(
        val code: Int,
        val message: String,
        val data: User
)