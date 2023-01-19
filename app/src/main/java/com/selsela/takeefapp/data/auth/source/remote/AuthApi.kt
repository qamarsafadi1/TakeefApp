package com.selsela.takeefapp.data.auth.source.remote

import com.selsela.takeefapp.data.auth.model.auth.AuthResponse
import com.selsela.takeefapp.data.auth.model.general.GeneralResponse
import com.selsela.takeefapp.utils.LocalData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    @POST("user/auth_mobile_only")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun auth(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

    @POST("user/update_user_fcm_token")
    @FormUrlEncoded
    suspend fun updateUserFCM(
        @Field("token") token: String? = LocalData.fcmToken
    ): Response<GeneralResponse>

    @POST("user/update_fcm_token")
    @FormUrlEncoded
    suspend fun updateFCM(
        @Field("token") token: String? = LocalData.fcmToken
    ): Response<GeneralResponse>

}