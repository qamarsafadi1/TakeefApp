package com.selsela.takeefapp.di

import com.selsela.takeefapp.data.address.RetrofitClientGoogle
import com.selsela.takeefapp.data.auth.source.remote.AuthApi
import com.selsela.takeefapp.data.config.source.remote.ConfigApi
import com.selsela.takeefapp.data.order.remote.OrderApi
import com.selsela.takeefapp.data.order.remote.SpecialOrderApi
import com.selsela.takeefapp.utils.retrofit.HeaderInterceptor
import com.selsela.takeefapp.utils.retrofit.RetrofitBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {


    @Provides
    @Singleton
    fun provideRetrofit(
        retrofitBuilder: RetrofitBuilder,
        headerInterceptor: HeaderInterceptor
    ): Retrofit =
        retrofitBuilder
            .addInterceptors(headerInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideConfigApi(retrofit: Retrofit): ConfigApi = retrofit.create(ConfigApi::class.java)


    /**
     * Auth API
     */
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)


    /**
     * Order API
     */

    @Provides
    @Singleton
    fun provideSpecialOrderApi(retrofit: Retrofit): SpecialOrderApi =
        retrofit.create(SpecialOrderApi::class.java)

    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApi =
        retrofit.create(OrderApi::class.java)


}

