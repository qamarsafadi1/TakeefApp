package com.selsela.takeefapp.data.order.model.order


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Transaction(
    @SerializedName("amount")
    val amount: Double = 0.0,
    @SerializedName("amount_paid_from_wallet")
    val amountPaidFromWallet: Double = 0.0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("paid_additional_from_wallet")
    val paidAdditionalFromWallet: Double = 0.0,
    @SerializedName("payment_type")
    val paymentType: Int = 0,
    @SerializedName("transaction_id")
    val transactionId: String = ""
)