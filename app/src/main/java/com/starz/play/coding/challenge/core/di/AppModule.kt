package com.starz.play.coding.challenge.core.di

import android.content.Context
import coil.ImageLoader
import coil.request.CachePolicy
import com.starz.play.coding.data.dataSource.remote.interceptor.CacheControlInterceptor
import com.starz.play.coding.data.dataSource.remote.interceptor.OfflineCacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
    ): ImageLoader {
        val cacheDir = File(context.cacheDir, "http_image_cache").apply { mkdirs() }
        val cache = Cache(cacheDir, 20L * 1024 * 1024)

        val client = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(OfflineCacheInterceptor(context,6))
            .addNetworkInterceptor(CacheControlInterceptor(6))
            .build()

        return ImageLoader.Builder(context)
            .okHttpClient(client)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build()
    }
}
