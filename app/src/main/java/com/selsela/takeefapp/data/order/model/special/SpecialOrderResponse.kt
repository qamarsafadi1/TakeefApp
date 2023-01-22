package com.selsela.takeefapp.data.order.model.special


import com.google.gson.annotations.SerializedName

data class SpecialOrderResponse(
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("specific_order")
    val specificOrder: SpecificOrder = SpecificOrder(),
    @SerializedName("status")
    val status: Boolean = false
)