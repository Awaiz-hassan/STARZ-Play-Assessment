package com.starz.play.coding.data.di

import android.content.Context
import com.starz.play.coding.data.BuildConfig
import com.starz.play.coding.data.dataSource.remote.apiService.ApiService
import com.starz.play.coding.data.dataSource.remote.interceptor.AuthInterceptor
import com.starz.play.coding.data.dataSource.remote.interceptor.CacheControlInterceptor
import com.starz.play.coding.data.dataSource.remote.interceptor.OfflineCacheInterceptor
import com.starz.play.coding.data.util.ApiConstants.BASE_URL
import com.starz.play.coding.data.util.ApiConstants.CACHE_SIZE_BYTES
import com.starz.play.coding.data.util.ApiConstants.TIMEOUT_SECONDS
import kotlin.jvm.java
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


private const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named(LOGGING_INTERCEPTOR)
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }


    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, CACHE_SIZE_BYTES)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        cache: Cache,
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(OfflineCacheInterceptor(context = context, hours = 1))
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(CacheControlInterceptor(hours = 1))
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
