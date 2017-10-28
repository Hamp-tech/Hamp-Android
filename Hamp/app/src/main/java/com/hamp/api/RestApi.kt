package com.hamp.api

import com.hamp.BuildConfig
import com.hamp.domain.response.BookingResponse
import com.hamp.domain.response.GenericResponse
import com.hamp.domain.response.LockerResponse
import com.hamp.domain.response.UserResponse
import com.hamp.hamp.domain.response.BookingRequest
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RestApi {

    private val hampApi: HampApi
    private val retrofitHamp: Retrofit

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()

            original.url().newBuilder().addQueryParameter("gangway", "7B3nPECsrty0vuZi7J74kSMVmHKljxK")

            val request = original?.newBuilder()
                    ?.method(original.method(), original.body())
                    ?.build()

            chain.proceed(request)
        }

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        retrofitHamp = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        hampApi = retrofitHamp.create(HampApi::class.java)
    }

    ///////////// HAMP ////////////////

    fun convertError(responseBody: ResponseBody): GenericResponse {
        val converter: Converter<ResponseBody, GenericResponse> = retrofitHamp.responseBodyConverter(GenericResponse::class.java, arrayOfNulls<Annotation>(0))
        return converter.convert(responseBody)
    }

    fun getUser(userID: String) = hampApi.getUser(userID)

    fun createUserWithID(userID: String, name: String, surname: String,
                         email: String, phone: String, birthday: String, gender: String,
                         tokenFCM: String): Observable<UserResponse> {
        return hampApi.createUserWithID(userID, name, surname, email, phone, birthday, gender,
                tokenFCM, getLanguageTag(), "Android")
    }

    fun updateUser(userID: String, fields: Map<String, String>) = hampApi.updateUser(userID, fields)

    fun unsubscribe(userID: String) = hampApi.unsubscribe(userID)

    fun subscribe(userID: String) = hampApi.subscribe(userID)

    fun getLocker(lockerID: String) = hampApi.getLocker(lockerID)

    fun createLocker(secretKey: String, lockerID: String)
            : Observable<LockerResponse> = hampApi.createLocker(secretKey, lockerID)

    fun updateLocker(lockerID: String, fields: Map<String, String>)
            : Observable<GenericResponse> = hampApi.updateLocker(lockerID, fields)

    fun createCreditCard(userID: String, number: Int, month: Int,
                         year: Int, cvv: Int)
            : Observable<GenericResponse> = hampApi.createCreditCard(userID, number, month, year, cvv)

    fun deleteCreditCard(userID: String, cardID: String) = hampApi.deleteCreditCard(userID, cardID)

    fun getTransactions(userID: String) = hampApi.getTransactions(userID)

    fun bookingWashService(userID: String, bookingRequest: BookingRequest)
            : Observable<BookingResponse> = hampApi.bookingWashService(userID, bookingRequest)

    private fun getLanguageTag() = Locale.getDefault().language + Locale.getDefault().country
}