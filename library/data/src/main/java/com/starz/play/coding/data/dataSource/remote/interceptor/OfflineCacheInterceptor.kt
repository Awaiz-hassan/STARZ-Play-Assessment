package com.starz.play.coding.data.dataSource.remote.interceptor


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor(
    private val context: Context,
    private val hours: Int
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!isNetworkAvailable()) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(hours, TimeUnit.HOURS)
                .build()

            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
