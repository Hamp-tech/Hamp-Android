package com.galodb.hamp.api

import com.galodb.hamp.domain.response.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface HampApi {

    //Users
    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): Observable<UserResponse>
}