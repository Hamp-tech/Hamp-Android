package com.hamp.domain

data class Locker(
        val code: String,
        val number: Int,
        val identifier: String,
        val available: Boolean,
        val capacity: String
)