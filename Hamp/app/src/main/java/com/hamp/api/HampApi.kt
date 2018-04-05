package com.hamp.api

import com.hamp.domain.Card
import com.hamp.db.domain.User
import com.hamp.domain.request.SignInRequest
import com.hamp.domain.response.CardResponse
import com.hamp.domain.response.UserResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HampApi {

    // AUTH
    @POST("auth/signup")
    fun signUp(@Body user: User): Single<UserResponse>

    @POST("auth/signin")
    fun signIn(@Body signInRequest: SignInRequest): Single<UserResponse>

    @PUT("users/{userid}")
    fun updateUser(@Body user: User, @Path("userid") userId: String): Single<UserResponse>

    // CARDS
    @POST("users/{userid}/cards")
    fun createCard(@Body card: Card, @Path("userid") userId: String): Single<CardResponse>

//    @POST("users/{id}")
//    fun createUser(@Body user: User): Single<UserResponse>
//
//    @PUT("users/{id}")
//    fun updateUser(@Path("id") id: String,
//                   @FieldMap fields: Map<String, String>): Observable<GenericResponse>
//
//    @DELETE("users/{id}")
//    fun unSubscribe(@Path("id") id: String): Observable<GenericResponse>
//
//    @DELETE("users/{id}/subscribe")
//    fun subscribe(@Path("id") id: String): Observable<GenericResponse>
//
//    //Lockers
//    @GET("lockers/{id}")
//    fun getLocker(@Path("id") id: String): Observable<LockerResponse>
//
//    @POST("lockers/")
//    fun createLocker(@Field("secretKey") secretKey: String,
//                     @Field("lockerID") lockerID: String): Observable<LockerResponse>
//
//    @PUT("lockers/{id}")
//    fun updateLocker(@Path("id") id: String,
//                     @FieldMap fields: Map<String, String>): Observable<GenericResponse>
//
//    //Credit card
//    @POST("users/{id}/cards/")
//    fun createCreditCard(@Path("id") id: String,
//                         @Field("number") number: Int,
//                         @Field("month") month: Int,
//                         @Field("year") year: Int,
//                         @Field("cvv") cvv: Int): Observable<GenericResponse>
//
//    @DELETE("users/{id}/cards/{card_id}")
//    fun deleteCreditCard(@Path("id") id: String,
//                         @Path("card_id") card_id: String): Observable<GenericResponse>
//
//    //Transactions
//    @GET("users/{id}/transactions")
//    fun getTransactions(@Path("id") id: String): Observable<TransactionsResponse>
//
//    //Booking
//    @POST("users/{id}/booking")
//    fun bookingWashService(@Path("id") id: String,
//                           @Body bookingRequest: BookingRequest): Observable<BookingResponse>
}