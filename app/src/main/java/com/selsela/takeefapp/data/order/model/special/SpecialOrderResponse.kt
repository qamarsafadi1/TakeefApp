package com.selsela.takeefapp.data.order.model.special


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpecialOrderResponse(
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("specific_order")
    val specificOrder: SpecificOrder = SpecificOrder(),
    @SerializedName("specific_orders")
    val specificOrders: List<SpecificOrder> = listOf(),
    @SerializedName("status")
    val status: Boolean = false
)