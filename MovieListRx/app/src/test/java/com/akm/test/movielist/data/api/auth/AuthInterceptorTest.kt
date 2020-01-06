package com.akm.test.movielist.data.api.auth

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(Request::class, Response::class, HttpUrl::class, HttpUrl.Builder::class)
class AuthInterceptorTest {

    @Test
    fun shouldAddApiKey() {
        val apiKey = "xyz123"

        val chain = Mockito.mock(Interceptor.Chain::class.java)
        val request = PowerMockito.mock(Request::class.java)

        val url = PowerMockito.mock(HttpUrl::class.java)
        val authUrl = PowerMockito.mock(HttpUrl::class.java)
        val urlBuilder = PowerMockito.mock(HttpUrl.Builder::class.java)

        val requestBuilder = Mockito.mock(Request.Builder::class.java)
        val authRequest = PowerMockito.mock(Request::class.java)
        val response = PowerMockito.mock(Response::class.java)

        Mockito.`when`(chain.request()).thenReturn(request)

        Mockito.`when`(request.url()).thenReturn(url)
        Mockito.`when`(url.newBuilder()).thenReturn(urlBuilder)
        Mockito.`when`(urlBuilder.addQueryParameter("api_key", apiKey)).thenReturn(urlBuilder)
        Mockito.`when`(urlBuilder.build()).thenReturn(authUrl)

        Mockito.`when`(request.newBuilder()).thenReturn(requestBuilder)
        Mockito.`when`(requestBuilder.url(authUrl)).thenReturn(requestBuilder)
        Mockito.`when`(requestBuilder.build()).thenReturn(authRequest)

        Mockito.`when`(chain.proceed(authRequest)).thenReturn(response)

        val underTest = AuthInterceptor(apiKey)
        Assert.assertEquals(response, underTest.intercept(chain))
    }
}