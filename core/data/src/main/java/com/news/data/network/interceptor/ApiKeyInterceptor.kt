package com.news.data.network.interceptor

import com.news.core.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


internal class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Add API key as a query parameter
        val modifiedUrl = originalUrl.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }
}
