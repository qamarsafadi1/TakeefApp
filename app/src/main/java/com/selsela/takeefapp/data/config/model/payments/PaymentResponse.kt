package com.selsela.takeefapp.data.config.model.payments


import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("payments")
    val payments: List<Payment> = listOf(),
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false
)