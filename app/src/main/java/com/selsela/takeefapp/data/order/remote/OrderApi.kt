package com.selsela.takeefapp.data.order.remote

import com.selsela.takeefapp.data.auth.model.auth.AuthResponse
import com.selsela.takeefapp.data.order.model.order.OrderResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApi {
    @GET("user/order/user_orders")
    suspend fun getOrder(
        @Query("page") page: Int = 1,
        @Query("case_id") caseId: Int = 1
    ): Response<OrderResponse>

    @GET("user/order/order_details")
    suspend fun getOrderDetails(
        @Query("order_id") orderId: Int
    ): Response<OrderResponse>

    @FormUrlEncoded
    @POST("user/order/cancel_order")
    suspend fun cancelOrder(
        @Field("order_id") orderId: Int
    ): Response<OrderResponse>

    @POST("user/order/place_order")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun placeOrder(
        @FieldMap
        body: Map<String, Any>
    ): Response<OrderResponse>
    @POST("user/order/rate_order")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun rateOrder(
        @FieldMap
        body: Map<String, Any>
    ): Response<OrderResponse>
    @POST("user/order/change_additional_cost_status")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun changeAdditionalCostStatus(
        @FieldMap
        body: Map<String, Any>
    ): Response<OrderResponse>
    @POST("app/confirm_payment")
    @JvmSuppressWildcards
    @FormUrlEncoded
    suspend fun confirmPayment(
        @FieldMap
        body: Map<String, Any>
    ): Response<OrderResponse>


}