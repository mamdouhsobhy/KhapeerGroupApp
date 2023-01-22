package com.example.khapeergroup.auth.presentation.di

import com.example.khapeergroup.auth.data.datasource.LoginService
import com.example.khapeergroup.auth.data.repositoryimb.LoginRepositoryImpl
import com.example.khapeergroup.auth.domain.repository.LoginRepository
import com.example.khapeergroup.core.data.module.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun provideLoginApi(retrofit: Retrofit) : LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepository(loginService: LoginService) : LoginRepository {
        return LoginRepositoryImpl(loginService)
    }


}