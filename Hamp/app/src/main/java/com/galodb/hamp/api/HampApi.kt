package com.galodb.hamp.api

import com.galodb.hamp.domain.response.*
import io.reactivex.Observable
import retrofit2.http.*

interface HampApi {

    //Users
    @GET("/users/{id}")
    fun getUser(@Path("id") id: String): Observable<UserResponse>

    @POST("/users/{id}")
    fun createUserWithID(@Path("id") id: String,
                         @Field("name") name: String,
                         @Field("surname") surname: String,
                         @Field("email") email: String,
                         @Field("phone") phone: String,
                         @Field("birthday") birthday: String,
                         @Field("gender") gender: String,
                         @Field("tokenFCM") tokenFCM: String,
                         @Field("language") language: String,
                         @Field("os") os: String): Observable<UserResponse>

    @POST("/users/{id}")
    fun createUserWithoutID(@Path("id") id: String,
                            @Field("name") name: String,
                            @Field("surname") surname: String,
                            @Field("email") email: String,
                            @Field("phone") phone: String,
                            @Field("birthday") birthday: String,
                            @Field("gender") gender: String,
                            @Field("tokenFCM") tokenFCM: String,
                            @Field("language") language: String,
                            @Field("os") os: String): Observable<UserResponse>

    @PUT("/users/{id}")
    fun updateUser(@Path("id") id: String,
                   @FieldMap fields: Map<String, String>): Observable<GenericResponse>

    @DELETE("/users/{id}")
    fun unsubscribe(@Path("id") id: String): Observable<GenericResponse>

    @DELETE("/users/{id}/subscribe")
    fun subscribe(@Path("id") id: String): Observable<GenericResponse>


    //Lockers
    @GET("/lockers/{id}")
    fun getLocker(@Path("id") id: String): Observable<LockerResponse>

    @POST("/lockers/")
    fun createLocker(@Field("secretKey") secretKey: String,
                     @Field("lockerID") lockerID: String): Observable<LockerResponse>

    @PUT("/lockers/{id}")
    fun updateLocker(@Path("id") id: String,
                     @FieldMap fields: Map<String, String>): Observable<GenericResponse>

    //Credit card
    @POST("/users/{id}/cards/")
    fun createCreditCard(@Path("id") id: String,
                         @Field("number") number: Int,
                         @Field("month") month: Int,
                         @Field("year") year: Int,
                         @Field("cvv") cvv: Int): Observable<GenericResponse>

    @DELETE("/users/{id}/cards/{card_id}")
    fun deleteCreditCard(@Path("id") id: String,
                         @Path("card_id") card_id: String): Observable<GenericResponse>

    //Transactions
    @GET("/users/{id}/transactions")
    fun getTransactions(@Path("id") id: String): Observable<TransactionsResponse>

    //Booking
    @POST("/users/{id}/booking")
    fun bookingWashService(@Path("id") id: String,
                           @Body bookingRequest: BookingRequest): Observable<BookingResponse>
}