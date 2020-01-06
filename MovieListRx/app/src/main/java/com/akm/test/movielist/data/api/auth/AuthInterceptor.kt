package com.akm.test.movielist.data.api.auth

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {
    private companion object {
        const val API_KEY_PARAM_NAME = "api_key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authUrl = addApiKey(request)
        val authRequest = request.newBuilder().url(authUrl).build()

        return chain.proceed(authRequest)
    }

    private fun addApiKey(request: Request): HttpUrl {
        val url = request.url()

        return url.newBuilder().addQueryParameter(API_KEY_PARAM_NAME, apiKey).build()
    }
}