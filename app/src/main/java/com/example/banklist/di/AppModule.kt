package com.example.banklist.di

import com.example.banklist.network.ApiServiceRepository
import com.example.banklist.network.ApiServiceRepositoryImpl
import com.example.banklist.utils.dispatcher_provider.DispatcherProvider
import com.example.banklist.utils.dispatcher_provider.DispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    fun bindDispatcherProvider(impl: DispatcherProviderImpl?): DispatcherProvider?

    @Singleton
    @Binds
    fun bindApiRepository(impl: ApiServiceRepositoryImpl?): ApiServiceRepository?
}