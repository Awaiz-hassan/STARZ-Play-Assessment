package com.starz.play.coding.data.dataSource.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit


class CacheControlInterceptor(private val hours: Long) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return originalResponse.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header(
                "Cache-Control",
                "public, max-age=${TimeUnit.HOURS.toSeconds(hours)}"
            )
            .build()
    }
}
