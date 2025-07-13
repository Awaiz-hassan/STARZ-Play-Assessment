package com.starz.play.coding.data.di

import com.starz.play.coding.data.contract.LocalCache
import com.starz.play.coding.data.dataSource.local.dataStore.LocalCacheImpl
import com.starz.play.coding.data.repository.MediaRepositoryImpl
import com.starz.play.coding.domain.repository.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMediaRepository(
        impl: MediaRepositoryImpl
    ): MediaRepository

    @Binds
    @Singleton
    abstract fun bindLocalCache(
        impl: LocalCacheImpl
    ): LocalCache
}