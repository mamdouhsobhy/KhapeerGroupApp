package com.example.khapeergroup.home.presentation.di

import com.example.khapeergroup.core.data.module.NetworkModule
import com.example.khapeergroup.home.data.datasource.HomeService
import com.example.khapeergroup.home.data.repositoryImp.HomeRepositoryImpl
import com.example.khapeergroup.home.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeService {
        return retrofit.create(HomeService::class.java)
    }

    @Singleton
    @Provides
    fun provideHomeRepository(homeService: HomeService): HomeRepository {
        return HomeRepositoryImpl(homeService)
    }

}