package com.galodb.hamp.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.galodb.hamp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by galo1100 on 29/6/17.
 */
class RestApi {

    private val hampApi: HampApi
    var retrofitHamp: Retrofit? = null

    init {
        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(StethoInterceptor())
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain?.request()
            val request = original?.newBuilder()
                    ?.method(original.method(), original.body())
                    ?.build()

            chain.proceed(request)
        }


        retrofitHamp = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        hampApi = retrofitHamp!!.create(HampApi::class.java)
    }
}