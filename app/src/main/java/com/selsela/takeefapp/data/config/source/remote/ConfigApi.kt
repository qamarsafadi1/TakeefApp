package com.selsela.takeefapp.data.config.source.remote

import com.selsela.takeefapp.data.config.model.ConfigResponse
import com.selsela.takeefapp.data.config.model.page.PageResponse
import retrofit2.Response
import retrofit2.http.GET

interface ConfigApi {
    @GET("app/get_configuration")
    suspend fun getConfigurations(): Response<ConfigResponse>

    @GET("app/page/3")
    suspend fun getAboutApp(): Response<PageResponse>

    @GET("app/page/1")
    suspend fun getTerms(): Response<PageResponse>

}