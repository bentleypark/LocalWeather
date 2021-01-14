package com.bentley.localweather.data.util

import com.bentley.localweather.utils.NetworkCheck
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AuthInterceptor constructor(private val networkCheck: NetworkCheck) : Interceptor {

    init {
        networkCheck.registerNetworkCallback()
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("UserBody-Agent", "android")
        requestBuilder.addHeader("Content-Type", "application/json")

        Timber.d("Network Status: ${networkCheck.isConnected()}")
//        if (networkCheck.isConnected()) {
//            requestBuilder.header("Cache-Control", "public, max-age=" + 60).build()
//        } else {
//            requestBuilder.header(
//                "Cache-Control",
//                "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
//            ).build()
//        }

        return chain.proceed(requestBuilder.build())
    }
}
