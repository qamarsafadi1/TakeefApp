package com.selsela.takeefapp.data.config.source.remote

import com.selsela.takeefapp.data.config.model.ConfigResponse
import com.selsela.takeefapp.data.config.model.city.CitiesResponse
import com.selsela.takeefapp.data.config.model.page.PageResponse
import com.selsela.takeefapp.data.config.model.payments.PaymentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigApi {
    @GET("app/get_configuration")
    suspend fun getConfigurations(): Response<ConfigResponse>
    @GET("app/page/3")
    suspend fun getAboutApp(): Response<PageResponse>
    @GET("app/page/1")
    suspend fun getTerms(): Response<PageResponse>
    @GET("app/get_cities")
    suspend fun getCities(
        @Query("country_id") country_id: Int = 1
    ): Response<CitiesResponse>
    @GET("app/get_payments")
    suspend fun getPayments(): Response<PaymentResponse>

}