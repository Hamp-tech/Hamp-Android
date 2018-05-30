package com.hamp.domain.response

import com.hamp.data.db.domain.User

data class UserResponse(
        val code: Int,
        val message: String = "",
        val data: User
)