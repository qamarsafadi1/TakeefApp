package com.selsela.takeefapp.data.auth.source.remote

import com.selsela.takeefapp.data.auth.model.AuthResponse
import com.selsela.takeefapp.data.config.model.ConfigResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("user/auth_mobile_only")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun auth(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

}