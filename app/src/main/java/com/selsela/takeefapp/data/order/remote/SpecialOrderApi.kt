package com.selsela.takeefapp.data.order.remote

import com.selsela.takeefapp.data.order.model.special.SpecialOrderResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface SpecialOrderApi {

    @POST("user/specific_order/place_specific_order")
    @JvmSuppressWildcards
    @Multipart
    suspend fun placeSpecialOrder(
        @Part
        uploadedImages: List<MultipartBody.Part?>,
        @PartMap
        body: HashMap<String, RequestBody>
    ): Response<SpecialOrderResponse>

    @GET("user/specific_order/user_specific_orders")
    suspend fun getSpecialOrders(): Response<SpecialOrderResponse>
}