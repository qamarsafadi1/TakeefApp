package com.selsela.takeefapp.data.auth.model.wallet


import com.google.gson.annotations.SerializedName

data class Wallet(
    @SerializedName("action")
    val action: String = "",
    @SerializedName("action_time")
    val actionTime: String = "",
    @SerializedName("amount")
    val amount: Double = 0.0,
    @SerializedName("created_at")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("order_number")
    val reference: String = "",
    @SerializedName("type")
    val type: String = ""
)