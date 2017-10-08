package com.hamp.firebase

interface IMapper<in From, out To> {
    fun map(from: From): To
}
