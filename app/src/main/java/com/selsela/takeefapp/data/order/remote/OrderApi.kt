package com.selsela.takeefapp.data.order.remote

import com.selsela.takeefapp.data.order.model.order.OrderResponse
import retrofit2.Response
import retrofit2.http.GET
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
}