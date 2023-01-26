package com.selsela.takeefapp.di

import com.selsela.takeefapp.data.address.GooglePlacesRepository
import com.selsela.takeefapp.data.auth.repository.AuthRepository
import com.selsela.takeefapp.data.auth.source.remote.AuthApi
import com.selsela.takeefapp.data.config.repository.ConfigurationsRepository
import com.selsela.takeefapp.data.config.source.remote.ConfigApi
import com.selsela.takeefapp.data.order.model.order.Order
import com.selsela.takeefapp.data.order.remote.OrderApi
import com.selsela.takeefapp.data.order.remote.SpecialOrderApi
import com.selsela.takeefapp.data.order.repository.OrderRepository
import com.selsela.takeefapp.data.order.repository.SpecialOrderRepository
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

    @Provides
    @Singleton
    fun providerGoogleRepository(
    ): GooglePlacesRepository {
        return GooglePlacesRepository()
    }

    /**
     * Order Repository
     */
    @Provides
    @Singleton
    fun providerSpecialOrderRepository(
        apiService: SpecialOrderApi
    ): SpecialOrderRepository {
        return SpecialOrderRepository(apiService)
    }

    @Provides
    @Singleton
    fun providerOrderRepository(
        apiService: OrderApi
    ): OrderRepository {
        return OrderRepository(apiService)
    }
}
