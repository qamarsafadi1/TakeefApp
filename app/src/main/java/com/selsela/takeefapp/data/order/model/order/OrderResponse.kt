package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("has_more_page")
    val hasMorePage: Boolean = false,
    @SerializedName("orders")
    val orders: List<Order> = listOf(),
    @SerializedName("order")
    val order: Order = Order(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("trans")
    val transaction: Transaction? = null
)