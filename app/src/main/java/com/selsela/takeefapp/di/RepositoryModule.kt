package com.selsela.takeefapp.di

import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.auth.source.remote.AuthApi
import com.selsela.takeefapp.data.config.repository.ConfigurationsRepository
import com.selsela.takeefapp.data.config.source.remote.ConfigApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providerConfigurationsRepository(
        apiService: ConfigApi
    ): ConfigurationsRepository {
        return ConfigurationsRepository(apiService)
    }

    /**
     * Auth Repository
     */
    @Provides
    @Singleton
    fun providerAuthRepository(
        apiService: AuthApi
    ): AuthRepository {
        return AuthRepository(apiService)
    }
}
