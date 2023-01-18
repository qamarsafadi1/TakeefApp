package com.selsela.takeefapp.data.config.source.remote

import com.selsela.takeefapp.data.config.model.ConfigResponse
import retrofit2.Response
import retrofit2.http.GET

interface ConfigApi {
    @GET("app/get_configuration")
    suspend fun getConfigurations(): Response<ConfigResponse>

}