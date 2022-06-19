package com.sample.superherosquad.model.remote_source.api

import com.sample.superherosquad.BuildConfig.PUBLIC_API_KEY
import com.sample.superherosquad.util.Utils
import okhttp3.Interceptor
import okhttp3.Response
import java.sql.Timestamp

/**
 * Interceptor for adding headers to API calls.
 **/
class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var originalRequest = chain.request()
        val timeStamp = Timestamp(System.currentTimeMillis())
        val url = originalRequest.url
            .newBuilder()
            .addQueryParameter("ts",timeStamp.toString())
            .addQueryParameter("apikey", PUBLIC_API_KEY)
            .addQueryParameter("hash", Utils.getAPIMd5Hash(timestamp = timeStamp.toString()))
            .build()
        originalRequest = originalRequest.newBuilder().url(url).build()
        return chain.proceed(originalRequest)
    }
}