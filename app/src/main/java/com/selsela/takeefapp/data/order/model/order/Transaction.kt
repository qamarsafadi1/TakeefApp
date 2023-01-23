package com.selsela.takeefapp.data.order.model.order


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("amount")
    val amount: Int = 0,
    @SerializedName("amount_paid_from_wallet")
    val amountPaidFromWallet: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("paid_additional_from_wallet")
    val paidAdditionalFromWallet: Int = 0,
    @SerializedName("payment_type")
    val paymentType: Int = 0,
    @SerializedName("transaction_id")
    val transactionId: String = ""
)