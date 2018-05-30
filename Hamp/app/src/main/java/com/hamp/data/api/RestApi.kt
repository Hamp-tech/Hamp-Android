package com.hamp.data.api

import com.hamp.BuildConfig
import com.hamp.data.api.interceptor.ServerErrorInterceptor
import com.hamp.data.db.domain.User
import com.hamp.domain.Card
import com.hamp.domain.Transaction
import com.hamp.domain.request.SignInRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class RestApi {

    private val OS = "Android"

    private val hampApi: HampApi
    private val retrofitHamp: Retrofit
//    private val gangway = "gangway"

    init {
        val httpClient = createHttpClient()
        val logging = createLoggingInterceptor()
        val serverError = createServerInterceptor()

        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(serverError)

        retrofitHamp = createRetrofit(httpClient)

        hampApi = retrofitHamp.create(HampApi::class.java)
    }

    private fun createHttpClient(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url()

                    val url = originalHttpUrl.newBuilder()
//                            .addQueryParameter(gangway, BuildConfig.GANGWAY)
                            .build()

                    val requestBuilder = original.newBuilder().url(url)
                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun createServerInterceptor() = ServerErrorInterceptor()

    private fun createRetrofit(httpClient: OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }

    ///////////// HAMP ////////////////

//    fun convertError(responseBody: ResponseBody): GenericResponse {
//        val converter: Converter<ResponseBody, GenericResponse> =
//                retrofitHamp.responseBodyConverter(GenericResponse::class.java,
//                        arrayOfNulls<Annotation>(0))
//        return converter.convert(responseBody)
//    }

    fun signIn(signInRequest: SignInRequest) = hampApi.signIn(signInRequest)

    fun signUp(user: User) = hampApi.signUp(user.apply {
        language = getLanguageTag()
        os = OS
    })

    fun updateUser(user: User, userId: String) = hampApi.updateUser(user, userId)

    fun addCard(card: Card, userId: String) = hampApi.createCard(card, userId)

    fun createTransaction(transaction: Transaction, userId: String) = hampApi.createTransaction(transaction, userId)

//    fun updateUser(userID: String, fields: Map<String, String>) = hampApi.updateUser(userID, fields)
//
//    fun unSubscribe(userID: String) = hampApi.unSubscribe(userID)
//
//    fun subscribe(userID: String) = hampApi.subscribe(userID)
//
//    fun getLocker(lockerID: String) = hampApi.getLocker(lockerID)
//
//    fun createLocker(secretKey: String, lockerID: String)
//            : Observable<LockerResponse> = hampApi.createLocker(secretKey, lockerID)
//
//    fun updateLocker(lockerID: String, fields: Map<String, String>)
//            : Observable<GenericResponse> = hampApi.updateLocker(lockerID, fields)
//
//    fun createCreditCard(userID: String, number: Int, month: Int,
//                         year: Int, cvv: Int)
//            : Observable<GenericResponse> = hampApi.createCreditCard(userID, number, month, year, cvv)
//
//    fun deleteCreditCard(userID: String, cardID: String) = hampApi.deleteCreditCard(userID, cardID)
//
//    fun getTransactions(userID: String) = hampApi.getTransactions(userID)
//
//    fun bookingWashService(userID: String, bookingRequest: BookingRequest)
//            : Observable<BookingResponse> = hampApi.bookingWashService(userID, bookingRequest)

    private fun getLanguageTag() = Locale.getDefault().language + Locale.getDefault().country

}