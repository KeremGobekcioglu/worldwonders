package com.kg.worldwonders.data

import com.kg.worldwonders.common.constants.ApiConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class WindyApiKeyInterceptor @Inject constructor() : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("x-windy-api-key", ApiConstants.WINDY_API_KEY)
            .build()
        return chain.proceed(newRequest)
    }
}