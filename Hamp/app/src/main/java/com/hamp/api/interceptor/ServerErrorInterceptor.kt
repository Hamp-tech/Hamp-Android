package com.hamp.api.interceptor

import com.hamp.api.exception.ServerException
import com.hamp.domain.response.GenericResponse
import com.hamp.extensions.fromJsonObject
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

class ServerErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {

        val request = chain!!.request()
        val response = chain.proceed(request)

        val contentType = response.body()?.contentType()
        val bodyString = response.body()?.string()

        if (!response.isSuccessful) {
            if (bodyString != null) {
                val errorTO: GenericResponse?
                try {
                    errorTO = bodyString.fromJsonObject<GenericResponse>()
                } catch (e: Exception) {
                    throw ServerException(bodyString)
                }

                throw ServerException(errorTO.message)
            } else {
                throw ServerException(response.message())
            }
        }

        // Re-create the response before returning it because body can be read only once
        return response.newBuilder()
                .protocol(response.protocol())
                .message(response.message())
                .code(response.code())
                .body(ResponseBody.create(contentType, bodyString))
                .build()
    }
}