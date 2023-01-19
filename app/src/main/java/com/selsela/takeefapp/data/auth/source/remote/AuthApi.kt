package com.selsela.takeefapp.data.auth.source.remote

import com.selsela.takeefapp.data.auth.model.auth.AuthResponse
import com.selsela.takeefapp.data.auth.model.general.GeneralResponse
import com.selsela.takeefapp.utils.LocalData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface AuthApi {
    @POST("user/auth_mobile_only")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun auth(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

    @POST("user/verify_code")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun verifyCode(
        @FieldMap
        body: Map<String, Any>
    ): Response<AuthResponse>

    @GET("user/me")
    suspend fun me(): Response<AuthResponse>

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

    @POST("user/update_profile")
    @JvmSuppressWildcards
    @Multipart
    suspend fun updateProfile(
        @Part
        avatar: MultipartBody.Part?,
        @PartMap
        body: HashMap<String, RequestBody>
    ): Response<AuthResponse>

}