package com.selsela.takeefapp.data.auth.model.wallet


import com.google.gson.annotations.SerializedName

data class WalletResponse(
    @SerializedName("balance")
    val balance: Double = 0.0,
    @SerializedName("response_message")
    val responseMessage: String = "",
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("wallet")
    val wallet: List<Wallet> = listOf()
)